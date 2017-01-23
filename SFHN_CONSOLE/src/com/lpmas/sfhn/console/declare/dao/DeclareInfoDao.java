package com.lpmas.sfhn.console.declare.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;

public class DeclareInfoDao {
	private static Logger log = LoggerFactory.getLogger(DeclareInfoDao.class);

	public List<DeclareInfoBean> getDeclareInfoListByMap(HashMap<String, String> condMap) {
		List<DeclareInfoBean> result = new ArrayList<DeclareInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String userId = condMap.get("userId");
			if (StringKit.isValid(userId)) {
				condList.add("user_id = ?");
				paramList.add(userId);
			}
			String declareType = condMap.get("declareType");
			if (StringKit.isValid(declareType)) {
				condList.add("declare_type = ?");
				paramList.add(declareType);
			}
			String declareStatus = condMap.get("declareStatus");
			if (StringKit.isValid(declareStatus)) {
				condList.add("declare_status = ?");
				paramList.add(declareStatus);
			}

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, DeclareInfoBean.class, db);
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

	public DeclareInfoBean getDeclareInfoByCondition(int userId, int declareType) {
		DeclareInfoBean result = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_info where user_id=? and declare_type=? and status=?";

			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, declareType);
			ps.setInt(3, Constants.STATUS_VALID);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				result = new DeclareInfoBean();
				result = BeanKit.resultSet2Bean(rs, DeclareInfoBean.class);
			}
			rs.close();

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

	public DeclareInfoBean getDeclareInfoByKey(int declareId) {
		DeclareInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_info where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new DeclareInfoBean();
				bean = BeanKit.resultSet2Bean(rs, DeclareInfoBean.class);
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

}
