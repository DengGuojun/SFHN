package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.declare.dao.IndustryTypeDao;

public class IndustryTypeBusiness {

	public List<IndustryTypeBean> getIndustryTypeAllList() {
		IndustryTypeDao dao = new IndustryTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getIndustryTypeListByMap(condMap);
	}

	public IndustryTypeBean getIndustryTypeByName(String TypeName) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.getIndustryTypeByName(TypeName);
	}

	public IndustryTypeBean getIndustryTypeByKey(int typeId) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.getIndustryTypeByKey(typeId);
	}

}