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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.business.AnnouncementInfoBusiness;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class AnnouncementInfoManage
 */
@WebServlet("/sfhn/admin/AnnouncementInfoManage.do")
public class AnnouncementInfoManage extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(AnnouncementInfoManage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnnouncementInfoManage() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 目前只有省级政府能管理公告
		GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (governmentOrgInfoBean.getOrganizationLevel() != GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);

		Map<Integer, FileInfoBean> fileInfoMap = new HashMap<Integer, FileInfoBean>();
		int announcementId = ParamKit.getIntParameter(request, "announcementId", 0);
		AnnouncementInfoBean bean = new AnnouncementInfoBean();
		if (announcementId > 0) {
			// 修改
			AnnouncementInfoBusiness business = new AnnouncementInfoBusiness();
			bean = business.getAnnouncementInfoByKey(announcementId);
			if (bean == null) {
				HttpResponseKit.alertMessage(response, "公告ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 获取附件信息
			FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT));
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT));
			condMap.put("infoId1", String.valueOf(announcementId));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
			fileInfoMap = ListKit.list2Map(fileInfoList, "fileType");
		} else {
			// 新建
			bean.setOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
			bean.setOrganizationId(orgUserBean.getOrganizationId());
			bean.setStatus(Constants.STATUS_VALID);
		}

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", true);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("FileInfoMap", fileInfoMap);
		request.setAttribute("AnnouncementInfo", bean);

		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "AnnouncementInfoManage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 目前只有省级政府能管理公告
		GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
		GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
				.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
		if (governmentOrgInfoBean.getOrganizationLevel() != GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		String announcementAttachFilePath = ParamKit.getParameter(request, "announcementAttachFilePath", "");

		try {
			AnnouncementInfoBusiness business = new AnnouncementInfoBusiness();
			AnnouncementInfoBean bean = BeanKit.request2Bean(request, AnnouncementInfoBean.class);
			// 判断权限
			if (bean.getOrganizationId() != orgUserBean.getOrganizationId()) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			int result = 0;
			if (bean.getAnnouncementId() > 0) {
				// 修改公告
				bean.setModifyUser(userId);
				result = business.updateAnnouncementInfo(bean);
			} else {
				// 新建公告
				bean.setCreateUser(userId);
				result = business.addAnnouncementInfo(bean);
				if (result > 0) {
					bean.setAnnouncementId(result);
					if (StringKit.isValid(announcementAttachFilePath)) {
						// 记录附件信息
						FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
						HashMap<String, String> condMap = new HashMap<String, String>();
						condMap.put("filePath", announcementAttachFilePath);
						List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
						FileInfoBean fileInfoBean = fileInfoList.get(0);
						fileInfoBean.setFileType(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT);
						fileInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT);
						fileInfoBean.setInfoId1(bean.getAnnouncementId());
						fileInfoBean.setModifyUser(userId);
						fileInfoBean.setStatus(Constants.STATUS_VALID);
						fileInfoBusiness.updateFileInfo(fileInfoBean);
					}
				}
			}

			if (result > 0) {
				int noticeProvince = ParamKit.getIntParameter(request, "noticeProvince", 0);
				int noticeCity = ParamKit.getIntParameter(request, "noticeCity", 0);
				int noticeRegion = ParamKit.getIntParameter(request, "noticeRegion", 0);
				int noticeTrainingOrg = ParamKit.getIntParameter(request, "noticeTrainingOrg", 0);
				// 发送消息
				MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
				if (noticeProvince == Constants.STATUS_VALID) {
					MessageInfoBean messageInfoBean = new MessageInfoBean();
					messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT);
					messageInfoBean.setInfoId(bean.getAnnouncementId());
					messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_ADMIN_ANNOUNCEMENT);
					messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
					messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
					messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
					messageInfoBean.setReceiveOrganizationId(orgUserBean.getOrganizationId());
					messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
					messageInfoBean.setStatus(Constants.STATUS_VALID);
					messageInfoBean.setCreateUser(userId);
					messageInfoBusiness.addMessageInfo(messageInfoBean);
				}
				if (noticeCity == Constants.STATUS_VALID) {
					List<GovernmentOrganizationInfoBean> list = governmentOrgInfoBusiness
							.getGovernmentOrganizationInfoListByRegion(governmentOrgInfoBean.getProvince(), null, null,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
					for (GovernmentOrganizationInfoBean cityGovernmentOrgBean : list) {
						MessageInfoBean messageInfoBean = new MessageInfoBean();
						messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT);
						messageInfoBean.setInfoId(bean.getAnnouncementId());
						messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_ADMIN_ANNOUNCEMENT);
						messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
						messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setReceiveOrganizationId(cityGovernmentOrgBean.getOrganizationId());
						messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
						messageInfoBean.setStatus(Constants.STATUS_VALID);
						messageInfoBean.setCreateUser(userId);
						messageInfoBusiness.addMessageInfo(messageInfoBean);
					}
				}
				if (noticeRegion == Constants.STATUS_VALID) {
					List<GovernmentOrganizationInfoBean> list = governmentOrgInfoBusiness
							.getGovernmentOrganizationInfoListByRegion(governmentOrgInfoBean.getProvince(), null, null,
									GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION);
					for (GovernmentOrganizationInfoBean regionGovernmentOrgBean : list) {
						MessageInfoBean messageInfoBean = new MessageInfoBean();
						messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT);
						messageInfoBean.setInfoId(bean.getAnnouncementId());
						messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_ADMIN_ANNOUNCEMENT);
						messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
						messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setReceiveOrganizationId(regionGovernmentOrgBean.getOrganizationId());
						messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
						messageInfoBean.setStatus(Constants.STATUS_VALID);
						messageInfoBean.setCreateUser(userId);
						messageInfoBusiness.addMessageInfo(messageInfoBean);
					}
				}
				if (noticeTrainingOrg == Constants.STATUS_VALID) {
					TrainingOrganizationInfoBusiness trainingOrgInfoBusiness = new TrainingOrganizationInfoBusiness();
					HashMap<String, String> condMap = new HashMap<String, String>();
					condMap.put("province", governmentOrgInfoBean.getProvince());
					condMap.put("status", String.valueOf(Constants.STATUS_VALID));
					List<TrainingOrganizationInfoBean> list = trainingOrgInfoBusiness
							.getTrainingOrganizationInfoListByMap(condMap);
					for (TrainingOrganizationInfoBean trainingOrgBean : list) {
						MessageInfoBean messageInfoBean = new MessageInfoBean();
						messageInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT);
						messageInfoBean.setInfoId(bean.getAnnouncementId());
						messageInfoBean.setMessageType(MessageInfoConfig.MESSAGE_TYPE_ADMIN_ANNOUNCEMENT);
						messageInfoBean.setSendOrganizationType(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION);
						messageInfoBean.setSendOrganizationId(orgUserBean.getOrganizationId());
						messageInfoBean.setReceiveOrganizationType(InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION);
						messageInfoBean.setReceiveOrganizationId(trainingOrgBean.getOrganizationId());
						messageInfoBean.setIsRead(Constants.STATUS_NOT_VALID);
						messageInfoBean.setStatus(Constants.STATUS_VALID);
						messageInfoBean.setCreateUser(userId);
						messageInfoBusiness.addMessageInfo(messageInfoBean);
					}
				}

			}

			if (result > 0) {
				HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/AnnouncementInfoList.do");
			} else {
				HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
