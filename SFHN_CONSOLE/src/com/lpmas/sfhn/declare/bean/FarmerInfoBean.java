package com.lpmas.sfhn.declare.bean;

import java.sql.Date;
import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class FarmerInfoBean {
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "姓名")
	private String userName = "";
	@FieldTag(name = "性别")
	private int userGender = 0;
	@FieldTag(name = "出生日期")
	private Date userBirthday = null;
	@FieldTag(name = "民族")
	private String nationality = "";
	@FieldTag(name = "文化程度")
	private String education = "";
	@FieldTag(name = "专业")
	private String major = "";
	@FieldTag(name = "身份证号")
	private String identityNumber = "";
	@FieldTag(name = "政治面貌")
	private String politicalStatus = "";
	@FieldTag(name = "人员类别")
	private String farmerType = "";
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