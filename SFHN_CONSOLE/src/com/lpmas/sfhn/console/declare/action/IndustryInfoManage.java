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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryTypeBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;

@WebServlet("/sfhn/IndustryInfoManage.do")
public class IndustryInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(IndustryInfoManage.class);
	private static final long serialVersionUID = 1L;

	public IndustryInfoManage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		int industryId = ParamKit.getIntParameter(request, "industryId", 0);
		boolean readOnly = ParamKit.getBooleanParameter(request, "readOnly", false);
		IndustryInfoBean bean = new IndustryInfoBean();
		IndustryInfoBusiness business = new IndustryInfoBusiness();
		// 获取产业类型分类
		IndustryTypeBusiness industryTypebusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypebusiness.getIndustryTypeAllList();
		request.setAttribute("IndustryTypeList", industryTypeList);
		IndustryTypeBean industryTypeBean = new IndustryTypeBean();
		if (industryId > 0) {
			if (!readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
				return;
			}
			if (readOnly
					&& !adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
				return;
			}
			bean = business.getIndustryInfoByKey(industryId);
			// 获取产业类型名称
			industryTypeBean = industryTypebusiness.getIndustryTypeByKey(bean.getTypeId());
		} else {
			if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
				return;
			}
			bean.setStatus(Constants.STATUS_VALID);
		}
		request.setAttribute("IndustryInfoBean", bean);
		request.setAttribute("IndustryTypeBean", industryTypeBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/IndustryInfoManage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		IndustryInfoBean bean = new IndustryInfoBean();
		try {
			bean = BeanKit.request2Bean(request, IndustryInfoBean.class);
			IndustryInfoBusiness business = new IndustryInfoBusiness();
			int result = 0;
			if (bean.getIndustryId() > 0) {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)) {
					return;
				}
				bean.setModifyUser(adminUserHelper.getAdminUserId());
				result = business.updateIndustryInfo(bean);
			} else {
				if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)) {
					return;
				}
				bean.setCreateUser(adminUserHelper.getAdminUserId());
				result = business.addIndustryInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/IndustryInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
