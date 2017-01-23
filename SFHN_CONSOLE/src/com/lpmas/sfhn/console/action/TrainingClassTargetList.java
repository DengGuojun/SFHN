package com.lpmas.sfhn.console.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.console.business.TrainingClassTargetBusiness;
import com.lpmas.sfhn.console.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/TrainingClassTargetList.do")
public class TrainingClassTargetList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassTargetList() {
		super();
		// TODO Auto-generated constructor stub
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
			return;
		}

		// 分页信息
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 查询条件参数
		HashMap<String, String> condMap = new HashMap<String, String>();
		String province = ParamKit.getParameter(request, "province", "").trim();
		if (StringKit.isValid(province)) {
			condMap.put("province", province);
		}
		String city = ParamKit.getParameter(request, "city", "").trim();
		if (StringKit.isValid(city)) {
			condMap.put("city", city);
		}
		String region = ParamKit.getParameter(request, "region", "").trim();
		if (StringKit.isValid(region)) {
			condMap.put("region", region);
		}
		String organizationId = ParamKit.getParameter(request, "organizationId", "").trim();
		if (StringKit.isValid(organizationId)) {
			condMap.put("organizationId", organizationId);
		}
		String trainingType = ParamKit.getParameter(request, "trainingType", "").trim();
		if (StringKit.isValid(trainingType)) {
			condMap.put("trainingType", trainingType);
		}
		String trainingYear = ParamKit.getParameter(request, "trainingYear", "").trim();
		if (StringKit.isValid(trainingYear)) {
			condMap.put("trainingYear", trainingYear);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		TrainingClassTargetBusiness business = new TrainingClassTargetBusiness();
		PageResultBean<TrainingClassTargetBean> result = business
				.getTrainingClassTargetPageListByMap(condMap, pageBean);

		// 获取对应的培训机构
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		List<TrainingOrganizationInfoBean> trainingOrgList = new ArrayList<TrainingOrganizationInfoBean>();
		for (TrainingClassTargetBean bean : result.getRecordList()) {
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(bean.getOrganizationId());
			trainingOrgList.add(trainingOrgBean);
		}

		Map<Object, TrainingOrganizationInfoBean> trainingOrgMap = ListKit.list2Map(trainingOrgList, "organizationId");

		// 获取所有培训机构
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		List<TrainingOrganizationInfoBean> trainingOrgAllList = trainingOrgBusiness.getTrainingOrganizationInfoALlList();

		// 页面数据绑定
		request.setAttribute("TrainingClassTargetList", result.getRecordList());
		request.setAttribute("TrainingOrgMap", trainingOrgMap);
		request.setAttribute("TrainingOrgAllList", trainingOrgAllList);
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		// 页面转发
		RequestDispatcher rd = request
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "TrainingClassTargetList.jsp");
		rd.forward(request, response);

	}

}
