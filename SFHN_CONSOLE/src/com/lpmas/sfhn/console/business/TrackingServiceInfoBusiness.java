package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrackingServiceInfoBean;
import com.lpmas.sfhn.console.dao.TrackingServiceInfoDao;

public class TrackingServiceInfoBusiness {
	public int addTrackingServiceInfo(TrackingServiceInfoBean bean) {
		TrackingServiceInfoDao dao = new TrackingServiceInfoDao();
		return dao.insertTrackingServiceInfo(bean);
	}

	public int updateTrackingServiceInfo(TrackingServiceInfoBean bean) {
		TrackingServiceInfoDao dao = new TrackingServiceInfoDao();
		return dao.updateTrackingServiceInfo(bean);
	}

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