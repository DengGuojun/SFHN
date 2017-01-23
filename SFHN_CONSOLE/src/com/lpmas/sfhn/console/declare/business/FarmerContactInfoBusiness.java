package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.FarmerContactInfoDao;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;

public class FarmerContactInfoBusiness {

	public FarmerContactInfoBean getFarmerContactInfoByKey(int declareId) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoByKey(declareId);
	}
}