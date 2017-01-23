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
import com.lpmas.sfhn.bean.TrackingServiceInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrackingServiceInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrackingServiceInfoDao.class);

	

	public TrackingServiceInfoBean getTrackingServiceInfoByKey(int serviceId) {
		TrackingServiceInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from tracking_service_info where service_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrackingServiceInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrackingServiceInfoBean.class);
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

	public PageResultBean<TrackingServiceInfoBean> getTrackingServiceInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrackingServiceInfoBean> result = new PageResultBean<TrackingServiceInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from tracking_service_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String trackingId = condMap.get("trackingId");
			if (StringKit.isValid(trackingId)) {
				condList.add("tracking_id = ?");
				paramList.add(trackingId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by service_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrackingServiceInfoBean.class,
					pageBean, db);
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
