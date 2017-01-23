package com.lpmas.sfhn.console.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.console.dao.TrainingOrganizationInfoDao;

public class TrainingOrganizationInfoBusiness {
	public int addTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.insertTrainingOrganizationInfo(bean);
	}

	public int updateTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.updateTrainingOrganizationInfo(bean);
	}

	public TrainingOrganizationInfoBean getTrainingOrganizationInfoByKey(int organizationId) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoByKey(organizationId);
	}

	public PageResultBean<TrainingOrganizationInfoBean> getTrainingOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoPageListByMap(condMap, pageBean);
	}
	
	public List<TrainingOrganizationInfoBean> getTrainingOrganizationInfoALlList() {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getTrainingOrganizationInfoListByMap(condMap);
	}

}