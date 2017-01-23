package com.lpmas.sfhn.portal.action.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.business.ActiveCodeInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerContactInfoBusiness;
import com.lpmas.sfhn.portal.declare.handler.DeclareReportHandler;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomMemberAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokCallBack;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvoker;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

/**
 * Servlet implementation class TrainingClassManage
 */
@WebServlet("/sfhn/TrainingClassManage.do")
public class TrainingClassManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TrainingClassManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		// 获取用户ID
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();

		// 检查这个用户是否填完表并提交了
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId,
				DateKit.formatDate(new Date(), DateKit.REGEX_YEAR));
		if (declareInfoBean == null) {
			HttpResponseKit.alertMessage(response, "类型非法，不能选择此班", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_EDIT)) {
			HttpResponseKit.alertMessage(response, "未申报或申报不完整 不能报名这个课程", "DeclareInfoManage.do?declareType="
					+ declareInfoBean.getDeclareType());
			return;
		}

		// 查看这个用户是不是报名了
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId,
				declareInfoBean.getDeclareId());

		// 获取培训机构
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrgBusiness
				.getTrainingOrganizationInfoByKey(classInfoBean.getOrganizationId());

		request.setAttribute("TrainingOrgInfoBean", trainingOrgInfoBean);
		request.setAttribute("TrainingClassInfoBean", classInfoBean);
		request.setAttribute("TrainingClassUserBean", trainingClassUserBean);

		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "TrainingClassManage.jsp");
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
		} else {
			Timestamp now = DateKit.getCurrentTimestamp();
			if (now.after(classInfoBean.getRegistrationEndTime())) {
				// 超过的报名时间
				HttpResponseKit.alertMessage(response, "此课程报名时间已过", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}

		// 获取用户ID
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();

		// 检查是不是政府或者培训机构(配置项ENABLE_ORGANIZATION_TRAINING)
		if (!TrainingClassUserConfig.ENABLE_ORGANIZATION_TRAINING) {
			OrganizationUserBusiness organizationUserBusiness = new OrganizationUserBusiness();
			OrganizationUserBean organizationUserBean = organizationUserBusiness.getOrganizationUserByUserId(userId);
			if (organizationUserBean != null) { // 是政府或者培训机构
				HttpResponseKit.alertMessage(response, "对不起,政府人员与培训机构不能参加培训班", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}

		// 检查这个用户的申报是否通过了
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId,
				DateKit.formatDate(new Date(), DateKit.REGEX_YEAR));
		if (declareInfoBean == null || declareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_EDIT)) {
			HttpResponseKit.alertMessage(response, "未申报或申报未通过不能报名这个课程", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 查看这个用户是不是报名了
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		TrainingClassUserBean userBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId,
				declareInfoBean.getDeclareId());

		int result = 0;
		if (userBean == null) {
			// 未报名就帮他报名
			userBean = new TrainingClassUserBean();
			userBean.setClassId(classId);
			userBean.setCreateUser(userId);
			userBean.setDeclareId(declareInfoBean.getDeclareId());
			userBean.setSignUpTime(DateKit.getCurrentTimestamp());
			userBean.setUserStatus(TrainingClassUserConfig.USER_STATUS_APPROVE);
			userBean.setStatus(Constants.STATUS_VALID);
			result = trainingClassUserBusiness.addTrainingClassUser(userBean);
			// 获取省市区
			FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
			FarmerContactInfoBean farmerContactInfoBean = farmerContactInfoBusiness
					.getFarmerContactInfoByKey(declareInfoBean.getDeclareId());
			// 获取激活码
			ActiveCodeInfoBusiness activeCodeBusiness = new ActiveCodeInfoBusiness();
			ReturnMessageBean returnMessageBean = activeCodeBusiness.bindActiveCodeWithUser(
					farmerContactInfoBean.getProvince(), farmerContactInfoBean.getCity(),
					farmerContactInfoBean.getRegion(), classInfoBean.getTrainingYear(),
					ActiveCodeInfoConfig.USER_TYPE_FARMER, userId, userId);
			if (returnMessageBean.getCode() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, returnMessageBean.getMessage(),
						HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 插入mongo
			DeclareReportHandler handler = new DeclareReportHandler();
			try {
				// 推送学员到云课堂
				YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
				commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
				commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_USER_TO_CLASS);
				commandBean.setBody(trainingClassUserBusiness.trainingClassUser2MemberAddBean(userBean,
						(String) returnMessageBean.getContent()));

				YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
					@Override
					public boolean process(Object data) {
						int result = 0;
						try {
							ClassRoomMemberAddBean postResult = JsonKit.toBean(data.toString(),
									ClassRoomMemberAddBean.class);
							// 更新到数据库
							TrainingClassUserBean userBean = trainingClassUserBusiness.getTrainingClassUserByKey(
									Integer.parseInt(postResult.getClassroomId()), declareInfoBean.getDeclareId());
							userBean.setModifyUser(userId);
							userBean.setSyncStatus(Constants.STATUS_VALID);
							result = trainingClassUserBusiness.updateTrainingClassUser(userBean);
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

				handler.createDeclareReport(declareInfoBean, classId, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("总表记录创建失败", e);
			}
		} else {
			// 如果是已经被拒绝的，就帮他改状态
			if (userBean.getUserStatus().equals(TrainingClassUserConfig.USER_STATUS_NOT_APPROVE)) {
				userBean.setUserStatus(TrainingClassUserConfig.USER_STATUS_WAIT_TRAINING_APPROVE);
				userBean.setModifyUser(userId);
				userBean.setSignUpTime(DateKit.getCurrentTimestamp());
				userBean.setStatus(Constants.STATUS_VALID);
				result = trainingClassUserBusiness.updateTrainingClassUser(userBean);
			} else {
				HttpResponseKit.alertMessage(response, "您已报名此课程,请等待审批", "TrainingClassInfoList.do?declareType="
						+ classInfoBean.getTrainingType());
				return;
			}
		}

		if (result > 0) {
			HttpResponseKit.alertMessage(response, "恭喜!您已成功报名此课程,请等待审批", "TrainingClassInfoList.do?declareType="
					+ classInfoBean.getTrainingType());
			return;
		} else {
			HttpResponseKit.alertMessage(response, "报名失败", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
	}

}
