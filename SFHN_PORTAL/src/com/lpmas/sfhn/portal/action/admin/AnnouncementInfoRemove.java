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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.AnnouncementInfoBusiness;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;

/**
 * Servlet implementation class AnnouncementInfoRemove
 */
@WebServlet("/sfhn/admin/AnnouncementInfoRemove.do")
public class AnnouncementInfoRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnnouncementInfoRemove() {
		super();
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
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response);
		int userId = helper.getUserId();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		int announcementId = ParamKit.getIntParameter(request, "announcementId", 0);
		AnnouncementInfoBusiness business = new AnnouncementInfoBusiness();
		AnnouncementInfoBean bean = business.getAnnouncementInfoByKey(announcementId);
		if (bean == null) {
			HttpResponseKit.alertMessage(response, "公告ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 判断权限
		if (bean.getOrganizationId() != orgUserBean.getOrganizationId()) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		bean.setStatus(Constants.STATUS_NOT_VALID);
		int result = business.updateAnnouncementInfo(bean);
		if (result > 0) {
			// 获取附件信息
			FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT));
			condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT));
			condMap.put("infoId1", String.valueOf(announcementId));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
			if (!fileInfoList.isEmpty()) {
				FileInfoBean fileInfoBean = fileInfoList.get(0);
				fileInfoBean.setStatus(Constants.STATUS_NOT_VALID);
				fileInfoBusiness.updateFileInfo(fileInfoBean);
			}
		}

		if (result > 0) {
			HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/admin/AnnouncementInfoList.do");
		} else {
			HttpResponseKit.alertMessage(response, "处理失败", HttpResponseKit.ACTION_HISTORY_BACK);
		}
	}
}
