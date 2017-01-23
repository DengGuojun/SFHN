package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.portal.dao.MajorTypeDao;

public class MajorTypeBusiness {

	public MajorTypeBean getMajorTypeByKey(int typeId) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByKey(typeId);
	}

	public MajorTypeBean getMajorTypeByName(String majorTypeName) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByName(majorTypeName);
	}

	public List<MajorTypeBean> getMajorTypeAllList() {
		MajorTypeDao dao = new MajorTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorTypeListByMap(condMap);
	}

}