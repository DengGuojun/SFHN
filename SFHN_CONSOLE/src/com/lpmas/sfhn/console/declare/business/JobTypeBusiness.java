package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.console.declare.dao.JobTypeDao;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;

public class JobTypeBusiness {
	public int addJobType(JobTypeBean bean) {
		JobTypeDao dao = new JobTypeDao();
		return dao.insertJobType(bean);
	}

	public int updateJobType(JobTypeBean bean) {
		JobTypeDao dao = new JobTypeDao();
		return dao.updateJobType(bean);
	}

	public JobTypeBean getJobTypeByKey(int typeId) {
		JobTypeDao dao = new JobTypeDao();
		return dao.getJobTypeByKey(typeId);
	}

	public PageResultBean<JobTypeBean> getJobTypePageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		JobTypeDao dao = new JobTypeDao();
		return dao.getJobTypePageListByMap(condMap, pageBean);
	}

	public List<JobTypeBean> getJobTypeAllList() {
		JobTypeDao dao = new JobTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getJobTypeListByMap(condMap);
	}

	public ReturnMessageBean verifyJobType(JobTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getTypeId() > 0 && bean.getStatus() == Constants.STATUS_NOT_VALID) {
			JobInfoBusiness jobInfoBusiness = new JobInfoBusiness();
			List<JobInfoBean> jobInfoList = jobInfoBusiness.getJobInfoListByType(bean.getTypeId());
			if (!jobInfoList.isEmpty()) {
				result.setMessage("该类型下包含工作岗位信息，不能设置为无效");
			}
		}
		return result;
	}

}