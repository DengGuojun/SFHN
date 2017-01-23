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
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.console.business.MajorInfoBusiness;
import com.lpmas.sfhn.console.business.MajorTypeBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class MajorInfoList
 */
@WebServlet("/sfhn/MajorInfoList.do")
public class MajorInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MajorInfoList() {
		super();
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
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
			return;
		}
		// 分页信息
		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnConsoleConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnConsoleConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 查询条件参数
		HashMap<String, String> condMap = new HashMap<String, String>();
		
		String typeId = ParamKit.getParameter(request, "typeId", "").trim();
		if (StringKit.isValid(typeId)) {
			condMap.put("typeId", typeId);
		}
		String majorName = ParamKit.getParameter(request, "majorName", "").trim();
		if (StringKit.isValid(majorName)) {
			condMap.put("majorName", majorName);
		}

		String status = ParamKit.getParameter(request, "status", String.valueOf(Constants.STATUS_VALID)).trim();
		if (StringKit.isValid(status)) {
			condMap.put("status", status);
		}
		// 初始化业务类
		MajorInfoBusiness business = new MajorInfoBusiness();
		PageResultBean<MajorInfoBean> result = business.getMajorInfoPageListByMap(condMap, pageBean);
		
		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness() ;
	    MajorTypeBean typeBean  = majorTypeBusiness.getMajorTypeByKey(Integer.valueOf(typeId));
		// 页面数据绑定
		request.setAttribute("AdminUserHelper", adminHelper); 
		request.setAttribute("MajorTypeBean", typeBean); 
		request.setAttribute("MajorInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));

		// 页面转发
		RequestDispatcher rd = request.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "MajorInfoList.jsp");
		rd.forward(request, response);
	}
}
