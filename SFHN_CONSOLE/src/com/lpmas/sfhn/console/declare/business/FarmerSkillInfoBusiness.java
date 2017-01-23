package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.FarmerSkillInfoDao;
import com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean;

public class FarmerSkillInfoBusiness {
	public FarmerSkillInfoBean getFarmerSkillInfoByKey(int declareId) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.getFarmerSkillInfoByKey(declareId);
	}
}