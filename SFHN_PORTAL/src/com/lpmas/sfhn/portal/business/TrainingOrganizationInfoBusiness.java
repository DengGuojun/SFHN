package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.portal.dao.TrainingOrganizationInfoDao;

public class TrainingOrganizationInfoBusiness {

	public TrainingOrganizationInfoBean getTrainingOrganizationInfoByKey(int organizationId) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoByKey(organizationId);
	}
	
	public List<TrainingOrganizationInfoBean> getTrainingOrganizationInfoListByMap(
			HashMap<String, String> condMap) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoListByMap(condMap);
	}

}