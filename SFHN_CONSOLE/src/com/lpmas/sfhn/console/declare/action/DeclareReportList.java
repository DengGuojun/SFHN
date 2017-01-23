package com.lpmas.sfhn.console.declare.action;

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
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;

@WebServlet("/sfhn/DeclareReportList.do")
public class DeclareReportList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareReportList() {
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
			return;
		}

		// 初始化页面分页参数
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		HashMap<String, String> condMap = new HashMap<String, String>();
		String userName = ParamKit.getParameter(request, "userName", "").trim();
		if (StringKit.isValid(userName)) {
			condMap.put("userName", userName);
		}
		HashMap<String, HashMap<String, String>> scopeMap = new HashMap<String, HashMap<String, String>>();
		// 从Mongo中获取相应的数据
		DeclareReportBusiness business = new DeclareReportBusiness();
		try {
			PageResultBean<DeclareReportBean> result = business.getDeclareReportPageListByMap(condMap, scopeMap,
					pageBean);

			request.setAttribute("DeclareReportList", result.getRecordList());
			// 初始化分页数据
			pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
			request.setAttribute("PageResult", pageBean);
			request.setAttribute("CondList", MapKit.map2List(condMap));
			request.setAttribute("AdminUserHelper", adminUserHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/DeclareReportList.jsp");
		rd.forward(request, response);

	}

}
