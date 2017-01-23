package com.lpmas.sfhn.declare.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class FarmerJobInfoBean {
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "工作岗位类型ID")
	private int jobType = 0;
	@FieldTag(name = "岗位ID")
	private int jobId = 0;
	@FieldTag(name = "年限")
	private double experience = 0;
	@FieldTag(name = "年收入(万)")
	private double income = 0;
	@FieldTag(name = "单位类型")
	private String companyType = "";
	@FieldTag(name = "工作地点(省)")
	private String jobProvince = "";
	@FieldTag(name = "工作地点(市)")
	private String jobCity = "";
	@FieldTag(name = "工作地点(县/区)")
	private String jobRegion = "";
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