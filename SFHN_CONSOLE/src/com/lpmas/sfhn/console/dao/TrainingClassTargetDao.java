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
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;

public class TrainingClassTargetDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassTargetDao.class);

	public int insertTrainingClassTarget(TrainingClassTargetBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_target ( training_type, training_year, province, city, region, organization_id, target_quantity, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTrainingType());
			ps.setString(2, bean.getTrainingYear());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setInt(6, bean.getOrganizationId());
			ps.setInt(7, bean.getTargetQuantity());
			ps.setInt(8, bean.getStatus());
			ps.setInt(9, bean.getCreateUser());
			ps.setString(10, bean.getMemo());

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

	public int updateTrainingClassTarget(TrainingClassTargetBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_target set training_type = ?, training_year = ?, province = ?, city = ?, region = ?, organization_id = ?, target_quantity = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where target_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTrainingType());
			ps.setString(2, bean.getTrainingYear());
			ps.setString(3, bean.getProvince());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getRegion());
			ps.setInt(6, bean.getOrganizationId());
			ps.setInt(7, bean.getTargetQuantity());
			ps.setInt(8, bean.getStatus());
			ps.setInt(9, bean.getModifyUser());
			ps.setString(10, bean.getMemo());

			ps.setInt(11, bean.getTargetId());

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

	public TrainingClassTargetBean getTrainingClassTargetByKey(int targetId) {
		TrainingClassTargetBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_target where target_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, targetId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassTargetBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassTargetBean.class);
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

	public PageResultBean<TrainingClassTargetBean> getTrainingClassTargetPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrainingClassTargetBean> result = new PageResultBean<TrainingClassTargetBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_target";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
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
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}
			String trainingYear = condMap.get("trainingYear");
			if (StringKit.isValid(trainingYear)) {
				condList.add("training_year = ?");
				paramList.add(trainingYear);
			}
			String trainingType = condMap.get("trainingType");
			if (StringKit.isValid(trainingType)) {
				condList.add("training_type = ?");
				paramList.add(trainingType);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by target_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassTargetBean.class,
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
