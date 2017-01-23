package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.dao.ProcessLogDao;

public class ProcessLogBusiness {
	public int addProcessLog(ProcessLogBean bean) {
		ProcessLogDao dao = new ProcessLogDao();
		return dao.insertProcessLog(bean);
	}

	public int updateProcessLog(ProcessLogBean bean) {
		ProcessLogDao dao = new ProcessLogDao();
		return dao.updateProcessLog(bean);
	}

	public ProcessLogBean getProcessLogByKey(int logId) {
		ProcessLogDao dao = new ProcessLogDao();
		return dao.getProcessLogByKey(logId);
	}

	public PageResultBean<ProcessLogBean> getProcessLogPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		ProcessLogDao dao = new ProcessLogDao();
		return dao.getProcessLogPageListByMap(condMap, pageBean);
	}
	
	public List<ProcessLogBean> getProcessLogListByMap(HashMap<String, String> condMap) {
		ProcessLogDao dao = new ProcessLogDao();
		return dao.getProcessLogListByMap(condMap);
	} 
	
	public List<ProcessLogBean> getProcessLogListByTrainingClassInfo(TrainingClassInfoBean bean) {
		ProcessLogDao dao = new ProcessLogDao();
		HashMap<String, String> condMap = new HashMap<String,String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
		condMap.put("infoId", String.valueOf(bean.getClassId()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getProcessLogListByMap(condMap);
	} 
	
	public List<ProcessLogBean> getProcessLogListByTrainingClassAccept(TrainingClassAcceptBean bean) {
		ProcessLogDao dao = new ProcessLogDao();
		HashMap<String, String> condMap = new HashMap<String,String>();
		condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT));
		condMap.put("infoId", String.valueOf(bean.getClassId()));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getProcessLogListByMap(condMap);
	} 
	
	

}