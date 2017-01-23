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
import com.lpmas.sfhn.portal.bean.TrainingClassUserHistoryBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingClassUserHistoryDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassUserHistoryDao.class);

	public int insertTrainingClassUserHistory(TrainingClassUserHistoryBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_user_history ( identity_number, user_name, training_type, training_year) value( ?, ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getIdentityNumber());
			ps.setString(2, bean.getUserName());
			ps.setInt(3, bean.getTrainingType());
			ps.setString(4, bean.getTrainingYear());

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

	public int updateTrainingClassUserHistory(TrainingClassUserHistoryBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_user_history set identity_number = ?, user_name = ?, training_type = ?, training_year = ? where history_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getIdentityNumber());
			ps.setString(2, bean.getUserName());
			ps.setInt(3, bean.getTrainingType());
			ps.setString(4, bean.getTrainingYear());

			ps.setInt(5, bean.getHistoryId());

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

	public TrainingClassUserHistoryBean getTrainingClassUserHistoryByKey(int historyId) {
		TrainingClassUserHistoryBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user_history where history_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, historyId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassUserHistoryBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassUserHistoryBean.class);
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

	public PageResultBean<TrainingClassUserHistoryBean> getTrainingClassUserHistoryPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<TrainingClassUserHistoryBean> result = new PageResultBean<TrainingClassUserHistoryBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user_history";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by history_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassUserHistoryBean.class,
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

	public TrainingClassUserHistoryBean getTrainingClassUserHistoryByCondtion(String identityNumber,
			String historyYear) {
		TrainingClassUserHistoryBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user_history where identity_number = ? and training_year > ? order by training_year desc";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, identityNumber);
			ps.setString(2, historyYear);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassUserHistoryBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassUserHistoryBean.class);
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
