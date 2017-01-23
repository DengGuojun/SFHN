package com.lpmas.sfhn.console.declare.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryTypeBusiness;
import com.lpmas.sfhn.console.declare.business.JobInfoBusiness;
import com.lpmas.sfhn.console.declare.business.JobTypeBusiness;
import com.lpmas.sfhn.console.declare.business.NationalCertificationBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareInfoResource;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;
import com.lpmas.sfhn.declare.bean.NationalCertificationBean;

@WebServlet("/sfhn/DeclareReportManage.do")
public class DeclareReportManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareReportManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.SEARCH)) {
			return;
		}
		int declareId = ParamKit.getIntParameter(request, "declareId", 0);
		if (declareId == 0) {
			HttpResponseKit.alertMessage(response, "申报ID缺失!", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 查出对应的BEAN
		DeclareReportBusiness business = new DeclareReportBusiness();
		DeclareReportBean declareReportBean = business.getDeclareReportByKey(String.valueOf(declareId));
		// 查工种类型和岗位
		if (declareReportBean.getJobType() != 0) {
			JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
			JobTypeBean jobTypeBean = jobTypeBusiness.getJobTypeByKey(declareReportBean.getJobType());
			if (jobTypeBean != null) {
				String jobType = jobTypeBean.getTypeName();
				request.setAttribute("JobType", jobType);
			}
		}
		if (declareReportBean.getJobName() != 0) {
			JobInfoBusiness jobInfoBusiness = new JobInfoBusiness();
			JobInfoBean jobInfoBean = jobInfoBusiness.getJobInfoByKey(declareReportBean.getJobName());
			if (jobInfoBean != null) {
				String jobName = jobInfoBean.getJobName();
				request.setAttribute("JobName", jobName);
			}
		}
		// 查国家资格证书
		if (declareReportBean.getHasNationalCertification() != 0) {
			NationalCertificationBusiness nationalCertificationBusiness = new NationalCertificationBusiness();
			List<NationalCertificationBean> nationalCertificationList = nationalCertificationBusiness
					.getNationalCertificationAllList();
			Map<Integer, NationalCertificationBean> nationalCertificationMap = new HashMap<Integer, NationalCertificationBean>();
			if (!nationalCertificationList.isEmpty()) {
				nationalCertificationMap = ListKit.list2Map(nationalCertificationList, "certificateId");
			}
			request.setAttribute("nationalCertificationMap", nationalCertificationMap);
		}
		// 查产业类型和产业
		if (declareReportBean.getIndustryTypeId1() != 0) {
			IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
			List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
			Map<Integer, IndustryTypeBean> industryTypeMap = new HashMap<Integer, IndustryTypeBean>();
			if (!industryTypeList.isEmpty()) {
				industryTypeMap = ListKit.list2Map(industryTypeList, "typeId");
			}
			request.setAttribute("industryTypeMap", industryTypeMap);
		}
		if (declareReportBean.getIndustryId1() != 0) {
			IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
			List<IndustryInfoBean> industryInfoList = industryInfoBusiness.getIndustryInfoAllList();
			Map<Integer, IndustryInfoBean> industryInfoMap = new HashMap<Integer, IndustryInfoBean>();
			if (!industryInfoList.isEmpty()) {
				industryInfoMap = ListKit.list2Map(industryInfoList, "industryId");
			}
			request.setAttribute("industryInfoMap", industryInfoMap);
		}

		// 放到页面
		request.setAttribute("DeclareReportBean", declareReportBean);
		request.setAttribute("AdminUserHelper", adminUserHelper);
		// 请求转发
		RequestDispatcher rd = request
				.getRequestDispatcher(SfhnConsoleConfig.PAGE_PATH + "declare/DeclareReportManage.jsp");
		rd.forward(request, response);
	}

}
