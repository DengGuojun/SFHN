package com.lpmas.sfhn.migrate.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.constant.user.GenderConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;

@WebServlet("/sfhn/migrate/TeacherInfoImport.do")
public class TeacherInfoImport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TeacherInfoImport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherInfoImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/sfhn/migrate/DataMigrationImport.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		boolean flag = false;
		List<String> message = new ArrayList<String>();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			String filePath = DateKit.getCurrentDateTime("yyyy" + "_" + "MM" + "_" + "dd");
			String uploadPath = FileInfoConfig.ADMIN_FILE_PATH + filePath;
			fileUploadKit.setAllowedFileType(FileInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
			fileUploadKit.setMaxSize(FileInfoConfig.MAX_SIZE);
			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			String extensionFileName = null;
			// 获取上传结果
			if (resultBean.getResult()) {
				List<FileUploadItem> list = resultBean.getFileItemList();
				for (FileUploadItem item : list) {
					if (item.getResult()) {
						extensionFileName = item.getExtensionFileName();
					} else {
						returnMessage.setMessage(item.getResultContent());
						message.add(returnMessage.getMessage());
					}
				}
			} else {
				returnMessage.setMessage(resultBean.getResultContent());
				message.add(returnMessage.getMessage());
			}
			// 读取excel表
			ExcelReadKit excelReadKit = new ExcelReadKit();
			ExcelReadResultBean excelReadResultBean = excelReadKit
					.readExcel(FileInfoConfig.ADMIN_FILE_PATH + filePath + Constants.FILE_SEPARATOR + fileName + "." + extensionFileName, 0);
			if (excelReadResultBean != null && excelReadResultBean.getResult()) {
				List<List<String>> contentList = excelReadResultBean.getContentList();
				TeacherInfoBusiness business = new TeacherInfoBusiness();
				Integer currentYear = Integer.valueOf(DateKit.getCurrentDate().split("-")[0]);
				for (List<String> content : contentList) {
					if (!content.get(0).isEmpty()) {
						TeacherInfoBean bean = new TeacherInfoBean();
						String teacherName = content.get(1);
						String gender = content.get(5);
						String birthday = content.get(6);
						String province = content.get(7);
						String city = content.get(8);
						String region = content.get(9);
						String identityNumber = content.get(10);
						if (business.getTeacherInfoByIdentityNumber(identityNumber) != null) {
							continue;
						}
						String majorType = content.get(14);
						String majorInfo = content.get(15);
						String mainCourse = content.get(16);
						String teacherMobile = content.get(17);
						String corporateName = content.get(11);
						String createTime = content.get(21);

						// 检验性别
						if (StringKit.isValid(gender) && GenderConfig.GENDER_MAP.containsKey(Integer.valueOf(gender))) {
							bean.setTeacherGender(Integer.valueOf(gender));
						}
						// 通过出生日期计算年龄
						if (StringKit.isValid(birthday) && StringKit.isValid(birthday.split("-")[0])
								&& NumberKit.isPositiveInteger(birthday.split("-")[0])) {
							try {
								Integer age = currentYear - Integer.valueOf(birthday.split("-")[0]);
								bean.setTeacherAge(age);
							} catch (Exception e) {

							}
						}
						// 检验专业
						if (StringKit.isValid(majorType) && StringKit.isValid(majorInfo)) {
							bean.setMajorTypeId(Integer.valueOf(majorType));
							bean.setMajorId(Integer.valueOf(majorInfo));
						}
						// 检验工作单位
						if (StringKit.isValid(corporateName)) {
							bean.setCorporateName(corporateName);
						}
						// 检验主讲课程
						if (StringKit.isValid(mainCourse)) {
							bean.setMainCourse(mainCourse);
						}
						// 检验电话
						if (StringKit.isValid(teacherMobile)) {
							bean.setTeacherMobile(teacherMobile);
						}

						if (StringKit.isValid(createTime)) {
							bean.setCreateTime(DateKit.str2Timestamp(createTime, DateKit.DEFAULT_DATE_TIME_FORMAT));
						}

						bean.setTeacherName(teacherName);
						bean.setProvince(province);
						bean.setCity(city);
						bean.setRegion(region);
						bean.setIdentityNumber(identityNumber);
						bean.setStatus(Constants.STATUS_VALID);

						result = business.addTeacherInfoWithCreateTime(bean);
						if (result < 0) {
							message.add(content.get(1) + "导入失败;");
							flag = true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
		}
		if (flag) {
			HttpResponseKit.alertMessage(response, ListKit.list2String(message, " "), "/sfhn/migrate/DataMigrationImport.do");
		} else {
			HttpResponseKit.alertMessage(response, "处理成功", "/sfhn/migrate/DataMigrationImport.do");
		}
	}
}
