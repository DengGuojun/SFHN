package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
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
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingCourseInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;

/**
 * Servlet implementation class TrainingCourseInfoImport
 */
@WebServlet("/sfhn/admin/TrainingCourseInfoImport.do")
public class TrainingCourseInfoImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingCourseInfoImport() {
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

		// 上传Excel到服务器
		FileUploadKit fileUploadKit = new FileUploadKit();
		String fileName = DateKit.getCurrentDateTime("yyyyMMddHHmmss");
		String uploadPath = FileInfoConfig.ADMIN_FILE_PATH;
		fileUploadKit.setAllowedFileType(FileInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
		fileUploadKit.setMaxSize(FileInfoConfig.MAX_SIZE);
		FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
		if (!resultBean.getResult()) {
			HttpResponseKit.alertMessage(response, resultBean.getResultContent(), HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (resultBean.getFileItemList() == null || resultBean.getFileItemList().size() == 0) {
			HttpResponseKit.alertMessage(response, "文件上传至服务器失败，详情请查看日志。", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}

		// 获取上传结果
		String filePath = resultBean.getFileItemList().get(0).getFilePath();
		ExcelReadKit excelReadKit = new ExcelReadKit();
		ExcelReadResultBean excelReadResultBean = excelReadKit.readExcel(filePath, 0);
		if (excelReadResultBean != null && excelReadResultBean.getResult()) {
			// 对象准备
			TrainingCourseInfoBusiness business = new TrainingCourseInfoBusiness();
			List<TrainingCourseInfoBean> trainingCourseInfoList = new ArrayList<TrainingCourseInfoBean>();
			TrainingCourseInfoBean trainingCourseInfoBean = null;
			MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
			MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
			MajorInfoBean majorInfoBean = null;
			MajorTypeBean majorTypeBean = null;
			// 解析Excel数据,从第二行开始读起
			try {
				List<List<String>> contentList = excelReadResultBean.getContentList();
				for (int i = 1; i < contentList.size(); i++) {
					List<String> content = contentList.get(i);
					String courseName = content.get(0).trim();
					// 如果课程名称为空，则跳出循环，停止导入
					if (!StringKit.isValid(courseName)) {
						break;
					}

					String majorType = content.get(1).trim();
					// 查找专业类型
					majorTypeBean = majorTypeBusiness.getMajorTypeByName(majorType);
					if (majorTypeBean == null) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的专业类型错误",
								"/sfhn/admin/TrainingCourseInfoList.do");
						return;
					}
					int majorTypeId = majorTypeBean.getMajorId();// 专业类型ID

					String major = content.get(2).trim();
					// 查找专业
					majorInfoBean = majorInfoBusiness.getMajorInfoByCondition(major, majorTypeId);
					if (majorInfoBean == null) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的专业错误",
								"/sfhn/admin/TrainingCourseInfoList.do");
						return;
					}
					int majorId = majorInfoBean.getMajorId();// 专业ID

					if (majorInfoBean.getTypeId() != majorTypeId) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据专业类型与专业不匹配",
								"/sfhn/admin/TrainingCourseInfoList.do");
						return;
					}
					// 组装BEAN
					trainingCourseInfoBean = new TrainingCourseInfoBean();
					trainingCourseInfoBean.setCourseName(courseName);
					trainingCourseInfoBean.setMajorTypeId(majorTypeId);
					trainingCourseInfoBean.setMajorId(majorId);
					trainingCourseInfoBean.setStatus(Constants.STATUS_VALID);
					trainingCourseInfoBean.setCreateUser(userId);
					// 判断是否存在相同的课程
					if (business.isExistsTrainingCourseInfo(trainingCourseInfoBean)) {
						HttpResponseKit.alertMessage(response,
								"第" + i + "行数据," + trainingCourseInfoBean.getCourseName() + "已经存在不能导入",
								"/sfhn/admin/TrainingCourseInfoList.do");
						return;
					}
					trainingCourseInfoList.add(trainingCourseInfoBean);
				}

				// EXCEL数据检验完成，开始插入数据库
				int result = -1;
				int successCount = 0;
				for (TrainingCourseInfoBean bean : trainingCourseInfoList) {
					result = business.addTrainingCourseInfo(bean);
					if (result > 0) {
						successCount++;
					}
				}
				int failCount = trainingCourseInfoList.size() - successCount;
				HttpResponseKit.alertMessage(response, "导入成功:" + successCount + "条,失败:" + failCount,
						"/sfhn/admin/TrainingCourseInfoList.do");
				return;
			} catch (Exception e) {
				HttpResponseKit.alertMessage(response, "导入失败", "/sfhn/admin/TrainingCourseInfoList.do");
				return;
			}

		} else {
			HttpResponseKit.alertMessage(response, excelReadResultBean.getErrMsg(),
					"/sfhn/admin/TrainingCourseInfoList.do");
			return;
		}
	}

}
