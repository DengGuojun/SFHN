package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;

@WebServlet("/sfhn/admin/TrainingClassUserDetailImport.do")
public class TrainingClassUserDetailImport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassUserDetailImport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserDetailImport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取classId
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
		List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
				.getTrainingClassUserListByClassId(classId);
		if (trainingClassUserList.isEmpty() || trainingClassUserList.size() == 0) {
			HttpResponseKit.alertMessage(response, "该班级学员为空，请先导入学员", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TrainingClassInfoBean trainingClassInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (trainingClassInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		FileInfoBean fileInfoBean = null;
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		boolean fileResult = true;
		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			String filePath = String
					.valueOf(orgUserBean.getInfoType() + Constants.FILE_SEPARATOR + orgUserBean.getOrganizationId());
			String uploadPath = FileInfoConfig.ADMIN_FILE_PATH + filePath;
			fileUploadKit.setAllowedFileType(FileInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
			fileUploadKit.setMaxSize(FileInfoConfig.MAX_SIZE);
			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			// 获取上传结果
			if (resultBean.getResult()) {
				List<FileUploadItem> list = resultBean.getFileItemList();
				for (FileUploadItem item : list) {
					if (item.getResult()) {
						// 构建实体Bean，保存数据信息在数据库
						fileInfoBean = new FileInfoBean();
						fileInfoBean.setFilePath(
								filePath + Constants.FILE_SEPARATOR + fileName + "." + item.getExtensionFileName());
						fileInfoBean.setStatus(Constants.STATUS_VALID);
						fileInfoBean.setFileName(item.getFileName().substring(0, item.getFileName().lastIndexOf(".")));
						fileInfoBean.setFileFormat(item.getExtensionFileName());
						fileInfoBean.setFileType(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SHEET);
						fileInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
						fileInfoBean.setInfoId1(classId);
						// 判断文件名是否为所在班级的学员的身份证号
						boolean identityFlag = true;
						for (TrainingClassUserBean trainingClassUserBean : trainingClassUserList) {
							FarmerInfoBean farmerInfoBean = farmerInfoBusiness
									.getFarmerInfoByKey(trainingClassUserBean.getDeclareId());
							if (farmerInfoBean != null) {
								if (farmerInfoBean.getIdentityNumber().equals(fileInfoBean.getFileName())) {
									if (!trainingClassUserBean.getUserStatus()
											.equals(TrainingClassUserConfig.USER_STATUS_APPROVE)) {
										fileInfoBusiness.deleteFile(fileInfoBean);
										HttpResponseKit.alertMessage(response, fileInfoBean.getFileName() + "学员未成功审核",
												HttpResponseKit.ACTION_HISTORY_BACK);
										return;
									}
									identityFlag = false;
								}
							}
						}
						if (identityFlag) {
							fileInfoBusiness.deleteFile(fileInfoBean);
							HttpResponseKit.alertMessage(response,
									fileInfoBean.getFileName() + "文件名输入有误(文件名为该班学员的身份证号)",
									HttpResponseKit.ACTION_HISTORY_BACK);
							return;
						}
						// 判断文件是否新建
						HashMap<String, String> condMap = new HashMap<String, String>();
						condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SHEET));
						condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
						condMap.put("infoId1", String.valueOf(classId));
						condMap.put("status", String.valueOf(Constants.STATUS_VALID));
						condMap.put("fileName", fileInfoBean.getFileName());
						List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
						int result = 0;
						if (!fileInfoList.isEmpty()) {
							// 修改文件信息
							fileInfoBean.setModifyUser(userId);
							fileInfoBean.setFileId(fileInfoList.get(0).getFileId());
							result = fileInfoBusiness.updateFileInfo(fileInfoBean);
							if (result > 0) {
								fileInfoBusiness.deleteFile(fileInfoList.get(0));
							}
						} else { // 新建文件信息
							fileInfoBean.setCreateUser(userId);
							result = fileInfoBusiness.addFileInfo(fileInfoBean);
						}
						if (result <= 0) {
							fileResult = false;
						}
					} else {
						fileResult = false;
						String temp = returnMessage.getMessage() + item.getResultContent();
						returnMessage.setMessage(temp);
					}
				}
				if (fileResult) {
					returnMessage.setCode(Constants.STATUS_VALID);
					returnMessage.setMessage("文件上传成功");
					HttpResponseKit.alertMessage(response, returnMessage.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				} else {
					String message = returnMessage.getMessage();
					returnMessage.setMessage("文件上传失败" + message);
					HttpResponseKit.alertMessage(response, returnMessage.getMessage(),
							HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
			} else {
				returnMessage.setMessage(resultBean.getResultContent());
				HttpResponseKit.alertMessage(response, returnMessage.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
	}
}
