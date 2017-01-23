package com.lpmas.sfhn.declare.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class DeclareInfoBean {
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "客户ID")
	private int userId = 0;
	@FieldTag(name = "申报类型")
	private int declareType = 0;
	@FieldTag(name = "申报年份")
	private String declareYear = "";
	@FieldTag(name = "申报状态")
	private String declareStatus = "";
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDeclareType() {
		return declareType;
	}

	public void setDeclareType(int declareType) {
		this.declareType = declareType;
	}

	public String getDeclareYear() {
		return declareYear;
	}

	public void setDeclareYear(String declareYear) {
		this.declareYear = declareYear;
	}

	public String getDeclareStatus() {
		return declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
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