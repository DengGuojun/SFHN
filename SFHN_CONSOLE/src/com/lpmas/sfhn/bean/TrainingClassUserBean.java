package com.lpmas.sfhn.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class TrainingClassUserBean {
	@FieldTag(name = "培训班ID")
	private int classId = 0;
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "报名时间")
	private Timestamp signUpTime = null;
	@FieldTag(name = "考核结果")
	private int examResult = 0;
	@FieldTag(name = "认定结果")
	private int authResult = 0;
	@FieldTag(name = "学员状态")
	private String userStatus = "";
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

	public int getDeclareId() {
		return declareId;
	}

	public void setDeclareId(int declareId) {
		this.declareId = declareId;
	}

	public Timestamp getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(Timestamp signUpTime) {
		this.signUpTime = signUpTime;
	}

	public int getExamResult() {
		return examResult;
	}

	public void setExamResult(int examResult) {
		this.examResult = examResult;
	}

	public int getAuthResult() {
		return authResult;
	}

	public void setAuthResult(int authResult) {
		this.authResult = authResult;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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