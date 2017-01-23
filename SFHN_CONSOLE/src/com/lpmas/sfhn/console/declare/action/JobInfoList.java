package com.lpmas.sfhn.console.declare.action;

import java.io.IOException;
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
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.JobInfoBusiness;
import com.lpmas.sfhn.console.declare.business.JobTypeBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;

@WebServlet("/sfhn/JobInfoList.do")
public class JobInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobInfoList() {
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
		// 分页信息
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 查询条件参数
		HashMap<String, String> condMap = new HashMap<String, String>();
		String jobName = ParamKit.getParameter(request, "jobName", "").trim();
		if (StringKit.isValid(jobName)) {
			condMap.put("jobName", jobName);
		}
		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}

		// 初始化业务类
		JobInfoBusiness business = new JobInfoBusiness();
		PageResultBean<JobInfoBean> result = business.getJobInfoPageListByMap(condMap, pageBean);

		// 获取当前所有的工作类型
		JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
		List<JobTypeBean> jobTypeList = jobTypeBusiness.getJobTypeAllList();
		// 转换成MAP,ID是KEY
		Map<Integer, JobTypeBean> jobTypeMap = ListKit.list2Map(jobTypeList, "typeId");

		// 页面数据绑定
		request.setAttribute("JobInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("JobTypeMap", jobTypeMap);
		// 页面转发
		RequestDispatcher rd = request.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/JobInfoList.jsp");
		rd.forward(request, response);

	}

}
