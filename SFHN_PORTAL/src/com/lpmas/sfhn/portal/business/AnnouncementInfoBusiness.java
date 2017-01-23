package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.dao.AnnouncementInfoDao;

public class AnnouncementInfoBusiness {
	public int addAnnouncementInfo(AnnouncementInfoBean bean) {
		AnnouncementInfoDao dao = new AnnouncementInfoDao();
		return dao.insertAnnouncementInfo(bean);
	}

	public int updateAnnouncementInfo(AnnouncementInfoBean bean) {
		AnnouncementInfoDao dao = new AnnouncementInfoDao();
		return dao.updateAnnouncementInfo(bean);
	}

	public AnnouncementInfoBean getAnnouncementInfoByKey(int announcementId) {
		AnnouncementInfoDao dao = new AnnouncementInfoDao();
		return dao.getAnnouncementInfoByKey(announcementId);
	}

	public PageResultBean<AnnouncementInfoBean> getAnnouncementInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AnnouncementInfoDao dao = new AnnouncementInfoDao();
		return dao.getAnnouncementInfoPageListByMap(condMap, pageBean);
	}

}