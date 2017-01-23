package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
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
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

@WebServlet("/sfhn/admin/TrainingClassRecruitVerify.do")
public class TrainingClassRecruitVerify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassRecruitVerify() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		if (classId <= 0) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 根据班级ID查询班级
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (classInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取培训机构
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrgBusiness
				.getTrainingOrganizationInfoByKey(classInfoBean.getOrganizationId());

		// 获取学员上传流程
		ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
		HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_RECRUITMENT));
		queryMap.put("infoId", String.valueOf(classId));
		queryMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<ProcessLogBean> processLogList = processLogBusiness.getProcessLogListByMap(queryMap);

		// 获取当前流程内容
		String logContent = "";
		for (ProcessLogBean bean : processLogList) {
			logContent = bean.getProcessContent();
		}

		// 获取学员名单信息
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_RECRUITMENT));
		condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CANDIDATE_LIST));
		condMap.put("infoId1", String.valueOf(classId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		if (!fileInfoList.isEmpty()) {
			request.setAttribute("fileId", fileInfoList.get(0).getFileId());
		}
		request.setAttribute("LogContent", logContent);
		request.setAttribute("classInfoBean", classInfoBean);
		request.setAttribute("TrainingOrgInfoBean", trainingOrgInfoBean);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassRecruitVerify.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int isFinish = ParamKit.getIntParameter(request, "isFinish", 0);
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		if (classId <= 0) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 根据班级ID查询班级
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (classInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 把原消息设为已读
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap = new HashMap<String, String>();
		condMap.put("processType", String.valueOf(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_LIST));
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_RECRUITMENT));
		condMap.put("infoId", String.valueOf(classId));
		condMap.put("receiveOrganizationType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION));
		condMap.put("receiveOrganizatoinId", String.valueOf(orgUserBean.getOrganizationId()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<MessageInfoBean> messageInfoList = messageInfoBusiness.getMessageInfoListByMap(condMap);
		MessageInfoBean processedMessageInfoBean = messageInfoList.get(0);
		processedMessageInfoBean.setIsRead(Constants.STATUS_VALID);
		messageInfoBusiness.updateMessageInfo(processedMessageInfoBean);
		int result = 0;
		if (isFinish == 0) {
			// 记录流程日志
			ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
			ProcessLogBean logBean = new ProcessLogBean();
			logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_REQUEST);
			logBean.setInfoType(InfoTypeConfig.INFO_TYPE_RECRUITMENT);
			logBean.setInfoId(classId);
			logBean.setOperatorId(orgUserBean.getOrganizationId());
			logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
			logBean.setCreateUser(userId);
			logBean.setStatus(Constants.STATUS_VALID);
			processLogBusiness.addProcessLog(logBean);
			// 发送消息
			MessageInfoBean messageInfoBean = new MessageInfoBean();
			messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_RECRUITMENT);
			messageInfoBean.setInfoId(classId);
			messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_REQUEST);
			messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
			messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
			GovernmentOrganizationInfoBean recevieGovernmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), classInfoBean.getCity(),
							classInfoBean.getRegion(), GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
			messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
			messageInfoBean.setReceiveOrganizationId(recevieGovernmentOrgBean.getOrganizationId());
			messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
			messageInfoBean.setCreateUser(userId);
			messageInfoBean.setStatus(Constants.STATUS_VALID);
			messageInfoBusiness.addMessageInfo(messageInfoBean);
			// 改变招生状态
			classInfoBean.setRecruitStatus(TrainingClassInfoConfig.RECRUIT_CONTINUE);
			classInfoBean.setModifyUser(userId);
			result = trainingClassInfoBusiness.updateTrainingClassInfo(classInfoBean);
		} else {
			classInfoBean.setRecruitStatus(TrainingClassInfoConfig.RECRUIT_FINISH);
			classInfoBean.setModifyUser(userId);
			result = trainingClassInfoBusiness.updateTrainingClassInfo(classInfoBean);
		}

		if (result > 0) {
			HttpResponseKit.alertMessage(response, "操作成功", "TrainingClassInfoList.do");
			return;
		} else {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}

}
