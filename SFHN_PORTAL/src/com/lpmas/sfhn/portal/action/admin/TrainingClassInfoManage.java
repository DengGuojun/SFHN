package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
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
import com.lpmas.sfhn.portal.business.IndustryBeltInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

/**
 * Servlet implementation class TrainingClassInfoAdminManage
 */
@WebServlet("/sfhn/admin/TrainingClassInfoManage.do")
public class TrainingClassInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(orgUserBean
				.getOrganizationId());
		if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		Map<Integer, FileInfoBean> fileInfoMap = new HashMap<Integer, FileInfoBean>();
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassInfoBean bean = new TrainingClassInfoBean();
		if (classId > 0) {
			// 修改
			TrainingClassInfoBusiness business = new TrainingClassInfoBusiness();
			bean = business.getTrainingClassInfoByKey(classId);
			if (bean == null) {
				HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)
					|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)) {
				HttpResponseKit.alertMessage(response, "班级信息正在审批流程中，不能编辑修改", "/sfhn/admin/TrainingClassInfoList.do");
				return;
			}
			if (bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
				HttpResponseKit.alertMessage(response, "班级信息已审批通过，不能编辑修改", "/sfhn/admin/TrainingClassInfoList.do");
				return;
			}
			// 获取附件信息
			FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
			condMap.put("infoId1", String.valueOf(classId));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
			fileInfoMap = ListKit.list2Map(fileInfoList, "fileType");
		} else {
			// 新建
			bean.setOrganizationId(orgUserBean.getOrganizationId());
			bean.setAcceptStatus(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
			bean.setClassStatus(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
			// 根据培训机构地区，设置培训班地区
			bean.setClassType(TrainingClassInfoConfig.CLASS_TYPE_ONLINE);
			bean.setProvince(trainingOrgBean.getProvince());
			bean.setCity(trainingOrgBean.getCity());
			bean.setRegion(trainingOrgBean.getRegion());
			bean.setSyncStatus(Constants.STATUS_NOT_VALID);
			bean.setStatus(Constants.STATUS_VALID);
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

		// 获取产业带信息
		IndustryBeltInfoBusiness beltBusiness = new IndustryBeltInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<IndustryBeltInfoBean> beltInfoList = beltBusiness.getIndustryBeltInfoListByMap(condMap);
		request.setAttribute("IndustryBeltInfoList", beltInfoList);

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

		request.setAttribute("FileInfoMap", fileInfoMap);
		request.setAttribute("TrainingOrgBean", trainingOrgBean);
		request.setAttribute("ClassInfo", bean);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassInfoManage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		TrainingClassInfoBean bean = new TrainingClassInfoBean();
		TrainingClassInfoBusiness business = new TrainingClassInfoBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(orgUserBean
				.getOrganizationId());
		if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 检查表格是否上传齐全
		String delcareFormFilePath = ParamKit.getParameter(request, "declareFormFilePath", "");
		String annualPlanFilePath = ParamKit.getParameter(request, "annualPlanFilePath", "");
		String trainingGuideFilePath = ParamKit.getParameter(request, "trainingGuideFilePath", "");

		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		FileInfoBean declareFormBean = new FileInfoBean();
		FileInfoBean annualPlanBean = new FileInfoBean();
		FileInfoBean trainingGuideBean = new FileInfoBean();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("filePath", delcareFormFilePath);
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		if (fileInfoList.isEmpty()) {
			HttpResponseKit.alertMessage(response, "请先上传培育工程项目申报表", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			declareFormBean = fileInfoList.get(0);
		}
		condMap.put("filePath", annualPlanFilePath);
		fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		if (fileInfoList.isEmpty()) {
			HttpResponseKit.alertMessage(response, "请先上传项目年度实施计划", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			annualPlanBean = fileInfoList.get(0);
		}
		condMap.put("filePath", trainingGuideFilePath);
		fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		if (fileInfoList.isEmpty()) {
			HttpResponseKit.alertMessage(response, "请先上传培训指南", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			trainingGuideBean = fileInfoList.get(0);
		}

		try {
			bean = BeanKit.request2Bean(request, TrainingClassInfoBean.class);
			ReturnMessageBean messageBean = business.verifyTrainingClassInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 判断权限
			if (bean.getOrganizationId() != orgUserBean.getOrganizationId()) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getClassId() > 0) {
				// 修改培训班
				// 判断审核状态没有被修改
				TrainingClassInfoBean originalBean = business.getTrainingClassInfoByKey(bean.getClassId());
				if (originalBean == null || !originalBean.getClassStatus().equals(bean.getClassStatus())) {
					HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				if (bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
					HttpResponseKit.alertMessage(response, "不能编辑此状态下的班级信息", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				bean.setModifyUser(userId);
				result = business.updateTrainingClassInfo(bean);
			} else {
				// 新建培训班
				if (bean.getClassType() == TrainingClassInfoConfig.CLASS_TYPE_OFFLINE) {
					// 线下培训班需要提交审核
					bean.setClassStatus(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
					if (!trainingOrgBean.getProvince().equals(bean.getProvince())
							|| !trainingOrgBean.getCity().equals(bean.getCity())
							|| !trainingOrgBean.getRegion().equals(bean.getRegion())) {
						HttpResponseKit.alertMessage(response, "班级的省市区信息与培训机构省市区不一致",
								HttpResponseKit.ACTION_HISTORY_BACK);
						return;
					}
				} else {
					// 线上培训班不需要审核
					bean.setClassStatus(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
				}

				bean.setCreateUser(userId);
				result = business.addTrainingClassInfo(bean);
				if (result > 0) {
					bean.setClassId(result);
					// 记录附件《培育工程申报表》信息
					declareFormBean.setFileType(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM);
					declareFormBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
					declareFormBean.setInfoId1(bean.getClassId());
					declareFormBean.setStatus(Constants.STATUS_VALID);
					declareFormBean.setModifyUser(userId);
					fileInfoBusiness.updateFileInfo(declareFormBean);
					// 记录附件《项目年度实施计划》信息
					annualPlanBean.setFileType(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN);
					annualPlanBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
					annualPlanBean.setInfoId1(bean.getClassId());
					annualPlanBean.setStatus(Constants.STATUS_VALID);
					annualPlanBean.setModifyUser(userId);
					fileInfoBusiness.updateFileInfo(annualPlanBean);
					// 记录附件《培训指南》信息
					trainingGuideBean.setFileType(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE);
					trainingGuideBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
					trainingGuideBean.setInfoId1(bean.getClassId());
					trainingGuideBean.setStatus(Constants.STATUS_VALID);
					trainingGuideBean.setModifyUser(userId);
					fileInfoBusiness.updateFileInfo(trainingGuideBean);
					// 记录流程日志
					ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
					ProcessLogBean logBean = new ProcessLogBean();
					logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_INFO);
					logBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
					logBean.setInfoId(bean.getClassId());
					logBean.setOperatorId(orgUserBean.getOrganizationId());
					logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
					logBean.setCreateUser(userId);
					logBean.setStatus(Constants.STATUS_VALID);
					processLogBusiness.addProcessLog(logBean);
					// 线下班需要发送通知告知上级政府部门审核
					if (bean.getClassType() == TrainingClassInfoConfig.CLASS_TYPE_OFFLINE) {
						// 发送消息
						MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
						MessageInfoBean messageInfoBean = new MessageInfoBean();
						messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
						messageInfoBean.setInfoId(result);
						messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_INFO);
						messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
						messageInfoBean.setSendOrganizationId(trainingOrgBean.getOrganizationId());
						GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
						GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
								.getGovernmentOrganizationInfoByRegion(trainingOrgBean.getProvince(),
										trainingOrgBean.getCity(), trainingOrgBean.getRegion(),
										GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
						messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setReceiveOrganizationId(governmentOrgBean.getOrganizationId());
						messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
						messageInfoBean.setStatus(Constants.STATUS_VALID);
						messageInfoBean.setCreateUser(userId);
						messageInfoBusiness.addMessageInfo(messageInfoBean);
					}
				}
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/TrainingClassInfoProcess.do?classId="
						+ bean.getClassId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
