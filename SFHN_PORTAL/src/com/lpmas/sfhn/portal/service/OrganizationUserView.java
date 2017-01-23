package com.lpmas.sfhn.portal.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.service.bean.OrganizationUserResponseBean;

/**
 * Servlet implementation class OrganizationUserView
 */
@WebServlet("/sfhn/admin/OrganizationUserView.action")
public class OrganizationUserView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrganizationUserView() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		ReturnMessageBean result = new ReturnMessageBean();
		
		// 获取用户Id
		int userId = ParamKit.getIntParameter(request, "userId", 0);
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			result.setCode(Constants.STATUS_NOT_VALID);
			result.setMessage("参数错误");
		}else{
			OrganizationUserResponseBean bean = new OrganizationUserResponseBean();
			bean.setInfoType(orgUserBean.getInfoType());
			if(orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION){
				GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
				GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
						.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
				bean.setOrganizationLevel(governmentOrgInfoBean.getOrganizationLevel());
				bean.setOrganizationName(governmentOrgInfoBean.getOrganizationName());
			}else{
				TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
				TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrganizationInfoBusiness.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
				bean.setOrganizationName(trainingOrgInfoBean.getOrganizationName());
			}
			
			result.setCode(Constants.STATUS_VALID);
			result.setContent(bean);
		}
		
		HttpResponseKit.printJson(request, response, result, "");
	}

}
