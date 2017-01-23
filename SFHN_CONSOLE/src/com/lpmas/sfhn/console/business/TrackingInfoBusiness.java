package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.console.dao.TrackingInfoDao;

public class TrackingInfoBusiness {
	public int addTrackingInfo(TrackingInfoBean bean) {
		TrackingInfoDao dao = new TrackingInfoDao();
		return dao.insertTrackingInfo(bean);
	}

	public int updateTrackingInfo(TrackingInfoBean bean) {
		TrackingInfoDao dao = new TrackingInfoDao();
		return dao.updateTrackingInfo(bean);
	}

	public TrackingInfoBean getTrackingInfoByKey(int trackingId) {
		TrackingInfoDao dao = new TrackingInfoDao();
		return dao.getTrackingInfoByKey(trackingId);
	}

	public PageResultBean<TrackingInfoBean> getTrackingInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrackingInfoDao dao = new TrackingInfoDao();
		return dao.getTrackingInfoPageListByMap(condMap, pageBean);
	}

}