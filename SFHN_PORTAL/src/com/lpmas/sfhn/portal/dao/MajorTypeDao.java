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
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class MajorTypeDao {
	private static Logger log = LoggerFactory.getLogger(MajorTypeDao.class);

	public MajorTypeBean getMajorTypeByKey(int majorId) {
		MajorTypeBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type where major_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, majorId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorTypeBean();
				bean = BeanKit.resultSet2Bean(rs, MajorTypeBean.class);
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

	public MajorTypeBean getMajorTypeByName(String majorTypeName) {
		MajorTypeBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type where major_name = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, majorTypeName);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorTypeBean();
				bean = BeanKit.resultSet2Bean(rs, MajorTypeBean.class);
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

	public List<MajorTypeBean> getMajorTypeListByMap(HashMap<String, String> condMap) {
		List<MajorTypeBean> result = new ArrayList<MajorTypeBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String memo = condMap.get("memo");
			if (StringKit.isValid(memo)) {
				condList.add("memo = ?");
				paramList.add(memo);
			}
			String orderQuery = "order by major_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, MajorTypeBean.class, db);
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
