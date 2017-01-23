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
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.bean.TrackingServiceInfoBean;
import com.lpmas.sfhn.console.business.TrackingInfoBusiness;
import com.lpmas.sfhn.console.business.TrackingServiceInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/TrackingServiceInfoManage.do")
public class TrackingServiceInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrackingServiceInfoManage.class);
	private static final long serialVersionUID = 1L;

	public TrackingServiceInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int serviceId = ParamKit.getIntParameter(request, "serviceId", 0);
		TrackingServiceInfoBean bean = new TrackingServiceInfoBean();
		TrackingServiceInfoBusiness business = new TrackingServiceInfoBusiness();
		
		if (serviceId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getTrackingServiceInfoByKey(serviceId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			int trackingId = ParamKit.getIntParameter(request, "trackingId", 0);
			TrackingInfoBusiness trackingInfoBusiness = new TrackingInfoBusiness();
			TrackingInfoBean trackingInfoBean = trackingInfoBusiness.getTrackingInfoByKey(trackingId);
			if (trackingInfoBean == null) {
				HttpResponseKit.alertMessage(response, "跟踪信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setTrackingId(trackingId);
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("TrackingServiceInfoBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SfhnConsoleConfig.PAGE_PATH + "TrackingServiceInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		TrackingServiceInfoBean bean = new TrackingServiceInfoBean();
		try {
			bean = BeanKit.request2Bean(request, TrackingServiceInfoBean.class);
			TrackingServiceInfoBusiness business = new TrackingServiceInfoBusiness();
			int result = 0;
			if (bean.getServiceId() > 0) {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateTrackingServiceInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addTrackingServiceInfo(bean);
			}

			if (result > 0 ) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/TrackingServiceInfoList.do?trackingId="+ bean.getTrackingId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
