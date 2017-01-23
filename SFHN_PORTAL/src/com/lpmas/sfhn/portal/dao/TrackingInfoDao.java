package com.lpmas.sfhn.portal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.TrackingInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrackingInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrackingInfoDao.class);

	public TrackingInfoBean getTrackingInfoByKey(int trackingId) {
		TrackingInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from tracking_info where tracking_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, trackingId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrackingInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrackingInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public PageResultBean<TrackingInfoBean> getTrackingInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrackingInfoBean> result = new PageResultBean<TrackingInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from tracking_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String expertId = condMap.get("expertId");
			if (StringKit.isValid(expertId)) {
				condList.add("expert_id = ?");
				paramList.add(expertId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
		
			String orderQuery = "order by tracking_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrackingInfoBean.class, pageBean,
					db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

}
