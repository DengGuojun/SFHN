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
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;

@WebServlet("/sfhn/admin/TrainingClassUserEvaluate.do")
public class TrainingClassUserEvaluate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserEvaluate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNum = ParamKit.getIntParameter(request, "pageNum", SfhnPortalConfig.DEFAULT_PAGE_NUM);
		int pageSize = ParamKit.getIntParameter(request, "pageSize", SfhnPortalConfig.DEFAULT_PAGE_SIZE);
		PageBean pageBean = new PageBean(pageNum, pageSize);

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
		// 根据班级查机构
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
				.getTrainingOrganizationInfoByKey(classInfoBean.getOrganizationId());
		if (trainingOrganizationInfoBean == null) {
			HttpResponseKit.alertMessage(response, "培训机构不存在", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		request.setAttribute("OrganizationName", trainingOrganizationInfoBean.getOrganizationName());

		// 用户权限校验
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (!trainingClassInfoBusiness.hasPermission(classId, userId)) {
			HttpResponseKit.alertMessage(response, "你没有权限操作这个数据", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 查询这个班级学员
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("classId", String.valueOf(classId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("userStatus", TrainingClassUserConfig.USER_STATUS_APPROVE);
		PageResultBean<TrainingClassUserBean> result = trainingClassUserBusiness
				.getTrainingClassUserPageListByMap(condMap, pageBean);
		// 查询学员个人信息
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		Map<Integer, DeclareReportBean> declareReportMap = new HashMap<Integer, DeclareReportBean>();

		for (TrainingClassUserBean bean : result.getRecordList()) {
			// 从Mongo中获取相应的数据
			try {
				DeclareReportBean declareReportBean = declareReportBusiness
						.getDeclareReportByKey(String.valueOf(bean.getDeclareId()));
				if (declareReportBean != null) {
					declareReportMap.put(bean.getDeclareId(), declareReportBean);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 获取产业类型列表
		IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
		// 转换成MAP,ID是KEY
		Map<Integer, String> industryTypeMap = ListKit.list2Map(industryTypeList, "typeId", "typeName");
		request.setAttribute("IndustryTypeMap", industryTypeMap);

		// 查出这个人是什么身份
		OrganizationUserBusiness business = new OrganizationUserBusiness();
		OrganizationUserBean orgBean = business.getOrganizationUserByUserId(userId);
		boolean isGovernment = false;
		if (orgBean != null && InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()) {
			isGovernment = true;
		}

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		// 查政府部门级别
		if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()) {
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgBean.getOrganizationId());
			if (governmentOrgInfoBean != null) {
				request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);
			}
		}

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		// 转发
		request.setAttribute("ClassUserList", result.getRecordList());
		request.setAttribute("DeclareReportMap", declareReportMap);
		request.setAttribute("TrainingClassInfoBean", classInfoBean);
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserEvaluate.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
				TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness
						.getTrainingClassUserByKey(classId, Integer.parseInt(sourceStrArray[i]));
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
					TrainingClassUserBean trainingClassUserBean = trainingClassUserMap
							.get(Integer.parseInt(sourceStrArray[i]));
					trainingClassUserBean.setAuthResult(Constants.STATUS_VALID);
					trainingClassUserMap.put(Integer.parseInt(sourceStrArray[i]), trainingClassUserBean);
				} else {
					TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness
							.getTrainingClassUserByKey(classId, Integer.parseInt(sourceStrArray[i]));
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
