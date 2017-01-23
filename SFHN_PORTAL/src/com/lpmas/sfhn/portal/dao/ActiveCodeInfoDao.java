package com.lpmas.sfhn.portal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class ActiveCodeInfoDao {
	private static Logger log = LoggerFactory.getLogger(ActiveCodeInfoDao.class);

	public int insertActiveCodeInfo(ActiveCodeInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into active_code_info ( active_code, province, city, region, training_year, usage_status, user_id, user_type, status, create_time, create_user) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getActiveCode());
			ps.setString(2, bean.getProvince());
			ps.setString(3, bean.getCity());
			ps.setString(4, bean.getRegion());
			ps.setString(5, bean.getTrainingYear());
			ps.setString(6, bean.getUsageStatus());
			ps.setInt(7, bean.getUserId());
			ps.setInt(8, bean.getUserType());
			ps.setInt(9, bean.getStatus());
			ps.setInt(10, bean.getCreateUser());

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

	public int updateActiveCodeInfo(ActiveCodeInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update active_code_info set active_code = ?, province = ?, city = ?, region = ?, training_year = ?, usage_status = ?, user_id = ?, user_type = ?, status = ?, modify_time = now(), modify_user = ? where active_code_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getActiveCode());
			ps.setString(2, bean.getProvince());
			ps.setString(3, bean.getCity());
			ps.setString(4, bean.getRegion());
			ps.setString(5, bean.getTrainingYear());
			ps.setString(6, bean.getUsageStatus());
			ps.setInt(7, bean.getUserId());
			ps.setInt(8, bean.getUserType());
			ps.setInt(9, bean.getStatus());
			ps.setInt(10, bean.getModifyUser());

			ps.setInt(11, bean.getActiveCodeId());

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

	public ActiveCodeInfoBean getActiveCodeInfoByKey(int activeCodeId) {
		ActiveCodeInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from active_code_info where active_code_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, activeCodeId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new ActiveCodeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, ActiveCodeInfoBean.class);
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

	public ActiveCodeInfoBean getActiveCodeInfoByCondition(String province, String city, String region,
			String trainingYear,String usageStatus) {
		ActiveCodeInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from active_code_info where province = ? and city = ? and region = ? and training_year = ? and usage_status = ? and status = ? limit 1";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, province);
			ps.setString(2, city);
			ps.setString(3, region);
			ps.setString(4, trainingYear);
			ps.setString(5, usageStatus);
			ps.setInt(6, Constants.STATUS_VALID);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new ActiveCodeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, ActiveCodeInfoBean.class);
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

	public ActiveCodeInfoBean getActiveCodeInfoByActiveCode(String activeCode) {
		ActiveCodeInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from active_code_info where active_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, activeCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new ActiveCodeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, ActiveCodeInfoBean.class);
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

	public PageResultBean<ActiveCodeInfoBean> getActiveCodeInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<ActiveCodeInfoBean> result = new PageResultBean<ActiveCodeInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from active_code_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String usageStatus = condMap.get("usageStatus");
			if (StringKit.isValid(usageStatus)) {
				condList.add("usage_status = ?");
				paramList.add(usageStatus);
			}
			String province = condMap.get("province");
			condList.add("province = ?");
			paramList.add(province);
			String city = condMap.get("city");
			condList.add("city = ?");
			paramList.add(city);
			String region = condMap.get("region");
			condList.add("region = ?");
			paramList.add(region);
			String trainingYear = condMap.get("trainingYear");
			if (StringKit.isValid(trainingYear)) {
				condList.add("training_year = ?");
				paramList.add(trainingYear);
			}

			String orderQuery = "order by usage_status desc,active_code_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, ActiveCodeInfoBean.class, pageBean,
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

	public List<ActiveCodeInfoBean> getActiveCodeInfoListByMap(Map<String, String> condMap) {
		List<ActiveCodeInfoBean> result = new ArrayList<ActiveCodeInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;

		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from active_code_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String userType = condMap.get("userType");
			if (StringKit.isValid(userType)) {
				condList.add("user_type = ?");
				paramList.add(userType);
			}
			String userId = condMap.get("userId");
			if (StringKit.isValid(userId)) {
				condList.add("user_Id = ?");
				paramList.add(userId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String usageStatus = condMap.get("usageStatus");
			if (StringKit.isValid(usageStatus)) {
				condList.add("usage_status = ?");
				paramList.add(usageStatus);
			}
			String province = condMap.get("province");
			if (StringKit.isValid(province)) {
				condList.add("province = ?");
				paramList.add(province);
			}
			String city = condMap.get("city");
			if (StringKit.isValid(city)) {
				condList.add("city = ?");
				paramList.add(city);
			}
			String region = condMap.get("region");
			if (StringKit.isValid(region)) {
				condList.add("region = ?");
				paramList.add(region);
			}
			String trainingYear = condMap.get("trainingYear");
			if (StringKit.isValid(trainingYear)) {
				condList.add("training_year = ?");
				paramList.add(trainingYear);
			}

			String orderQuery = "order by active_code_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, ActiveCodeInfoBean.class, db);

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
