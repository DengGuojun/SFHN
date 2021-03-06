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
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.bean.UserInfoBean;
import com.lpmas.sfhn.portal.business.ActiveCodeInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerContactInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;

/**
 * Servlet implementation class ActiveCodeInfoList
 */
@WebServlet("/sfhn/admin/ActiveCodeInfoList.do")
public class ActiveCodeInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActiveCodeInfoList() {
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
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		boolean isGovernment = false;
		String fixProvince = "";
		String fixCity = "";
		String fixRegion = "";
		if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			isGovernment = true;
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			fixProvince = governmentOrgInfoBean.getProvince();
			fixCity = governmentOrgInfoBean.getCity();
			fixRegion = governmentOrgInfoBean.getRegion();
			request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);

		} else {
			//不是政府无法查看
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		ActiveCodeInfoBusiness business = new ActiveCodeInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("province", fixProvince);
		String queryCity = StringKit.isValid(fixCity) ? fixCity :ParamKit.getParameter(request, "queryCity", "").trim();
		if (StringKit.isValid(queryCity)) {
			condMap.put("city", queryCity);
		}
		String queryRegion = StringKit.isValid(fixRegion) ? fixRegion :ParamKit.getParameter(request, "queryRegion","").trim();
		if (StringKit.isValid(queryRegion)) {
			condMap.put("region", queryRegion);
		}
		String trainingYear = ParamKit.getParameter(request, "trainingYear", "").trim();
		if (StringKit.isValid(trainingYear)) {
			condMap.put("trainingYear", trainingYear);
		}
		String usageStatus = ParamKit.getParameter(request, "usageStatus", "").trim();
		if (StringKit.isValid(usageStatus)) {
			condMap.put("usageStatus", usageStatus);
		}
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		PageResultBean<ActiveCodeInfoBean> result = business.getActiveCodeInfoPageListByMap(condMap, pageBean);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);
		// 处理用户信息
		Map<Integer, UserInfoBean> userInfoMap = new HashMap<Integer, UserInfoBean>();
		for (ActiveCodeInfoBean activeCodeInfoBean : result.getRecordList()) {
			// 用户信息
			if (activeCodeInfoBean.getUserType() == ActiveCodeInfoConfig.USER_TYPE_FARMER) {
				// 是农民的话就去查农民信息
				DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
				DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(
						activeCodeInfoBean.getUserId(), activeCodeInfoBean.getTrainingYear());
				if (declareInfoBean != null) {
					UserInfoBean userInfoBean = new UserInfoBean();
					userInfoBean.setUserId(activeCodeInfoBean.getUserId());
					userInfoBean.setUserType(activeCodeInfoBean.getUserType());

					FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
					FarmerInfoBean farmerInfoBean = farmerInfoBusiness
							.getFarmerInfoByKey(declareInfoBean.getDeclareId());
					if (farmerInfoBean != null)
						userInfoBean.setUserName(farmerInfoBean.getUserName());

					FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
					FarmerContactInfoBean farmerContactInfoBean = farmerContactInfoBusiness
							.getFarmerContactInfoByKey(declareInfoBean.getDeclareId());
					if (farmerContactInfoBean != null)
						userInfoBean.setUserMobile(farmerContactInfoBean.getUserMobile());

					userInfoMap.put(activeCodeInfoBean.getUserId(), userInfoBean);
				}
			} else if (activeCodeInfoBean.getUserType() == ActiveCodeInfoConfig.USER_TYPE_TEACHER) {
				// 是教师就去教师信息里面找
				TeacherInfoBusiness teacherInfoBusiness = new TeacherInfoBusiness();
				TeacherInfoBean teacherInfoBean = teacherInfoBusiness
						.getTeacherInfoByUserId(activeCodeInfoBean.getUserId());
				if (teacherInfoBean != null) {
					UserInfoBean userInfoBean = new UserInfoBean();
					userInfoBean.setUserId(activeCodeInfoBean.getUserId());
					userInfoBean.setUserType(activeCodeInfoBean.getUserType());
					userInfoBean.setUserName(teacherInfoBean.getTeacherName());
					userInfoBean.setUserMobile(teacherInfoBean.getTeacherMobile());
					userInfoMap.put(activeCodeInfoBean.getUserId(), userInfoBean);
				}
			}
		}
		// 处理各个状态的数字
		int usedCount = 0;
		int activatedCount = 0;
		int unuseCount = 0;
		int totalCount = 0;
		//去除不需要的条件
		condMap.remove("usageStatus");
		List<ActiveCodeInfoBean> activeCodeInfoList = business.getActiveCodeInfoListByMap(condMap);
		for (ActiveCodeInfoBean activeCodeInfoBean : activeCodeInfoList) {
			if (activeCodeInfoBean.getUsageStatus().equals(ActiveCodeInfoConfig.USAGE_STATUS_USED)) {
				usedCount++;
			} else if (activeCodeInfoBean.getUsageStatus().equals(ActiveCodeInfoConfig.USAGE_STATUS_UNUSE)) {
				unuseCount++;
			} else if (activeCodeInfoBean.getUsageStatus().equals(ActiveCodeInfoConfig.USAGE_STATUS_ACTIVATED)) {
				activatedCount++;
			}
		}
		totalCount = activeCodeInfoList.size();
		request.setAttribute("usedCount", usedCount);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("activatedCount", activatedCount);
		request.setAttribute("unuseCount", unuseCount);
		request.setAttribute("userInfoMap", userInfoMap);

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

		request.setAttribute("ActiveCodeInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("FixProvince", fixProvince);
		request.setAttribute("FixCity", fixCity);
		request.setAttribute("FixRegion", fixRegion);
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "ActiveCodeInfoList.jsp");
	}

}
