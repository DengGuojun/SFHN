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
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.ProcessLogBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassStatusHelper;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class TrainingClassInfoProcess
 */
@WebServlet("/sfhn/admin/TrainingClassInfoProcess.do")
public class TrainingClassInfoProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoProcess() {
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

		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		if (!trainingClassInfoBusiness.hasPermission(classId, userId)) {
			HttpResponseKit.alertMessage(response, "你没有权限操作这个数据", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取培训机构
		TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrgBusiness
				.getTrainingOrganizationInfoByKey(classInfoBean.getOrganizationId());

		// 获取培训班流程
		ProcessLogBusiness processLogBusiness = new ProcessLogBusiness();
		List<ProcessLogBean> processLogList = processLogBusiness.getProcessLogListByTrainingClassInfo(classInfoBean);

		// 获取当前流程内容
		String logContent = "";
		for (ProcessLogBean bean : processLogList) {
			logContent = bean.getProcessContent();
		}
		// 获取是否有审批权限或者是否Owner
		TrainingClassStatusHelper statusHelper = new TrainingClassStatusHelper();
		request.setAttribute("HasApprovePermission", statusHelper.hasApprovePermission(classInfoBean, userId));
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		request.setAttribute("isGovernment", orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);

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

		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

		// 获取附件信息
		Map<Integer, Integer> fileInfoMap = new HashMap<Integer, Integer>();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
		condMap.put("infoId1", String.valueOf(classId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
		fileInfoMap = ListKit.list2Map(fileInfoList, "fileType", "fileId");
		request.setAttribute("FileInfoMap", fileInfoMap);

		request.setAttribute("ClassInfo", classInfoBean);
		request.setAttribute("TrainingOrgInfoBean", trainingOrgInfoBean);
		request.setAttribute("ProcessLogList", processLogList);
		request.setAttribute("LogContent", logContent);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassInfoProcess.jsp");
	}
}
