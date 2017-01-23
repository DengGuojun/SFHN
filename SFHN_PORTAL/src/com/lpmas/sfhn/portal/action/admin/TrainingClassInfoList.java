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

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TrainingClassInfoEntityBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.IndustryBeltInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class TrainingClassInfoAdminList
 */
@WebServlet("/sfhn/admin/TrainingClassInfoList.do")
public class TrainingClassInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();

		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		String classStatusSelection = ParamKit.getParameter(request, "classStatusSelection", "").trim();
		if (StringKit.isValid(classStatusSelection)) {
			condMap.put("classStatusSelection", classStatusSelection);
		}
		String acceptStatusSelection = ParamKit.getParameter(request, "acceptStatusSelection", "").trim();
		if (StringKit.isValid(acceptStatusSelection)) {
			condMap.put("acceptStatusSelection", acceptStatusSelection);
		}
		String openStatus = ParamKit.getParameter(request, "openStatus", "").trim();
		if (StringKit.isValid(openStatus)) {
			condMap.put("openStatus", openStatus);
		}

		String trainingType = ParamKit.getParameter(request, "trainingType", "").trim();
		if (StringKit.isValid(trainingType)) {
			condMap.put("trainingType", trainingType);
		}
		String trainingYear = ParamKit.getParameter(request, "trainingYear", "").trim();
		if (StringKit.isValid(trainingYear)) {
			condMap.put("trainingYear", trainingYear);
		}
		String industryBeltId = ParamKit.getParameter(request, "industryBeltId", "").trim();
		if (StringKit.isValid(industryBeltId)) {
			condMap.put("industryBeltId", industryBeltId);
		}
		TrainingClassInfoBusiness trainingClassBusiness = new TrainingClassInfoBusiness();
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			// 用户是政府机构
			String organizationId = ParamKit.getParameter(request, "organizationId", "").trim();
			if (StringKit.isValid(organizationId)) {
				condMap.put("organizationId", organizationId);
			}
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 根据政府机构的机构级别，获取对应的培训班信息
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
				// 省级机构，可以选择市和区进行筛选
				condMap.put("province", governmentOrgBean.getProvince());
				request.setAttribute("FixProvince", governmentOrgBean.getProvince());
				String queryCity = ParamKit.getParameter(request, "queryCity", "").trim();
				if (StringKit.isValid(queryCity)) {
					condMap.put("city", queryCity);
				}
				String queryRegion = ParamKit.getParameter(request, "queryRegion", "").trim();
				if (StringKit.isValid(queryRegion)) {
					condMap.put("region", queryRegion);
				}
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				// 市级机构，可以选择区进行筛选
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
				request.setAttribute("FixProvince", governmentOrgBean.getProvince());
				request.setAttribute("FixCity", governmentOrgBean.getCity());
				String queryRegion = ParamKit.getParameter(request, "queryRegion", "").trim();
				if (StringKit.isValid(queryRegion)) {
					condMap.put("region", queryRegion);
				}
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				// 区级机构，不能客制化筛选
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
				condMap.put("region", governmentOrgBean.getRegion());
				request.setAttribute("FixProvince", governmentOrgBean.getProvince());
				request.setAttribute("FixCity", governmentOrgBean.getCity());
				request.setAttribute("FixRegion", governmentOrgBean.getRegion());
			}
			// 获取政府区域对应的培训机构
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			HashMap<String, String> trainingOrgCondMap = new HashMap<String, String>();
			trainingOrgCondMap.put("province", condMap.get("province"));
			trainingOrgCondMap.put("city", condMap.get("city"));
			trainingOrgCondMap.put("region", condMap.get("region"));
			trainingOrgCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoListByMap(trainingOrgCondMap);

			request.setAttribute("TrainingOrganizationInfoList", trainingOrganizationInfoList);
			request.setAttribute("isGovernment", true);
			// 查政府部门级别
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgInfoBean != null) {
				request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);
			}
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			condMap.put("organizationId", String.valueOf(trainingOrgBean.getOrganizationId()));
			request.setAttribute("isGovernment", false);
		}

		List<TrainingClassInfoEntityBean> classInfoList = new ArrayList<TrainingClassInfoEntityBean>();
		PageResultBean<TrainingClassInfoBean> result = trainingClassBusiness.getTrainingClassInfoPageListByMap(condMap,
				pageBean);
		for (TrainingClassInfoBean trainingClassInfoBean : result.getRecordList()) {
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(trainingClassInfoBean.getOrganizationId());
			TrainingClassInfoEntityBean entityBean = new TrainingClassInfoEntityBean(trainingClassInfoBean,
					trainingOrgBean);
			classInfoList.add(entityBean);
		}

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		// 获取产业带信息
		IndustryBeltInfoBusiness beltBusiness = new IndustryBeltInfoBusiness();
		condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<IndustryBeltInfoBean> beltInfoList = beltBusiness.getIndustryBeltInfoListByMap(condMap);
		request.setAttribute("IndustryBeltInfoList", beltInfoList);

		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

		request.setAttribute("ClassInfoList", classInfoList);
		request.setAttribute("UserId", userId);
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassInfoList.jsp");

	}
}
