package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;

@WebServlet("/sfhn/admin/TrainingClassUserEvaluateManage.do")
public class TrainingClassUserEvaluateManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserEvaluateManage() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReturnMessageBean messageBean = new ReturnMessageBean();
		String checkStrexamResultApprove = ParamKit.getParameter(request, "checkStrexamResultApprove", "").trim();
		String checkStrauthResultApprove = ParamKit.getParameter(request, "checkStrauthResultApprove", "").trim();
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
		// 用户权限校验
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		if (!trainingClassInfoBusiness.hasPermission(classId, userId)) {
			HttpResponseKit.alertMessage(response, "你没有权限操作这个数据", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<Integer, TrainingClassUserBean> trainingClassUserMap = new HashMap<Integer, TrainingClassUserBean>();
		if (StringKit.isValid(checkStrexamResultApprove)) {
			String[] sourceStrArray = checkStrexamResultApprove.split(",");
			for (int i = 0; i < sourceStrArray.length; ++i) {
				TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId,
						Integer.parseInt(sourceStrArray[i]));
				if (trainingClassUserBean == null) {
					messageBean.setMessage("学员为空，请刷新重试");
					HttpResponseKit.printJson(request, response, messageBean, "");
					return;
				} else {
					trainingClassUserBean.setExamResult(Constants.STATUS_VALID);
					trainingClassUserMap.put(Integer.parseInt(sourceStrArray[i]), trainingClassUserBean);
				}

			}

		}
		if (StringKit.isValid(checkStrauthResultApprove)) {
			String[] sourceStrArray = checkStrauthResultApprove.split(",");
			for (int i = 0; i < sourceStrArray.length; ++i) {
				if (trainingClassUserMap.containsKey(Integer.parseInt(sourceStrArray[i]))) {
					TrainingClassUserBean trainingClassUserBean = trainingClassUserMap.get(Integer.parseInt(sourceStrArray[i]));
					trainingClassUserBean.setAuthResult(Constants.STATUS_VALID);
					trainingClassUserMap.put(Integer.parseInt(sourceStrArray[i]), trainingClassUserBean);
				} else {
					TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId,
							Integer.parseInt(sourceStrArray[i]));
					if (trainingClassUserBean == null) {
						messageBean.setMessage("学员为空，请刷新重试");
						HttpResponseKit.printJson(request, response, messageBean, "");
						return;
					} else {
						trainingClassUserBean.setAuthResult(Constants.STATUS_VALID);
						trainingClassUserMap.put(Integer.parseInt(sourceStrArray[i]), trainingClassUserBean);
					}
				}
			}
		}
		int result = 0;
		for (Integer key : trainingClassUserMap.keySet()) {
			result = trainingClassUserBusiness.updateTrainingClassUser(trainingClassUserMap.get(key));
			if (result < 0) {
				messageBean.setMessage("处理失败");
				HttpResponseKit.printJson(request, response, messageBean, "");
				return;
			}
		}
		if (result >= 0) {
			messageBean.setCode(Constants.STATUS_VALID);
			messageBean.setMessage("处理成功");
		} else {
			messageBean.setMessage("处理失败");
		}
		HttpResponseKit.printJson(request, response, messageBean, "");
		return;
	}
}
