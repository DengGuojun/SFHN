package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.transfer.HttpClientKit;
import com.lpmas.framework.transfer.HttpClientResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.invoker.bean.ClassroomLiveStatusBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeHelper;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

/**
 * Servlet implementation class TrainingClassAdminMonitor
 */
@WebServlet("/sfhn/admin/TrainingClassInfoMonitor.do")
public class TrainingClassInfoMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TrainingClassInfoMonitor.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoMonitor() {
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
		// 获取用户ID
		SsoClientHelper clientHelper = new SsoClientHelper(request);
		int userId = clientHelper.getUserId();

		Boolean isBroadcasting = false;
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		// 查出对应的课程
		TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(classId);
		if (classInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级不存在或者还没通过审批!", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 权限判断
		OrganizationUserBusiness organizationUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean organizationUserBean = organizationUserBusiness.getOrganizationUserByUserId(userId);
		if (organizationUserBean == null
				|| organizationUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else {
			if (!classInfoBusiness.hasPermission(classId, userId)) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}
		// 获取云课堂对应的课程ID
		int yunClassId = classInfoBean.getEduClassId();
		// 调用云课堂
		// 获取请求URL
		StringBuffer url = YunClassInvokeHelper.getUrl(YunClassInvokeExecutor.HTTP_GET,
				YunClassInvokeConfig.YUN_SERVICE_CLASSROOM_LIVE_STATUS, YunClassInvokeConfig.IS_DEBUG_MODE);
		url.append("?classId=" + yunClassId);
		HttpClientKit httpClient = new HttpClientKit();
		HttpClientResultBean httpResult = httpClient.getContent(url.toString(), "UTF-8");
		String playurl = "";
		if (httpResult.getResult()) {
			try {
				YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
						YunClassInvokeResponseBean.class);
				if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
					// 取出DATA，此时DATA是MAP
					Object data = responseBean.getData();
					if (data != null) {
						@SuppressWarnings("unchecked")
						Map<Object, Object> dataMap = (Map<Object, Object>) data;
						ClassroomLiveStatusBean[] classroomStatus = JsonKit.getObjectMapper()
								.readValue(JsonKit.toJson(dataMap.get("classrooms")), ClassroomLiveStatusBean[].class);
						for (ClassroomLiveStatusBean bean : classroomStatus) {
							if (yunClassId == Integer.valueOf(bean.getClassid())) {
								playurl = bean.getPlayurl();
								isBroadcasting = true;
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		SsoClientHelper helper = new SsoClientHelper(request);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("isBroadcasting", isBroadcasting);
		request.setAttribute("classInfoBean", classInfoBean);
		request.setAttribute("playurl", playurl);
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassInfoMonitor.jsp");
	}

}
