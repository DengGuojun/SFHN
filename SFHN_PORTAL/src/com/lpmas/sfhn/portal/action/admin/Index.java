package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

/**
 * Servlet implementation class Index
 */
@WebServlet("/sfhn/admin/Index.do")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
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
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();

		// 查出这个人是什么身份
		OrganizationUserBusiness business = new OrganizationUserBusiness();
		OrganizationUserBean orgBean = business.getOrganizationUserByUserId(userId);

		// 判断ID是否合法
		if (orgBean == null || !(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()
				|| InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION == orgBean.getInfoType())) {
			HttpResponseKit.alertMessage(response, "你没有权限操作这个数据", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		//判断用户的省市区信息
		String province = null;
		String city = null;
		String region = null;
		boolean isGovernment = false;
		if (orgBean != null && InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()) {
			isGovernment = true;
			GovernmentOrganizationInfoBusiness governmentOrganizationInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean govBean = governmentOrganizationInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgBean.getOrganizationId());
			province = govBean.getProvince();
			city = govBean.getCity();
			region = govBean.getRegion();
			request.setAttribute("GovernmentOrgInfoBean", govBean);//页面需要判断政府部门级别
		}else{
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgBean.getOrganizationId());
			province = trainingOrgBean.getProvince();
			city = trainingOrgBean.getCity();
			region = trainingOrgBean.getRegion();
		}

		// 处理待办通知
		HashMap<String, String> messageCondMap = new HashMap<String, String>();
		messageCondMap = new HashMap<String, String>();
		messageCondMap.put("receiveOrganizationType", String.valueOf(orgBean.getInfoType()));
		messageCondMap.put("receiveOrganizationId", String.valueOf(orgBean.getOrganizationId()));
		messageCondMap.put("isRead", String.valueOf(Constants.STATUS_NOT_VALID));
		messageCondMap.put("status", String.valueOf(Constants.STATUS_VALID));

		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		List<MessageInfoBean> messageInfoList = messageInfoBusiness.getMessageInfoListByMap(messageCondMap);
		request.setAttribute("MessageInfoList", messageInfoList);
		request.setAttribute("unreadMessageCount", messageInfoList.size());

		
		//查询开班情况
		HashMap<String,String> classCondMap = new HashMap<String,String>();
		classCondMap.put("province", province);
		classCondMap.put("city", city);
		classCondMap.put("region", region);
		classCondMap.put("openStatus", TrainingClassInfoConfig.OPEN_STATUS_OPENED);
		classCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		classCondMap.put("trainingType", String.valueOf(DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER));
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		int count1 = trainingClassInfoBusiness.getTrainingClassInfoCountByMap(classCondMap);
		classCondMap.put("trainingType", String.valueOf(DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER));
		int count2 = trainingClassInfoBusiness.getTrainingClassInfoCountByMap(classCondMap);
		classCondMap.put("trainingType", String.valueOf(DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER));
		int count3 = trainingClassInfoBusiness.getTrainingClassInfoCountByMap(classCondMap);
		classCondMap.put("trainingType", String.valueOf(DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER));
		int count4 = trainingClassInfoBusiness.getTrainingClassInfoCountByMap(classCondMap);
		classCondMap.put("trainingType", String.valueOf(DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER));
		int count5 = trainingClassInfoBusiness.getTrainingClassInfoCountByMap(classCondMap);
		request.setAttribute("Count1", count1);
		request.setAttribute("Count2", count2);
		request.setAttribute("Count3", count3);
		request.setAttribute("Count4", count4);
		request.setAttribute("Count5", count5);
		

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "Index.jsp");
	}
}
