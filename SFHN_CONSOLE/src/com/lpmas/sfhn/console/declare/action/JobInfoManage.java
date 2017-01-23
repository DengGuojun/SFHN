package com.lpmas.sfhn.console.declare.action;

import java.io.IOException;
import java.util.List;

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
import com.lpmas.sfhn.console.declare.business.JobInfoBusiness;
import com.lpmas.sfhn.console.declare.business.JobTypeBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;

@WebServlet("/sfhn/JobInfoManage.do")
public class JobInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(JobInfoManage.class);
	private static final long serialVersionUID = 1L;

	public JobInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int jobId = ParamKit.getIntParameter(request, "jobId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		JobInfoBean bean = new JobInfoBean();
		JobInfoBusiness business = new JobInfoBusiness();
		// 获取工作类型分类
		JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
		List<JobTypeBean> jobTypeList = jobTypeBusiness.getJobTypeAllList();
		request.setAttribute("JobTypeList", jobTypeList);
		JobTypeBean jobTypeBean = new JobTypeBean();
		if (jobId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getJobInfoByKey(jobId);
			// 获取产业类型名称
			jobTypeBean = jobTypeBusiness.getJobTypeByKey(bean.getTypeId());
		} else {
			if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("JobInfoBean", bean);
		request.setAttribute("JobTypeBean", jobTypeBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/JobInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		JobInfoBean bean = new JobInfoBean();
		try {
			bean = BeanKit.request2Bean(request, JobInfoBean.class);
			JobInfoBusiness business = new JobInfoBusiness();
			ReturnMessageBean messageBean = business.verifyJobInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getJobId() > 0) {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateJobInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addJobInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/JobInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
