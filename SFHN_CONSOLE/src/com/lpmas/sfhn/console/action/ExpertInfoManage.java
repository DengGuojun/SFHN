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
import com.lpmas.sfhn.console.business.ExpertInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/ExpertInfoManage.do")
public class ExpertInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(ExpertInfoManage.class);
	private static final long serialVersionUID = 1L;

	public ExpertInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int expertId = ParamKit.getIntParameter(request, "expertId", 0);
		ExpertInfoBean bean = new ExpertInfoBean();
		ExpertInfoBusiness business = new ExpertInfoBusiness();
		int userId = 0;
		if (expertId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getExpertInfoByKey(expertId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("ExpertInfoBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("userId", userId);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SfhnConsoleConfig.PAGE_PATH + "ExpertInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		ExpertInfoBean bean = new ExpertInfoBean();
		try {
			bean = BeanKit.request2Bean(request, ExpertInfoBean.class);
			ExpertInfoBusiness business = new ExpertInfoBusiness();
			int result = 0;
			if (bean.getExpertId() > 0) {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateExpertInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addExpertInfo(bean);
			}

			if (result > 0 ) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/ExpertInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
