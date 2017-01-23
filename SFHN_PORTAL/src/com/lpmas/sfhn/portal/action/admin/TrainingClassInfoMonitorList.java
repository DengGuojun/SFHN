package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.transfer.HttpClientKit;
import com.lpmas.framework.transfer.HttpClientResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.SetKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TrainingClassInfoEntityBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.invoker.bean.ClassroomLiveStatusBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeHelper;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

/**
 * Servlet implementation class TrainingClassAdminMonitorList
 */
@WebServlet("/sfhn/admin/TrainingClassInfoMonitorList.do")
public class TrainingClassInfoMonitorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TrainingClassInfoMonitorList.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoMonitorList() {
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

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();

		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		HashMap<String, String> condMap = new HashMap<String, String>();
		String trainingType = ParamKit.getParameter(request, "trainingType", "").trim();
		if (StringKit.isValid(trainingType)) {
			condMap.put("trainingType", trainingType);
		}
		condMap.put("syncStatus", String.valueOf(Constants.STATUS_VALID));
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
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
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

		// 查看哪些班级正在直播
		List<Integer> isBroadcastingList = new ArrayList<Integer>();
		List<TrainingClassInfoBean> list = trainingClassBusiness.getTrainingClassInfoListByMap(condMap);
		String eduClassIds = ListKit.list2String(list, "eduClassId", ",");
		Set<String> eduClassIdSet = SetKit.string2Set(eduClassIds, ",");
		// 请求云课堂
		StringBuffer url = YunClassInvokeHelper.getUrl(YunClassInvokeExecutor.HTTP_GET,
				YunClassInvokeConfig.YUN_SERVICE_CLASSROOM_LIVE_STATUS, YunClassInvokeConfig.IS_DEBUG_MODE);
		url.append("?classId=" + eduClassIds);
		HttpClientKit httpClient = new HttpClientKit();
		HttpClientResultBean httpResult = httpClient.getContent(url.toString(), "UTF-8");
		if (httpResult.getResult()) {
			try {
				YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
						YunClassInvokeResponseBean.class);
				if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
					// 取出DATA，此时DATA是MAP
					Object data = responseBean.getData();
					if (data != null) {
						@SuppressWarnings("unchecked")
						Map<Object, Object> dataMap = (Map<Object, Object>) data;
						ClassroomLiveStatusBean[] classroomStatus = JsonKit.getObjectMapper()
								.readValue(JsonKit.toJson(dataMap.get("classrooms")), ClassroomLiveStatusBean[].class);
						for (ClassroomLiveStatusBean bean : classroomStatus) {
							if (eduClassIdSet.contains(bean.getClassid())) {
								isBroadcastingList.add(Integer.valueOf(bean.getClassid()));
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		// 处理“已连线现场”
		int isBroadcasting = ParamKit.getIntParameter(request, "isBroadcasting", Constants.STATUS_NOT_VALID);
		int totalRecordCount = result.getTotalRecordNumber();
		if (isBroadcasting == Constants.STATUS_VALID) {
			// 要求筛选已连线现场
			totalRecordCount = isBroadcastingList.size();
			Iterator<TrainingClassInfoEntityBean> iterator = classInfoList.iterator();
			while (iterator.hasNext()) {
				TrainingClassInfoEntityBean bean = iterator.next();
				if (!isBroadcastingList.contains(bean.getEduClassId())) {
					iterator.remove();
				}
			}
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
		request.setAttribute("isBroadcastingList", isBroadcastingList);
		request.setAttribute("ClassInfoList", classInfoList);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("UserId", userId);
		pageBean.init(pageNum, pageSize, totalRecordCount);
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassInfoMonitorList.jsp");
	}

}
