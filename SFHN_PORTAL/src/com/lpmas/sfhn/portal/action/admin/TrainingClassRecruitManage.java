package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

@WebServlet("/sfhn/admin/TrainingClassRecruitManage.do")
public class TrainingClassRecruitManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassRecruitManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassRecruitManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();

		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		// 判是否为政府
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		GovernmentOrganizationInfoBusiness governmentOrganizationInfoBusiness = new GovernmentOrganizationInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrganizationInfoBean = governmentOrganizationInfoBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		// 判是否为县级
		if (governmentOrganizationInfoBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (governmentOrganizationInfoBean
				.getOrganizationLevel() != GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		request.setAttribute("GovernmentOrgInfoBean", governmentOrganizationInfoBean);
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		if (StringKit.isValid(governmentOrganizationInfoBean.getProvince())) {
			condMap.put("province", governmentOrganizationInfoBean.getProvince());
		}
		if (StringKit.isValid(governmentOrganizationInfoBean.getCity())) {
			condMap.put("city", governmentOrganizationInfoBean.getCity());
		}
		if (StringKit.isValid(governmentOrganizationInfoBean.getRegion())) {
			condMap.put("region", governmentOrganizationInfoBean.getRegion());
		}
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
				.getTrainingOrganizationInfoListByMap(condMap);
		request.setAttribute("TrainingOrganizationInfoList", trainingOrganizationInfoList);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", true);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassRecruitManage.jsp");

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
		GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		request.setAttribute("GovernmentOrgInfoBean", governmentOrgBean);
		request.setAttribute("isGovernment", true);
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean trainingClassInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (trainingClassInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (!trainingClassInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
			HttpResponseKit.alertMessage(response, "培训班未审核通过", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (trainingClassInfoBean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_START)
				|| trainingClassInfoBean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH)) {
			HttpResponseKit.alertMessage(response, "培训班状态不允许上传", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		FileInfoBean fileInfoBean = new FileInfoBean();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		int result = 0;
		List<String> message = new ArrayList<String>();
		List<String> userInfoList = new ArrayList<String>();
		StringBuilder returnResult = new StringBuilder();
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
						fileInfoBean.setFilePath(
								filePath + Constants.FILE_SEPARATOR + fileName + "." + item.getExtensionFileName());
						fileInfoBean.setStatus(Constants.STATUS_VALID);
						fileInfoBean.setFileName(item.getFileName().substring(0, item.getFileName().lastIndexOf(".")));
						fileInfoBean.setFileFormat(item.getExtensionFileName());
						fileInfoBean.setFileType(FileInfoConfig.FILE_TYPE_CANDIDATE_LIST);
						fileInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_RECRUITMENT);
						fileInfoBean.setInfoId1(classId);
						// 判断文件是否新建
						HashMap<String, String> condMap = new HashMap<String, String>();
						condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CANDIDATE_LIST));
						condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_RECRUITMENT));
						condMap.put("infoId1", String.valueOf(classId));
						condMap.put("status", String.valueOf(Constants.STATUS_VALID));
						List<FileInfoBean> FileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
						if (!FileInfoList.isEmpty()) {
							// 修改文件信息
							fileInfoBean.setModifyUser(userId);
							fileInfoBean.setFileId(FileInfoList.get(0).getFileId());
							result = fileInfoBusiness.updateFileInfo(fileInfoBean);
							if (result > 0) {
								fileInfoBusiness.deleteFile(FileInfoList.get(0));
							}
							// 把原消息设为已读
							HashMap<String, String> queryMap = new HashMap<String, String>();
							queryMap = new HashMap<String, String>();
							queryMap.put("messageType",
									String.valueOf(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_REQUEST));
							queryMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_RECRUITMENT));
							queryMap.put("infoId", String.valueOf(classId));
							queryMap.put("receiveOrganizationType",
									String.valueOf(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION));
							queryMap.put("receiveOrganizatoinId", String.valueOf(orgUserBean.getOrganizationId()));
							queryMap.put("status", String.valueOf(Constants.STATUS_VALID));
							List<MessageInfoBean> messageInfoList = messageInfoBusiness
									.getMessageInfoListByMap(queryMap);
							MessageInfoBean processedMessageInfoBean = messageInfoList.get(0);
							processedMessageInfoBean.setIsRead(Constants.STATUS_VALID);
							messageInfoBusiness.updateMessageInfo(processedMessageInfoBean);
						} else { // 新建文件信息
							fileInfoBean.setCreateUser(userId);
							result = fileInfoBusiness.addFileInfo(fileInfoBean);
						}
						if (result > 0) {
							returnMessage.setCode(Constants.STATUS_VALID);
							returnMessage.setMessage("文件上传成功");
							message.add(returnMessage.getMessage());
						} else {
							returnMessage.setMessage("文件上传失败");
							message.add(returnMessage.getMessage());
						}
					} else {
						returnMessage.setMessage(item.getResultContent());
						message.add(returnMessage.getMessage());
					}
				}
			} else {
				returnMessage.setMessage(resultBean.getResultContent());
				message.add(returnMessage.getMessage());
			}
			// 读取excel表
			ExcelReadKit excelReadKit = new ExcelReadKit();
			ExcelReadResultBean excelReadResultBean = excelReadKit
					.readExcel(FileInfoConfig.ADMIN_FILE_PATH + fileInfoBean.getFilePath(), 0);
			TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
			if (excelReadResultBean != null && excelReadResultBean.getResult()) {
				List<List<String>> contentList = excelReadResultBean.getContentList();
				int count = 0;
				int countFail = 0;
				for (List<String> content : contentList) {
					if (!content.get(0).isEmpty() && !content.get(0).equals("姓名")) {
						++count;
						// 检验身份证的输入
						if (content.get(1).isEmpty()) {
							message.add(content.get(0) + "," + content.get(1) + ",身份证输入有误");
							++countFail;
							continue;
						}
						// 检验年龄的输入
						if (content.get(2).isEmpty()) {
							message.add(content.get(0) + "," + content.get(1) + ",年龄输入有误");
							++countFail;
							continue;
						}
						// 检验是否超龄
						if (Integer.parseInt(content.get(2)) >= 55) {
							message.add(content.get(0) + "," + content.get(1) + ",年龄超龄");
							++countFail;
							continue;
						}
						// 记录名单
						userInfoList.add(content.get(0) + "," + content.get(1) + "," + " ," + " ," + " ,"
								+ content.get(2) + ",否");

					}
				}
				message.set(0, message.get(0) + ",导入学员" + String.valueOf(count) + "人" + "，不符合"
						+ String.valueOf(countFail) + "人");
				boolean flag = false;
				for (String string : message) {
					if (flag) {
						returnResult.append("!");
					} else {
						flag = true;
					}
					returnResult.append(string);
				}
				// 记录流程日志
				ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
				ProcessLogBean logBean = new ProcessLogBean();
				logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_LIST);
				logBean.setInfoType(InfoTypeConfig.INFO_TYPE_RECRUITMENT);
				logBean.setInfoId(classId);
				logBean.setOperatorId(orgUserBean.getOrganizationId());
				logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
				logBean.setCreateUser(userId);
				logBean.setStatus(Constants.STATUS_VALID);
				logBean.setProcessContent(returnResult.toString());
				processLogBusiness.addProcessLog(logBean);
				// 发送消息
				MessageInfoBean messageInfoBean = new MessageInfoBean();
				messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_RECRUITMENT);
				messageInfoBean.setInfoId(classId);
				messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_LIST);
				messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
				messageInfoBean.setSendOrganizationId(governmentOrgBean.getOrganizationId());
				messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
				messageInfoBean.setReceiveOrganizationId(trainingClassInfoBean.getOrganizationId());
				messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
				messageInfoBean.setStatus(Constants.STATUS_VALID);
				messageInfoBean.setCreateUser(userId);
				messageInfoBusiness.addMessageInfo(messageInfoBean);
				// 改变招生状态
				trainingClassInfoBean.setRecruitStatus(TrainingClassInfoConfig.RECRUIT_START);
				trainingClassInfoBean.setModifyUser(userId);
				trainingClassInfoBusiness.updateTrainingClassInfo(trainingClassInfoBean);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
		request.setAttribute("message", message);
		request.setAttribute("userInfoList", userInfoList);
		// 处理待办通知
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		// 转发
		PortalKit.forwardPage(request, response,
				SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserImportResult.jsp");
	}
}
