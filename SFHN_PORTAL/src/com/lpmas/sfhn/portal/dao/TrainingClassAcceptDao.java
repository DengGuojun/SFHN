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
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingClassAcceptDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassAcceptDao.class);

	public int insertTrainingClassAccept(TrainingClassAcceptBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_accept ( class_id, organization_id, training_address, accept_content, accept_status, status, create_time, create_user, memo) value(?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1,  bean.getClassId());
			ps.setInt(2, bean.getOrganizationId());
			ps.setString(3, bean.getTrainingAddress());
			ps.setString(4, bean.getAcceptContent());
			ps.setString(5, bean.getAcceptStatus());
			ps.setInt(6, bean.getStatus());
			ps.setInt(7, bean.getCreateUser());
			ps.setString(8, bean.getMemo());

			
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

	public int updateTrainingClassAccept(TrainingClassAcceptBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_accept set organization_id = ?, training_address = ?, accept_content = ?, accept_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where class_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrganizationId());
			ps.setString(2, bean.getTrainingAddress());
			ps.setString(3, bean.getAcceptContent());
			ps.setString(4, bean.getAcceptStatus());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());

			ps.setInt(8, bean.getClassId());

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

	public TrainingClassAcceptBean getTrainingClassAcceptByKey(int classId) {
		TrainingClassAcceptBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_accept where class_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, classId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassAcceptBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassAcceptBean.class);
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

	public PageResultBean<TrainingClassAcceptBean> getTrainingClassAcceptPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrainingClassAcceptBean> result = new PageResultBean<TrainingClassAcceptBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_accept";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassAcceptBean.class,
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
