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
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TeacherInfoDao {
	private static Logger log = LoggerFactory.getLogger(TeacherInfoDao.class);

	public int insertTeacherInfo(TeacherInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into teacher_info ( user_id, teacher_name, identity_number, tearcher_type, teacher_gender, teacher_age, major_type_id, major_id, province, city, region, main_course, corporate_name, teacher_mobile, address, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getTeacherName());
			ps.setString(3, bean.getIdentityNumber());
			ps.setString(4, bean.getTearcherType());
			ps.setInt(5, bean.getTeacherGender());
			ps.setInt(6, bean.getTeacherAge());
			ps.setInt(7, bean.getMajorTypeId());
			ps.setInt(8, bean.getMajorId());
			ps.setString(9, bean.getProvince());
			ps.setString(10, bean.getCity());
			ps.setString(11, bean.getRegion());
			ps.setString(12, bean.getMainCourse());
			ps.setString(13, bean.getCorporateName());
			ps.setString(14, bean.getTeacherMobile());
			ps.setString(15, bean.getAddress());
			ps.setInt(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getCreateUser());
			ps.setString(19, bean.getMemo());

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

	public int insertTeacherInfoWithCreateTime(TeacherInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into teacher_info ( user_id, teacher_name, identity_number, tearcher_type, teacher_gender, teacher_age, major_type_id, major_id, province, city, region, main_course, corporate_name, teacher_mobile, address, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getTeacherName());
			ps.setString(3, bean.getIdentityNumber());
			ps.setString(4, bean.getTearcherType());
			ps.setInt(5, bean.getTeacherGender());
			ps.setInt(6, bean.getTeacherAge());
			ps.setInt(7, bean.getMajorTypeId());
			ps.setInt(8, bean.getMajorId());
			ps.setString(9, bean.getProvince());
			ps.setString(10, bean.getCity());
			ps.setString(11, bean.getRegion());
			ps.setString(12, bean.getMainCourse());
			ps.setString(13, bean.getCorporateName());
			ps.setString(14, bean.getTeacherMobile());
			ps.setString(15, bean.getAddress());
			ps.setInt(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setTimestamp(18, bean.getCreateTime());
			ps.setInt(19, bean.getCreateUser());
			ps.setString(20, bean.getMemo());

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

	public int updateTeacherInfo(TeacherInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update teacher_info set user_id = ?, teacher_name = ?, identity_number = ?, tearcher_type = ?, teacher_gender = ?, teacher_age = ?, major_type_id = ?, major_id = ?, province = ?, city = ?, region = ?, main_course = ?, corporate_name = ?, teacher_mobile = ?, address = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where teacher_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getUserId());
			ps.setString(2, bean.getTeacherName());
			ps.setString(3, bean.getIdentityNumber());
			ps.setString(4, bean.getTearcherType());
			ps.setInt(5, bean.getTeacherGender());
			ps.setInt(6, bean.getTeacherAge());
			ps.setInt(7, bean.getMajorTypeId());
			ps.setInt(8, bean.getMajorId());
			ps.setString(9, bean.getProvince());
			ps.setString(10, bean.getCity());
			ps.setString(11, bean.getRegion());
			ps.setString(12, bean.getMainCourse());
			ps.setString(13, bean.getCorporateName());
			ps.setString(14, bean.getTeacherMobile());
			ps.setString(15, bean.getAddress());
			ps.setInt(16, bean.getSyncStatus());
			ps.setInt(17, bean.getStatus());
			ps.setInt(18, bean.getModifyUser());
			ps.setString(19, bean.getMemo());

			ps.setInt(20, bean.getTeacherId());

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

	public TeacherInfoBean getTeacherInfoByKey(int teacherId) {
		TeacherInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info where teacher_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, teacherId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherInfoBean.class);
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

	public TeacherInfoBean getTeacherInfoByMobileAndStatus(String teacherMobile, int status) {
		TeacherInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info where teacher_mobile = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, teacherMobile);
			ps.setInt(2, status);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherInfoBean.class);
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

	public TeacherInfoBean getTeacherInfoByUserId(int userId) {
		TeacherInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info where user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherInfoBean.class);
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

	public PageResultBean<TeacherInfoBean> getTeacherInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<TeacherInfoBean> result = new PageResultBean<TeacherInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info";

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
			String teacherName = condMap.get("teacherName");
			if (StringKit.isValid(teacherName)) {
				condList.add("teacher_name like ?");
				paramList.add("%" + teacherName + "%");
			}
			String identityNumber = condMap.get("identityNumber");
			if (StringKit.isValid(identityNumber)) {
				condList.add("identity_number like ?");
				paramList.add("%" + identityNumber + "%");
			}
			String teacherMobile = condMap.get("teacherMobile");
			if (StringKit.isValid(teacherMobile)) {
				condList.add("teacher_mobile like ?");
				paramList.add("%" + teacherMobile + "%");
			}
			String mainCourse = condMap.get("mainCourse");
			if (StringKit.isValid(mainCourse)) {
				condList.add("main_course like ?");
				paramList.add("%" + mainCourse + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by teacher_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TeacherInfoBean.class, pageBean, db);
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

	public TeacherInfoBean getTeacherInfoByUserIdAndStatus(int status, int userId) {
		TeacherInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info where  status = ? and user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, userId);
			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherInfoBean.class);
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

	public TeacherInfoBean getTeacherInfoByIdentityNumber(String identityNumber) {
		TeacherInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from teacher_info where identity_number = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, identityNumber);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TeacherInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TeacherInfoBean.class);
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
