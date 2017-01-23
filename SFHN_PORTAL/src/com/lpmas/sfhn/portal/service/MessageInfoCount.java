package com.lpmas.sfhn.portal.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;

/**
 * Servlet implementation class MessageInfoCount
 */
@WebServlet("/sfhn/admin/MessageInfoCount.action")
public class MessageInfoCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageInfoCount() {
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
		
		int userId = ParamKit.getIntParameter(request, "userId", 0);
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			result.setCode(Constants.STATUS_NOT_VALID);
			result.setMessage("参数错误");
		}else{
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap = new HashMap<String, String>();
			condMap.put("receiveOrganizationType", String.valueOf(orgUserBean.getInfoType()));
			condMap.put("receiveOrganizationId", String.valueOf(orgUserBean.getOrganizationId()));
			condMap.put("isRead", String.valueOf(Constants.STATUS_NOT_VALID));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));

			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			List<MessageInfoBean> list = messageInfoBusiness.getMessageInfoListByMap(condMap);
			
			result.setCode(Constants.STATUS_VALID);
			result.setContent(list.size());
		}
		
		HttpResponseKit.printJson(request, response, result, "");
	}

}
