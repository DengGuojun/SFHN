package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokCallBack;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvoker;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

/**
 * Servlet implementation class TrainingClassInfoApprove
 */
@WebServlet("/sfhn/admin/TrainingClassInfoOpen.do")
public class TrainingClassInfoOpen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoOpen() {
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

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		if (classId <= 0) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 根据班级ID查询班级
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (classInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		
		if (!classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
			HttpResponseKit.alertMessage(response, "审核未通过，不能进行开班", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 用户权限校验
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		if(classInfoBean.getCreateUser() != userId){
			HttpResponseKit.alertMessage(response, "你不是培训班的创建者，不能进行开班", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		
		classInfoBean.setClassStatus(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
		classInfoBean.setModifyUser(userId);

		int result = trainingClassInfoBusiness.updateTrainingClassInfo(classInfoBean);

		if (result > 0 ) {
			// 推送消息到云课堂
			YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
			commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
			commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_CLASS);
			commandBean.setBody(trainingClassInfoBusiness.TrainingClassInfo2ClassRoomAddBean(classInfoBean));

			YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
				@Override
				public boolean process(Object data) {
					int result = 0;
					try {
						ClassRoomAddBean postResult = JsonKit.toBean(data.toString(), ClassRoomAddBean.class);
						// 更新到数据库
						TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
						TrainingClassInfoBean yunClass = trainingClassInfoBusiness.getTrainingClassInfoByKey(Integer
								.valueOf(postResult.getClassroomId()));
						yunClass.setSyncStatus(Constants.STATUS_VALID);
						yunClass.setEduClassId(Integer.valueOf(postResult.getReClassroomId()));
						result = trainingClassInfoBusiness.updateTrainingClassInfo(yunClass);
						if (result > 0) {
							return true;
						} else {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			YunClassInvokeExecutor.attachAsync(invoker);
		}

		// 存在操作成功的MEMBER当成成功
		if (result > 0) {
			HttpResponseKit.alertMessage(response, "操作成功", "TrainingClassInfoList.do");
			return;
		} else {
			HttpResponseKit.alertMessage(response, "操作失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}
}
