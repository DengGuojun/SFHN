package com.lpmas.sfhn.console.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.console.business.ActiveCodeInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class ActiveCodeInfoManage
 */
@WebServlet("/sfhn/ActiveCodeInfoManage.do")
public class ActiveCodeInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActiveCodeInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
			return;
		}
		request.setAttribute("AdminUserHelper", adminUserHelper);
		// 页面转发
		RequestDispatcher rd = request.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "ActiveCodeInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
			return;
		}

		String province = ParamKit.getParameter(request, "province", "").trim();
		String city = ParamKit.getParameter(request, "city", "").trim();
		String region = ParamKit.getParameter(request, "region", "").trim();
		String trainingYear = ParamKit.getParameter(request, "trainingYear", "").trim();
		int count = ParamKit.getIntParameter(request, "count", 0);

		ActiveCodeInfoBusiness business = new ActiveCodeInfoBusiness();
		int successCount = business.batchGenerateActiveCode(province, city, region, trainingYear,
				adminUserHelper.getAdminUserId(), count);

		HttpResponseKit.alertMessage(response, "请求生成激活码" + count + "个,实际生成" + successCount + "个",
				"/sfhn/ActiveCodeInfoList.do?province=" + province + "&city=" + city + "&region=" + region
						+ "&trainingYear=" + trainingYear);
	}

}
