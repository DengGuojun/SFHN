package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.portal.dao.TrackingInfoDao;

public class TrackingInfoBusiness {

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