package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.ExpertInfoBean;
import com.lpmas.sfhn.console.dao.ExpertInfoDao;

public class ExpertInfoBusiness {
	public int addExpertInfo(ExpertInfoBean bean) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.insertExpertInfo(bean);
	}

	public int updateExpertInfo(ExpertInfoBean bean) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.updateExpertInfo(bean);
	}

	public ExpertInfoBean getExpertInfoByKey(int expertId) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.getExpertInfoByKey(expertId);
	}

	public PageResultBean<ExpertInfoBean> getExpertInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.getExpertInfoPageListByMap(condMap, pageBean);
	}

}