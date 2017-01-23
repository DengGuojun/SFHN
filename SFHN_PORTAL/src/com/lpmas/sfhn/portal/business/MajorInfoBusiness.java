package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.portal.dao.MajorInfoDao;

public class MajorInfoBusiness {

	public MajorInfoBean getMajorInfoByKey(int majorId) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByKey(majorId);
	}
	
	public MajorInfoBean getMajorInfoByCondition(String majorName,int majorType) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByCondition(majorName,majorType);
	}

	public List<MajorInfoBean> getMajorInfoListByTypeId(int typeId) {
		MajorInfoDao dao = new MajorInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeId", String.valueOf(typeId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorInfoListByMap(condMap);
	}

}