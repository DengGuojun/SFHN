package com.lpmas.sfhn.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class TrainingClassInfoBean {
	@FieldTag(name = "培训班ID")
	private int classId = 0;
	@FieldTag(name = "培训班名称")
	private String className = "";
	@FieldTag(name = "培训机构ID")
	private int organizationId = 0;
	@FieldTag(name = "云课题班级ID")
	private int eduClassId = 0;
	@FieldTag(name = "培训类型")
	private int trainingType = 0;
	@FieldTag(name = "培训班类型")
	private int classType = 0;
	@FieldTag(name = "")
	private int industryBeltId = 0;
	@FieldTag(name = "培育年份")
	private String trainingYear = "";
	@FieldTag(name = "开班人数")
	private int classPeopleQuantity = 0;
	@FieldTag(name = "课程开始时间")
	private Timestamp trainingBeginTime = null;
	@FieldTag(name = "课程结束时间")
	private Timestamp trainingEndTime = null;
	@FieldTag(name = "培训时间")
	private String trainingTime = "";
	@FieldTag(name = "行程安排")
	private String trainingSchedule = "";
	@FieldTag(name = "报名截止时间")
	private Timestamp registrationEndTime = null;
	@FieldTag(name = "省")
	private String province = "";
	@FieldTag(name = "市")
	private String city = "";
	@FieldTag(name = "区")
	private String region = "";
	@FieldTag(name = "招生状态")
	private String recruitStatus = "";
	@FieldTag(name = "验收状态")
	private String acceptStatus = "";
	@FieldTag(name = "培训班状态")
	private String classStatus = "";
	@FieldTag(name = "同步状态")
	private int syncStatus = 0;
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

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getEduClassId() {
		return eduClassId;
	}

	public void setEduClassId(int eduClassId) {
		this.eduClassId = eduClassId;
	}

	public int getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(int trainingType) {
		this.trainingType = trainingType;
	}

	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public int getIndustryBeltId() {
		return industryBeltId;
	}

	public void setIndustryBeltId(int industryBeltId) {
		this.industryBeltId = industryBeltId;
	}

	public String getTrainingYear() {
		return trainingYear;
	}

	public void setTrainingYear(String trainingYear) {
		this.trainingYear = trainingYear;
	}

	public int getClassPeopleQuantity() {
		return classPeopleQuantity;
	}

	public void setClassPeopleQuantity(int classPeopleQuantity) {
		this.classPeopleQuantity = classPeopleQuantity;
	}

	public Timestamp getTrainingBeginTime() {
		return trainingBeginTime;
	}

	public void setTrainingBeginTime(Timestamp trainingBeginTime) {
		this.trainingBeginTime = trainingBeginTime;
	}

	public Timestamp getTrainingEndTime() {
		return trainingEndTime;
	}

	public void setTrainingEndTime(Timestamp trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}

	public String getTrainingTime() {
		return trainingTime;
	}

	public void setTrainingTime(String trainingTime) {
		this.trainingTime = trainingTime;
	}

	public String getTrainingSchedule() {
		return trainingSchedule;
	}

	public void setTrainingSchedule(String trainingSchedule) {
		this.trainingSchedule = trainingSchedule;
	}

	public Timestamp getRegistrationEndTime() {
		return registrationEndTime;
	}

	public void setRegistrationEndTime(Timestamp registrationEndTime) {
		this.registrationEndTime = registrationEndTime;
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

	public String getRecruitStatus() {
		return recruitStatus;
	}

	public void setRecruitStatus(String recruitStatus) {
		this.recruitStatus = recruitStatus;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
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