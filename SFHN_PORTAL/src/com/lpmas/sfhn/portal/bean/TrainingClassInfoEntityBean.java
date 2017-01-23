package com.lpmas.sfhn.portal.bean;

import java.sql.Timestamp;

import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;

public class TrainingClassInfoEntityBean {

	private TrainingClassInfoBean trainingClassInfoBean;
	private TrainingOrganizationInfoBean trainingOrganizationInfoBean;

	public TrainingClassInfoEntityBean(TrainingClassInfoBean trainingClassInfoBean,
			TrainingOrganizationInfoBean trainingOrganizationInfoBean) {
		super();
		this.trainingClassInfoBean = trainingClassInfoBean;
		this.trainingOrganizationInfoBean = trainingOrganizationInfoBean;
	}

	public TrainingClassInfoBean getTrainingClassInfoBean() {
		return trainingClassInfoBean;
	}

	public void setTrainingClassInfoBean(TrainingClassInfoBean trainingClassInfoBean) {
		this.trainingClassInfoBean = trainingClassInfoBean;
	}

	public TrainingOrganizationInfoBean getTrainingOrganizationInfoBean() {
		return trainingOrganizationInfoBean;
	}

	public void setTrainingOrganizationInfoBean(TrainingOrganizationInfoBean trainingOrganizationInfoBean) {
		this.trainingOrganizationInfoBean = trainingOrganizationInfoBean;
	}

	public int getClassId() {
		return trainingClassInfoBean.getClassId();
	}

	public int getOrganizationId() {
		return trainingClassInfoBean.getOrganizationId();
	}

	public int getEduClassId() {
		return trainingClassInfoBean.getEduClassId();
	}

	public String getClassName() {
		return trainingClassInfoBean.getClassName();
	}

	public int getTrainingType() {
		return trainingClassInfoBean.getTrainingType();
	}

	public String getTrainingYear() {
		return trainingClassInfoBean.getTrainingYear();
	}
	
	public int getClassType(){
		return trainingClassInfoBean.getClassType();
	}

	public int getClassPeopleQuantity() {
		return trainingClassInfoBean.getClassPeopleQuantity();
	}
	
	public Timestamp getTrainingBeginTime(){
		return trainingClassInfoBean.getTrainingBeginTime();
	}
	
	public Timestamp getTrainingEndTime(){
		return trainingClassInfoBean.getTrainingEndTime();
	}

	public String getTrainingTime() {
		return trainingClassInfoBean.getTrainingTime();
	}

	public String getTrainingSchedule() {
		return trainingClassInfoBean.getTrainingSchedule();
	}

	public Timestamp getRegistrationEndTime() {
		return trainingClassInfoBean.getRegistrationEndTime();
	}

	public String getProvince() {
		return trainingClassInfoBean.getProvince();
	}

	public String getCity() {
		return trainingClassInfoBean.getCity();
	}

	public String getRegion() {
		return trainingClassInfoBean.getRegion();
	}

	public String getAcceptStatus() {
		return trainingClassInfoBean.getAcceptStatus();
	}

	public String getClassStatus() {
		return trainingClassInfoBean.getClassStatus();
	}

	public int getStatus() {
		return trainingClassInfoBean.getStatus();
	}

	public String getMemo() {
		return trainingClassInfoBean.getMemo();
	}

	public String getOrganizationName() {
		return trainingOrganizationInfoBean.getOrganizationName();
	}

	public String getOrganizationType() {
		return trainingOrganizationInfoBean.getOrganizationType();
	}

	public String getRepresentativeName() {
		return trainingOrganizationInfoBean.getRepresentativeName();
	}

	public String getMobile() {
		return trainingOrganizationInfoBean.getMobile();
	}

	public String getAddress() {
		return trainingOrganizationInfoBean.getAddress();
	}

	public String getZipCode() {
		return trainingOrganizationInfoBean.getZipCode();
	}

}
