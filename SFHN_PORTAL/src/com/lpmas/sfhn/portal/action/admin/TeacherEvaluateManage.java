package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TeacherEvaluateBean;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.bean.TrainingClassTeacherBean;
import com.lpmas.sfhn.portal.bean.TrainingCourseInfoBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TeacherEvaluateBusiness;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassTeacherBusiness;
import com.lpmas.sfhn.portal.business.TrainingCourseInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class TeacherEvaluateManage
 */
@WebServlet("/sfhn/admin/TeacherEvaluateManage.do")
public class TeacherEvaluateManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TeacherEvaluateManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherEvaluateManage() {
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
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		boolean isGovernment = false;
		int organizationId = 0;
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			isGovernment = true;
			organizationId = governmentOrgBean.getOrganizationId();
			request.setAttribute("GovernmentOrgInfoBean", governmentOrgBean);
		} else {
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			organizationId = trainingOrgBean.getOrganizationId();
			request.setAttribute("TrainingOrgInfoBean", trainingOrgBean);
		}

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		int teacherId = ParamKit.getIntParameter(request, "teacherId", 0);
		if (classId <= 0) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (teacherId <= 0) {
			HttpResponseKit.alertMessage(response, "教师ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		TrainingClassTeacherBusiness trainingClassTeacherBusiness = new TrainingClassTeacherBusiness();
		TrainingClassTeacherBean classTeacherBean = trainingClassTeacherBusiness.getTrainingClassTeacherByKey(classId,
				teacherId);
		if (classTeacherBean == null) {
			HttpResponseKit.alertMessage(response, "班级授课教师信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		TeacherInfoBusiness teacherInfoBusiness = new TeacherInfoBusiness();
		TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
		TrainingCourseInfoBusiness courseInfoBusiness = new TrainingCourseInfoBusiness();
		TeacherInfoBean teacherInfoBean = teacherInfoBusiness.getTeacherInfoByKey(teacherId);
		TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(classId);
		TrainingCourseInfoBean courseInfoBean = courseInfoBusiness.getTrainingCourseInfoByKey(classTeacherBean
				.getCourseId());

		TeacherEvaluateBean bean = new TeacherEvaluateBean();
		TeacherEvaluateBusiness business = new TeacherEvaluateBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("teacherId", String.valueOf(teacherId));
		condMap.put("classId", String.valueOf(classId));
		condMap.put("courseId", String.valueOf(courseInfoBean.getCourseId()));
		condMap.put("organizationId", String.valueOf(organizationId));
		condMap.put("organizationType", String.valueOf(orgUserBean.getInfoType()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<TeacherEvaluateBean> result = business.getTeacherEvaluateListByMap(condMap);
		if (result.isEmpty()) {
			// 新增评价
			bean.setTeacherId(teacherId);
			bean.setClassId(classId);
			bean.setCourseId(courseInfoBean.getCourseId());
			bean.setStatus(Constants.STATUS_VALID);
		} else {
			// 修改评价
			bean = result.get(0);
		}

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("TeacherInfoBean", teacherInfoBean);
		request.setAttribute("ClassInfoBean", classInfoBean);
		request.setAttribute("CourseInfoBean", courseInfoBean);
		request.setAttribute("TeacherEvaluateBean", bean);
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TeacherEvaluateManage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
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
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}

		int classId = ParamKit.getIntParameter(request, "classId", 0);
		int teacherId = ParamKit.getIntParameter(request, "teacherId", 0);
		if (classId <= 0) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (teacherId <= 0) {
			HttpResponseKit.alertMessage(response, "教师ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		TrainingClassTeacherBusiness trainingClassTeacherBusiness = new TrainingClassTeacherBusiness();
		TrainingClassTeacherBean classTeacherBean = trainingClassTeacherBusiness.getTrainingClassTeacherByKey(classId,
				teacherId);
		if (classTeacherBean == null) {
			HttpResponseKit.alertMessage(response, "班级授课教师信息不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		TeacherEvaluateBean bean = new TeacherEvaluateBean();
		TeacherEvaluateBusiness business = new TeacherEvaluateBusiness();
		try {
			int result = 0;
			bean = BeanKit.request2Bean(request, TeacherEvaluateBean.class);
			if (bean.getEvaluateId() > 0) {
				// 修改师资评价
				bean.setModifyUser(userId);
				result = business.updateTeacherEvaluate(bean);
			} else {
				// 新建师资评价
				bean.setCreateUser(userId);
				result = business.addTeacherEvaluate(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/TrainingClassTeacherList.do?classId="
						+ bean.getClassId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
