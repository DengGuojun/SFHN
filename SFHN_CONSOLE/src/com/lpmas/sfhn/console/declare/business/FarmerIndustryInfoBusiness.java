package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.FarmerIndustryInfoDao;
import com.lpmas.sfhn.declare.bean.FarmerIndustryInfoBean;

public class FarmerIndustryInfoBusiness {
	public FarmerIndustryInfoBean getFarmerIndustryInfoByKey(int declareId) {
		FarmerIndustryInfoDao dao = new FarmerIndustryInfoDao();
		return dao.getFarmerIndustryInfoByKey(declareId);
	}
}