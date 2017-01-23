package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.FarmerInfoDao;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;

public class FarmerInfoBusiness {
	public FarmerInfoBean getFarmerInfoByKey(int declareId) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoByKey(declareId);
	}
}