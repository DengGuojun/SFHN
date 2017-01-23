package com.lpmas.sfhn.console.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.ExpertInfoBean;
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.console.business.ExpertInfoBusiness;
import com.lpmas.sfhn.console.business.TrackingInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/TrackingInfoList.do")
public class TrackingInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackingInfoList() {
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
		int expertId = ParamKit.getIntParameter(request, "expertId", 0);
		ExpertInfoBusiness expertInfoBusiness = new ExpertInfoBusiness();
		ExpertInfoBean expertInfoBean = expertInfoBusiness.getExpertInfoByKey(expertId);
		if (expertInfoBean == null) {
			HttpResponseKit.alertMessage(response, "专家信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		condMap.put("expertId", String.valueOf(expertId));
		String status = ParamKit.getParameter(request, "status", "").trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		TrackingInfoBusiness business = new TrackingInfoBusiness();
		PageResultBean<TrackingInfoBean> result = business.getTrackingInfoPageListByMap(condMap, pageBean);

		// 页面数据绑定
		request.setAttribute("TrackingInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("ExpertId", expertId);
		// 页面转发
		RequestDispatcher rd = request.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "TrackingInfoList.jsp");
		rd.forward(request, response);

	}

}
