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
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.NumeralOperationKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassTargetBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

@WebServlet("/sfhn/admin/TrainingClassTargetView.do")
public class TrainingClassTargetView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassTargetView() {
		super();
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
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
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
			} else if (governmentOrgBean
					.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				// 市级机构，可以选择区进行筛选
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
				request.setAttribute("FixProvince", governmentOrgBean.getProvince());
				request.setAttribute("FixCity", governmentOrgBean.getCity());
				String queryRegion = ParamKit.getParameter(request, "queryRegion", "").trim();
				if (StringKit.isValid(queryRegion)) {
					condMap.put("region", queryRegion);
				}
			} else if (governmentOrgBean
					.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				// 区级机构，不能客制化筛选
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
				condMap.put("region", governmentOrgBean.getRegion());
				request.setAttribute("FixProvince", governmentOrgBean.getProvince());
				request.setAttribute("FixCity", governmentOrgBean.getCity());
				request.setAttribute("FixRegion", governmentOrgBean.getRegion());
			}
			request.setAttribute("isOwner", false);
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			condMap.put("organizationId", String.valueOf(trainingOrgBean.getOrganizationId()));
			request.setAttribute("isOwner", true);
		}
		String trainingYear = ParamKit.getParameter(request, "trainingYear",
				DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR));
		condMap.put("trainingYear", trainingYear);

		request.setAttribute("trainingYear", trainingYear);
		// 统计不同类型的开班数目
		int youngFarmerCount = 0;
		int productFarmerCount = 0;
		int technicalFarmerCount = 0;
		int serviceFarmerCount = 0;
		int leaderFarmerCount = 0;
		HashMap<String, String> trainingOrgCondMap = new HashMap<String, String>();
		trainingOrgCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		trainingOrgCondMap.put("province", condMap.get("province"));
		trainingOrgCondMap.put("city", condMap.get("city"));
		trainingOrgCondMap.put("region", condMap.get("region"));
		trainingOrgCondMap.put("organizationId",condMap.get("organizationId"));
		List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
				.getTrainingOrganizationInfoListByMap(trainingOrgCondMap);
		for (TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList) {
			TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
			HashMap<String, String> trainingClassCondMap = new HashMap<String, String>();
			trainingClassCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
			trainingClassCondMap.put("province", condMap.get("province"));
			trainingClassCondMap.put("city", condMap.get("city"));
			trainingClassCondMap.put("region", condMap.get("region"));
			trainingClassCondMap.put("trainingYear", condMap.get("trainingYear"));
			trainingClassCondMap.put("classStatus", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
			trainingClassCondMap.put("organizationId",
					String.valueOf(trainingOrganizationInfoBean.getOrganizationId()));
			List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
					.getTrainingClassInfoListByMap(trainingClassCondMap);
			if (!trainingClassInfoList.isEmpty()) {
				for (TrainingClassInfoBean trainingClassInfoBean : trainingClassInfoList) {
					// 现代青年农场主
					if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
						++youngFarmerCount;
					}
					// 生产经营职业农民
					if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
						++productFarmerCount;
					}
					// 专业技能型农民
					if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
						++technicalFarmerCount;
					}
					// 专业服务型农民
					if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
						++serviceFarmerCount;
					}
					// 现代农业经营带头人
					if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
						++leaderFarmerCount;
					}
				}

			}
		}
		int generalConut = youngFarmerCount + productFarmerCount + technicalFarmerCount + serviceFarmerCount
				+ leaderFarmerCount;
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		// 记录目标数
		int youngFarmerTarget = 0;
		int productFarmerTarget = 0;
		int technicalFarmerTarget = 0;
		int serviceFarmerTarget = 0;
		int leaderFarmerTarget = 0;
		TrainingClassTargetBusiness trainingClassTargetBusiness = new TrainingClassTargetBusiness();
		List<TrainingClassTargetBean> trainingClassTargetList = trainingClassTargetBusiness
				.getTrainingClassTargetListByMap(condMap);
		if (!trainingClassTargetList.isEmpty()) {
			for (TrainingClassTargetBean trainingClassTargetBean : trainingClassTargetList) {
				// 现代青年农场主
				if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					youngFarmerTarget = trainingClassTargetBean.getTargetQuantity();
				}
				// 生产经营职业农民
				if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					productFarmerTarget = trainingClassTargetBean.getTargetQuantity();
				}
				// 专业技能型农民
				if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
					technicalFarmerTarget = trainingClassTargetBean.getTargetQuantity();
				}
				// 专业服务型农民
				if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
					serviceFarmerTarget = trainingClassTargetBean.getTargetQuantity();
				}
				// 现代农业经营带头人
				if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					leaderFarmerTarget = trainingClassTargetBean.getTargetQuantity();
				}
			}

		}
		int generalTarget = youngFarmerTarget + productFarmerTarget + technicalFarmerTarget + serviceFarmerTarget
				+ leaderFarmerTarget;
		request.setAttribute("youngFarmerTarget", youngFarmerTarget);
		request.setAttribute("productFarmerTarget", productFarmerTarget);
		request.setAttribute("technicalFarmerTarget", technicalFarmerTarget);
		request.setAttribute("serviceFarmerTarget", serviceFarmerTarget);
		request.setAttribute("leaderFarmerTarget", leaderFarmerTarget);
		request.setAttribute("generalTarget", generalTarget);
		request.setAttribute("youngFarmerCount", youngFarmerCount);
		request.setAttribute("productFarmerCount", productFarmerCount);
		request.setAttribute("technicalFarmerCount", technicalFarmerCount);
		request.setAttribute("serviceFarmerCount", serviceFarmerCount);
		request.setAttribute("leaderFarmerCount", leaderFarmerCount);
		request.setAttribute("generalConut", generalConut);
		// 计算百分比
		String youngFarmerPercent = "0";
		String productFarmerPercent = "0";
		String technicalFarmerPercent = "0";
		String serviceFarmerPercent = "0";
		String leaderFarmerPercent = "0";
		String generalPercent = "0";
		if (youngFarmerTarget != 0) {
			youngFarmerPercent = NumeralOperationKit.calculatePercent(youngFarmerCount, youngFarmerTarget, 1);
		}
		if (productFarmerTarget != 0) {
			productFarmerPercent = NumeralOperationKit.calculatePercent(productFarmerCount, productFarmerTarget, 1);
		}
		if (technicalFarmerTarget != 0) {
			technicalFarmerPercent = NumeralOperationKit.calculatePercent(technicalFarmerCount, technicalFarmerTarget,
					1);
		}
		if (serviceFarmerTarget != 0) {
			serviceFarmerPercent = NumeralOperationKit.calculatePercent(serviceFarmerCount, serviceFarmerTarget, 1);
		}
		if (leaderFarmerTarget != 0) {
			leaderFarmerPercent = NumeralOperationKit.calculatePercent(leaderFarmerCount, leaderFarmerTarget, 1);
		}
		if (generalTarget != 0) {
			generalPercent = NumeralOperationKit.calculatePercent(generalConut, generalTarget, 1);
		}
		request.setAttribute("youngFarmerPercent", youngFarmerPercent);
		request.setAttribute("productFarmerPercent", productFarmerPercent);
		request.setAttribute("technicalFarmerPercent", technicalFarmerPercent);
		request.setAttribute("serviceFarmerPercent", serviceFarmerPercent);
		request.setAttribute("leaderFarmerPercent", leaderFarmerPercent);
		request.setAttribute("generalPercent", generalPercent);
		// 设置年份页数
		condMap.remove("trainingYear");
		request.setAttribute("CondList", MapKit.map2List(condMap));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassTargetView.jsp");
	}

}
