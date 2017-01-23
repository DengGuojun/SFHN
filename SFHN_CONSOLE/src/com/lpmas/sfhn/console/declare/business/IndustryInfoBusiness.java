package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.console.declare.dao.IndustryInfoDao;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;

public class IndustryInfoBusiness {
	public int addIndustryInfo(IndustryInfoBean bean) {
		IndustryInfoDao dao = new IndustryInfoDao();
		return dao.insertIndustryInfo(bean);
	}

	public int updateIndustryInfo(IndustryInfoBean bean) {
		IndustryInfoDao dao = new IndustryInfoDao();
		return dao.updateIndustryInfo(bean);
	}

	public IndustryInfoBean getIndustryInfoByKey(int industryId) {
		IndustryInfoDao dao = new IndustryInfoDao();
		return dao.getIndustryInfoByKey(industryId);
	}

	public PageResultBean<IndustryInfoBean> getIndustryInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		IndustryInfoDao dao = new IndustryInfoDao();
		return dao.getIndustryInfoPageListByMap(condMap, pageBean);
	}

	public List<IndustryInfoBean> getIndustryInfoAllList() {
		IndustryInfoDao dao = new IndustryInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getIndustryInfoListByMap(condMap);
	}

	public List<IndustryInfoBean> getIndustryInfoListByType(int typeId) {
		IndustryInfoDao dao = new IndustryInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeId", String.valueOf(typeId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getIndustryInfoListByMap(condMap);
	}

}
