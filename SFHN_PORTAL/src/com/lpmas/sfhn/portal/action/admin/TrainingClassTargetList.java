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
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassTargetBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

@WebServlet("/sfhn/admin/TrainingClassTargetList.do")
public class TrainingClassTargetList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassTargetList() {
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
		TrainingClassTargetBusiness trainingClassTargetBusiness = new TrainingClassTargetBusiness();
		List<String> countList = null;
		HashMap<String, List<String>> declareReportMap = new HashMap<String, List<String>>();
		HashMap<String, List<String>> declareCollectMap = new HashMap<String, List<String>>();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			// 用户是政府机构
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (governmentOrgBean != null) {
				request.setAttribute("GovernmentOrgInfoBean", governmentOrgBean);
			}
			// 根据政府机构的机构级别，获取对应的培训班信息
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
				condMap.put("province", governmentOrgBean.getProvince());
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
			}
			List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoListByMap(condMap);
			for (TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList) {
				if (StringKit.isValid(condMap.get("region"))) {
					if (StringKit.isValid(trainingOrganizationInfoBean.getRegion())) {
						countList = new ArrayList<String>();
						// 新插入县
						if (!declareCollectMap.containsKey(trainingOrganizationInfoBean.getRegion())) {
							countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(
									trainingOrganizationInfoBean, condMap,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
							// 增加省,市名称，以作筛选
							countList.add(trainingOrganizationInfoBean.getProvince());
							countList.add(trainingOrganizationInfoBean.getCity());
							declareCollectMap.put(trainingOrganizationInfoBean.getRegion(), countList);
						} else {
							countList = declareCollectMap.get(trainingOrganizationInfoBean.getRegion());
							declareCollectMap.put(trainingOrganizationInfoBean.getRegion(), trainingClassTargetBusiness
									.getDeclareInfoTargetCountList(trainingOrganizationInfoBean, condMap, countList));
						}
					}
				} else if (StringKit.isValid(condMap.get("city"))) {
					if (StringKit.isValid(trainingOrganizationInfoBean.getCity())) {
						countList = new ArrayList<String>();
						// 新插入市
						if (!declareCollectMap.containsKey(trainingOrganizationInfoBean.getCity())) {
							countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(
									trainingOrganizationInfoBean, condMap,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
							// 增加省名称，以作筛选
							countList.add(trainingOrganizationInfoBean.getProvince());
							declareCollectMap.put(trainingOrganizationInfoBean.getCity(), countList);
						} else {
							countList = declareCollectMap.get(trainingOrganizationInfoBean.getCity());
							declareCollectMap.put(trainingOrganizationInfoBean.getCity(), trainingClassTargetBusiness
									.getDeclareInfoTargetCountList(trainingOrganizationInfoBean, condMap, countList));
						}
					}
					if (StringKit.isValid(trainingOrganizationInfoBean.getRegion())) {
						countList = new ArrayList<String>();
						// 新插入县
						if (!declareReportMap.containsKey(trainingOrganizationInfoBean.getRegion())) {
							countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(
									trainingOrganizationInfoBean, condMap,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
							// 增加省,市名称，以作筛选
							countList.add(trainingOrganizationInfoBean.getProvince());
							countList.add(trainingOrganizationInfoBean.getCity());
							declareReportMap.put(trainingOrganizationInfoBean.getRegion(), countList);
						} else {
							countList = declareReportMap.get(trainingOrganizationInfoBean.getRegion());
							declareReportMap.put(trainingOrganizationInfoBean.getRegion(), trainingClassTargetBusiness
									.getDeclareInfoTargetCountList(trainingOrganizationInfoBean, condMap, countList));
						}
					}
				} else if (StringKit.isValid(condMap.get("province"))) {
					if (StringKit.isValid(trainingOrganizationInfoBean.getProvince())) {
						countList = new ArrayList<String>();
						// 新插入省
						if (!declareCollectMap.containsKey(trainingOrganizationInfoBean.getProvince())) {
							countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(
									trainingOrganizationInfoBean, condMap,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE);
							declareCollectMap.put(trainingOrganizationInfoBean.getProvince(), countList);
						} else {
							countList = declareCollectMap.get(trainingOrganizationInfoBean.getProvince());
							declareCollectMap.put(trainingOrganizationInfoBean.getProvince(),
									trainingClassTargetBusiness.getDeclareInfoTargetCountList(
											trainingOrganizationInfoBean, condMap, countList));
						}
					}
					if (StringKit.isValid(trainingOrganizationInfoBean.getCity())) {
						countList = new ArrayList<String>();
						// 新插入市
						if (!declareReportMap.containsKey(trainingOrganizationInfoBean.getCity())) {
							countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(
									trainingOrganizationInfoBean, condMap,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
							// 增加省名称，以作筛选
							countList.add(trainingOrganizationInfoBean.getProvince());
							declareReportMap.put(trainingOrganizationInfoBean.getCity(), countList);
						} else {
							countList = declareReportMap.get(trainingOrganizationInfoBean.getCity());
							declareReportMap.put(trainingOrganizationInfoBean.getCity(), trainingClassTargetBusiness
									.getDeclareInfoTargetCountList(trainingOrganizationInfoBean, condMap, countList));
						}
					}
				}
			}

			request.setAttribute("isGovernment", true);
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			condMap.put("organizationId", String.valueOf(trainingOrgBean.getOrganizationId()));
			request.setAttribute("isGovernment", false);

			countList = trainingClassTargetBusiness.getDeclareInfoTargetCountList(trainingOrgBean, condMap,
					Constants.STATUS_NOT_VALID);
			countList.add(String.valueOf(trainingOrgBean.getOrganizationId()));
			declareReportMap.put(trainingOrgBean.getOrganizationName(), countList);
		}

		request.setAttribute("declareReportMap", declareReportMap);
		request.setAttribute("declareCollectMap", declareCollectMap);
		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassTargetList.jsp");
	}

}
