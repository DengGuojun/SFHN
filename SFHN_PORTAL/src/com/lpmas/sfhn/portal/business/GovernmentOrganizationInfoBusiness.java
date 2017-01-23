package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.portal.dao.GovernmentOrganizationInfoDao;

public class GovernmentOrganizationInfoBusiness {

	public GovernmentOrganizationInfoBean getGovernmentOrganizationInfoByKey(int organizationId) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoByKey(organizationId);
	}

	public GovernmentOrganizationInfoBean getGovernmentOrganizationInfoByRegion(String province, String city,
			String region, int organizationLevel) {
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("province", province);
		condMap.put("city", city);
		condMap.put("region", region);
		condMap.put("organizationLevel", String.valueOf(organizationLevel));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoByMap(condMap);
	}
	
	public List<GovernmentOrganizationInfoBean>  getGovernmentOrganizationInfoListByRegion(String province, String city,
			String region, int organizationLevel) {
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("province", province);
		condMap.put("city", city);
		condMap.put("region", region);
		condMap.put("organizationLevel", String.valueOf(organizationLevel));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoListByMap(condMap);
	}
	

}