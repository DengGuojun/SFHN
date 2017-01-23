package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
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

/**
 * Servlet implementation class TeacherInfoList
 */
@WebServlet("/sfhn/admin/TeacherInfoList.do")
public class TeacherInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherInfoList() {
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

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		boolean isGovernment = false;
		String fixProvince = "";
		String fixCity = "";
		String fixRegion = "";
		if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			isGovernment = true;
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			fixProvince = governmentOrgInfoBean.getProvince();
			fixCity = governmentOrgInfoBean.getCity();
			fixRegion = governmentOrgInfoBean.getRegion();
			request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);

		} else {
			TrainingOrganizationInfoBusiness trainingOrgInfoBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgInfoBean = trainingOrgInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			fixProvince = trainingOrgInfoBean.getProvince();
			fixCity = trainingOrgInfoBean.getCity();
			fixRegion = trainingOrgInfoBean.getRegion();
		}

		TeacherInfoBusiness business = new TeacherInfoBusiness();
		HashMap<String, String> condMap = ParamKit.getParameterMap(request,
				"majorTypeId,majorId,teacherName,teacherMobile,identityNumber,mainCourse,");
		condMap.put("province", fixProvince);
		String queryCity = StringKit.isValid(fixCity) ? fixCity :ParamKit.getParameter(request, "queryCity", "").trim();
		if (StringKit.isValid(queryCity)) {
			condMap.put("city", queryCity);
		}
		String queryRegion = StringKit.isValid(fixRegion) ? fixRegion :ParamKit.getParameter(request, "queryRegion","").trim();
		if (StringKit.isValid(queryRegion)) {
			condMap.put("region", queryRegion);
		}
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		PageResultBean<TeacherInfoBean> result = business.getTeacherInfoPageListByMap(condMap, pageBean);

		// 获取专业名字
		Map<Integer, String> majorInfoMap = new HashMap<Integer, String>();
		Map<Integer, String> majorTypeMap = new HashMap<Integer, String>();
		MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
		MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
		for (TeacherInfoBean teacherInfoBean : result.getRecordList()) {
			MajorInfoBean majorInfoBean = majorInfoBusiness.getMajorInfoByKey(teacherInfoBean.getMajorId());
			majorInfoMap.put(teacherInfoBean.getTeacherId(), majorInfoBean.getMajorName());
			MajorTypeBean majorTypeBean = majorTypeBusiness.getMajorTypeByKey(majorInfoBean.getTypeId());
			majorTypeMap.put(teacherInfoBean.getTeacherId(), majorTypeBean.getMajorName());
		}
		request.setAttribute("MajorInfoMap", majorInfoMap);
		request.setAttribute("MajorTypeMap", majorTypeMap);
		// 获取专业类型
		List<MajorTypeBean> majorTypeList = majorTypeBusiness.getMajorTypeAllList();
		request.setAttribute("MajorTypeList", majorTypeList);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

		request.setAttribute("TeacherInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("FixProvince", fixProvince);
		request.setAttribute("FixCity", fixCity);
		request.setAttribute("FixRegion", fixRegion);
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TeacherInfoList.jsp");
	}
	
}
