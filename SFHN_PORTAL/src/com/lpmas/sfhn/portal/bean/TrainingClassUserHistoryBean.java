package com.lpmas.sfhn.portal.bean;

import com.lpmas.framework.annotation.FieldTag;

public class TrainingClassUserHistoryBean {
	@FieldTag(name = "历史信息ID")
	private int historyId = 0;
	@FieldTag(name = "身份证号")
	private String identityNumber = "";
	@FieldTag(name = "学员名称")
	private String userName = "";
	@FieldTag(name = "培训类型")
	private int trainingType = 0;
	@FieldTag(name = "培育年份")
	private String trainingYear = "";

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(int trainingType) {
		this.trainingType = trainingType;
	}

	public String getTrainingYear() {
		return trainingYear;
	}

	public void setTrainingYear(String trainingYear) {
		this.trainingYear = trainingYear;
	}
}