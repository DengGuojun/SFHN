package com.lpmas.sfhn.portal.action.user;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerContactInfoBusiness;

/**
 * Servlet implementation class TrainingClassInfoList
 */
@WebServlet("/sfhn/TrainingClassInfoList.do")
public class TrainingClassInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoList() {
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
		int declareType = ParamKit.getIntParameter(request, "declareType", 0);
		if (declareType <= 0) {
			HttpResponseKit.alertMessage(response, "申报类型必须填写", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (!DeclareInfoConfig.DECLARE_TYPE_MAP.containsKey(declareType)) {
			HttpResponseKit.alertMessage(response, "申报类型非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();

		// 获取申报信息与联系地址
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId,
				DateKit.formatDate(new Date(), DateKit.REGEX_YEAR));
		if (declareInfoBean == null) {
			HttpResponseKit.alertMessage(response, "申报信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取地址信息
		FarmerContactInfoBusiness contactInfoBusiness = new FarmerContactInfoBusiness();
		FarmerContactInfoBean contactInfoBean = contactInfoBusiness.getFarmerContactInfoByKey(declareInfoBean
				.getDeclareId());
		if (contactInfoBean == null) {
			HttpResponseKit.alertMessage(response, "联系信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取培育班列表
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("province", contactInfoBean.getProvince());
		condMap.put("trainingType", String.valueOf(declareType));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("classStatusSelection", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
		condMap.put("trainingYear", DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR));
		condMap.put("orderBy", "registration_end_time desc");
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(condMap);

		request.setAttribute("DeclareInfoBean", declareInfoBean);
		request.setAttribute("ContactInfoBean", contactInfoBean);
		request.setAttribute("TrainingClassInfoList", trainingClassInfoList);

		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "TrainingClassInfoList.jsp");
	}

}
