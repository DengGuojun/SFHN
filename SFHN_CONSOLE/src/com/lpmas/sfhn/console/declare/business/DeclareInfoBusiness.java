package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.console.declare.dao.DeclareInfoDao;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;

public class DeclareInfoBusiness {

	public List<DeclareInfoBean> getDeclareInfoAllList() {
		DeclareInfoDao dao = new DeclareInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getDeclareInfoListByMap(condMap);
	}

	public DeclareInfoBean getDeclareInfoByCondition(int userId, int declareType) {
		DeclareInfoDao dao = new DeclareInfoDao();
		return dao.getDeclareInfoByCondition(userId, declareType);
	}

	public DeclareInfoBean getDeclareInfoByKey(int declareId) {
		DeclareInfoDao dao = new DeclareInfoDao();
		return dao.getDeclareInfoByKey(declareId);
	}

}