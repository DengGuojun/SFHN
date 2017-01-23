package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.dao.TrainingClassAcceptDao;

public class TrainingClassAcceptBusiness {
	public int addTrainingClassAccept(TrainingClassAcceptBean bean) {
		TrainingClassAcceptDao dao = new TrainingClassAcceptDao();
		return dao.insertTrainingClassAccept(bean);
	}

	public int updateTrainingClassAccept(TrainingClassAcceptBean bean) {
		TrainingClassAcceptDao dao = new TrainingClassAcceptDao();
		return dao.updateTrainingClassAccept(bean);
	}

	public TrainingClassAcceptBean getTrainingClassAcceptByKey(int classId) {
		TrainingClassAcceptDao dao = new TrainingClassAcceptDao();
		return dao.getTrainingClassAcceptByKey(classId);
	}

	public PageResultBean<TrainingClassAcceptBean> getTrainingClassAcceptPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrainingClassAcceptDao dao = new TrainingClassAcceptDao();
		return dao.getTrainingClassAcceptPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyTrainingClassAccept(TrainingClassAcceptBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getClassId()<=0) {
			result.setMessage("培训班必须填写");
		} else if (!StringKit.isValid(bean.getTrainingAddress())) {
			result.setMessage("办班地点必须填写");
		} else if (!StringKit.isValid(bean.getAcceptContent())) {
			result.setMessage("验收重点内容必须填写");
		}
		return result;
	}

}