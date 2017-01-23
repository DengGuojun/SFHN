package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.AnnouncementInfoBusiness;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

/**
 * Servlet implementation class AnnouncementInfoAdminList
 */
@WebServlet("/sfhn/admin/AnnouncementInfoList.do")
public class AnnouncementInfoList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnnouncementInfoList() {
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

		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap = new HashMap<String, String>();
		condMap.put("organizationType", String.valueOf(InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION));
		condMap.put("organizationId", String.valueOf(orgUserBean.getOrganizationId()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));

		AnnouncementInfoBusiness business = new AnnouncementInfoBusiness();
		PageResultBean<AnnouncementInfoBean> result = business.getAnnouncementInfoPageListByMap(condMap, pageBean);

		// 查看公告是否包含附件
		HashMap<Integer, Integer> fileResultMap = new HashMap<Integer, Integer>();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		HashMap<String, String> fileCondMap = new HashMap<String, String>();
		for (AnnouncementInfoBean bean : result.getRecordList()) {
			fileCondMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT));
			fileCondMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT));
			fileCondMap.put("infoId1", String.valueOf(bean.getAnnouncementId()));
			fileCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(fileCondMap);
			if (!fileInfoList.isEmpty()) {
				fileResultMap.put(bean.getAnnouncementId(), fileInfoList.size());
			}
		}

		request.setAttribute("AnnouncementInfoList", result.getRecordList());
		pageBean.init(pageNum, pageSize, result.getTotalRecordNumber());
		request.setAttribute("PageResult", pageBean);
		request.setAttribute("CondList", MapKit.map2List(condMap));
		request.setAttribute("FileResultMap", fileResultMap);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", true);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		// 转发
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "AnnouncementInfoList.jsp");
	}

}
