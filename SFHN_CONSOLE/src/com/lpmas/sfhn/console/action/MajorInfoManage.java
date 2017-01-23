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
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.console.business.MajorInfoBusiness;
import com.lpmas.sfhn.console.business.MajorTypeBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

/**
 * Servlet implementation class MajorInfoManage
 */
@WebServlet("/sfhn/MajorInfoManage.do")
public class MajorInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MajorInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MajorInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		int majorId = ParamKit.getIntParameter(request, "majorId", 0);
		int typeId = ParamKit.getIntParameter(request, "typeId", 0);

		MajorInfoBean bean = new MajorInfoBean();
		MajorInfoBusiness business = new MajorInfoBusiness();
		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
		if (majorId > 0) {
			if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getMajorInfoByKey(majorId);
			typeId = bean.getTypeId();
		} else {
			if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		MajorTypeBean typeBean = majorTypeBusiness.getMajorTypeByKey(typeId);
		request.setAttribute("MajorInfoBean", bean);
		request.setAttribute("MajorTypeBean", typeBean);
		request.setAttribute("AdminUserHelper", adminHelper);

		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "/MajorInfoManage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminHelper = new AdminUserHelper(request, response);

		MajorInfoBean bean = new MajorInfoBean();
		try {
			bean = BeanKit.request2Bean(request, MajorInfoBean.class);
			MajorInfoBusiness business = new MajorInfoBusiness();
			ReturnMessageBean messageBean = business.verifyMajorInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getMajorId() > 0) {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminHelper.getAdminUserId());
				result = business.updateMajorInfo(bean);
			} else {
				if (!adminHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminHelper.getAdminUserId());
				result = business.addMajorInfo(bean);
			}
			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/MajorInfoList.do?typeId="+bean.getTypeId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
