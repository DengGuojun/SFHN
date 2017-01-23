package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrackingServiceInfoBean;
import com.lpmas.sfhn.portal.dao.TrackingServiceInfoDao;

public class TrackingServiceInfoBusiness {

	public TrackingServiceInfoBean getTrackingServiceInfoByKey(int serviceId) {
		TrackingServiceInfoDao dao = new TrackingServiceInfoDao();
		return dao.getTrackingServiceInfoByKey(serviceId);
	}

	public PageResultBean<TrackingServiceInfoBean> getTrackingServiceInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrackingServiceInfoDao dao = new TrackingServiceInfoDao();
		return dao.getTrackingServiceInfoPageListByMap(condMap, pageBean);
	}

}