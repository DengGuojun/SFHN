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
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.JobTypeBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.JobTypeBean;

@WebServlet("/sfhn/JobTypeManage.do")
public class JobTypeManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(IndustryTypeManage.class);
	private static final long serialVersionUID = 1L;

	public JobTypeManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int typeId = ParamKit.getIntParameter(request, "typeId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		JobTypeBean bean = new JobTypeBean();
		JobTypeBusiness business = new JobTypeBusiness();
		if (typeId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getJobTypeByKey(typeId);
		} else {
			if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("JobTypeBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/JobTypeManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		JobTypeBean bean = new JobTypeBean();
		try {
			bean = BeanKit.request2Bean(request, JobTypeBean.class);
			JobTypeBusiness business = new JobTypeBusiness();
			ReturnMessageBean messageBean = business.verifyJobType(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getTypeId() > 0) {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateJobType(bean);
			} else {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addJobType(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/JobTypeList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
