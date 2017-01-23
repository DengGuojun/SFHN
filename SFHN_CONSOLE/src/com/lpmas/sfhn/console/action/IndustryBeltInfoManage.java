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
import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
import com.lpmas.sfhn.console.business.IndustryBeltInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class IndustryBeltInfoManage
 */
@WebServlet("/sfhn/IndustryBeltInfoManage.do")
public class IndustryBeltInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(IndustryBeltInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndustryBeltInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int beltId = ParamKit.getIntParameter(request, "beltId", 0);

		IndustryBeltInfoBean bean = new IndustryBeltInfoBean();
		IndustryBeltInfoBusiness business = new IndustryBeltInfoBusiness();

		if (beltId > 0) {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getIndustryBeltInfoByKey(beltId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("IndustryBeltInfoBean", bean);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "/IndustryBeltInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);
		int result = 0;
		
		IndustryBeltInfoBean bean = new IndustryBeltInfoBean();
		try {
			bean = BeanKit.request2Bean(request, IndustryBeltInfoBean.class);
			IndustryBeltInfoBusiness business = new IndustryBeltInfoBusiness();
			
			if (bean.getBeltId() > 0) {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminHelper.getAdminUserId());
				result = business.updateIndustryBeltInfo(bean);
			} else {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminHelper.getAdminUserId());
				result = business.addIndustryBeltInfo(bean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/IndustryBeltInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}