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

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.transfer.FileUploadResultBean.FileUploadItem;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.util.UuidKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.DeclareImageBean;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerJobInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean;
import com.lpmas.sfhn.declare.config.DeclareImageInfoConfig;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareImageBusiness;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerContactInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerIndustryInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerJobInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerSkillInfoBusiness;
import com.lpmas.sfhn.portal.declare.handler.DeclareReportHandler;

@WebServlet("/sfhn/migrate/DeclareInfoImport.do")
public class DeclareInfoImport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(DeclareInfoImport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoImport() {
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
		int count = 0;
		int identityNumberCount = 0;
		boolean flag = false;
		List<String> message = new ArrayList<String>();
		List<String> identityNumberMessage = new ArrayList<String>();
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
				DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
				FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
				FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
				FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
				FarmerJobInfoBusiness farmerJobInfoBusiness = new FarmerJobInfoBusiness();
				FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
				DeclareImageBusiness declareImageBusiness = new DeclareImageBusiness();
				for (List<String> content : contentList) {
					if (!content.get(0).isEmpty()) {
						int result = 0;
						DeclareInfoBean declareInfoBean = new DeclareInfoBean();
						declareInfoBean.setMemo(content.get(0));
						String declareType = content.get(1);
						String declareYear = content.get(2);
						String declareStatus = content.get(5);

						DeclareImageBean imageBean = new DeclareImageBean();
						imageBean.setImageType(DeclareImageInfoConfig.IMG_TYPE_ONE_INCH);

						FarmerContactInfoBean contactInfoBean = new FarmerContactInfoBean();
						contactInfoBean.setMemo(content.get(0));
						String userMobile = content.get(7);
						String userEmail = content.get(8);
						String userQq = content.get(9);
						String userWechat = content.get(10);
						String familyPerson = content.get(11);
						String province = content.get(12);
						String city = content.get(13);
						String region = content.get(14);
						String address = content.get(15);

						FarmerIndustryInfoBean farmerIndustryInfoBean = new FarmerIndustryInfoBean();
						farmerIndustryInfoBean.setMemo(content.get(0));
						String industryProvince = content.get(16);
						String industryCity = content.get(17);
						String industryRegion = content.get(18);
						String familyWorkingPerson = content.get(19);
						String driveFarmerNumber = content.get(20);
						String areaType = content.get(21);
						String economicAreaType = content.get(22);
						String industryTypeId1 = content.get(23);
						String industryId1 = content.get(24);
						String industryScale1 = content.get(25);
						String experience1 = content.get(26);
						String industryUnit1 = content.get(27);
						String industryTypeId2 = content.get(28);
						String industryId2 = content.get(29);
						String industryScale2 = content.get(30);
						String experience2 = content.get(31);
						String industryUnit2 = content.get(32);
						String industryTypeId3 = content.get(33);
						String industryId3 = content.get(34);
						String industryScale3 = content.get(35);
						String experience3 = content.get(36);
						String industryUnit3 = content.get(37);
						String lastYearIncome = content.get(38);
						String lastYearFamilyIncome = content.get(39);
						String hasRegisted = content.get(64);
						String isExampleFamilyFarm = content.get(65);
						String familyFarmType = content.get(66);
						String exampleFarmLevel = content.get(67);
						String farmLandScale = content.get(68);
						String industryJobType = content.get(69);
						String industryJobId = content.get(70);
						String industryExperience = content.get(71);
						String industryIncome = content.get(72);
						String industryServiceScale = content.get(73);

						FarmerInfoBean farmerInfoBean = new FarmerInfoBean();
						farmerInfoBean.setMemo(content.get(0));
						String userName = content.get(40);
						String userGender = content.get(41);
						String userBirthday = content.get(42);
						String nationality = content.get(43);
						String education = content.get(44);
						String major = content.get(45);
						String identityNumber = content.get(46);
						String politicalStatus = content.get(47);
						String farmerType = content.get(48);
						farmerInfoBean.setIdentityNumber(identityNumber);
						if (StringKit.isValid(farmerInfoBusiness.checkHasExistIdentityNumber(farmerInfoBean).getMessage())) {
							++identityNumberCount;
							identityNumberMessage.add(identityNumber);
							continue;
						}

						FarmerJobInfoBean jobInfoBean = new FarmerJobInfoBean();
						jobInfoBean.setMemo(content.get(0));
						String jobType = content.get(49);
						String jobId = content.get(74);
						String experience = content.get(50);
						String income = content.get(51);
						String companyType = content.get(52);
						String jobProvince = content.get(53);
						String jobCity = content.get(54);
						String jobRegion = content.get(55);

						FarmerSkillInfoBean skillInfoBean = new FarmerSkillInfoBean();
						skillInfoBean.setMemo(content.get(0));
						String isTrained = content.get(56);
						String otherTrainingTime = content.get(57);
						String hasNationalCertification = content.get(58);
						String hasNewTypeTrainingCertification = content.get(59);
						String hasNewTypeCertification = content.get(60);
						String hasNoCertification = content.get(61);
						String applyType = content.get(75);
						String certificationGrade = content.get(76);
						String certificationDate = content.get(77);
						String certificationDepartment = content.get(78);
						String certificationTitle = content.get(81);

						// 检验类型
						if (StringKit.isValid(declareType)) {
							declareInfoBean.setDeclareType(Integer.valueOf(declareType));
						}
						// 年份
						declareInfoBean.setDeclareYear(declareYear);
						// 状态
						declareInfoBean.setDeclareStatus(declareStatus);

						declareInfoBean.setStatus(Constants.STATUS_VALID);
						int declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
						if (declareId > 0) {
							declareInfoBean.setDeclareId(declareId);

							imageBean.setDeclareId(declareId);
							imageBean.setStatus(Constants.STATUS_VALID);
							declareImageBusiness.addDeclareImage(imageBean);

							contactInfoBean.setUserMobile(userMobile);
							contactInfoBean.setUserEmail(userEmail);
							contactInfoBean.setUserQq(userQq);
							contactInfoBean.setUserWechat(userWechat);
							if (StringKit.isValid(familyPerson)) {
								contactInfoBean.setFamilyPerson(Integer.valueOf(familyPerson));
							}
							contactInfoBean.setProvince(province);
							contactInfoBean.setCity(city);
							contactInfoBean.setRegion(region);
							contactInfoBean.setAddress(address);
							contactInfoBean.setDeclareId(declareId);
							contactInfoBean.setStatus(Constants.STATUS_VALID);
							farmerContactInfoBusiness.addFarmerContactInfo(contactInfoBean);

							farmerIndustryInfoBean.setIndustryProvince(industryProvince);
							farmerIndustryInfoBean.setIndustryCity(industryCity);
							farmerIndustryInfoBean.setIndustryRegion(industryRegion);
							farmerIndustryInfoBean.setFamilyWorkingPerson(Integer.valueOf(familyWorkingPerson));
							farmerIndustryInfoBean.setDriveFarmerNumber(Integer.valueOf(driveFarmerNumber));
							farmerIndustryInfoBean.setAreaType(Integer.valueOf(areaType));
							farmerIndustryInfoBean.setEconomicAreaType(Integer.valueOf(economicAreaType));
							farmerIndustryInfoBean.setIndustryTypeId1(Integer.valueOf(industryTypeId1));
							farmerIndustryInfoBean.setIndustryId1(Integer.valueOf(industryId1));
							farmerIndustryInfoBean.setIndustryScale1(Double.valueOf(industryScale1));
							farmerIndustryInfoBean.setExperience1(Double.valueOf(experience1));
							farmerIndustryInfoBean.setIndustryUnit1(industryUnit1);
							farmerIndustryInfoBean.setIndustryTypeId2(Integer.valueOf(industryTypeId2));
							farmerIndustryInfoBean.setIndustryId2(Integer.valueOf(industryId2));
							farmerIndustryInfoBean.setIndustryScale2(Double.valueOf(industryScale2));
							farmerIndustryInfoBean.setExperience2(Double.valueOf(experience2));
							farmerIndustryInfoBean.setIndustryUnit2(industryUnit2);
							farmerIndustryInfoBean.setIndustryTypeId3(Integer.valueOf(industryTypeId3));
							farmerIndustryInfoBean.setIndustryId3(Integer.valueOf(industryId3));
							farmerIndustryInfoBean.setIndustryScale3(Double.valueOf(industryScale3));
							farmerIndustryInfoBean.setExperience3(Double.valueOf(experience3));
							farmerIndustryInfoBean.setIndustryUnit3(industryUnit3);
							farmerIndustryInfoBean.setLastYearIncome(Double.valueOf(lastYearIncome));
							farmerIndustryInfoBean.setLastYearFamilyIncome(Double.valueOf(lastYearFamilyIncome));
							farmerIndustryInfoBean.setHasRegisted(Integer.valueOf(hasRegisted));
							farmerIndustryInfoBean.setIsExampleFamilyFarm(Integer.valueOf(isExampleFamilyFarm));
							farmerIndustryInfoBean.setFamilyFarmType(familyFarmType);
							farmerIndustryInfoBean.setExampleFarmLevel(exampleFarmLevel);
							farmerIndustryInfoBean.setFarmLandScale(Double.valueOf(farmLandScale));
							farmerIndustryInfoBean.setJobType(Integer.valueOf(industryJobType));
							farmerIndustryInfoBean.setJobId(Integer.valueOf(industryJobId));
							farmerIndustryInfoBean.setExperience(Double.valueOf(industryExperience));
							farmerIndustryInfoBean.setIncome(Double.valueOf(industryIncome));
							farmerIndustryInfoBean.setServiceScale(industryServiceScale);
							farmerIndustryInfoBean.setDeclareId(declareId);
							farmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
							farmerIndustryInfoBusiness.addFarmerIndustryInfo(farmerIndustryInfoBean);

							farmerInfoBean.setUserName(userName);
							farmerInfoBean.setUserGender(Integer.valueOf(userGender));
							farmerInfoBean.setUserBirthday(DateKit.date2SqlDate(DateKit.str2Date(userBirthday, DateKit.DEFAULT_DATE_FORMAT)));
							farmerInfoBean.setNationality(nationality);
							farmerInfoBean.setEducation(education);
							farmerInfoBean.setMajor(major);
							farmerInfoBean.setPoliticalStatus(politicalStatus);
							farmerInfoBean.setFarmerType(farmerType);
							farmerInfoBean.setDeclareId(declareId);
							farmerInfoBean.setStatus(Constants.STATUS_VALID);
							farmerInfoBusiness.addFarmerInfo(farmerInfoBean);

							jobInfoBean.setJobType(Integer.valueOf(jobType));
							jobInfoBean.setJobId(Integer.valueOf(jobId));
							jobInfoBean.setExperience(Double.valueOf(experience));
							jobInfoBean.setIncome(Double.valueOf(income));
							jobInfoBean.setCompanyType(companyType);
							jobInfoBean.setJobProvince(jobProvince);
							jobInfoBean.setJobCity(jobCity);
							jobInfoBean.setJobRegion(jobRegion);
							jobInfoBean.setDeclareId(declareId);
							jobInfoBean.setStatus(Constants.STATUS_VALID);
							farmerJobInfoBusiness.addFarmerJobInfo(jobInfoBean);

							skillInfoBean.setIsTrained(Integer.valueOf(isTrained));
							skillInfoBean.setOtherTrainingTime(Integer.valueOf(otherTrainingTime));
							skillInfoBean.setHasNationalCertification(Integer.valueOf(hasNationalCertification));
							skillInfoBean.setHasNewTypeTrainingCertification(Integer.valueOf(hasNewTypeTrainingCertification));
							skillInfoBean.setHasNewTypeCertification(Integer.valueOf(hasNewTypeCertification));
							skillInfoBean.setHasNoCertification(Integer.valueOf(hasNoCertification));
							skillInfoBean.setApplyType(applyType);
							skillInfoBean.setCertificationGrade(certificationGrade);
							skillInfoBean.setCertificationDate(DateKit.str2Timestamp(certificationDate, DateKit.DEFAULT_DATE_FORMAT));
							skillInfoBean.setCertificationDepartment(certificationDepartment);
							skillInfoBean.setCertificationTitle(certificationTitle);
							skillInfoBean.setDeclareId(declareId);
							skillInfoBean.setStatus(Constants.STATUS_VALID);
							farmerSkillInfoBusiness.addFarmerSkillInfo(skillInfoBean);

							DeclareReportHandler handler = new DeclareReportHandler();
							try {
								handler.createDeclareReport(declareInfoBean);
								result = Constants.STATUS_VALID;
								++count;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								log.error("审核操作失败，总表记录创建失败", e);
							}
						}

						if (result < 0) {
							message.add(content.get(0) + "导入失败;");
							flag = true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			log.info("插入" + count + "条,重复身份证" + identityNumberCount + "条：" + ListKit.list2String(identityNumberMessage, " "));
		}
		if (flag) {
			HttpResponseKit.alertMessage(response, "插入" + count + "条；" + ListKit.list2String(message, " "), "/sfhn/migrate/DataMigrationImport.do");
		} else {
			HttpResponseKit.alertMessage(response,
					"处理成功,插入" + count + "条,重复身份证" + identityNumberCount + "条：" + ListKit.list2String(identityNumberMessage, " "),
					"/sfhn/migrate/DataMigrationImport.do");
		}
	}
}
