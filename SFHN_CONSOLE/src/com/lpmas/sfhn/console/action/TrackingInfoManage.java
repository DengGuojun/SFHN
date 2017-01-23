package com.lpmas.sfhn.console.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.ExpertInfoBean;
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.console.business.ExpertInfoBusiness;
import com.lpmas.sfhn.console.business.TrackingInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/TrackingInfoManage.do")
public class TrackingInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrackingInfoManage.class);
	private static final long serialVersionUID = 1L;

	public TrackingInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int trackingId = ParamKit.getIntParameter(request, "trackingId", 0);
		TrackingInfoBean bean = new TrackingInfoBean();
		TrackingInfoBusiness business = new TrackingInfoBusiness();
		int userId = 0;
		
		if (trackingId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getTrackingInfoByKey(trackingId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			int expertId = ParamKit.getIntParameter(request, "expertId", 0);
			ExpertInfoBusiness expertInfoBusiness = new ExpertInfoBusiness();
			ExpertInfoBean expertInfoBean = expertInfoBusiness.getExpertInfoByKey(expertId);
			if (expertInfoBean == null) {
				HttpResponseKit.alertMessage(response, "专家信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setExpertId(expertId);
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("TrackingInfoBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("userId", userId);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SfhnConsoleConfig.PAGE_PATH + "TrackingInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		TrackingInfoBean bean = new TrackingInfoBean();
		try {
			bean = BeanKit.request2Bean(request, TrackingInfoBean.class);
			TrackingInfoBusiness business = new TrackingInfoBusiness();
			int result = 0;
			if (bean.getTrackingId() > 0) {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateTrackingInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addTrackingInfo(bean);
			}

			if (result > 0 ) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/TrackingInfoList.do?expertId="+ bean.getExpertId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
