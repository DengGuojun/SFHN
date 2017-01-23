package com.lpmas.sfhn.console.action;

import java.io.IOException;

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
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.console.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.console.config.ManualSyncConfig;
import com.lpmas.sfhn.console.config.SfhnResource;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeHelper;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

/**
 * Servlet implementation class TrainingClassInfoManualSync
 */
@WebServlet("/sfhn/TrainingClassInfoManualSync.do")
public class TrainingClassInfoManualSync extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoManualSync() {
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
		if (!adminUserHelper.checkPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)) {
			return;
		}

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (classInfoBean == null || classInfoBean.getSyncStatus() != Constants.STATUS_NOT_VALID
				|| !classInfoBean.getClassStatus().equals(ManualSyncConfig.YUN_CLASS_OPEN_STATUS)) {
			HttpResponseKit.alertMessage(response, "班级不存在或者已经同步成功不能再同步", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 开始同步
		// 获取URL
		StringBuffer url = YunClassInvokeHelper.getUrl("POST", YunClassInvokeConfig.YUN_SERVICE_ADD_CLASS,
				YunClassInvokeConfig.IS_DEBUG_MODE);
		ClassRoomAddBean addBean = trainingClassInfoBusiness.TrainingClassInfo2ClassRoomAddBean(classInfoBean);
		// Http同步请求
		HttpClientKit clientKit = new HttpClientKit();
		String content = JsonKit.toJson(addBean);
		String message = "";
		try {
			HttpClientResultBean httpResult = clientKit.postContent(url.toString(), content,
					Constants.ENCODING_UNICODE);
			if (httpResult.getResult()) {
				// 请求成功
				// 处理结果
				YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
						YunClassInvokeResponseBean.class);
				if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
					// 云课堂那边已经请求成功了
					// 成功就要取出数据
					int result = 0;
					ClassRoomAddBean postResult = JsonKit.toBean(JsonKit.toJson(responseBean.getData()),
							ClassRoomAddBean.class);
					// 更新到数据库
					TrainingClassInfoBean yunClass = trainingClassInfoBusiness
							.getTrainingClassInfoByKey(Integer.valueOf(postResult.getClassroomId()));
					yunClass.setSyncStatus(Constants.STATUS_VALID);
					yunClass.setEduClassId(Integer.valueOf(postResult.getReClassroomId()));
					result = trainingClassInfoBusiness.updateTrainingClassInfo(yunClass);
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
				message = "请求云课堂URL:" + url.toString() + ",请求失败";
			}
		} catch (Exception e) {
			message = "请求云课堂URL:" + url.toString() + ",失败，发生异常：" + e.getMessage();
		}

		HttpResponseKit.alertMessage(response, message, "TrainingClassInfoManualSyncList.do");
	}

}
