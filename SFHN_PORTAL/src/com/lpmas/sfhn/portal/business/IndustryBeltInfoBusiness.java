package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
import com.lpmas.sfhn.portal.dao.IndustryBeltInfoDao;

public class IndustryBeltInfoBusiness {

	public List<IndustryBeltInfoBean> getIndustryBeltInfoListByMap(HashMap<String, String> condMap) {
		IndustryBeltInfoDao dao = new IndustryBeltInfoDao();
		return dao.getIndustryBeltInfoListByMap(condMap);
	}
}
