package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
import com.lpmas.sfhn.console.dao.IndustryBeltInfoDao;

public class IndustryBeltInfoBusiness {
	public int addIndustryBeltInfo(IndustryBeltInfoBean bean) {
		IndustryBeltInfoDao dao = new IndustryBeltInfoDao();
		return dao.insertIndustryBeltInfo(bean);
	}

	public int updateIndustryBeltInfo(IndustryBeltInfoBean bean) {
		IndustryBeltInfoDao dao = new IndustryBeltInfoDao();
		return dao.updateIndustryBeltInfo(bean);
	}

	public IndustryBeltInfoBean getIndustryBeltInfoByKey(int beltId) {
		IndustryBeltInfoDao dao = new IndustryBeltInfoDao();
		return dao.getIndustryBeltInfoByKey(beltId);
	}

	public PageResultBean<IndustryBeltInfoBean> getIndustryBeltInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		IndustryBeltInfoDao dao = new IndustryBeltInfoDao();
		return dao.getIndustryBeltInfoPageListByMap(condMap, pageBean);
	}

}