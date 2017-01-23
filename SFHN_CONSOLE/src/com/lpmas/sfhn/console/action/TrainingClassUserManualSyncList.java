package com.lpmas.sfhn.console.action;

import java.io.IOException;
import java.util.HashMap;
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
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.console.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.console.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.console.config.ManualSyncConfig;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;
import com.lpmas.sfhn.console.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.console.declare.business.FarmerInfoBusiness;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;

/**
 * Servlet implementation class TrainingClassUserManualSyncList
 */
@WebServlet("/sfhn/TrainingClassUserManualSyncList.do")
public class TrainingClassUserManualSyncList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserManualSyncList() {
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
		String userStatus = ParamKit.getParameter(request, "userStatus", ManualSyncConfig.YUN_CLASS_USER_APPROVE_STATUS)
				.trim();
		if (StringKit.isValid(userStatus)) {
			condMap.put("userStatus", userStatus);
		}

		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		PageResultBean<TrainingClassUserBean> result = trainingClassUserBusiness
				.getTrainingClassUserPageListByMap(condMap, pageBean);
		// 找到农民信息
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean trainingClassInfoBean = null;
		DeclareInfoBean declareInfoBean = null;
		Map<Integer, FarmerInfoBean> farmerInfoMap = new HashMap<Integer, FarmerInfoBean>();
		Map<Integer, Integer> userIdMap = new HashMap<Integer, Integer>();
		for (TrainingClassUserBean bean : result.getRecordList()) {
			trainingClassInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(bean.getClassId());
			if (trainingClassInfoBean == null) {
				continue;
			}
			declareInfoBean = declareInfoBusiness.getDeclareInfoByKey(bean.getDeclareId());
			if (declareInfoBean != null) {
				farmerInfoMap.put(bean.getDeclareId(),
						farmerInfoBusiness.getFarmerInfoByKey(declareInfoBean.getDeclareId()));
				userIdMap.put(declareInfoBean.getDeclareId(), declareInfoBean.getUserId());
			}
		}

		// 页面数据绑定
		request.setAttribute("farmerInfoMap", farmerInfoMap);
		request.setAttribute("TrainingClassUserList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		// 页面转发
		RequestDispatcher rd = request
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "TrainingClassUserManualSyncList.jsp");
		rd.forward(request, response);
	}

}
