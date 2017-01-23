package com.lpmas.sfhn.console.action;

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
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.console.business.TrainingClassTargetBusiness;
import com.lpmas.sfhn.console.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/TrainingClassTargetManage.do")
public class TrainingClassTargetManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassTargetManage.class);
	private static final long serialVersionUID = 1L;

	public TrainingClassTargetManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int targetId = ParamKit.getIntParameter(request, "targetId", 0);
		TrainingClassTargetBean bean = new TrainingClassTargetBean();
		TrainingClassTargetBusiness business = new TrainingClassTargetBusiness();
		int userId = 0;
		if (targetId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getTrainingClassTargetByKey(targetId);
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}

		// 获取所有培训机构
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		List<TrainingOrganizationInfoBean> trainingOrgList = trainingOrgBusiness.getTrainingOrganizationInfoALlList();

		request.setAttribute("TrainingClassTargetBean", bean);
		request.setAttribute("TrainingOrgList", trainingOrgList);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("userId", userId);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SfhnConsoleConfig.PAGE_PATH + "TrainingClassTargetManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		int result = 0;
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		TrainingClassTargetBean bean = new TrainingClassTargetBean();
		try {
			bean = BeanKit.request2Bean(request, TrainingClassTargetBean.class);
			// 判断培训指标是属于政府机构还是属于培训机构
			if (StringKit.isValid(bean.getProvince()) || StringKit.isValid(bean.getCity())
					|| StringKit.isValid(bean.getRegion())) {
				bean.setOrganizationId(0);
			}else if (bean.getOrganizationId() >0){
				bean.setProvince(null);
				bean.setCity(null);
				bean.setRegion(null);
			}else{
				HttpResponseKit.alertMessage(response, "必须选择省市区或培训机构", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			TrainingClassTargetBusiness business = new TrainingClassTargetBusiness();
			if (bean.getTargetId() > 0) {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateTrainingClassTarget(bean);
			} else {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addTrainingClassTarget(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/TrainingClassTargetList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
