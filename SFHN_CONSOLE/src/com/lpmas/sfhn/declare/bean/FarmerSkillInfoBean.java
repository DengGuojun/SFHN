package com.lpmas.sfhn.declare.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class FarmerSkillInfoBean {
	@FieldTag(name = "申报ID")
	private int declareId = 0;
	@FieldTag(name = "是否曾经参加培训")
	private int isTrained = 0;
	@FieldTag(name = "申请方式")
	private String applyType = "";
	@FieldTag(name = "认定等级")
	private String certificationGrade = "";
	@FieldTag(name = "认定时间")
	private Timestamp certificationDate = null;
	@FieldTag(name = "认定部门")
	private String certificationDepartment = "";
	@FieldTag(name = "农民技术等级或职称证书")
	private String certificationTitle = "";
	@FieldTag(name = "参加其他培训次数")
	private int otherTrainingTime = 0;
	@FieldTag(name = "是否有国家职业资格证书")
	private int hasNationalCertification = 0;
	@FieldTag(name = "国家职业资格证书名称1")
	private int nationalCertificationId1 = 0;
	@FieldTag(name = "国家职业资格证书等级1")
	private int nationalCertificationGrade1 = 0;
	@FieldTag(name = "国家职业资格证书名称1")
	private int nationalCertificationId2 = 0;
	@FieldTag(name = "国家职业资格证书等级1")
	private int nationalCertificationGrade2 = 0;
	@FieldTag(name = "是否有新型职业农民培训证书")
	private int hasNewTypeTrainingCertification = 0;
	@FieldTag(name = "是否有新型职业农民证书")
	private int hasNewTypeCertification = 0;
	@FieldTag(name = "未获得证书")
	private int hasNoCertification = 0;
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

	public int getIsTrained() {
		return isTrained;
	}

	public void setIsTrained(int isTrained) {
		this.isTrained = isTrained;
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

	public int getNationalCertificationId1() {
		return nationalCertificationId1;
	}

	public void setNationalCertificationId1(int nationalCertificationId1) {
		this.nationalCertificationId1 = nationalCertificationId1;
	}

	public int getNationalCertificationGrade1() {
		return nationalCertificationGrade1;
	}

	public void setNationalCertificationGrade1(int nationalCertificationGrade1) {
		this.nationalCertificationGrade1 = nationalCertificationGrade1;
	}

	public int getNationalCertificationId2() {
		return nationalCertificationId2;
	}

	public void setNationalCertificationId2(int nationalCertificationId2) {
		this.nationalCertificationId2 = nationalCertificationId2;
	}

	public int getNationalCertificationGrade2() {
		return nationalCertificationGrade2;
	}

	public void setNationalCertificationGrade2(int nationalCertificationGrade2) {
		this.nationalCertificationGrade2 = nationalCertificationGrade2;
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