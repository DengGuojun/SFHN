package com.lpmas.sfhn.declare.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class DeclareReportBean {
	private String _id;
	private int declareId = 0;
	private int userId = 0;
	private int declareType = 0;
	private String declareStatus = "";
	private String declareYear = "";
	private String imageType = "";
	private String imagePath = "";
	private String userMobile = "";
	private String userEmail = "";
	private String userQq = "";
	private String userWechat = "";
	private String province = "";
	private String city = "";
	private String region = "";
	private String address = "";
	private String industryProvince = "";
	private String industryCity = "";
	private String industryRegion = "";
	private int familyPerson = 0;
	private int hasRegisted = 0;
	private int isExampleFamilyFarm = 0;
	private String familyFarmType = "";
	private String exampleFarmLevel = "";
	private String farmerType = "";
	private double farmLandScale = 0;
	private int familyWorkingPerson = 0;
	private int driveFarmerNumber = 0;
	private int areaType = 0;
	private int economicAreaType = 0;
	private int industryJobType = 0;
	private int industryJobId = 0;
	private double industryExperience = 0;
	private double industryIncome = 0;
	private String serviceScale = "";
	private int industryTypeId1 = 0;
	private int industryId1 = 0;
	private double industryScale1 = 0;
	private double experience1 = 0;
	private int industryTypeId2 = 0;
	private int industryId2 = 0;
	private double industryScale2 = 0;
	private double experience2 = 0;
	private int industryTypeId3 = 0;
	private int industryId3 = 0;
	private double industryScale3 = 0;
	private String industryUnit1 = "";
	private String industryUnit2 = "";
	private String industryUnit3 = "";
	private double experience3 = 0;
	private double lastYearIncome = 0;
	private double lastYearFamilyIncome = 0;
	private String userName = "";
	private int userGender = 0;
	private Date userBirthday = null;
	private String nationality = "";
	private String education = "";
	private String major = "";
	private String identityNumber = "";
	private String politicalStatus = "";
	private int jobType = 0;
	private int jobName = 0;
	private double experience = 0;
	private double income = 0;
	private String companyType = "";
	private String jobProvince = "";
	private String jobCity = "";
	private String jobRegion = "";
	private int isTrained = 0;
	private String applyType = "";
	private String certificationGrade = "";
	private Timestamp certificationDate = null;
	private String certificationDepartment = "";
	private String certificationTitle = "";
	private int otherTrainingTime = 0;
	private int hasNationalCertification = 0;
	private int hasNewTypeTrainingCertification = 0;
	private int hasNewTypeCertification = 0;
	private int hasNoCertification = 0;
	private Timestamp modifyTime = null;
	private int modifyUser = 0;
	private Timestamp createTime = null;
	private int createUser = 0;
	private int status = 0;
	private List<Integer> trainingClassInfoList;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getDeclareType() {
		return declareType;
	}

	public void setDeclareType(int declareType) {
		this.declareType = declareType;
	}

	public int getDeclareId() {
		return declareId;
	}

	public void setDeclareId(int declareId) {
		this.declareId = declareId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeclareStatus() {
		return declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getUserWechat() {
		return userWechat;
	}

	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}

	public int getFamilyPerson() {
		return familyPerson;
	}

	public void setFamilyPerson(int familyPerson) {
		this.familyPerson = familyPerson;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustryProvince() {
		return industryProvince;
	}

	public void setIndustryProvince(String industryProvince) {
		this.industryProvince = industryProvince;
	}

	public String getIndustryCity() {
		return industryCity;
	}

	public void setIndustryCity(String industryCity) {
		this.industryCity = industryCity;
	}

	public String getIndustryRegion() {
		return industryRegion;
	}

	public void setIndustryRegion(String industryRegion) {
		this.industryRegion = industryRegion;
	}

	public int getFamilyWorkingPerson() {
		return familyWorkingPerson;
	}

	public void setFamilyWorkingPerson(int familyWorkingPerson) {
		this.familyWorkingPerson = familyWorkingPerson;
	}

	public int getDriveFarmerNumber() {
		return driveFarmerNumber;
	}

	public void setDriveFarmerNumber(int driveFarmerNumber) {
		this.driveFarmerNumber = driveFarmerNumber;
	}

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public int getEconomicAreaType() {
		return economicAreaType;
	}

	public void setEconomicAreaType(int economicAreaType) {
		this.economicAreaType = economicAreaType;
	}

	public int getIndustryTypeId1() {
		return industryTypeId1;
	}

	public void setIndustryTypeId1(int industryTypeId1) {
		this.industryTypeId1 = industryTypeId1;
	}

	public int getIndustryId1() {
		return industryId1;
	}

	public void setIndustryId1(int industryId1) {
		this.industryId1 = industryId1;
	}

	public double getIndustryScale1() {
		return industryScale1;
	}

	public void setIndustryScale1(double industryScale1) {
		this.industryScale1 = industryScale1;
	}

	public double getExperience1() {
		return experience1;
	}

	public void setExperience1(double experience1) {
		this.experience1 = experience1;
	}

	public int getIndustryTypeId2() {
		return industryTypeId2;
	}

	public void setIndustryTypeId2(int industryTypeId2) {
		this.industryTypeId2 = industryTypeId2;
	}

	public int getIndustryId2() {
		return industryId2;
	}

	public void setIndustryId2(int industryId2) {
		this.industryId2 = industryId2;
	}

	public double getIndustryScale2() {
		return industryScale2;
	}

	public void setIndustryScale2(double industryScale2) {
		this.industryScale2 = industryScale2;
	}

	public double getExperience2() {
		return experience2;
	}

	public void setExperience2(double experience2) {
		this.experience2 = experience2;
	}

	public int getIndustryTypeId3() {
		return industryTypeId3;
	}

	public void setIndustryTypeId3(int industryTypeId3) {
		this.industryTypeId3 = industryTypeId3;
	}

	public int getIndustryId3() {
		return industryId3;
	}

	public void setIndustryId3(int industryId3) {
		this.industryId3 = industryId3;
	}

	public double getIndustryScale3() {
		return industryScale3;
	}

	public void setIndustryScale3(double industryScale3) {
		this.industryScale3 = industryScale3;
	}

	public double getExperience3() {
		return experience3;
	}

	public void setExperience3(double experience3) {
		this.experience3 = experience3;
	}

	public double getLastYearIncome() {
		return lastYearIncome;
	}

	public void setLastYearIncome(double lastYearIncome) {
		this.lastYearIncome = lastYearIncome;
	}

	public double getLastYearFamilyIncome() {
		return lastYearFamilyIncome;
	}

	public void setLastYearFamilyIncome(double lastYearFamilyIncome) {
		this.lastYearFamilyIncome = lastYearFamilyIncome;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserGender() {
		return userGender;
	}

	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getFarmerType() {
		return farmerType;
	}

	public void setFarmerType(String farmerType) {
		this.farmerType = farmerType;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getJobProvince() {
		return jobProvince;
	}

	public void setJobProvince(String jobProvince) {
		this.jobProvince = jobProvince;
	}

	public String getJobCity() {
		return jobCity;
	}

	public void setJobCity(String jobCity) {
		this.jobCity = jobCity;
	}

	public String getJobRegion() {
		return jobRegion;
	}

	public void setJobRegion(String jobRegion) {
		this.jobRegion = jobRegion;
	}

	public int getIsTrained() {
		return isTrained;
	}

	public void setIsTrained(int isTrained) {
		this.isTrained = isTrained;
	}

	public int getOtherTrainingTime() {
		return otherTrainingTime;
	}

	public void setOtherTrainingTime(int otherTrainingTime) {
		this.otherTrainingTime = otherTrainingTime;
	}

	public int getHasNationalCertification() {
		return hasNationalCertification;
	}

	public void setHasNationalCertification(int hasNationalCertification) {
		this.hasNationalCertification = hasNationalCertification;
	}

	public int getHasNewTypeTrainingCertification() {
		return hasNewTypeTrainingCertification;
	}

	public void setHasNewTypeTrainingCertification(int hasNewTypeTrainingCertification) {
		this.hasNewTypeTrainingCertification = hasNewTypeTrainingCertification;
	}

	public int getHasNewTypeCertification() {
		return hasNewTypeCertification;
	}

	public void setHasNewTypeCertification(int hasNewTypeCertification) {
		this.hasNewTypeCertification = hasNewTypeCertification;
	}

	public int getHasNoCertification() {
		return hasNoCertification;
	}

	public void setHasNoCertification(int hasNoCertification) {
		this.hasNoCertification = hasNoCertification;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getIndustryUnit1() {
		return industryUnit1;
	}

	public void setIndustryUnit1(String industryUnit1) {
		this.industryUnit1 = industryUnit1;
	}

	public String getIndustryUnit2() {
		return industryUnit2;
	}

	public void setIndustryUnit2(String industryUnit2) {
		this.industryUnit2 = industryUnit2;
	}

	public String getIndustryUnit3() {
		return industryUnit3;
	}

	public void setIndustryUnit3(String industryUnit3) {
		this.industryUnit3 = industryUnit3;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public int getHasRegisted() {
		return hasRegisted;
	}

	public void setHasRegisted(int hasRegisted) {
		this.hasRegisted = hasRegisted;
	}

	public int getIsExampleFamilyFarm() {
		return isExampleFamilyFarm;
	}

	public void setIsExampleFamilyFarm(int isExampleFamilyFarm) {
		this.isExampleFamilyFarm = isExampleFamilyFarm;
	}

	public String getFamilyFarmType() {
		return familyFarmType;
	}

	public void setFamilyFarmType(String familyFarmType) {
		this.familyFarmType = familyFarmType;
	}

	public String getExampleFarmLevel() {
		return exampleFarmLevel;
	}

	public void setExampleFarmLevel(String exampleFarmLevel) {
		this.exampleFarmLevel = exampleFarmLevel;
	}

	public double getFarmLandScale() {
		return farmLandScale;
	}

	public void setFarmLandScale(double farmLandScale) {
		this.farmLandScale = farmLandScale;
	}

	public int getIndustryJobType() {
		return industryJobType;
	}

	public void setIndustryJobType(int industryJobType) {
		this.industryJobType = industryJobType;
	}

	public int getIndustryJobId() {
		return industryJobId;
	}

	public void setIndustryJobId(int industryJobId) {
		this.industryJobId = industryJobId;
	}

	public double getIndustryExperience() {
		return industryExperience;
	}

	public void setIndustryExperience(double industryExperience) {
		this.industryExperience = industryExperience;
	}

	public double getIndustryIncome() {
		return industryIncome;
	}

	public void setIndustryIncome(double industryIncome) {
		this.industryIncome = industryIncome;
	}

	public String getServiceScale() {
		return serviceScale;
	}

	public void setServiceScale(String serviceScale) {
		this.serviceScale = serviceScale;
	}

	public int getJobName() {
		return jobName;
	}

	public void setJobName(int jobName) {
		this.jobName = jobName;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getCertificationGrade() {
		return certificationGrade;
	}

	public void setCertificationGrade(String certificationGrade) {
		this.certificationGrade = certificationGrade;
	}

	public Timestamp getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Timestamp certificationDate) {
		this.certificationDate = certificationDate;
	}

	public String getCertificationDepartment() {
		return certificationDepartment;
	}

	public void setCertificationDepartment(String certificationDepartment) {
		this.certificationDepartment = certificationDepartment;
	}

	public String getCertificationTitle() {
		return certificationTitle;
	}

	public void setCertificationTitle(String certificationTitle) {
		this.certificationTitle = certificationTitle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeclareYear() {
		return declareYear;
	}

	public void setDeclareYear(String declareYear) {
		this.declareYear = declareYear;
	}

	public List<Integer> getTrainingClassInfoList() {
		return trainingClassInfoList;
	}

	public void setTrainingClassInfoList(List<Integer> trainingClassInfoList) {
		this.trainingClassInfoList = trainingClassInfoList;
	}
}
