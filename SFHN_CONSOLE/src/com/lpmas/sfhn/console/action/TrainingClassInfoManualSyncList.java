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
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.console.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.console.config.ManualSyncConfig;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class TrainingClassInfoManualSyncList
 */
@WebServlet("/sfhn/TrainingClassInfoManualSyncList.do")
public class TrainingClassInfoManualSyncList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoManualSyncList() {
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
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
			return;
		}

		// 分页信息
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 查询条件参数
		HashMap<String, String> condMap = new HashMap<String, String>();
		String syncStatus = ParamKit.getParameter(request, "syncStatus", String.valueOf(Constants.STATUS_NOT_VALID))
				.trim();
		if (StringKit.isValid(syncStatus)) {
			condMap.put("syncStatus", syncStatus);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}
		String classStatus = ParamKit.getParameter(request, "classStatus", ManualSyncConfig.YUN_CLASS_OPEN_STATUS).trim();
		if (StringKit.isValid(classStatus)) {
			condMap.put("classStatus", classStatus);
		}

		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		PageResultBean<TrainingClassInfoBean> result = trainingClassInfoBusiness
				.getTrainingClassInfoPageListByMap(condMap, pageBean);

		// 页面数据绑定
		request.setAttribute("TrainingClassInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		// 页面转发
		RequestDispatcher rd = request
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "TrainingClassInfoManualSyncList.jsp");
		rd.forward(request, response);
	}

}
