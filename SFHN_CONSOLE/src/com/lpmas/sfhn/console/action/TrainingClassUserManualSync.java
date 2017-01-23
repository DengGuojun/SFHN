package com.lpmas.sfhn.console.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.transfer.HttpClientKit;
import com.lpmas.framework.transfer.HttpClientResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.console.business.ActiveCodeInfoBusiness;
import com.lpmas.sfhn.console.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.console.config.ManualSyncConfig;
import com.lpmas.sfhn.console.config.SfhnResource;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomMemberAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeHelper;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

/**
 * Servlet implementation class TrainingClassUserManualSync
 */
@WebServlet("/sfhn/TrainingClassUserManualSync.do")
public class TrainingClassUserManualSync extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserManualSync() {
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
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
			return;
		}

		int declareId = ParamKit.getIntParameter(request, "declareId", 0);
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		TrainingClassUserBean userBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId, declareId);
		if (userBean == null || userBean.getSyncStatus() != Constants.STATUS_NOT_VALID
				|| !userBean.getUserStatus().equals(ManualSyncConfig.YUN_CLASS_USER_APPROVE_STATUS)) {
			HttpResponseKit.alertMessage(response, "学员不存在或者已经同步", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		DeclareReportBean declareReportBean = declareReportBusiness.getDeclareReportByKey(String.valueOf(declareId));

		// 判断是否已经绑定了激活码
		ActiveCodeInfoBusiness activeCodeBusiness = new ActiveCodeInfoBusiness();
		Map<String, String> condMap = new HashMap<String, String>();
		condMap.put("userType", String.valueOf(ActiveCodeInfoConfig.USER_TYPE_FARMER));
		condMap.put("userId", String.valueOf(declareReportBean.getUserId()));
		condMap.put("trainingYear", DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR));
		List<ActiveCodeInfoBean> activeCodeInfoList = activeCodeBusiness.getActiveCodeInfoListByMap(condMap);
		if (activeCodeInfoList.isEmpty()) {
			HttpResponseKit.alertMessage(response, "找不到学员的激活码", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 开始同步
		// 获取URL
		StringBuffer url = YunClassInvokeHelper.getUrl("POST", YunClassInvokeConfig.YUN_SERVICE_ADD_USER_TO_CLASS,
				YunClassInvokeConfig.IS_DEBUG_MODE);
		// Http同步请求
		HttpClientKit clientKit = new HttpClientKit();
		String content = JsonKit.toJson(trainingClassUserBusiness.trainingClassUser2MemberAddBean(userBean,
				activeCodeInfoList.get(0).getActiveCode()));
		String message = "";
		try {
			HttpClientResultBean httpResult = clientKit
					.postContent(url.toString(), content, Constants.ENCODING_UNICODE);
			if (httpResult.getResult()) {
				// 请求成功
				// 处理结果
				YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
						YunClassInvokeResponseBean.class);
				if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
					// 云课堂那边已经请求成功了
					// 成功就要取出数据
					ClassRoomMemberAddBean postResult = JsonKit.toBean(JsonKit.toJson(responseBean.getData()),
							ClassRoomMemberAddBean.class);
					// 更新同步状态
					int result = 0;
					TrainingClassUserBusiness userBusiness = new TrainingClassUserBusiness();
					TrainingClassUserBean user = userBusiness.getTrainingClassUserByKey(classId, declareId);
					user.setSyncStatus(Constants.STATUS_VALID);
					result = userBusiness.updateTrainingClassUser(user);
					if (result > 0) {
						message = "同步成功";
					} else {
						message = "请求云课堂URL:" + url.toString() + ",同步失败";
					}
				} else {
					// 云课堂反馈请求失败
					message = "请求云课堂URL:" + url.toString() + ",失败,失败代码:" + responseBean.getCode() + ",消息:"
							+ responseBean.getMessage() + ",LogId:" + responseBean.getLogid();
				}
			} else {
				message = "请求云课堂URL:" + url.toString() + ",请求失败!";
			}
		} catch (Exception e) {
			message = "请求云课堂URL:" + url.toString() + ",失败，发生异常";
			e.printStackTrace();
		}

		HttpResponseKit.alertMessage(response, message, "TrainingClassUserManualSyncList.do");
	}

}
