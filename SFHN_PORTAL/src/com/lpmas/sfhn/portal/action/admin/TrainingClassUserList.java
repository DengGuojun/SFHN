package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.transfer.HttpClientKit;
import com.lpmas.framework.transfer.HttpClientResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;
import com.lpmas.sfhn.portal.invoker.bean.LearningDataBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeHelper;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

/**
 * Servlet implementation class TrainingClassUserAdminList
 */
@WebServlet("/sfhn/admin/TrainingClassUserList.do")
public class TrainingClassUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(TrainingClassUserList.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserList() {
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

		// 查询学员线上学习时长
		List<Integer> userIdList = new ArrayList<Integer>();
		Map<Integer, Integer> userIdMap = new HashMap<Integer, Integer>();// USERID是KEY,DECLAREID是VALUE
		Map<Integer, String> learningTimeMap = new HashMap<Integer, String>();// DECLAREID是KEY
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();

		Map<String, Integer> fileInfoMap = new HashMap<String, Integer>();
		for (TrainingClassUserBean bean : result.getRecordList()) {
			// 从Mongo中获取相应的数据
			try {
				DeclareReportBean declareReportBean = declareReportBusiness
						.getDeclareReportByKey(String.valueOf(bean.getDeclareId()));
				if (declareReportBean != null) {
					declareReportMap.put(bean.getDeclareId(), declareReportBean);

					// 获取附件信息
					FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
					HashMap<String, String> fileCondMap = new HashMap<String, String>();
					fileCondMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SHEET));
					fileCondMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
					fileCondMap.put("fileName", declareReportBean.getIdentityNumber());
					fileCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
					List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(fileCondMap);
					if (!fileInfoList.isEmpty()) {
						fileInfoMap.put(declareReportBean.getIdentityNumber(), fileInfoList.get(0).getFileId());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 查出USERID
			DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByKey(bean.getDeclareId());
			if (declareInfoBean != null) {
				userIdList.add(declareInfoBean.getUserId());
				userIdMap.put(declareInfoBean.getUserId(), declareInfoBean.getDeclareId());
			}
		}
		request.setAttribute("fileInfoMap", fileInfoMap);

		if (!userIdList.isEmpty() && classInfoBean.getClassType() == Constants.STATUS_VALID) {
			// 调用云课堂API获取学习时长
			String userIds = ListKit.list2String(userIdList, ",");
			StringBuffer url = YunClassInvokeHelper.getUrl(YunClassInvokeExecutor.HTTP_GET,
					YunClassInvokeConfig.YUN_SERVICE_BATCH_LEARNING_TIME, YunClassInvokeConfig.IS_DEBUG_MODE);
			url.append("?userId=" + userIds);
			HttpClientKit httpClient = new HttpClientKit();
			HttpClientResultBean httpResult = httpClient.getContent(url.toString(), "UTF-8");
			if (httpResult.getResult()) {
				try {
					YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
							YunClassInvokeResponseBean.class);
					if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
						// 取出DATA，此时DATA是MAP
						Object data = responseBean.getData();
						if (data != null) {
							String learningDate = JsonKit.toJson(data);
							List<LearningDataBean> learningDataList = JsonKit.toList(learningDate,
									LearningDataBean.class);
							for (LearningDataBean bean : learningDataList) {
								int user = Integer.valueOf(bean.getUserId());
								// 把秒转换成小时：分钟的形式
								int hours = bean.getTotalTime() / 3600;
								int mins = bean.getTotalTime() % 3600 / 60;
								String housrStr = hours < 10 ? "0" + hours : hours + "";
								String minStr = mins < 10 ? "0" + mins : mins + "";
								learningTimeMap.put(userIdMap.get(user), housrStr + ":" + minStr);
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
		request.setAttribute("learningTimeMap", learningTimeMap);

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
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserList.jsp");
	}

}
