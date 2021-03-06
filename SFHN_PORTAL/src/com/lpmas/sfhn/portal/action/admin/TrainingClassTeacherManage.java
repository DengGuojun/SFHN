package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.bean.TrainingClassTeacherBean;
import com.lpmas.sfhn.portal.bean.TrainingCourseInfoBean;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassTeacherBusiness;
import com.lpmas.sfhn.portal.business.TrainingCourseInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;

/**
 * Servlet implementation class TrainingClassTeacherManage
 */
@WebServlet("/sfhn/admin/TrainingClassTeacherManage.do")
public class TrainingClassTeacherManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TrainingClassTeacherManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassTeacherManage() {
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
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		TrainingClassTeacherBean bean = new TrainingClassTeacherBean();
		TrainingClassTeacherBusiness business = new TrainingClassTeacherBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

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

		int teacherId = ParamKit.getIntParameter(request, "teacherId", 0);
		if (teacherId <= 0) {
			HttpResponseKit.alertMessage(response, "教师ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		TeacherInfoBusiness teacherInfoBusiness = new TeacherInfoBusiness();
		TeacherInfoBean teacherInfoBean = teacherInfoBusiness.getTeacherInfoByKey(teacherId);
		if (teacherInfoBean == null) {
			HttpResponseKit.alertMessage(response, "教师不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 查看课程是否存在，若存在则使用，不存在则新增
		int courseId = 0;
		String courseName = ParamKit.getParameter(request, "courseName", "");
		TrainingCourseInfoBusiness courseInfoBusiness = new TrainingCourseInfoBusiness();
		TrainingCourseInfoBean courseInfoBean = courseInfoBusiness.getTrainingCourseInfoByName(courseName);
		if (courseInfoBean == null) {
			courseInfoBean = BeanKit.request2Bean(request, TrainingCourseInfoBean.class);
			ReturnMessageBean messageBean = courseInfoBusiness.verifyTrainingCourseInfo(courseInfoBean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			bean.setCreateUser(userId);
			courseInfoBusiness.addTrainingCourseInfo(courseInfoBean);
			courseInfoBean = courseInfoBusiness.getTrainingCourseInfoByName(courseName);
		}
		courseId = courseInfoBean.getCourseId();

		try {
			bean = BeanKit.request2Bean(request, TrainingClassTeacherBean.class);
			bean.setCourseId(courseId);
			int result = 0;
			// 新建
			bean.setCreateUser(userId);
			result = business.addTrainingClassTeacher(bean);

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/TrainingClassTeacherList.do?classId=" + bean.getClassId());
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
