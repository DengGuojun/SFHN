package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.constant.user.GenderConfig;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorInfoBusiness;
import com.lpmas.sfhn.portal.business.MajorTypeBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TeacherInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.invoker.bean.TeacherAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokCallBack;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvoker;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

@WebServlet("/sfhn/admin/TeacherInfoImport.do")
public class TeacherInfoImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherInfoImport() {
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
		TeacherInfoBusiness business = new TeacherInfoBusiness();
		OrganizationUserBusiness orgUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserBusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null || (orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION
				&& orgUserBean.getInfoType() != InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
		} else {
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrganizationInfoBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
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
			List<TeacherInfoBean> teacherInfoList = new ArrayList<TeacherInfoBean>();
			// 解析Excel数据,从第二行开始读起
			try {
				List<List<String>> contentList = excelReadResultBean.getContentList();
				for (int i = 1; i < contentList.size(); i++) {
					List<String> content = contentList.get(i);
					String teacherName = content.get(0);
					// 如果教师姓名为空，则跳出循环，停止导入
					if (!StringKit.isValid(teacherName)) {
						break;
					}
					String gender = content.get(1);
					if (StringKit.isValid(gender) && GenderConfig.GENDER_MALE != Integer.valueOf(gender)
							&& GenderConfig.GENDER_FEMALE != Integer.valueOf(gender)) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的性别信息错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					String age = content.get(2);
					if (StringKit.isValid(age) && !NumberKit.isPositiveInteger(age)) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的年龄信息错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					String identityNumber = content.get(3);
					if (!StringKit.isValid(identityNumber) || !NumberKit.isAllDigit(identityNumber)) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的身份证信息错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					String mobile = content.get(4);
					if (!StringKit.isValid(mobile) || !NumberKit.isAllDigit(mobile)) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的手机号码信息错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					String province = content.get(5);
					String city = content.get(6);
					String region = content.get(7);
					String corporateName = content.get(8);
					String address = content.get(9);
					// 查找专业类型
					String majorType = content.get(10).trim();
					MajorTypeBusiness majorTypeBusiness = new MajorTypeBusiness();
					MajorTypeBean majorTypeBean = null;
					majorTypeBean = majorTypeBusiness.getMajorTypeByName(majorType);
					if (majorTypeBean == null) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的专业类型错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					int majorTypeId = majorTypeBean.getMajorId();// 专业类型ID

					String majorName = content.get(11).trim();
					MajorInfoBean majorInfoBean = new MajorInfoBean();
					if (!StringKit.isValid(majorName)) {
						HttpResponseKit.alertMessage(response, "第" + i + "行数据的专业信息错误", "/sfhn/admin/TeacherInfoList.do");
						return;
					} else {
						MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
						majorInfoBean = majorInfoBusiness.getMajorInfoByCondition(majorName, majorTypeId);
						if (majorInfoBean == null) {
							HttpResponseKit.alertMessage(response, "第" + i + "行数据的专业信息错误", "/sfhn/admin/TeacherInfoList.do");
							return;
						}
					}
					String mainCourse = content.get(12);

					TeacherInfoBean bean = new TeacherInfoBean();
					bean.setTeacherName(teacherName);
					bean.setTeacherGender(Integer.valueOf(gender));
					bean.setTeacherAge(Integer.valueOf(age));
					bean.setIdentityNumber(identityNumber);
					bean.setTeacherMobile(mobile);
					bean.setProvince(province);
					bean.setCity(city);
					bean.setRegion(region);
					bean.setCorporateName(corporateName);
					bean.setAddress(address);
					bean.setMajorTypeId(majorInfoBean.getTypeId());
					bean.setMajorId(majorInfoBean.getMajorId());
					bean.setMainCourse(mainCourse);
					bean.setStatus(Constants.STATUS_VALID);
					bean.setUserId(business.getUserIdByUserClient(bean.getTeacherMobile()));
					if (business.isExistsTeacherInfo(bean)) {
						HttpResponseKit.alertMessage(response, "师资" + bean.getTeacherName() + "的数据已经存在，不能导入", "/sfhn/admin/TeacherInfoList.do");
						return;
					}
					teacherInfoList.add(bean);
				}
				// Excel表通过验证，则开始导入师资
				for (TeacherInfoBean bean : teacherInfoList) {
					// 获取激活码
					/*ActiveCodeInfoBusiness activeCodeBusiness = new ActiveCodeInfoBusiness();
					ReturnMessageBean returnMessageBean = activeCodeBusiness.bindActiveCodeWithUser(bean.getProvince(), bean.getCity(),
							bean.getRegion(), DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR),
							ActiveCodeInfoConfig.USER_TYPE_TEACHER, bean.getUserId(), userId);
					if (returnMessageBean.getCode() == Constants.STATUS_NOT_VALID) {
						HttpResponseKit.alertMessage(response, "师资[" + bean.getTeacherName() + "]获取激活码失败，后续数据停止导入", "/sfhn/admin/TeacherInfoList.do");
						return;
					}*/

					bean.setCreateUser(userId);
					int result = business.addTeacherInfo(bean);
					if (result > 0) {
						if (result > 0) {
							// 推送消息到云课堂
							YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
							commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
							commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_TEACHER);
							commandBean.setBody(business.teacherInfo2TeacherAddBean(bean));

							YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
								@Override
								public boolean process(Object data) {
									int result = 0;
									try {
										TeacherAddBean postResult = JsonKit.toBean(data.toString(), TeacherAddBean.class);
										// 更新到数据库
										TeacherInfoBean bean = business.getTeacherInfoByUserIdAndStatus(Constants.STATUS_VALID,
												Integer.valueOf(postResult.getUserId()));
										bean.setSyncStatus(Constants.STATUS_VALID);
										result = business.updateTeacherInfo(bean);
										if (result > 0) {
											return true;
										} else {
											return false;
										}
									} catch (Exception e) {
										return false;
									}
								}
							});
							YunClassInvokeExecutor.attachAsync(invoker);
						}
					}
				}

				HttpResponseKit.alertMessage(response, "导入成功", "/sfhn/admin/TeacherInfoList.do");
			} catch (Exception e) {
				HttpResponseKit.alertMessage(response, "导入失败", "/sfhn/admin/TeacherInfoList.do");
			}
		} else {
			HttpResponseKit.alertMessage(response, excelReadResultBean.getErrMsg(), "/sfhn/admin/TeacherInfoList.do");
			return;
		}
	}

}
