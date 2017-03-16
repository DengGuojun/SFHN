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
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
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
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassAcceptBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

/**
 * Servlet implementation class TrainingClassAcceptManage
 */
@WebServlet("/sfhn/admin/TrainingClassAcceptManage.do")
public class TrainingClassAcceptManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassAcceptManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassAcceptManage() {
		super();
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
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
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

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassAcceptBean bean = new TrainingClassAcceptBean();
		TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
		List<TrainingClassInfoBean> classInfoList = new ArrayList<TrainingClassInfoBean>();
		if (classId > 0) {
			// 修改
			TrainingClassAcceptBusiness business = new TrainingClassAcceptBusiness();
			bean = business.getTrainingClassAcceptByKey(classId);
			if (bean == null || bean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)
					|| bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)) {
				HttpResponseKit.alertMessage(response, "项目验收正在审批流程中，不能编辑修改", "/sfhn/admin/TrainingClassAceptList.do");
				return;
			}
			if (bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
				HttpResponseKit.alertMessage(response, "项目验收已审批通过，不能编辑修改", "/sfhn/admin/TrainingClassAceptList.do");
				return;
			}
			// 判断权限
			if (bean.getOrganizationId() != orgUserBean.getOrganizationId()) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 获取对应的培训班
			classInfoList.add(classInfoBusiness.getTrainingClassInfoByKey(classId));
			// 获取项目验收表附件信息
			FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM));
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
			condMap.put("infoId1", String.valueOf(classId));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
			if (!fileInfoList.isEmpty()) {
				request.setAttribute("AcceptFormBean", fileInfoList.get(0));
			}
		} else {
			// 新建
			bean.setOrganizationId(orgUserBean.getOrganizationId());
			bean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
			bean.setStatus(Constants.STATUS_VALID);
			// 获取尚未提交验收申请的培训班
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("acceptStatus", TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
			condMap.put("openStatus", TrainingClassInfoConfig.OPEN_STATUS_FINISHED);
			condMap.put("classType", String.valueOf(TrainingClassInfoConfig.CLASS_TYPE_OFFLINE));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			condMap.put("organizationId", String.valueOf(orgUserBean.getOrganizationId()));
			classInfoList = classInfoBusiness.getTrainingClassInfoListByMap(condMap);
		}

		boolean isGovernment = false;
		if (orgUserBean != null && InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			isGovernment = true;
		}

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		// 查政府部门级别
		if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgInfoBean != null) {
				request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);
			}
		}

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("TrainingOrgBean", trainingOrgBean);
		request.setAttribute("ClassAccept", bean);
		request.setAttribute("ClassInfoList", classInfoList);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassAcceptManage.jsp");
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
		TrainingClassAcceptBean bean = new TrainingClassAcceptBean();
		TrainingClassAcceptBusiness business = new TrainingClassAcceptBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
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

		// 检查项目验收表是否上传
		String acceptFormPath = ParamKit.getParameter(request, "acceptFormFilePath", "");
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		FileInfoBean acceptFormBean = new FileInfoBean();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("filePath", acceptFormPath);
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		if (fileInfoList.isEmpty()) {
			HttpResponseKit.alertMessage(response, "请先上传项目验收表", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			acceptFormBean = fileInfoList.get(0);
		}

		try {
			bean = BeanKit.request2Bean(request, TrainingClassAcceptBean.class);
			ReturnMessageBean messageBean = business.verifyTrainingClassAccept(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 判断权限
			if (bean.getOrganizationId() != orgUserBean.getOrganizationId()) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 判断班级状态
			TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
			TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(bean.getClassId());
			if (classInfoBean == null || classInfoBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "班级ID错误", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (classInfoBean.getClassType() == TrainingClassInfoConfig.CLASS_TYPE_ONLINE) {
				HttpResponseKit.alertMessage(response, "线上班级不能提交验收申请", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			TrainingClassAcceptBean originalBean = business.getTrainingClassAcceptByKey(bean.getClassId());
			if (originalBean != null) {
				// 修改
				// 判断审核状态没有被修改
				if (originalBean == null || !originalBean.getAcceptStatus().equals(bean.getAcceptStatus())) {
					HttpResponseKit.alertMessage(response, "项目已经发起验收流程，请不要重复操作", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)
						|| bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)
						|| bean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
					HttpResponseKit.alertMessage(response, "不能编辑此状态下的项目验收", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setModifyUser(userId);
				result = business.updateTrainingClassAccept(bean);
			} else {
				// 新建
				bean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);// 新建时同时提交审核
				bean.setCreateUser(userId);
				result = business.addTrainingClassAccept(bean);
				if (result > 0) {
					// 修改对应的培训班对象
					classInfoBean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);
					classInfoBean.setModifyUser(userId);
					classInfoBusiness.updateTrainingClassInfo(classInfoBean);
					// 记录附件《项目验收表》信息
					acceptFormBean.setFileType(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM);
					acceptFormBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
					acceptFormBean.setInfoId1(bean.getClassId());
					acceptFormBean.setStatus(Constants.STATUS_VALID);
					acceptFormBean.setModifyUser(userId);
					fileInfoBusiness.updateFileInfo(acceptFormBean);

					// 记录流程日志
					ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
					ProcessLogBean logBean = new ProcessLogBean();
					logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT);
					logBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
					logBean.setInfoId(bean.getClassId());
					logBean.setOperatorId(orgUserBean.getOrganizationId());
					logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
					logBean.setCreateUser(userId);
					logBean.setStatus(Constants.STATUS_VALID);
					processLogBusiness.addProcessLog(logBean);
					// 发送消息
					MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
					MessageInfoBean messageInfoBean = new MessageInfoBean();
					messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
					messageInfoBean.setInfoId(bean.getClassId());
					messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT);
					messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
					messageInfoBean.setSendOrganizationId(trainingOrgBean.getOrganizationId());
					GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
					GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
							.getGovernmentOrganizationInfoByRegion(trainingOrgBean.getProvince(), trainingOrgBean.getCity(),
									trainingOrgBean.getRegion(), GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
					messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
					messageInfoBean.setReceiveOrganizationId(governmentOrgBean.getOrganizationId());
					messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
					messageInfoBean.setStatus(Constants.STATUS_VALID);
					messageInfoBean.setCreateUser(userId);
					messageInfoBusiness.addMessageInfo(messageInfoBean);
				}
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功",
						"/sfhn/admin/TrainingClassAcceptProcess.do?classId=" + bean.getClassId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
