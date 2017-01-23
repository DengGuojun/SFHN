package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.portal.bean.TrainingClassUserHistoryBean;
import com.lpmas.sfhn.portal.dao.TrainingClassUserHistoryDao;

public class TrainingClassUserHistoryBusiness {
	public int addTrainingClassUserHistory(TrainingClassUserHistoryBean bean) {
		TrainingClassUserHistoryDao dao = new TrainingClassUserHistoryDao();
		return dao.insertTrainingClassUserHistory(bean);
	}

	public int updateTrainingClassUserHistory(TrainingClassUserHistoryBean bean) {
		TrainingClassUserHistoryDao dao = new TrainingClassUserHistoryDao();
		return dao.updateTrainingClassUserHistory(bean);
	}

	public TrainingClassUserHistoryBean getTrainingClassUserHistoryByKey(int historyId) {
		TrainingClassUserHistoryDao dao = new TrainingClassUserHistoryDao();
		return dao.getTrainingClassUserHistoryByKey(historyId);
	}

	public PageResultBean<TrainingClassUserHistoryBean> getTrainingClassUserHistoryPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		TrainingClassUserHistoryDao dao = new TrainingClassUserHistoryDao();
		return dao.getTrainingClassUserHistoryPageListByMap(condMap, pageBean);
	}

	public String getHistoryYearByCondition(String identityNumber, String historyYear) {
		TrainingClassUserHistoryDao dao = new TrainingClassUserHistoryDao();
		TrainingClassUserHistoryBean bean = dao.getTrainingClassUserHistoryByCondtion(identityNumber, historyYear);
		if (bean == null) {
			return "";
		} else {
			return bean.getTrainingYear();
		}
	}

}