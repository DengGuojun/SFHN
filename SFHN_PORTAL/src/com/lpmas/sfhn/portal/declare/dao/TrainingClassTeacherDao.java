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
import com.lpmas.sfhn.portal.bean.TrainingClassTeacherBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingClassTeacherDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassTeacherDao.class);

	public int insertTrainingClassTeacher(TrainingClassTeacherBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_teacher ( class_id, teacher_id, course_id, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getClassId());
			ps.setInt(2, bean.getTeacherId());
			ps.setInt(3, bean.getCourseId());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());
			ps.setString(6, bean.getMemo());

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

	public int updateTrainingClassTeacher(TrainingClassTeacherBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_teacher set course_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where class_id = ? and teacher_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getCourseId());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getClassId());
			ps.setInt(6, bean.getTeacherId());

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

	public TrainingClassTeacherBean getTrainingClassTeacherByKey(int classId, int teacherId) {
		TrainingClassTeacherBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_teacher where class_id = ? and teacher_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, classId);
			ps.setInt(2, teacherId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassTeacherBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassTeacherBean.class);
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

	public PageResultBean<TrainingClassTeacherBean> getTrainingClassTeacherPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<TrainingClassTeacherBean> result = new PageResultBean<TrainingClassTeacherBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_teacher";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String classId = condMap.get("classId");
			if (StringKit.isValid(classId)) {
				condList.add("class_id = ?");
				paramList.add(classId);
			}

			String orderQuery = "order by teacher_id,class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassTeacherBean.class,
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

	public List<TrainingClassTeacherBean> getTrainingClassTeacherListByClassId(int classId) {
		List<TrainingClassTeacherBean> list = new ArrayList<TrainingClassTeacherBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_teacher";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			condList.add("class_id = ?");
			paramList.add(String.valueOf(classId));
			String orderQuery = "order by teacher_id";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, TrainingClassTeacherBean.class,
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
		return list;
	}

}
