package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.console.dao.TrainingClassTargetDao;

public class TrainingClassTargetBusiness {
	public int addTrainingClassTarget(TrainingClassTargetBean bean) {
		TrainingClassTargetDao dao = new TrainingClassTargetDao();
		return dao.insertTrainingClassTarget(bean);
	}

	public int updateTrainingClassTarget(TrainingClassTargetBean bean) {
		TrainingClassTargetDao dao = new TrainingClassTargetDao();
		return dao.updateTrainingClassTarget(bean);
	}

	public TrainingClassTargetBean getTrainingClassTargetByKey(int targetId) {
		TrainingClassTargetDao dao = new TrainingClassTargetDao();
		return dao.getTrainingClassTargetByKey(targetId);
	}

	public PageResultBean<TrainingClassTargetBean> getTrainingClassTargetPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrainingClassTargetDao dao = new TrainingClassTargetDao();
		return dao.getTrainingClassTargetPageListByMap(condMap, pageBean);
	}
	

}