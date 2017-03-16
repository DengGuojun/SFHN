package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorTypeBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.invoker.bean.TeacherAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokCallBack;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvoker;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

/**
 * Servlet implementation class TeacherInfoManage
 */
@WebServlet("/sfhn/admin/TeacherInfoManage.do")
public class TeacherInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TeacherInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION
				&& orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		String fixProvince = "";
		String fixCity = "";
		String fixRegion = "";
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			fixProvince = governmentOrgBean.getProvince();
			fixCity = governmentOrgBean.getCity();
			fixRegion = governmentOrgBean.getRegion();
			request.setAttribute("GovernmentOrgInfoBean", governmentOrgBean);
			request.setAttribute("isGovernment", true);
		} else {
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrganizationInfoBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			fixProvince = trainingOrganizationInfoBean.getProvince();
			fixCity = trainingOrganizationInfoBean.getCity();
			fixRegion = trainingOrganizationInfoBean.getRegion();
			request.setAttribute("isGovernment", false);
		}

		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
		MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
		int teacherId = ParamKit.getIntParameter(request, "teacherId", 0);
		TeacherInfoBean bean = new TeacherInfoBean();
		MajorInfoBean majorInfoBean = new MajorInfoBean();
		if (teacherId > 0) {
			// 修改
			TeacherInfoBusiness business = new TeacherInfoBusiness();
			bean = business.getTeacherInfoByKey(teacherId);
			// 获取对应的专业类型
			majorInfoBean = majorInfoBusiness.getMajorInfoByKey(bean.getMajorId());
			request.setAttribute("TeacherProvince", bean.getProvince());
			request.setAttribute("TeacherCity", bean.getCity());
			request.setAttribute("TeacherRegion", bean.getRegion());
		} else {
			// 新建
			bean.setSyncStatus(Constants.STATUS_NOT_VALID);
			bean.setStatus(Constants.STATUS_VALID);
		}

		// 获取专业类型
		List<MajorTypeBean> majorTypeList = majorTypeBusiness.getMajorTypeAllList();
		request.setAttribute("MajorTypeList", majorTypeList);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);
		request.setAttribute("MajorInfoBean", majorInfoBean);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("TeacherInfo", bean);
		request.setAttribute("FixProvince", fixProvince);
		request.setAttribute("FixCity", fixCity);
		request.setAttribute("FixRegion", fixRegion);
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TeacherInfoManage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		TeacherInfoBean bean = new TeacherInfoBean();
		TeacherInfoBusiness business = new TeacherInfoBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION
				&& orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} else {
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrganizationInfoBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}

		try {
			bean = BeanKit.request2Bean(request, TeacherInfoBean.class);
			// 验证数据的正确性
			ReturnMessageBean messageBean = business.verifyTeacherInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getTeacherId() > 0) {
				// 修改师资
				bean.setModifyUser(userId);
				result = business.updateTeacherInfo(bean);
			} else {
				bean.setUserId(business.getUserIdByUserClient(bean.getTeacherMobile()));
				if (business.isExistsTeacherInfo(bean)) {
					HttpResponseKit.alertMessage(response, "师资" + bean.getTeacherName() + "的数据已经存在，不能导入", "/sfhn/admin/TeacherInfoList.do");
					return;
				}
				// 获取激活码
				/*
				 * ActiveCodeInfoBusiness activeCodeBusiness = new
				 * ActiveCodeInfoBusiness(); ReturnMessageBean returnMessageBean
				 * =
				 * activeCodeBusiness.bindActiveCodeWithUser(bean.getProvince(),
				 * bean.getCity(), bean.getRegion(),
				 * DateKit.formatTimestamp(DateKit.getCurrentTimestamp(),
				 * DateKit.REGEX_YEAR), ActiveCodeInfoConfig.USER_TYPE_TEACHER,
				 * bean.getUserId(), userId); if (returnMessageBean.getCode() ==
				 * Constants.STATUS_NOT_VALID) {
				 * HttpResponseKit.alertMessage(response,
				 * returnMessageBean.getMessage(),
				 * HttpResponseKit.ACTION_HISTORY_BACK); return; }
				 */

				// 新建师资
				bean.setCreateUser(userId);
				result = business.addTeacherInfo(bean);

				if (result > 0) {
					// 推送消息到云课堂
					YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
					commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
					commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_TEACHER);
					commandBean.setBody(business.teacherInfo2TeacherAddBean(bean));

					YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
						@Override
						public boolean process(Object data) {
							int result = 0;
							try {
								TeacherAddBean postResult = JsonKit.toBean(data.toString(), TeacherAddBean.class);
								// 更新到数据库
								TeacherInfoBean bean = business.getTeacherInfoByUserIdAndStatus(Constants.STATUS_VALID,
										Integer.valueOf(postResult.getUserId()));
								bean.setSyncStatus(Constants.STATUS_VALID);
								result = business.updateTeacherInfo(bean);
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
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/TeacherInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
