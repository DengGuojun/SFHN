package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.declare.bean.NationalCertificationBean;
import com.lpmas.sfhn.portal.declare.dao.NationalCertificationDao;

public class NationalCertificationBusiness {

	public List<NationalCertificationBean> getNationalCertificationAllList(){
		NationalCertificationDao dao = new NationalCertificationDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getNationalCertificationListByMap(condMap);
	}

}