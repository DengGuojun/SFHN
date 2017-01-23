package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.console.declare.dao.NationalCertificationDao;
import com.lpmas.sfhn.declare.bean.NationalCertificationBean;

public class NationalCertificationBusiness {
	public int addNationalCertification(NationalCertificationBean bean) {
		NationalCertificationDao dao = new NationalCertificationDao();
		return dao.insertNationalCertification(bean);
	}

	public int updateNationalCertification(NationalCertificationBean bean) {
		NationalCertificationDao dao = new NationalCertificationDao();
		return dao.updateNationalCertification(bean);
	}

	public NationalCertificationBean getNationalCertificationByKey(int certificateId) {
		NationalCertificationDao dao = new NationalCertificationDao();
		return dao.getNationalCertificationByKey(certificateId);
	}

	public PageResultBean<NationalCertificationBean> getNationalCertificationPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		NationalCertificationDao dao = new NationalCertificationDao();
		return dao.getNationalCertificationPageListByMap(condMap, pageBean);
	}
	
	
	public List<NationalCertificationBean> getNationalCertificationAllList(){
		NationalCertificationDao dao = new NationalCertificationDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getNationalCertificationListByMap(condMap);
	}

}