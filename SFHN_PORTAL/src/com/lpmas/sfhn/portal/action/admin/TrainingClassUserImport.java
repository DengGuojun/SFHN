package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.business.DeclareInfoHelper;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerInfoConfig;
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
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerContactInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerIndustryInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;
import com.lpmas.sfhn.portal.declare.handler.DeclareReportHandler;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomMemberAddBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokCallBack;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvokeExecutor;
import com.lpmas.sfhn.portal.invoker.business.YunClassInvoker;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

@WebServlet("/sfhn/admin/TrainingClassUserImport.do")
public class TrainingClassUserImport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(TrainingClassUserImport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		boolean isGovernment = false;

		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			isGovernment = true;
			GovernmentOrganizationInfoBusiness governmentOrganizationInfoBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrganizationInfoBean = governmentOrganizationInfoBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrganizationInfoBean.getOrganizationLevel() != GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			request.setAttribute("GovernmentOrgInfoBean", governmentOrganizationInfoBean);
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			condMap.put("province", governmentOrganizationInfoBean.getProvince());
			condMap.put("city", governmentOrganizationInfoBean.getCity());
			condMap.put("region", governmentOrganizationInfoBean.getRegion());
			List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoListByMap(condMap);
			request.setAttribute("TrainingOrganizationInfoList", trainingOrganizationInfoList);
		} else {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (!isGovernment) {
			TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrganizationInfoBean != null) {
				request.setAttribute("organizationName", trainingOrganizationInfoBean.getOrganizationName());
			}
		}
		// 获取培训班列表
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("organizationId", String.valueOf(orgUserBean.getOrganizationId()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("classStatusSelection", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(condMap);

		// 处理待办通知
		MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
		int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
		request.setAttribute("unreadMessageCount", unreadMessageCount);

		request.setAttribute("isGovernment", isGovernment);
		NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
		request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
		request.setAttribute("trainingClassInfoList", trainingClassInfoList);
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserImport.jsp");
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
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		int classId = ParamKit.getIntParameter(request, "classId", 0);
		boolean isGovernment = false;
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean trainingClassInfoBean = trainingClassInfoBusiness.getTrainingClassInfoByKey(classId);
		if (trainingClassInfoBean == null) {
			HttpResponseKit.alertMessage(response, "班级ID非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (!trainingClassInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_OPENED)) {
			HttpResponseKit.alertMessage(response, "培训班未开班", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (trainingClassInfoBean.getStatus() == Constants.STATUS_NOT_VALID) {
			HttpResponseKit.alertMessage(response, "培训班非法", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		FileInfoBean fileInfoBean = new FileInfoBean();
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		ReturnMessageBean returnMessage = new ReturnMessageBean();
		int result = 0;
		List<String> message = new ArrayList<String>();
		List<String> messageDelete = new ArrayList<String>();
		try {
			FileUploadKit fileUploadKit = new FileUploadKit();
			String fileName = UuidKit.getUuid();
			String filePath = String.valueOf(orgUserBean.getInfoType() + Constants.FILE_SEPARATOR
					+ orgUserBean.getOrganizationId());
			String uploadPath = FileInfoConfig.ADMIN_FILE_PATH + filePath;
			fileUploadKit.setAllowedFileType(FileInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
			fileUploadKit.setMaxSize(FileInfoConfig.MAX_SIZE);
			FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
			// 获取上传结果
			if (resultBean.getResult()) {
				List<FileUploadItem> list = resultBean.getFileItemList();
				for (FileUploadItem item : list) {
					if (item.getResult()) {
						// 构建实体Bean，保存数据信息在数据库
						fileInfoBean.setFilePath(filePath + Constants.FILE_SEPARATOR + fileName + "."
								+ item.getExtensionFileName());
						fileInfoBean.setStatus(Constants.STATUS_VALID);
						fileInfoBean.setFileName(item.getFileName().substring(0, item.getFileName().lastIndexOf(".")));
						fileInfoBean.setFileFormat(item.getExtensionFileName());
						fileInfoBean.setFileType(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET);
						fileInfoBean.setInfoType(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO);
						fileInfoBean.setInfoId1(classId);
						// 判断文件是否新建
						HashMap<String, String> condMap = new HashMap<String, String>();
						condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET));
						condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
						condMap.put("infoId1", String.valueOf(classId));
						condMap.put("status", String.valueOf(Constants.STATUS_VALID));
						List<FileInfoBean> FileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
						if (!FileInfoList.isEmpty()) {
							// 修改文件信息
							fileInfoBean.setModifyUser(userId);
							fileInfoBean.setFileId(FileInfoList.get(0).getFileId());
							result = fileInfoBusiness.updateFileInfo(fileInfoBean);
							if (result > 0) {
								fileInfoBusiness.deleteFile(FileInfoList.get(0));
							}
						} else { // 新建文件信息
							fileInfoBean.setCreateUser(userId);
							result = fileInfoBusiness.addFileInfo(fileInfoBean);
						}
						if (result > 0) {
							returnMessage.setCode(Constants.STATUS_VALID);
							returnMessage.setMessage("文件上传成功");
							message.add(returnMessage.getMessage());
						} else {
							returnMessage.setMessage("文件上传失败");
							message.add(returnMessage.getMessage());
						}
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
			ExcelReadResultBean excelReadResultBean = excelReadKit.readExcel(FileInfoConfig.ADMIN_FILE_PATH
					+ fileInfoBean.getFilePath(), 0);
			// 获取培训班的学员，判断是否新插入，或者需要删除学员。
			TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
			FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
			DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
			List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
					.getTrainingClassUserListByClassId(classId);
			Map<Integer, Integer> trainingClassUserMap = new HashMap<Integer, Integer>();
			if (!trainingClassUserList.isEmpty()) {
				for (TrainingClassUserBean trainingClassUserBean : trainingClassUserList) {
					DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByKey(trainingClassUserBean
							.getDeclareId());
					if (declareInfoBean != null) {
						trainingClassUserMap.put(declareInfoBean.getUserId(), declareInfoBean.getDeclareId());
					}
				}
			}

			if (excelReadResultBean != null && excelReadResultBean.getResult()) {
				List<List<String>> contentList = excelReadResultBean.getContentList();
				FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
				FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
				DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
				DeclareReportHandler handler = new DeclareReportHandler();
				DeclareInfoBean declareInfoBean = null;
				FarmerInfoBean farmerInfoBean = null;
				FarmerIndustryInfoBean farmerIndustryInfoBean = null;
				FarmerContactInfoBean farmerContactInfoBean = null;
				TrainingClassUserBean userBean = null;
				int resultInfo = 0;
				int resultContact = 0;
				int resultIndustry = 0;
				int resultMongo = 0;
				int resultClass = 0;
				int resultDeclare = 0;
				int count = 0;
				int countFail = 0;
				for (List<String> content : contentList) {
					if (count <= 2) {
						// 过滤表头数据
						++count;
						continue;
					}
					String name = content.get(0).trim();
					String gender = content.get(1).trim();
					String education = content.get(2).trim();
					String identityNumber = content.get(3).trim();
					// String category = content.get(4);
					String industryType = content.get(5).trim();
					String industryName = content.get(6).trim();
					String industryScale = content.get(7).trim();
					String industryUnit = content.get(8).trim();
					String mobile = toPlainString(content.get(9).trim());
					String province = content.get(10).trim();
					String city = content.get(11).trim();
					String region = content.get(12).trim();
					if (count > 2 && !StringKit.isValid(name)) {
						// 结束循环
						break;
					}
					if (StringKit.isValid(name)) {
						++count;
						farmerInfoBean = new FarmerInfoBean();
						farmerIndustryInfoBean = new FarmerIndustryInfoBean();
						farmerContactInfoBean = new FarmerContactInfoBean();
						// 检验身份证的输入
						if (StringKit.isValid(identityNumber)) {
							farmerInfoBean.setIdentityNumber(identityNumber);
						} else {
							message.add(name + "," + identityNumber + ",身份证输入有误");
							++countFail;
							continue;
						}
						// 检验联系电话的输入
						if (StringKit.isValid(mobile) && mobile.length() >= 11) {
							farmerContactInfoBean.setUserMobile(mobile);
						} else {
							message.add(name + "," + identityNumber + ",联系电话输入有误");
							++countFail;
							continue;
						}
						// 检验根据联系电话是否能正确获取userId
						int classUserId = trainingClassUserBusiness.getUserIdByUserClient(mobile);
						if (classUserId == 0) {
							message.add(name + "," + identityNumber + ",联系电话格式有误");
							++countFail;
							continue;
						}
						// 检验性别的输入
						if (StringKit.isValid(gender)) {
							farmerInfoBean.setUserGender(gender.equals("男") ? 1 : 2);
						} else {
							message.add(name + "," + identityNumber + ",性别输入有误");
							++countFail;
							continue;
						}
						// 检查文化程度
						if (StringKit.isValid(education)) {
							for (String key : FarmerInfoConfig.EDUCATION_LEVEL_MAP.keySet()) {
								String tvalue = FarmerInfoConfig.EDUCATION_LEVEL_MAP.get(key);
								if (education.equals(tvalue)) {
									farmerInfoBean.setEducation(key);
								} else if (education.equals("高中") || education.equals("中专")) {
									farmerInfoBean.setEducation(FarmerInfoConfig.EDUCATION_LEVEL_HEIGHT_SCHOOL);
								} else if (education.equals("本科")) {
									farmerInfoBean.setEducation(FarmerInfoConfig.EDUCATION_LEVEL_UNIVERSITY_AND_ABOVE);
								}
							}
							if (farmerInfoBean.getEducation().isEmpty()) {
								message.add(name + "," + identityNumber + ",文化程度输入有误");
								++countFail;
								continue;
							}
						}
						// 检查产业类型
						IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
						IndustryTypeBean industryTypeBean = industryTypeBusiness.getIndustryTypeByName(industryType);
						if (industryTypeBean == null) {
							message.add(name + "," + identityNumber + ",产业类型输入有误");
							++countFail;
							continue;
						} else {
							farmerIndustryInfoBean.setIndustryTypeId1(industryTypeBean.getTypeId());
							// 检查产业名称
							IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
							List<IndustryInfoBean> industryInfoList = industryInfoBusiness
									.getIndustryInfoListByTypeId(industryTypeBean.getTypeId());
							for (IndustryInfoBean industryInfoBean : industryInfoList) {
								if (industryName.equals(industryInfoBean.getIndustryName())) {
									farmerIndustryInfoBean.setIndustryId1(industryInfoBean.getIndustryId());
									// 检查规模单位
									if (industryUnit.equals(industryInfoBean.getUnit())) {
										farmerIndustryInfoBean.setIndustryUnit1(industryInfoBean.getUnit());
									}
									if (industryType.equals("其他产业") && StringKit.isValid(industryUnit)) {
										farmerIndustryInfoBean.setIndustryUnit1(industryUnit);
									}
								}
							}
							if (farmerIndustryInfoBean.getIndustryId1() == 0) {
								message.add(name + "," + identityNumber + ",产业名称输入有误");
								++countFail;
								continue;
							}
							if (farmerIndustryInfoBean.getIndustryUnit1().isEmpty()) {
								message.add(name + "," + identityNumber + ",规模单位输入有误");
								++countFail;
								continue;
							}
						}
						// 检验产业规模的输入
						if (StringKit.isValid(industryScale)) {
							farmerIndustryInfoBean.setIndustryScale1(Double.parseDouble(industryScale));
						} else {
							message.add(name + "," + identityNumber + ",产业规模输入有误");
							++countFail;
							continue;
						}
						// 检验省市区的输入
						if (StringKit.isValid(province) && StringKit.isValid(city) && StringKit.isValid(region)) {
							farmerContactInfoBean.setProvince(province);
							farmerContactInfoBean.setCity(city);
							farmerContactInfoBean.setRegion(region);
						} else {
							message.add(name + "," + identityNumber + ",省市区输入有误");
							++countFail;
							continue;
						}

						boolean Modify = false;
						int declareId = 0;
						declareInfoBean = new DeclareInfoBean();
						declareInfoBean.setDeclareType(trainingClassInfoBean.getTrainingType());
						declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE);
						DeclareInfoHelper declareInfoHelper = new DeclareInfoHelper();
						declareInfoBean.setDeclareYear(String.valueOf(declareInfoHelper.getDeclareYear()));
						declareInfoBean.setStatus(Constants.STATUS_VALID);
						farmerInfoBean.setStatus(Constants.STATUS_VALID);
						farmerInfoBean.setUserName(name);
						farmerContactInfoBean.setStatus(Constants.STATUS_VALID);
						farmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
						userBean = new TrainingClassUserBean();
						userBean.setClassId(classId);
						userBean.setUserStatus(TrainingClassUserConfig.USER_STATUS_APPROVE);
						userBean.setStatus(Constants.STATUS_VALID);

						if (!trainingClassUserList.isEmpty() && trainingClassUserMap.containsKey(classUserId)) {
							// 原来班级中有此学员
							Modify = true;
						}
						if (Modify) {
							// 从Mongo中根据userId获取用户的数据
							DeclareReportBean declareReportBean = declareReportBusiness
									.getDeclareReportByUserIdAndYear(classUserId,
											DateKit.formatDate(new Date(), DateKit.REGEX_YEAR));
							// 获取激活码
							/*ActiveCodeInfoBusiness activeCodeBusiness = new ActiveCodeInfoBusiness();
							ReturnMessageBean returnMessageBean = activeCodeBusiness.bindActiveCodeWithUser(province,
									city, region, trainingClassInfoBean.getTrainingYear(),
									ActiveCodeInfoConfig.USER_TYPE_FARMER, classUserId, userId);
							if (returnMessageBean.getCode() == Constants.STATUS_NOT_VALID) {
								message.add(name + "," + identityNumber + ",获取激活码有误," + returnMessageBean.getMessage());
								++countFail;
								continue;
							}*/
							// 修改申报对象
							DeclareInfoBean updateDeclareInfoBean = declareInfoBusiness
									.getDeclareInfoByKey(declareReportBean.getDeclareId());
							updateDeclareInfoBean.setStatus(Constants.STATUS_VALID);
							updateDeclareInfoBean.setUserId(classUserId);
							updateDeclareInfoBean.setModifyUser(userId);
							updateDeclareInfoBean.setDeclareType(trainingClassInfoBean.getTrainingType());
							resultDeclare = declareInfoBusiness.updateDeclareInfo(updateDeclareInfoBean);
							// 修改农民信息
							FarmerInfoBean updateFarmerInfoBean = farmerInfoBusiness
									.getFarmerInfoByKey(declareReportBean.getDeclareId());
							updateFarmerInfoBean.setStatus(Constants.STATUS_VALID);
							updateFarmerInfoBean.setModifyUser(userId);
							updateFarmerInfoBean.setEducation(farmerInfoBean.getEducation());
							updateFarmerInfoBean.setIdentityNumber(farmerInfoBean.getIdentityNumber());
							updateFarmerInfoBean.setUserGender(farmerInfoBean.getUserGender());
							updateFarmerInfoBean.setUserName(farmerInfoBean.getUserName());
							resultInfo = farmerInfoBusiness.updateFarmerInfo(updateFarmerInfoBean);
							// 修改联系信息
							FarmerContactInfoBean updateFarmerContactInfoBean = farmerContactInfoBusiness
									.getFarmerContactInfoByKey(declareReportBean.getDeclareId());
							updateFarmerContactInfoBean.setStatus(Constants.STATUS_VALID);
							updateFarmerContactInfoBean.setUserMobile(farmerContactInfoBean.getUserMobile());
							updateFarmerContactInfoBean.setProvince(farmerContactInfoBean.getProvince());
							updateFarmerContactInfoBean.setCity(farmerContactInfoBean.getCity());
							updateFarmerContactInfoBean.setRegion(farmerContactInfoBean.getRegion());
							updateFarmerContactInfoBean.setModifyUser(userId);
							resultContact = farmerContactInfoBusiness
									.updateFarmerContactInfo(updateFarmerContactInfoBean);
							// 修改产业信息
							FarmerIndustryInfoBean updateFarmerIndustryInfoBean = farmerIndustryInfoBusiness
									.getFarmerIndustryInfoByKey(declareReportBean.getDeclareId());
							updateFarmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
							updateFarmerIndustryInfoBean.setModifyUser(userId);
							updateFarmerIndustryInfoBean
									.setIndustryTypeId1(farmerIndustryInfoBean.getIndustryTypeId1());
							updateFarmerIndustryInfoBean.setIndustryId1(farmerIndustryInfoBean.getIndustryId1());
							updateFarmerIndustryInfoBean.setIndustryScale1(farmerIndustryInfoBean.getIndustryScale1());
							updateFarmerIndustryInfoBean.setIndustryUnit1(farmerIndustryInfoBean.getIndustryUnit1());
							updateFarmerIndustryInfoBean.setFarmerType(farmerIndustryInfoBean.getFarmerType());
							resultIndustry = farmerIndustryInfoBusiness
									.updateFarmerIndustryInfo(updateFarmerIndustryInfoBean);
							// 修改学员表
							userBean = trainingClassUserBusiness.getTrainingClassUserByKey(classId,
									declareReportBean.getDeclareId());
							userBean.setStatus(Constants.STATUS_VALID);
							userBean.setModifyUser(userId);
							resultClass = trainingClassUserBusiness.updateTrainingClassUser(userBean);
							// 推送学员到云课堂
							int commandDeclareId = declareId;
							YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
							commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
							commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_USER_TO_CLASS);
							commandBean.setBody(trainingClassUserBusiness.trainingClassUser2MemberAddBean(userBean));

							YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
								@Override
								public boolean process(Object data) {
									int result = 0;
									try {
										ClassRoomMemberAddBean postResult = JsonKit.toBean(data.toString(),
												ClassRoomMemberAddBean.class);
										// 更新到数据库
										TrainingClassUserBean userBean = trainingClassUserBusiness
												.getTrainingClassUserByKey(
														Integer.parseInt(postResult.getClassroomId()), commandDeclareId);
										userBean.setModifyUser(userId);
										userBean.setSyncStatus(Constants.STATUS_VALID);
										result = trainingClassUserBusiness.updateTrainingClassUser(userBean);
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
							if (resultDeclare < 0) {
								message.add(name + "," + identityNumber + ",创建有误");
								++countFail;
							}
							try {
								handler.createDeclareReport(updateDeclareInfoBean, classId, false);
								resultMongo = Constants.STATUS_VALID;
							} catch (Exception e) {
								// TODO Auto-generated catch
								// block
								log.error("总表记录创建失败", e);
							}
							// // 修改的学员从map中除去
							// Iterator<Integer> iter =
							// trainingClassUserMap.keySet().iterator();
							// while (iter.hasNext()) {
							// int key = iter.next();
							// if (classUserId == key) {
							// iter.remove();
							// trainingClassUserMap.remove(key);
							// }
							// }
						} else {
							// 获取激活码
							/*ActiveCodeInfoBusiness activeCodeBusiness = new ActiveCodeInfoBusiness();
							ReturnMessageBean returnMessageBean = activeCodeBusiness.bindActiveCodeWithUser(province,
									city, region, trainingClassInfoBean.getTrainingYear(),
									ActiveCodeInfoConfig.USER_TYPE_FARMER, classUserId, userId);
							if (returnMessageBean.getCode() == Constants.STATUS_NOT_VALID) {
								message.add(name + "," + identityNumber + ",获取激活码有误," + returnMessageBean.getMessage());
								++countFail;
								continue;
							}*/
							// 从Mongo中根据userId获取用户的数据
							DeclareReportBean declareReportBean = declareReportBusiness
									.getDeclareReportByUserIdAndYear(classUserId,
											DateKit.formatDate(new Date(), DateKit.REGEX_YEAR));
							if (declareReportBean == null) {
								// 如果没有此用户的申报信息，需要先新增申报信息
								declareInfoBean.setCreateUser(userId);
								declareInfoBean.setUserId(classUserId);
								declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
								if (declareId > 0) {
									// 新建农民信息
									farmerInfoBean.setCreateUser(userId);
									farmerInfoBean.setDeclareId(declareId);
									resultInfo = farmerInfoBusiness.addFarmerInfo(farmerInfoBean);
									// 新建联系信息
									farmerContactInfoBean.setDeclareId(declareId);
									farmerContactInfoBean.setCreateUser(userId);
									resultContact = farmerContactInfoBusiness
											.addFarmerContactInfo(farmerContactInfoBean);
									// 新建产业信息
									farmerIndustryInfoBean.setDeclareId(declareId);
									farmerIndustryInfoBean.setCreateUser(userId);
									resultIndustry = farmerIndustryInfoBusiness
											.addFarmerIndustryInfo(farmerIndustryInfoBean);
									declareInfoBean.setDeclareId(declareId);
								}
							} else {
								// 如果有此用户的申报信息，需要修改申报信息
								// 修改申报对象
								DeclareInfoBean updateDeclareInfoBean = declareInfoBusiness
										.getDeclareInfoByKey(declareReportBean.getDeclareId());
								declareId = declareReportBean.getDeclareId();
								updateDeclareInfoBean.setStatus(Constants.STATUS_VALID);
								updateDeclareInfoBean.setUserId(classUserId);
								updateDeclareInfoBean.setModifyUser(userId);
								updateDeclareInfoBean.setDeclareType(trainingClassInfoBean.getTrainingType());
								resultDeclare = declareInfoBusiness.updateDeclareInfo(updateDeclareInfoBean);
								// 修改农民信息
								FarmerInfoBean updateFarmerInfoBean = farmerInfoBusiness
										.getFarmerInfoByKey(declareReportBean.getDeclareId());
								updateFarmerInfoBean.setStatus(Constants.STATUS_VALID);
								updateFarmerInfoBean.setModifyUser(userId);
								updateFarmerInfoBean.setEducation(farmerInfoBean.getEducation());
								updateFarmerInfoBean.setIdentityNumber(farmerInfoBean.getIdentityNumber());
								updateFarmerInfoBean.setUserGender(farmerInfoBean.getUserGender());
								updateFarmerInfoBean.setUserName(farmerInfoBean.getUserName());
								resultInfo = farmerInfoBusiness.updateFarmerInfo(updateFarmerInfoBean);
								// 修改联系信息
								FarmerContactInfoBean updateFarmerContactInfoBean = farmerContactInfoBusiness
										.getFarmerContactInfoByKey(declareReportBean.getDeclareId());
								updateFarmerContactInfoBean.setStatus(Constants.STATUS_VALID);
								updateFarmerContactInfoBean.setUserMobile(farmerContactInfoBean.getUserMobile());
								updateFarmerContactInfoBean.setProvince(farmerContactInfoBean.getProvince());
								updateFarmerContactInfoBean.setCity(farmerContactInfoBean.getCity());
								updateFarmerContactInfoBean.setRegion(farmerContactInfoBean.getRegion());
								updateFarmerContactInfoBean.setModifyUser(userId);
								resultContact = farmerContactInfoBusiness
										.updateFarmerContactInfo(updateFarmerContactInfoBean);
								// 修改产业信息
								FarmerIndustryInfoBean updateFarmerIndustryInfoBean = farmerIndustryInfoBusiness
										.getFarmerIndustryInfoByKey(declareReportBean.getDeclareId());
								updateFarmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
								updateFarmerIndustryInfoBean.setModifyUser(userId);
								updateFarmerIndustryInfoBean.setIndustryTypeId1(farmerIndustryInfoBean
										.getIndustryTypeId1());
								updateFarmerIndustryInfoBean.setIndustryId1(farmerIndustryInfoBean.getIndustryId1());
								updateFarmerIndustryInfoBean.setIndustryScale1(farmerIndustryInfoBean
										.getIndustryScale1());
								updateFarmerIndustryInfoBean
										.setIndustryUnit1(farmerIndustryInfoBean.getIndustryUnit1());
								updateFarmerIndustryInfoBean.setFarmerType(farmerIndustryInfoBean.getFarmerType());
								resultIndustry = farmerIndustryInfoBusiness
										.updateFarmerIndustryInfo(updateFarmerIndustryInfoBean);
								declareInfoBean.setDeclareId(declareReportBean.getDeclareId());
								declareInfoBean.setUserId(classUserId);
							}
							try {
								handler.createDeclareReport(declareInfoBean, classId, false);
								resultMongo = Constants.STATUS_VALID;
							} catch (Exception e) {
								// TODO Auto-generated catch
								// block
								log.error("总表记录创建失败", e);
							}

							// 新建学员表
							TrainingClassUserBean existUserBean = trainingClassUserBusiness.getTrainingClassUserByKey(
									classId, declareId);
							if (existUserBean != null) {
								// 如果同一个excel表，同一学员出现两次，则更新
								existUserBean.setStatus(Constants.STATUS_VALID);
								existUserBean.setModifyUser(userId);
								resultClass = trainingClassUserBusiness.updateTrainingClassUser(existUserBean);
								userBean = existUserBean;
							} else {
								userBean.setCreateUser(userId);
								userBean.setDeclareId(declareId);
								userBean.setSignUpTime(DateKit.getCurrentTimestamp());
								resultClass = trainingClassUserBusiness.addTrainingClassUser(userBean);
							}

							// 推送学员到云课堂
							int commandDeclareId = declareId;
							YunClassInvokeCommandBean commandBean = new YunClassInvokeCommandBean();
							commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
							commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_ADD_USER_TO_CLASS);
							commandBean.setBody(trainingClassUserBusiness.trainingClassUser2MemberAddBean(userBean));

							YunClassInvoker invoker = new YunClassInvoker(commandBean, new YunClassInvokCallBack() {
								@Override
								public boolean process(Object data) {
									int result = 0;
									try {
										ClassRoomMemberAddBean postResult = JsonKit.toBean(data.toString(),
												ClassRoomMemberAddBean.class);
										// 更新到数据库
										TrainingClassUserBean userBean = trainingClassUserBusiness
												.getTrainingClassUserByKey(
														Integer.parseInt(postResult.getClassroomId()), commandDeclareId);
										userBean.setModifyUser(userId);
										userBean.setSyncStatus(Constants.STATUS_VALID);
										result = trainingClassUserBusiness.updateTrainingClassUser(userBean);
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
							if (declareId <= 0) {
								message.add(name + "," + identityNumber + ",创建有误");
								++countFail;
								continue;
							}
						}
					}
					if (resultInfo < 0 || resultContact < 0 || resultIndustry < 0 || resultMongo <= 0
							|| resultClass <= 0) {
						message.add(name + "," + identityNumber + ",创建有误");
						++countFail;
						continue;
					}
				}
				// 删除多余的学员
				// for (int key : trainingClassUserMap.keySet()) {
				// HashMap<String, String> queryMap = new HashMap<String,
				// String>();
				// queryMap.put("declareId",
				// String.valueOf(trainingClassUserMap.get(key)));
				// // 从Mongo中获取相应的数据
				// List<DeclareReportBean> declareReportList =
				// declareReportBusiness
				// .getDeclareReportListByMap(queryMap);
				// if (!declareReportList.isEmpty()
				// && declareReportList.get(0).getStatus() !=
				// Constants.STATUS_NOT_VALID) {
				// int decalreReportId =
				// declareReportList.get(0).getDeclareId();
				// declareInfoBean =
				// declareInfoBusiness.getDeclareInfoByKey(decalreReportId);
				// // 修改学员表
				// userBean =
				// trainingClassUserBusiness.getTrainingClassUserByKey(classId,
				// decalreReportId);
				// userBean.setModifyUser(userId);
				// userBean.setStatus(Constants.STATUS_NOT_VALID);
				// resultClass =
				// trainingClassUserBusiness.updateTrainingClassUser(userBean);
				// // 删除推送学员
				// YunClassInvokeCommandBean commandBean = new
				// YunClassInvokeCommandBean();
				// commandBean.setMethod(YunClassInvokeExecutor.HTTP_POST);
				// commandBean.setService(YunClassInvokeConfig.YUN_SERVICE_DELETE_USER_TO_CLASS);
				// commandBean.setBody(trainingClassUserBusiness.trainingClassUser2MemberDeleteBean(userBean));
				//
				// YunClassInvoker invoker = new YunClassInvoker(commandBean,
				// new YunClassInvokCallBack() {
				// @Override
				// public boolean process(Object data) {
				// int result = 0;
				// try {
				// ClassRoomMemberAddBean postResult =
				// JsonKit.toBean(data.toString(),
				// ClassRoomMemberAddBean.class);
				// // 更新到数据库
				// TrainingClassUserBean userBean = trainingClassUserBusiness
				// .getTrainingClassUserByKey(Integer.parseInt(postResult.getClassroomId()),
				// decalreReportId);
				// userBean.setModifyUser(userId);
				// userBean.setSyncStatus(Constants.STATUS_NOT_VALID);
				// result =
				// trainingClassUserBusiness.updateTrainingClassUser(userBean);
				// if (result > 0) {
				// return true;
				// } else {
				// return false;
				// }
				// } catch (Exception e) {
				// return false;
				// }
				// }
				// });
				// YunClassInvokeExecutor.attachAsync(invoker);
				// try {
				// handler.createDeclareReport(declareInfoBean, classId, true);
				// resultMongo = Constants.STATUS_VALID;
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// log.error("总表记录创建失败", e);
				// }
				// if (resultInfo < 0 || resultContact < 0 || resultIndustry < 0
				// || resultMongo <= 0
				// || resultClass <= 0) {
				// messageDelete.add(" ," + key + ",删除有误");
				// }
				// }
				// }
				message.set(
						0,
						message.get(0) + ",导入学员" + String.valueOf(count - 3 - countFail) + "人" + "失败"
								+ String.valueOf(countFail) + "人");
			}
		} catch (Exception e) {
			log.error("", e);
			returnMessage.setMessage("导入失败");
			message.add(returnMessage.getMessage());
		} finally {
		}

		request.setAttribute("message", message);
		// 记录导入结果
		for (String str : message) {
			log.info(str);
		}
		request.setAttribute("messageDelete", messageDelete);
		// 转发
		if (PortalKit.getDevicePath(request).equals("web")) {
			TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
			if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
				TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
						.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
				if (trainingOrganizationInfoBean != null) {
					request.setAttribute("organizationName", trainingOrganizationInfoBean.getOrganizationName());
				}
			}
			if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
				isGovernment = true;
				GovernmentOrganizationInfoBusiness governmentOrganizationInfoBusiness = new GovernmentOrganizationInfoBusiness();
				GovernmentOrganizationInfoBean governmentOrganizationInfoBean = governmentOrganizationInfoBusiness
						.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
				if (governmentOrganizationInfoBean.getOrganizationLevel() != GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
					HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
					return;
				}
				request.setAttribute("GovernmentOrgInfoBean", governmentOrganizationInfoBean);
				HashMap<String, String> condMap = new HashMap<String, String>();
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				condMap.put("province", governmentOrganizationInfoBean.getProvince());
				condMap.put("city", governmentOrganizationInfoBean.getCity());
				condMap.put("region", governmentOrganizationInfoBean.getRegion());
				List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = trainingOrganizationInfoBusiness
						.getTrainingOrganizationInfoListByMap(condMap);
				request.setAttribute("TrainingOrganizationInfoList", trainingOrganizationInfoList);
			}
			// 获取培训班列表
			HashMap<String, String> condMap = new HashMap<String, String>();
			condMap.put("organizationId", String.valueOf(orgUserBean.getOrganizationId()));
			condMap.put("status", String.valueOf(Constants.STATUS_VALID));
			condMap.put("classStatusSelection", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
			List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
					.getTrainingClassInfoListByMap(condMap);

			// 处理待办通知
			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgUserBean);
			request.setAttribute("unreadMessageCount", unreadMessageCount);

			request.setAttribute("isGovernment", isGovernment);
			NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
			request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));
			request.setAttribute("trainingClassInfoList", trainingClassInfoList);
			PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserImport.jsp");
		} else {
			PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH
					+ "TrainingClassUserImportResult.jsp");
		}
	}

	/**
	 * @param str
	 * @return 讲科学记数法的字符串非强制的转换成十进制的字符串
	 */
	private String toPlainString(String str) {
		try {
			BigDecimal bd = new BigDecimal(str);
			return bd.toPlainString();
		} catch (Exception e) {

		}
		return str;
	}

}
