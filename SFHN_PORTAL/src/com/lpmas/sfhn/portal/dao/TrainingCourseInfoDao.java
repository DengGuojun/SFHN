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
import com.lpmas.sfhn.portal.bean.TrainingCourseInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingCourseInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrainingCourseInfoDao.class);

	public int insertTrainingCourseInfo(TrainingCourseInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_course_info ( course_name, major_type_id, major_id, status, create_time, create_user, memo) value( ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCourseName());
			ps.setInt(2, bean.getMajorTypeId());
			ps.setInt(3, bean.getMajorId());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());
			ps.setString(6, bean.getMemo());

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

	public int updateTrainingCourseInfo(TrainingCourseInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_course_info set course_name = ?, major_type_id = ?, major_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where course_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getCourseName());
			ps.setInt(2, bean.getMajorTypeId());
			ps.setInt(3, bean.getMajorId());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getModifyUser());
			ps.setString(6, bean.getMemo());

			ps.setInt(7, bean.getCourseId());

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

	public TrainingCourseInfoBean getTrainingCourseInfoByKey(int courseId) {
		TrainingCourseInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_course_info where course_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, courseId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingCourseInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingCourseInfoBean.class);
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
	
	public TrainingCourseInfoBean getTrainingCourseInfoByName(String courseName) {
		TrainingCourseInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_course_info where course_name = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, courseName);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingCourseInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingCourseInfoBean.class);
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

	public PageResultBean<TrainingCourseInfoBean> getTrainingCourseInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrainingCourseInfoBean> result = new PageResultBean<TrainingCourseInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_course_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String majorId = condMap.get("majorId");
			if (StringKit.isValid(majorId)) {
				condList.add("major_id = ?");
				paramList.add(majorId);
			}
			String majorTypeId = condMap.get("majorTypeId");
			if (StringKit.isValid(majorTypeId)) {
				condList.add("major_type_id = ?");
				paramList.add(majorTypeId);
			}
			String courseName = condMap.get("courseName");
			if (StringKit.isValid(courseName)) {
				condList.add("course_name like ?");
				paramList.add("%" + courseName + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by course_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingCourseInfoBean.class,
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
