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
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.portal.business.ExpertInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrackingInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class TrackingInfoList
 */
@WebServlet("/sfhn/admin/TrackingInfoList.do")
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

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		int expertId = ParamKit.getIntParameter(request, "expertId", 0);

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		
		//获取对应的专家
		ExpertInfoBusiness expertInfoBusiness = new ExpertInfoBusiness();
		ExpertInfoBean expertInfoBean = expertInfoBusiness.getExpertInfoByKey(expertId);
		if(expertInfoBean == null || expertInfoBean.getStatus() == Constants.STATUS_NOT_VALID){
			HttpResponseKit.alertMessage(response, "专家信息错误", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		
		TrackingInfoBusiness trackingInfoBusiness = new TrackingInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("expertId", String.valueOf(expertId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		PageResultBean<TrackingInfoBean> result = trackingInfoBusiness.getTrackingInfoPageListByMap(condMap, pageBean);


		request.setAttribute("ClassId", classId);
		request.setAttribute("TrackingInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrackingInfoList.jsp");
	}

}
