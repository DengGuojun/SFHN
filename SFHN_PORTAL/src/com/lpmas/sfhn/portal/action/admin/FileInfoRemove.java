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
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;

@WebServlet("/sfhn/admin/FileInfoRemove.do")
public class FileInfoRemove extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(FileInfoRemove.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileInfoRemove() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		int fileId = ParamKit.getIntParameter(request, "fileId", 0);
		String url = ParamKit.getParameter(request, "url", "");
		try {

			FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
			FileInfoBean bean = new FileInfoBean();
			if (fileId > 0) {
				// 更新文件
				bean = fileInfoBusiness.getFileInfoByKey(fileId);
				bean.setStatus(Constants.STATUS_NOT_VALID);
				int result = fileInfoBusiness.updateFileInfo(bean);
				// 删除原文件
				if (result > 0) {
					fileInfoBusiness.deleteFile(bean);
				} else {
					HttpResponseKit.alertMessage(response, "删除失败", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
			}
			HttpResponseKit.alertMessage(response, "处理成功",
					"/sfhn/admin/" + url + ".do?classId=" + bean.getInfoId1() + "&fileType=" + bean.getFileType());

		} catch (Exception e) {
			log.error("", e);
		}
	}

}
