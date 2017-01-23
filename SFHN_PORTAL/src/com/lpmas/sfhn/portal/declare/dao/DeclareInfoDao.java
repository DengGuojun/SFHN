package com.lpmas.sfhn.portal.declare.dao;

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
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class DeclareInfoDao {
	private static Logger log = LoggerFactory.getLogger(DeclareInfoDao.class);

	public int insertDeclareInfo(DeclareInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into declare_info ( user_id, declare_type, declare_year, declare_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setInt(2, bean.getDeclareType());
			ps.setString(3, bean.getDeclareYear());
			ps.setString(4, bean.getDeclareStatus());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getCreateUser());
			ps.setString(7, bean.getMemo());

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

	public int updateDeclareInfo(DeclareInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update declare_info set user_id = ?, declare_type = ?, declare_year = ?, declare_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setInt(2, bean.getDeclareType());
			ps.setString(3, bean.getDeclareYear());
			ps.setString(4, bean.getDeclareStatus());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());

			ps.setInt(8, bean.getDeclareId());

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

	public int updateDeclareInfo(DBObject db, DeclareInfoBean bean) {
		int result = -1;
		try {
			String sql = "update declare_info set user_id = ?, declare_type = ?, declare_year = ?, declare_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setInt(2, bean.getDeclareType());
			ps.setString(3, bean.getDeclareYear());
			ps.setString(4, bean.getDeclareStatus());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());

			ps.setInt(8, bean.getDeclareId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
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

	public PageResultBean<DeclareInfoBean> getDeclareInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<DeclareInfoBean> result = new PageResultBean<DeclareInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, DeclareInfoBean.class, pageBean,
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
			String sql = "select * from declare_info where user_id=? and declare_type=?";

			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, declareType);

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

	public DeclareInfoBean getDeclareInfoByCondition(int userId, String declareYear) {
		DeclareInfoBean result = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_info where user_id=? and declare_year=?";

			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, declareYear);

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

}
