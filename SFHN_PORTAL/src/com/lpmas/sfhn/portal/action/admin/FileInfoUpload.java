package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;

@WebServlet("/sfhn/admin/FileInfoUpload.do")
public class FileInfoUpload extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(FileInfoUpload.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileInfoUpload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		List<String> resultList = new ArrayList<String>();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			returnMessage.setMessage("你没有该功能的操作权限");
			HttpResponseKit.printJson(request, response, returnMessage, "");
			return;
		}

		int fileId = ParamKit.getIntParameter(request, "fileId", 0);

		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			String filePath = String.valueOf(orgUserBean.getInfoType() + "/" + orgUserBean.getOrganizationId());
			String uploadPath = FileInfoConfig.ADMIN_FILE_PATH + filePath;
			fileUploadKit.setAllowedFileType(FileInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
			fileUploadKit.setMaxSize(FileInfoConfig.MAX_SIZE);
			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			// 获取上传结果
			if (resultBean.getResult()) {
				List<FileUploadItem> list = resultBean.getFileItemList();
				for (FileUploadItem item : list) {
					if (item.getResult()) {
						FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
						FileInfoBean bean = new FileInfoBean();
						if (fileId > 0) {
							// 更新文件
							bean = fileInfoBusiness.getFileInfoByKey(fileId);
							String deletePath = bean.getFilePath();
							String deleteName = bean.getFileName();
							String deleteFormat = bean.getFileFormat();
							bean.setFileName(item.getFileName().substring(0, item.getFileName().lastIndexOf(".")));
							bean.setFileFormat(item.getExtensionFileName());
							bean.setFilePath(filePath + Constants.FILE_SEPARATOR + fileName + "."
									+ item.getExtensionFileName());
							bean.setModifyUser(userId);
							int result = fileInfoBusiness.updateFileInfo(bean);
							//删除原文件
							if(result > 0){
								bean.setFilePath(deletePath);
								bean.setFileName(deleteName);
								bean.setFileFormat(deleteFormat);
								fileInfoBusiness.deleteFile(bean);
							}
						} else {
							int fileType = ParamKit.getIntParameter(request, "fileType", 0);
							int infoType = ParamKit.getIntParameter(request, "infoType", 0);
							int infoId1 = ParamKit.getIntParameter(request, "infoId1", 0);
							bean.setFileType(fileType);
							bean.setInfoType(infoType);
							bean.setInfoId1(infoId1);
							if (fileType > 0 && infoType > 0 && infoId1 > 0) {
								//当存在此参数，表名是有了实体后再上传文件，则文件直接为有效的文件
								bean.setStatus(Constants.STATUS_VALID);
							}
							bean.setFileName(item.getFileName().substring(0, item.getFileName().lastIndexOf(".")));
							bean.setFileFormat(item.getExtensionFileName());
							bean.setFilePath(filePath + Constants.FILE_SEPARATOR + fileName + "."
									+ item.getExtensionFileName());
							bean.setCreateUser(userId);
							fileInfoBusiness.addFileInfo(bean);
						}
						resultList.add(filePath + Constants.FILE_SEPARATOR + fileName + "."
								+ item.getExtensionFileName());
					} else {
						returnMessage.setCode(Constants.STATUS_NOT_VALID);
						returnMessage.setMessage(item.getResultContent());
						HttpResponseKit.printJson(request, response, returnMessage, "");
						return;
					}
				}
				returnMessage.setCode(Constants.STATUS_VALID);
				returnMessage.setMessage(JsonKit.toJson(resultList));
				HttpResponseKit.printJson(request, response, returnMessage, "");
				return;
			} else {
				returnMessage.setCode(Constants.STATUS_NOT_VALID);
				returnMessage.setMessage(resultBean.getResultContent());
				HttpResponseKit.printJson(request, response, returnMessage, "");
				return;
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}
}