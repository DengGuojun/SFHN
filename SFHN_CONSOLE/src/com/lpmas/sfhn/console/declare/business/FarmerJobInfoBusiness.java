package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.FarmerJobInfoDao;
import com.lpmas.sfhn.declare.bean.FarmerJobInfoBean;

public class FarmerJobInfoBusiness {
	public FarmerJobInfoBean getFarmerJobInfoByKey(int declareId) {
		FarmerJobInfoDao dao = new FarmerJobInfoDao();
		return dao.getFarmerJobInfoByKey(declareId);
	}

}