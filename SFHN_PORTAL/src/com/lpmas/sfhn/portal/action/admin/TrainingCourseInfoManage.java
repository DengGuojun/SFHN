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
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TrainingCourseInfoBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorTypeBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingCourseInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class TrainingCourseInfoManage
 */
@WebServlet("/sfhn/admin/TrainingCourseInfoManage.do")
public class TrainingCourseInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TrainingCourseInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingCourseInfoManage() {
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
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
		MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
		int courseId = ParamKit.getIntParameter(request, "courseId", 0);
		TrainingCourseInfoBean bean = new TrainingCourseInfoBean();
		MajorInfoBean majorInfoBean = new MajorInfoBean();
		if (courseId > 0) {
			// 修改
			TrainingCourseInfoBusiness business = new TrainingCourseInfoBusiness();
			bean = business.getTrainingCourseInfoByKey(courseId);
			//获取对应的专业类型
			majorInfoBean = majorInfoBusiness.getMajorInfoByKey(bean.getMajorId());
		} else {
			// 新建
			bean.setStatus(Constants.STATUS_VALID);
		}
		
		//获取专业类型
		List<MajorTypeBean> majorTypeList = majorTypeBusiness.getMajorTypeAllList();
		request.setAttribute("MajorTypeList", majorTypeList);
		
		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);
		
		request.setAttribute("isGovernment", true);
		request.setAttribute("MajorInfoBean", majorInfoBean);
		request.setAttribute("GovernmentOrgInfoBean", governmentOrgBean);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("TrainingCourseInfo", bean);
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingCourseInfoManage.jsp");
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
		TrainingCourseInfoBean bean = new TrainingCourseInfoBean();
		TrainingCourseInfoBusiness business = new TrainingCourseInfoBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		try {
			bean = BeanKit.request2Bean(request, TrainingCourseInfoBean.class);
			// 验证数据的正确性
			ReturnMessageBean messageBean = business.verifyTrainingCourseInfo(bean);
			if (StringKit.isValid(messageBean.getMessage())) {
				HttpResponseKit.alertMessage(response, messageBean.getMessage(), HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getCourseId() > 0) {
				// 修改课程
				bean.setModifyUser(userId);
				result = business.updateTrainingCourseInfo(bean);
			} else {
				// 新建课程
				bean.setCreateUser(userId);
				result = business.addTrainingCourseInfo(bean);
			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/TrainingCourseInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
