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
import com.lpmas.sfhn.portal.bean.TeacherEvaluateBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TeacherEvaluateDao {
	private static Logger log = LoggerFactory.getLogger(TeacherEvaluateDao.class);

	public int insertTeacherEvaluate(TeacherEvaluateBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into teacher_evaluate ( teacher_id, class_id, course_id, evaluate_score, organization_type, organization_id, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTeacherId());
			ps.setInt(2, bean.getClassId());
			ps.setInt(3, bean.getCourseId());
			ps.setInt(4, bean.getEvaluateScore());
			ps.setInt(5, bean.getOrganizationType());
			ps.setInt(6, bean.getOrganizationId());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getCreateUser());
			ps.setString(9, bean.getMemo());

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

	public int updateTeacherEvaluate(TeacherEvaluateBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update teacher_evaluate set teacher_id = ?, class_id = ?, course_id = ?, evaluate_score = ?, organization_type = ?, organization_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where evaluate_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getTeacherId());
			ps.setInt(2, bean.getClassId());
			ps.setInt(3, bean.getCourseId());
			ps.setInt(4, bean.getEvaluateScore());
			ps.setInt(5, bean.getOrganizationType());
			ps.setInt(6, bean.getOrganizationId());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getModifyUser());
			ps.setString(9, bean.getMemo());

			ps.setInt(10, bean.getEvaluateId());

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

	public TeacherEvaluateBean getTeacherEvaluateByKey(int evaluateId) {
		TeacherEvaluateBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_evaluate where evaluate_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, evaluateId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherEvaluateBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherEvaluateBean.class);
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

	public PageResultBean<TeacherEvaluateBean> getTeacherEvaluatePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TeacherEvaluateBean> result = new PageResultBean<TeacherEvaluateBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_evaluate";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String teacherId = condMap.get("teacherId");
			if (StringKit.isValid(teacherId)) {
				condList.add("teacher_id = ?");
				paramList.add(teacherId);
			}

			String orderQuery = "order by evaluate_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TeacherEvaluateBean.class,
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
	
	public List<TeacherEvaluateBean> getTeacherEvaluateListByMap(HashMap<String, String> condMap) {
		List<TeacherEvaluateBean> list = new ArrayList<TeacherEvaluateBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql =  "select * from teacher_evaluate";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String teacherId = condMap.get("teacherId");
			if (StringKit.isValid(teacherId)) {
				condList.add("teacher_id = ?");
				paramList.add(teacherId);
			}
			String classId = condMap.get("classId");
			if (StringKit.isValid(classId)) {
				condList.add("class_id = ?");
				paramList.add(classId);
			}
			String courseId = condMap.get("courseId");
			if (StringKit.isValid(courseId)) {
				condList.add("course_id = ?");
				paramList.add(courseId);
			}
			String organizationType = condMap.get("organizationType");
			if (StringKit.isValid(organizationType)) {
				condList.add("organization_type = ?");
				paramList.add(organizationType);
			}
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
			String orderQuery = "order by evaluate_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, TeacherEvaluateBean.class, db);
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
