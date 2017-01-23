package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.declare.bean.JobTypeBean;
import com.lpmas.sfhn.portal.declare.dao.JobTypeDao;

public class JobTypeBusiness {
	public List<JobTypeBean> getJobTypeAllList() {
		JobTypeDao dao = new JobTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getJobTypeListByMap(condMap);
	}

}