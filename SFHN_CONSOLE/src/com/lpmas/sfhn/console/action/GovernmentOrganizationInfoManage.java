package com.lpmas.sfhn.console.action;

import java.io.IOException;
import java.util.HashMap;
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
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.console.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.console.business.OrganizationUserBusiness;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.config.SfhnResource;

@WebServlet("/sfhn/GovernmentOrganizationInfoManage.do")
public class GovernmentOrganizationInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(GovernmentOrganizationInfoManage.class);
	private static final long serialVersionUID = 1L;

	public GovernmentOrganizationInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		int organizationId = ParamKit.getIntParameter(request, "organizationId", 0);
		GovernmentOrganizationInfoBean bean = new GovernmentOrganizationInfoBean();
		GovernmentOrganizationInfoBusiness business = new GovernmentOrganizationInfoBusiness();
		OrganizationUserBusiness organizationUserBusiness = new OrganizationUserBusiness();
		String userId = "";
		if (organizationId > 0) {
			if (!readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly && !adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getGovernmentOrganizationInfoByKey(organizationId);
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("organizationId", String.valueOf(organizationId));
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION));
			List<OrganizationUserBean> organizationUserList = organizationUserBusiness
					.getOrganizationUserListByMap(condMap);
			for (OrganizationUserBean userBean : organizationUserList) {
				userId += userBean.getUserId() + ",";
			}
			if (userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
		} else {
			if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("GovernmentOrganizationInfoBean", bean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		request.setAttribute("userId", userId);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				SfhnConsoleConfig.PAGE_PATH + "GovernmentOrganizationInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		GovernmentOrganizationInfoBean bean = new GovernmentOrganizationInfoBean();
		try {
			String userIds = ParamKit.getParameter(request, "userId", "");
			bean = BeanKit.request2Bean(request, GovernmentOrganizationInfoBean.class);
			GovernmentOrganizationInfoBusiness business = new GovernmentOrganizationInfoBusiness();
			ReturnMessageBean messageBean = business.verifyGovernmentOrganizationInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			OrganizationUserBusiness organizationUserBusiness = new OrganizationUserBusiness();
			OrganizationUserBean organizationUserBean = new OrganizationUserBean();
			int result = 0;
			if (bean.getOrganizationId() > 0) {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateGovernmentOrganizationInfo(bean);
				HashMap<String, String> condMap = new HashMap<String, String>();
				condMap.put("organizationId", String.valueOf(bean.getOrganizationId()));
				condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION));
				List<OrganizationUserBean> organizationUserList = organizationUserBusiness
						.getOrganizationUserListByMap(condMap);
				for (OrganizationUserBean userBean : organizationUserList) {
					organizationUserBusiness.deleteOrganizationUser(userBean);
				}
				if (bean.getStatus() == Constants.STATUS_VALID) {
					String[] userIdArray = userIds.split(",");
					for (String userIdStr : userIdArray) {
						organizationUserBean.setCreateUser(adminUserHelper.getAdminUserId());
						organizationUserBean.setUserId(Integer.valueOf(userIdStr));
						organizationUserBean.setOrganizationId(bean.getOrganizationId());
						organizationUserBean.setInfoType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						organizationUserBusiness.addOrganizationUser(organizationUserBean);
					}
				}
			} else {
				if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addGovernmentOrganizationInfo(bean);
				if (bean.getStatus() == Constants.STATUS_VALID) {
					String[] userIdArray = userIds.split(",");
					for (String userIdStr : userIdArray) {
						organizationUserBean.setCreateUser(adminUserHelper.getAdminUserId());
						organizationUserBean.setUserId(Integer.valueOf(userIdStr));
						organizationUserBean.setOrganizationId(bean.getOrganizationId());
						organizationUserBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
						organizationUserBusiness.addOrganizationUser(organizationUserBean);
					}
				}
			}

			if (result > 0 ) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/GovernmentOrganizationInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
