package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.console.declare.dao.JobInfoDao;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;

public class JobInfoBusiness {
	public int addJobInfo(JobInfoBean bean) {
		JobInfoDao dao = new JobInfoDao();
		return dao.insertJobInfo(bean);
	}

	public int updateJobInfo(JobInfoBean bean) {
		JobInfoDao dao = new JobInfoDao();
		return dao.updateJobInfo(bean);
	}

	public JobInfoBean getJobInfoByKey(int jobId) {
		JobInfoDao dao = new JobInfoDao();
		return dao.getJobInfoByKey(jobId);
	}

	public PageResultBean<JobInfoBean> getJobInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		JobInfoDao dao = new JobInfoDao();
		return dao.getJobInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyJobInfo(JobInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
		JobTypeBean typeBean = jobTypeBusiness.getJobTypeByKey(bean.getTypeId());
		if (typeBean == null || typeBean.getStatus() == Constants.STATUS_NOT_VALID) {
			result.setMessage("工作类型无效");
		}
		return result;
	}

	public List<JobInfoBean> getJobInfoListByType(int typeId) {
		JobInfoDao dao = new JobInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeId", String.valueOf(typeId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getJobInfoListByMap(condMap);
	}

}
