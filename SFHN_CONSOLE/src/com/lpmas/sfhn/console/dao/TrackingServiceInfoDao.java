package com.lpmas.sfhn.console.dao;

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
import com.lpmas.sfhn.console.factory.SfhnDBFactory;

public class TrackingServiceInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrackingServiceInfoDao.class);

	public int insertTrackingServiceInfo(TrackingServiceInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into tracking_service_info ( service_type, tracking_id, tracking_title, tracking_content, tracking_feedback, status, create_time, create_user) value( ?, ?, ?, ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getServiceType());
			ps.setInt(2, bean.getTrackingId());
			ps.setString(3, bean.getTrackingTitle());
			ps.setString(4, bean.getTrackingContent());
			ps.setString(5, bean.getTrackingFeedback());
			ps.setInt(6, bean.getStatus());
			ps.setInt(7, bean.getCreateUser());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateTrackingServiceInfo(TrackingServiceInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update tracking_service_info set service_type = ?, tracking_id = ?, tracking_title = ?, tracking_content = ?, tracking_feedback = ?, status = ?, modify_time = now(), modify_user = ? where service_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getServiceType());
			ps.setInt(2, bean.getTrackingId());
			ps.setString(3, bean.getTrackingTitle());
			ps.setString(4, bean.getTrackingContent());
			ps.setString(5, bean.getTrackingFeedback());
			ps.setInt(6, bean.getStatus());
			ps.setInt(7, bean.getModifyUser());

			ps.setInt(8, bean.getServiceId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

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
