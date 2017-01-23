package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.ExpertInfoBean;
import com.lpmas.sfhn.portal.dao.ExpertInfoDao;

public class ExpertInfoBusiness {

	public ExpertInfoBean getExpertInfoByKey(int expertId) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.getExpertInfoByKey(expertId);
	}

	public PageResultBean<ExpertInfoBean> getExpertInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		ExpertInfoDao dao = new ExpertInfoDao();
		return dao.getExpertInfoPageListByMap(condMap, pageBean);
	}

}