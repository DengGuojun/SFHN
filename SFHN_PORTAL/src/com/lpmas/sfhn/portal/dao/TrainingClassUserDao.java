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

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingClassUserDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassUserDao.class);

	public int insertTrainingClassUser(TrainingClassUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_user ( class_id, declare_id, sign_up_time, exam_result, auth_result, user_status, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getClassId());
			ps.setInt(2, bean.getDeclareId());
			ps.setTimestamp(3, bean.getSignUpTime());
			ps.setInt(4, bean.getExamResult());
			ps.setInt(5, bean.getAuthResult());
			ps.setString(6, bean.getUserStatus());
			ps.setInt(7, bean.getSyncStatus());
			ps.setInt(8, bean.getStatus());
			ps.setInt(9, bean.getCreateUser());
			ps.setString(10, bean.getMemo());

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

	public int updateTrainingClassUser(TrainingClassUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_user set sign_up_time = ?, exam_result = ?, auth_result = ?, user_status = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where class_id = ? and declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setTimestamp(1, bean.getSignUpTime());
			ps.setInt(2, bean.getExamResult());
			ps.setInt(3, bean.getAuthResult());
			ps.setString(4, bean.getUserStatus());
			ps.setInt(5, bean.getSyncStatus());
			ps.setInt(6, bean.getStatus());
			ps.setInt(7, bean.getModifyUser());
			ps.setString(8, bean.getMemo());

			ps.setInt(9, bean.getClassId());
			ps.setInt(10, bean.getDeclareId());

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

	public int updateTrainingClassUserByDeclare(TrainingClassUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_user set sign_up_time = ?, exam_result = ?, auth_result = ?, user_status = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ?, class_id = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setTimestamp(1, bean.getSignUpTime());
			ps.setInt(2, bean.getExamResult());
			ps.setInt(3, bean.getAuthResult());
			ps.setString(4, bean.getUserStatus());
			ps.setInt(5, bean.getSyncStatus());
			ps.setInt(6, bean.getStatus());
			ps.setInt(7, bean.getModifyUser());
			ps.setString(8, bean.getMemo());

			ps.setInt(9, bean.getClassId());
			ps.setInt(10, bean.getDeclareId());

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

	public TrainingClassUserBean getTrainingClassUserByKey(int classId, int declareId) {
		TrainingClassUserBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user where class_id = ? and declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, classId);
			ps.setInt(2, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassUserBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassUserBean.class);
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

	public PageResultBean<TrainingClassUserBean> getTrainingClassUserPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrainingClassUserBean> result = new PageResultBean<TrainingClassUserBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String classId = condMap.get("classId");
			if (StringKit.isValid(classId)) {
				condList.add("class_id = ?");
				paramList.add(classId);
			}
			String userStatus = condMap.get("userStatus");
			if (StringKit.isValid(userStatus)) {
				condList.add("user_status = ?");
				paramList.add(userStatus);
			}
			String orderQuery = "order by declare_id desc,class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassUserBean.class,
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

	public List<TrainingClassUserBean> getTrainingClassUserListByMap(Map<String, String> condMap) {
		List<TrainingClassUserBean> result = new ArrayList<TrainingClassUserBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String classId = condMap.get("classId");
			if (StringKit.isValid(classId)) {
				condList.add("class_id = ?");
				paramList.add(classId);
			}
			String userStatus = condMap.get("userStatus");
			if (StringKit.isValid(userStatus)) {
				condList.add("user_status = ?");
				paramList.add(userStatus);
			}
			String userId = condMap.get("userId");
			if (StringKit.isValid(userId)) {
				condList.add("user_id = ?");
				paramList.add(userId);
			}
			String declareId = condMap.get("declareId");
			if (StringKit.isValid(declareId)) {
				condList.add("declare_id = ?");
				paramList.add(declareId);
			}
			String orderQuery = "order by declare_id,class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, TrainingClassUserBean.class,
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

	public TrainingClassUserBean getTrainingClassUserByDeclare(int declareId) {
		TrainingClassUserBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_user where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassUserBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassUserBean.class);
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

	public PageResultBean<TrainingClassUserBean> getTrainingClassUserPageListByClassIdList(
			HashMap<String, String> condMap, PageBean pageBean, List<Integer> classInfoIdList) {
		PageResultBean<TrainingClassUserBean> result = new PageResultBean<TrainingClassUserBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String sql = "select * from training_class_user";
			if (classInfoIdList.size() > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(" where ( ");
				for (int i = 0; i < classInfoIdList.size(); i++) {
					Integer cond = classInfoIdList.get(i);
					if (i > 0) {
						sb.append(" or ");
					}
					sb.append("class_id =" + String.valueOf(cond));
				}
				sb.append(" ) ");
				sql = sql + sb.toString();
				// 条件处理
				String status = condMap.get("status");
				if (StringKit.isValid(status)) {
					sql = sql + " and status = " + status;
				}
				String userStatus = condMap.get("userStatus");
				if (StringKit.isValid(userStatus)) {
					sql = sql + " and user_status = " + userStatus;
				}
				String authResult = condMap.get("authResult");
				if (StringKit.isValid(authResult)) {
					sql = sql + " and auth_result = " + authResult;
				}
			} else {
				sql = sql + " where null ";
			}
			String orderQuery = "order by declare_id,class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassUserBean.class,
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
