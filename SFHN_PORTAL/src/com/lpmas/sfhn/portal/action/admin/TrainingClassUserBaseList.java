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
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;

@WebServlet("/sfhn/admin/TrainingClassUserBaseList.do")
public class TrainingClassUserBaseList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserBaseList() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);
		pageBean.setPrePageNumber(pageNum - 1);
		// 是否参与培训班
		int isTrain = ParamKit.getIntParameter(request, "isTrain", -1);
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		String organizationId = ParamKit.getParameter(request, "organizationId", "").trim();
		if (StringKit.isValid(organizationId)) {
			condMap.put("organizationId", organizationId);
		}
		String declareYear = ParamKit.getParameter(request, "declareYear", "").trim();
		if (StringKit.isValid(declareYear)) {
			condMap.put("declareYear", declareYear);
		}
		String declareType = ParamKit.getParameter(request, "declareType", "").trim();
		if (StringKit.isValid(declareType)) {
			condMap.put("declareType", declareType);
		}
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 根据政府机构的机构级别，获取对应的学员信息
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

		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			condMap.put("province", trainingOrgBean.getProvince());
			condMap.put("city", trainingOrgBean.getCity());
			condMap.put("region", trainingOrgBean.getRegion());
		}
		int totalRecordNumber = 0;
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		List<DeclareReportBean> declareList = new ArrayList<DeclareReportBean>();
		if (isTrain == 0) {
			condMap.put("classId", "0");
		} else if (isTrain == 1) {
			condMap.put("classId", "1");
		}
		try {
			PageResultBean<DeclareReportBean> declarePageList = declareReportBusiness.getDeclareReportPageListByMap(condMap, pageBean);
			declareList = declarePageList.getRecordList();
			totalRecordNumber = declarePageList.getTotalRecordNumber();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取产业类型列表
		IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
		request.setAttribute("IndustryTypeList", industryTypeList);

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
		request.setAttribute("DeclareList", declareList);
		pageBean.init(pageNum, pageSize, totalRecordNumber);
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserBaseList.jsp");

	}

}
