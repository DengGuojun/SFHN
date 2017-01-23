package com.lpmas.sfhn.console.declare.action;

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
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.NationalCertificationBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.NationalCertificationBean;

@WebServlet("/sfhn/NationalCertificationManage.do")
public class NationalCertificationManage extends HttpServlet {

	private static Logger log = LoggerFactory.getLogger(NationalCertificationManage.class);
	private static final long serialVersionUID = 1L;

	public NationalCertificationManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int certificateId = ParamKit.getIntParameter(request, "certificateId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		NationalCertificationBean bean = new NationalCertificationBean();
		NationalCertificationBusiness business = new NationalCertificationBusiness();
		if (certificateId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getNationalCertificationByKey(certificateId);
		} else {
			if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("NationalCertificationBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/NationalCertificationManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		NationalCertificationBean bean = new NationalCertificationBean();
		try {
			bean = BeanKit.request2Bean(request, NationalCertificationBean.class);
			NationalCertificationBusiness business = new NationalCertificationBusiness();

			int result = 0;
			if (bean.getCertificateId() > 0) {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateNationalCertification(bean);
			} else {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addNationalCertification(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/NationalCertificationList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
