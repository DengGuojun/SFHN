package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;

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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.ExpertInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.business.ExpertInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrackingInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class ExpertInfoList
 */
@WebServlet("/sfhn/admin/ExpertInfoList.do")
public class ExpertInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExpertInfoList() {
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

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		int classId = ParamKit.getIntParameter(request, "classId", 0);

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		ExpertInfoBusiness business = new ExpertInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		PageResultBean<ExpertInfoBean> result = business.getExpertInfoPageListByMap(new HashMap<String, String>(),
				pageBean);

		// 查询专家的跟踪学员数量
		HashMap<Integer,Integer> trackingInfoMap = new HashMap<Integer,Integer>();
		TrackingInfoBusiness trackingInfoBusiness = new TrackingInfoBusiness();
		for (ExpertInfoBean bean : result.getRecordList()) {
			HashMap<String, String> trackingCondMap = new HashMap<String, String>();
			trackingCondMap.put("expertId", String.valueOf(bean.getExpertId()));
			trackingCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
			int trackCount = trackingInfoBusiness.getTrackingInfoPageListByMap(trackingCondMap, pageBean).getTotalRecordNumber();
			trackingInfoMap.put(bean.getExpertId(), trackCount);
		}

		request.setAttribute("ClassId", classId);
		request.setAttribute("ExpertInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("TrackingInfoMap", trackingInfoMap);
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "ExpertInfoList.jsp");
	}

}
