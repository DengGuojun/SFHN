package com.lpmas.sfhn.declare.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class FarmerIndustryInfoBean {
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "产业所在省")
	private String industryProvince = "";
	@FieldTag(name = "是否登记注册")
	private int hasRegisted = 0;
	@FieldTag(name = "是否示范性家庭农场")
	private int isExampleFamilyFarm = 0;
	@FieldTag(name = "家庭农场类型")
	private String familyFarmType = "";
	@FieldTag(name = "示范性农场级别")
	private String exampleFarmLevel = "";
	@FieldTag(name = "人员类别")
	private String farmerType = "";
	@FieldTag(name = "土地规模")
	private double farmLandScale = 0;
	@FieldTag(name = "产业所在市")
	private String industryCity = "";
	@FieldTag(name = "产业所在区")
	private String industryRegion = "";
	@FieldTag(name = "家庭总人口")
	private int familyPerson = 0;
	@FieldTag(name = "家庭从业人数")
	private int familyWorkingPerson = 0;
	@FieldTag(name = "带动农民数量")
	private int driveFarmerNumber = 0;
	@FieldTag(name = "地区类型")
	private int areaType = 0;
	@FieldTag(name = "经济区域类型")
	private int economicAreaType = 0;
	@FieldTag(name = "从事工种岗位类型")
	private int jobType = 0;
	@FieldTag(name = "从业年限")
	private int jobId = 0;
	@FieldTag(name = "从业年限")
	private double experience = 0;
	@FieldTag(name = "年收入")
	private double income = 0;
	@FieldTag(name = "服务规模")
	private String serviceScale = "";
	@FieldTag(name = "产业类型1")
	private int industryTypeId1 = 0;
	@FieldTag(name = "产业名称1")
	private int industryId1 = 0;
	@FieldTag(name = "产业规模1")
	private double industryScale1 = 0;
	@FieldTag(name = "产业单位1")
	private String industryUnit1 = "";
	@FieldTag(name = "从事年限1(年)")
	private double experience1 = 0;
	@FieldTag(name = "产业类型2")
	private int industryTypeId2 = 0;
	@FieldTag(name = "产业名称2")
	private int industryId2 = 0;
	@FieldTag(name = "产业规模2")
	private double industryScale2 = 0;
	@FieldTag(name = "产业单位2")
	private String industryUnit2 = "";
	@FieldTag(name = "从事年限2(年)")
	private double experience2 = 0;
	@FieldTag(name = "产业类型3")
	private int industryTypeId3 = 0;
	@FieldTag(name = "产业名称3")
	private int industryId3 = 0;
	@FieldTag(name = "产业规模3")
	private double industryScale3 = 0;
	@FieldTag(name = "从事年限3(年)")
	private double experience3 = 0;
	@FieldTag(name = "产业单位3")
	private String industryUnit3 = "";
	@FieldTag(name = "上年度产业收入(万元)")
	private double lastYearIncome = 0;
	@FieldTag(name = "上年度家庭收入(万元)")
	private double lastYearFamilyIncome = 0;
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;
	@FieldTag(name = "备注")
	private String memo = "";

	public int getDeclareId() {
		return declareId;
	}

	public void setDeclareId(int declareId) {
		this.declareId = declareId;
	}

	public String getIndustryProvince() {
		return industryProvince;
	}

	public void setIndustryProvince(String industryProvince) {
		this.industryProvince = industryProvince;
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

	public String getFarmerType() {
		return farmerType;
	}

	public void setFarmerType(String farmerType) {
		this.farmerType = farmerType;
	}

	public double getFarmLandScale() {
		return farmLandScale;
	}

	public void setFarmLandScale(double farmLandScale) {
		this.farmLandScale = farmLandScale;
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

	public int getFamilyPerson() {
		return familyPerson;
	}

	public void setFamilyPerson(int familyPerson) {
		this.familyPerson = familyPerson;
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

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
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

	public String getServiceScale() {
		return serviceScale;
	}

	public void setServiceScale(String serviceScale) {
		this.serviceScale = serviceScale;
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

	public String getIndustryUnit1() {
		return industryUnit1;
	}

	public void setIndustryUnit1(String industryUnit1) {
		this.industryUnit1 = industryUnit1;
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

	public String getIndustryUnit2() {
		return industryUnit2;
	}

	public void setIndustryUnit2(String industryUnit2) {
		this.industryUnit2 = industryUnit2;
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

	public String getIndustryUnit3() {
		return industryUnit3;
	}

	public void setIndustryUnit3(String industryUnit3) {
		this.industryUnit3 = industryUnit3;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}