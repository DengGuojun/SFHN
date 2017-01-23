package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean;
import com.lpmas.sfhn.portal.declare.dao.FarmerSkillInfoDao;

public class FarmerSkillInfoBusiness {
	public int addFarmerSkillInfo(FarmerSkillInfoBean bean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.insertFarmerSkillInfo(bean);
	}

	public int updateFarmerSkillInfo(FarmerSkillInfoBean bean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.updateFarmerSkillInfo(bean);
	}

	public FarmerSkillInfoBean getFarmerSkillInfoByKey(int declareId) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.getFarmerSkillInfoByKey(declareId);
	}

	public PageResultBean<FarmerSkillInfoBean> getFarmerSkillInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerSkillInfoDao dao = new FarmerSkillInfoDao();
		return dao.getFarmerSkillInfoPageListByMap(condMap, pageBean);
	}
	
	public ReturnMessageBean verifyFarmerSkillInfo(FarmerSkillInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		return result;
	}

}