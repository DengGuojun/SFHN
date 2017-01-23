package com.lpmas.sfhn.portal.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class TeacherInfoBean {
	@FieldTag(name = "师资ID")
	private int teacherId = 0;
	@FieldTag(name = "用户ID")
	private int userId = 0;
	@FieldTag(name = "师资名称")
	private String teacherName = "";
	@FieldTag(name = "身份证号")
	private String identityNumber = "";
	@FieldTag(name = "师资类型")
	private String tearcherType = "";
	@FieldTag(name = "师资性别")
	private int teacherGender = 0;
	@FieldTag(name = "师资年龄")
	private int teacherAge = 0;
	@FieldTag(name = "专业类型ID")
	private int majorTypeId = 0;
	@FieldTag(name = "专业ID")
	private int majorId = 0;
	@FieldTag(name = "省")
	private String province = "";
	@FieldTag(name = "市")
	private String city = "";
	@FieldTag(name = "区")
	private String region = "";
	@FieldTag(name = "主讲课程")
	private String mainCourse = "";
	@FieldTag(name = "工作单位")
	private String corporateName = "";
	@FieldTag(name = "手机")
	private String teacherMobile = "";
	@FieldTag(name = "家庭住址")
	private String address = "";
	@FieldTag(name="同步状态")
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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getTearcherType() {
		return tearcherType;
	}

	public void setTearcherType(String tearcherType) {
		this.tearcherType = tearcherType;
	}

	public int getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(int teacherGender) {
		this.teacherGender = teacherGender;
	}

	public int getTeacherAge() {
		return teacherAge;
	}

	public void setTeacherAge(int teacherAge) {
		this.teacherAge = teacherAge;
	}

	public int getMajorTypeId() {
		return majorTypeId;
	}

	public void setMajorTypeId(int majorTypeId) {
		this.majorTypeId = majorTypeId;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
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

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getTeacherMobile() {
		return teacherMobile;
	}

	public void setTeacherMobile(String teacherMobile) {
		this.teacherMobile = teacherMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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