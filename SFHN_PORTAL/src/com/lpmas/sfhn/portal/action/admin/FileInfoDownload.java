package com.lpmas.sfhn.portal.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;

@WebServlet("/sfhn/admin/FileInfoDownload.do")
public class FileInfoDownload extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(FileInfoDownload.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileInfoDownload() {
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
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			HttpResponseKit.printJson(request, response, "你没有该功能的操作权限", "");
			return;
		}
		FileInfoBean bean = null;
		int fileId = ParamKit.getIntParameter(request, "fileId", 0);
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		// 先根据fileId获取对象，如果没传fileId，则根据其他参数条件获取对象
		if (fileId > 0) {
			bean = fileInfoBusiness.getFileInfoByKey(fileId);
		} else {
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("fileType", ParamKit.getParameter(request, "fileType", ""));
			condMap.put("infoType", ParamKit.getParameter(request, "infoType", ""));
			condMap.put("infoId1", ParamKit.getParameter(request, "infoId1", ""));
			condMap.put("fileName", ParamKit.getParameter(request, "fileName", ""));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			List<FileInfoBean> list = fileInfoBusiness.getFileInfoListByMap(condMap);
			if (!list.isEmpty() && list.size() == 1) {
				// 仅当匹配到一个文件时，才触发下载
				bean = list.get(0);
			}
		}

		if (bean == null || bean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "没有对应的文件", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO) {
			// 如果附件是培训班附件，需要判断权限
			TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
			if (!trainingClassInfoBusiness.hasPermission(bean.getInfoId1(), userId)) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		}

		// 设置文件头：最后一个参数是设置下载文件名
		response.setHeader(
				"Content-Disposition",
				"attachment;fileName=" + new String(bean.getFileName().getBytes("gb2312"), "ISO8859-1") + "."
						+ bean.getFileFormat());
		ServletOutputStream out;
		// 通过文件路径获得File对象
		File file = new File(FileInfoConfig.ADMIN_FILE_PATH + bean.getFilePath());
		// 设置文件ContentType类型
		response.setContentType(new MimetypesFileTypeMap().getContentType(file));
		try {
			FileInputStream inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				out.write(buffer, 0, b);
			}
			inputStream.close();
			out.close();
			out.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}
}