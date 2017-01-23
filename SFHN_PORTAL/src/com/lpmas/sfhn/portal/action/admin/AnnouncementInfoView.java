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
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
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
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class AnnouncementInfoView
 */
@WebServlet("/sfhn/admin/AnnouncementInfoView.do")
public class AnnouncementInfoView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnnouncementInfoView() {
		super();
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
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		Map<Integer, FileInfoBean> fileInfoMap = new HashMap<Integer, FileInfoBean>();
		int announcementId = ParamKit.getIntParameter(request, "announcementId", 0);
		AnnouncementInfoBusiness business = new AnnouncementInfoBusiness();
		AnnouncementInfoBean bean = business.getAnnouncementInfoByKey(announcementId);
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
		if (!fileInfoList.isEmpty()) {
			request.setAttribute("AnnouncmentAttatch", fileInfoList.get(0));
		}

		boolean isOwner = false;
		if (bean.getOrganizationType() == orgUserBean.getInfoType()
				&& bean.getOrganizationId() == orgUserBean.getOrganizationId()) {
			isOwner = true;
		}

		// 阅读公告后，把消息设置为已读
		int messageId = ParamKit.getIntParameter(request, "messageId", 0);
		if (messageId > 0) {
			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			MessageInfoBean messageInfoBean = messageInfoBusiness.getMessageInfoByKey(messageId);
			messageInfoBean.setIsRead(Constants.STATUS_VALID);
			messageInfoBusiness.updateMessageInfo(messageInfoBean);
		}

		request.setAttribute("FileInfoMap", fileInfoMap);
		request.setAttribute("AnnouncementInfo", bean);
		request.setAttribute("isOwner", isOwner);
		boolean isGovernment = false;
		if (orgUserBean != null && InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			isGovernment = true;
		}
		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		// 查政府部门级别
		if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgUserBean.getInfoType()) {
			GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgInfoBean != null) {
				request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);
			}
		}
		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "AnnouncementInfoView.jsp");
	}
}
