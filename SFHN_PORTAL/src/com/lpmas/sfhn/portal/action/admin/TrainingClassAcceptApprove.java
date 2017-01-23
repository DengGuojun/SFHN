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

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.ListKit;
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
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassAcceptBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassStatusHelper;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

/**
 * Servlet implementation class TrainingClassAcceptApprove
 */
@WebServlet("/sfhn/admin/TrainingClassAcceptApprove.do")
public class TrainingClassAcceptApprove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassAcceptApprove() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

		if (!classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)
				&& !classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)
				&& !classAcceptBean.getAcceptStatus().equals(
						TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)) {
			HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", "/sfhn/admin/TrainingClassAcceptProcess.do?classId="
					+ classId);
			return;
		}

		// 判断是否有审批权限
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		TrainingClassStatusHelper statusHelper = new TrainingClassStatusHelper();
		if (!statusHelper.hasApprovePermission(classAcceptBean, userId)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取培训机构
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrgBusiness
				.getTrainingOrganizationInfoByKey(classInfoBean.getOrganizationId());

		// 获取项目相关资料的附件信息
		Map<String, FileInfoBean> fileInfoMap = new HashMap<String, FileInfoBean>();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
		condMap.put("infoId1", String.valueOf(classId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		fileInfoMap = ListKit.list2Map(fileInfoList, "fileType", "fileId");
		// 判断集中培训材料是否齐全
		String lackOfMaterial = "";
		int centralizedMaterialCount = 0;
		for (int i = FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG; i <= FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY; i++) {
			if (fileInfoMap.get(i) != null) {
				centralizedMaterialCount++;
			} else {
				lackOfMaterial += FileInfoConfig.FILE_TYPE_MAP.get(i) + "、";
			}
		}
		// 判断田间实训材料是否齐全
		int fieldMaterialCount = 0;
		for (int i = FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN; i <= FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PHOTO; i++) {
			if (fileInfoMap.get(i) != null) {
				fieldMaterialCount++;
			} else {
				lackOfMaterial += FileInfoConfig.FILE_TYPE_MAP.get(i) + "、";
			}
		}

		if (lackOfMaterial.length() > 1) {
			lackOfMaterial = lackOfMaterial.substring(0, lackOfMaterial.length() - 1);
		}

		request.setAttribute("ClassInfo", classInfoBean);
		request.setAttribute("ClassAccept", classAcceptBean);
		request.setAttribute("FileInfoMap", fileInfoMap);
		request.setAttribute("CentralizedMaterialCount", centralizedMaterialCount);
		request.setAttribute("FieldMaterialCount", fieldMaterialCount);
		request.setAttribute("LackOfMaterial", lackOfMaterial);
		request.setAttribute("TrainingOrgInfoBean", trainingOrgInfoBean);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassAcceptApprove.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

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

		if (!classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)
				&& !classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)
				&& !classAcceptBean.getAcceptStatus().equals(
						TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)) {
			HttpResponseKit.alertMessage(response, "页面已过期，请刷新重试", "/sfhn/admin/TrainingClassAcceptProcess.do?classId="
					+ classId);
			return;
		}

		// 用户权限校验
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		TrainingClassStatusHelper statusHelper = new TrainingClassStatusHelper();
		if (!statusHelper.hasApprovePermission(classAcceptBean, userId)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		String action = ParamKit.getParameter(request, "action", "");
		String nextStatus = "";
		if (action.equals(TrainingClassInfoConfig.APPROVE_ACTION_FAIL)) {
			nextStatus = statusHelper.getNextRejectStatus(classAcceptBean);
		} else if (action.equals(TrainingClassInfoConfig.APPROVE_ACTION_PASS)) {
			nextStatus = statusHelper.getNextApproveStatus(classAcceptBean);
		} else {
			HttpResponseKit.alertMessage(response, "审批操作非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		classAcceptBean.setAcceptStatus(nextStatus);
		classAcceptBean.setModifyUser(userId);

		int result = trainingClassAcceptBusiness.updateTrainingClassAccept(classAcceptBean);
		if (result > 0) {
			// 修改培训班对应信息
			classInfoBean.setAcceptStatus(nextStatus);
			classInfoBean.setModifyUser(userId);
			trainingClassInfoBusiness.updateTrainingClassInfo(classInfoBean);

			OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
			OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			// 记录流程日志
			String reason = ParamKit.getParameter(request, "reason", "");
			ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
			ProcessLogBean logBean = new ProcessLogBean();
			if (action.equals(TrainingClassInfoConfig.APPROVE_ACTION_FAIL)) {
				logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_REJECT_CLASS_ACCEPT);
			} else {
				logBean.setProcessType(MessageInfoConfig.MESSAGE_TYPE_APPROVE_CLASS_ACCEPT);
			}
			logBean.setProcessContent(reason);
			logBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
			logBean.setInfoId(classInfoBean.getClassId());
			logBean.setOperatorId(orgUserBean.getOrganizationId());
			logBean.setOperatorType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
			logBean.setCreateUser(userId);
			logBean.setStatus(Constants.STATUS_VALID);
			processLogBusiness.addProcessLog(logBean);

			// 把原消息设为已读
			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap = new HashMap<String, String>();
			condMap.put("processType", String.valueOf(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT));
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT));
			condMap.put("infoId", String.valueOf(classInfoBean.getClassId()));
			condMap.put("receiveOrganizationType", String.valueOf(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION));
			condMap.put("receiveOrganizatoinId", String.valueOf(orgUserBean.getOrganizationId()));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<MessageInfoBean> messageInfoList = messageInfoBusiness.getMessageInfoListByMap(condMap);
			MessageInfoBean processedMessageInfoBean = messageInfoList.get(0);
			processedMessageInfoBean.setIsRead(Constants.STATUS_VALID);
			messageInfoBusiness.updateMessageInfo(processedMessageInfoBean);

			// 当下一状态不是审核通过时，则需要发送消息
			if (!nextStatus.equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
				MessageInfoBean messageInfoBean = new MessageInfoBean();
				messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT);
				messageInfoBean.setInfoId(classInfoBean.getClassId());
				if (action.equals(TrainingClassInfoConfig.APPROVE_ACTION_FAIL)) {
					messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_REJECT_CLASS_ACCEPT);
				} else {
					messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT);
				}
				messageInfoBean.setMessageContent(reason);
				messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
				messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
				// 寻找对应的消息接收者
				if (action.equals(TrainingClassInfoConfig.APPROVE_ACTION_FAIL)) {
					// 如果审批驳回，则消息接收者为培训机构
					messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
					messageInfoBean.setReceiveOrganizationId(classInfoBean.getOrganizationId());
				} else if (nextStatus.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)) {
					// 如果待市审批，则消息接收者为市政府
					GovernmentOrganizationInfoBean recevieGovernmentOrgBean = governmentOrgBusiness
							.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(),
									classInfoBean.getCity(), null, GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
					messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
					messageInfoBean.setReceiveOrganizationId(recevieGovernmentOrgBean.getOrganizationId());
				} else if (nextStatus.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)) {
					// 如果待省审批，则消息接收者为省政府
					GovernmentOrganizationInfoBean recevieGovernmentOrgBean = governmentOrgBusiness
							.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), null, null,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE);
					messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
					messageInfoBean.setReceiveOrganizationId(recevieGovernmentOrgBean.getOrganizationId());
				}
				messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
				messageInfoBean.setCreateUser(userId);
				messageInfoBean.setStatus(Constants.STATUS_VALID);
				messageInfoBusiness.addMessageInfo(messageInfoBean);
			}
		}

		// 存在操作成功的MEMBER当成成功
		if (result > 0) {
			HttpResponseKit.alertMessage(response, "操作成功", "TrainingClassAcceptProcess.do?classId=" + classId);
			return;
		} else {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}
}
