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
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.config.MajorConfig;
import com.lpmas.sfhn.console.business.MajorTypeBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class MajorTypeManage
 */
@WebServlet("/sfhn/MajorTypeManage.do")
public class MajorTypeManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MajorTypeManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MajorTypeManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int majorId = ParamKit.getIntParameter(request, "majorId", 0);

		MajorTypeBean bean = new MajorTypeBean();
		MajorTypeBusiness business = new MajorTypeBusiness();

		if (majorId > 0) {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getMajorTypeByKey(majorId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("adminUserHelper", adminUserHelper);
		request.setAttribute("MajorTypeBean", bean);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "/MajorTypeManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		MajorTypeBean bean = new MajorTypeBean();
		try {
			bean = BeanKit.request2Bean(request, MajorTypeBean.class);
			MajorTypeBusiness business = new MajorTypeBusiness();
			int result = 0;
			// 设置区域的等级
			if (StringKit.isValid(bean.getRegion())) {
				bean.setMajorLevel(MajorConfig.ADMIN_LEVEL_REGION);
			} else if (StringKit.isValid(bean.getCity())) {
				bean.setMajorLevel(MajorConfig.ADMIN_LEVEL_CITY);
			} else if (StringKit.isValid(bean.getProvince())) {
				bean.setMajorLevel(MajorConfig.ADMIN_LEVEL_PROVINCE);
			} else {
				bean.setMajorLevel(MajorConfig.ADMIN_LEVEL_COUNTRY);
			}
			ReturnMessageBean messageBean = business.verifyMajorType(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			if (bean.getMajorId() > 0) {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminHelper.getAdminUserId());
				result = business.updateMajorType(bean);
			} else {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminHelper.getAdminUserId());
				result = business.addMajorType(bean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/MajorTypeList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}