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
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassAcceptBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

/**
 * Servlet implementation class TrainingClassAcceptCommit
 */
@WebServlet("/sfhn/admin/TrainingClassAcceptCommit.do")
public class TrainingClassAcceptCommit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassAcceptCommit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		// 根据班级ID查询项目验收信息
		TrainingClassAcceptBusiness trainingClassAcceptBusiness = new TrainingClassAcceptBusiness();
		TrainingClassAcceptBean classAcceptBean = trainingClassAcceptBusiness.getTrainingClassAcceptByKey(classId);
		if (classAcceptBean == null) {
			HttpResponseKit.alertMessage(response, "项目验收不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取用户Id,判断是否拥有权限
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (orgUserBean.getOrganizationId() != classInfoBean.getOrganizationId()) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
				.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		String action = ParamKit.getParameter(request, "action", "");
		if (action.equals(TrainingClassInfoConfig.COMMIT_ACTION_COMMIT)) {
			// 检查当前状态是否已经被改变
			if (!classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)
					&& !classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION)
					&& !classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY)
					&& !classAcceptBean.getAcceptStatus()
							.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试",
						"/sfhn/admin/TrainingClassAcceptProcess.do?classId=" + classId);
				return;
			}
			classAcceptBean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);
		} else if (action.equals(TrainingClassInfoConfig.COMMIT_ACTION_CANCEL_COMMIT)) {
			// 检查当前状态是否已经被改变
			if (!classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)) {
				HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试",
						"/sfhn/admin/TrainingClassAcceptProcess.do?classId=" + classId);
				return;
			}
			classAcceptBean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
		} else {
			HttpResponseKit.alertMessage(response, "提交审批操作非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		classAcceptBean.setModifyUser(userId);
		int result = trainingClassAcceptBusiness.updateTrainingClassAccept(classAcceptBean);
		if (result > 0) {
			// 记录相关培训班信息
			classInfoBean.setAcceptStatus(classAcceptBean.getAcceptStatus());
			classInfoBean.setModifyUser(userId);
			trainingClassInfoBusiness.updateTrainingClassInfo(classInfoBean);
			ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			if (action.equals(TrainingClassInfoConfig.COMMIT_ACTION_COMMIT)) {
				// 记录流程日志
				ProcessLogBean logBean = new ProcessLogBean();
				logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT);
				logBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
				logBean.setInfoId(classInfoBean.getClassId());
				logBean.setOperatorId(trainingOrgBean.getOrganizationId());
				logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
				logBean.setCreateUser(userId);
				logBean.setStatus(Constants.STATUS_VALID);
				processLogBusiness.addProcessLog(logBean);

				// 把原消息设为已读
				HashMap<String, String> condMap = new HashMap<String, String>();
				condMap = new HashMap<String, String>();
				condMap.put("messageType", String.valueOf(MessageInfoConfig.MESSAGE_TYPE_REJECT_CLASS_ACCEPT));
				condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT));
				condMap.put("infoId", String.valueOf(classInfoBean.getClassId()));
				condMap.put("receiveOrganizationType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION));
				condMap.put("receiveOrganizatoinId", String.valueOf(orgUserBean.getOrganizationId()));
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				List<MessageInfoBean> messageInfoList = messageInfoBusiness.getMessageInfoListByMap(condMap);
				if (!messageInfoList.isEmpty()) {
					MessageInfoBean processedMessageInfoBean = messageInfoList.get(0);
					processedMessageInfoBean.setIsRead(Constants.STATUS_VALID);
					messageInfoBusiness.updateMessageInfo(processedMessageInfoBean);
				}

				// 发送消息
				MessageInfoBean messageInfoBean = new MessageInfoBean();
				messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
				messageInfoBean.setInfoId(classInfoBean.getClassId());
				messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT);
				messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
				messageInfoBean.setSendOrganizationId(trainingOrgBean.getOrganizationId());
				GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
				GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
						.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), classInfoBean.getCity(),
								classInfoBean.getRegion(), GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
				messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
				messageInfoBean.setReceiveOrganizationId(governmentOrgBean.getOrganizationId());
				messageInfoBean.setCreateUser(userId);
				messageInfoBean.setStatus(Constants.STATUS_VALID);
				messageInfoBusiness.addMessageInfo(messageInfoBean);

			} else {
				// 撤回动作需要删除流转日志
				HashMap<String, String> condMap = new HashMap<String, String>();
				condMap.put("processType", String.valueOf(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT));
				condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT));
				condMap.put("infoId", String.valueOf(classInfoBean.getClassId()));
				condMap.put("operatorType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION));
				condMap.put("operatorId", String.valueOf(trainingOrgBean.getOrganizationId()));
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				List<ProcessLogBean> logList = processLogBusiness.getProcessLogListByMap(condMap);
				ProcessLogBean logBean = logList.get(0);
				logBean.setStatus(Constants.STATUS_NOT_VALID);
				logBean.setModifyUser(userId);
				processLogBusiness.updateProcessLog(logBean);
				// 撤回动作需要删除消息
				condMap = new HashMap<String, String>();
				condMap.put("messageType", String.valueOf(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT));
				condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT));
				condMap.put("infoId", String.valueOf(classInfoBean.getClassId()));
				condMap.put("sendOrganizationType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION));
				condMap.put("sendOrganizatoinId", String.valueOf(trainingOrgBean.getOrganizationId()));
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				List<MessageInfoBean> messageInfoList = messageInfoBusiness.getMessageInfoListByMap(condMap);
				MessageInfoBean messageInfoBean = messageInfoList.get(0);
				messageInfoBean.setStatus(Constants.STATUS_NOT_VALID);
				messageInfoBean.setModifyUser(userId);
				messageInfoBusiness.updateMessageInfo(messageInfoBean);
			}

		}

		if (result > 0) {
			if (PortalKit.getDevicePath(request).equals("web")) {
				HttpResponseKit.alertMessage(response, "操作成功", "TrainingClassAcceptProcess.do?classId=" + classId);
				return;
			} else {
				HttpResponseKit.alertMessage(response, "操作成功", "TrainingClassAcceptList.do");
				return;
			}
		} else {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}
}
