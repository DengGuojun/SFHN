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
import com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class FarmerSkillInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerSkillInfoDao.class);

	public int insertFarmerSkillInfo(FarmerSkillInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into farmer_skill_info ( declare_id, is_trained, apply_type, certification_grade, certification_date, certification_department, certification_title, other_training_time, has_national_certification, national_certification_id_1, national_certification_grade_1, national_certification_id_2, national_certification_grade_2, has_new_type_training_certification, has_new_type_certification, has_no_certification, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getDeclareId());
			ps.setInt(2, bean.getIsTrained());
			ps.setString(3, bean.getApplyType());
			ps.setString(4, bean.getCertificationGrade());
			ps.setTimestamp(5, bean.getCertificationDate());
			ps.setString(6, bean.getCertificationDepartment());
			ps.setString(7, bean.getCertificationTitle());
			ps.setInt(8, bean.getOtherTrainingTime());
			ps.setInt(9, bean.getHasNationalCertification());
			ps.setInt(10, bean.getNationalCertificationId1());
			ps.setInt(11, bean.getNationalCertificationGrade1());
			ps.setInt(12, bean.getNationalCertificationId2());
			ps.setInt(13, bean.getNationalCertificationGrade2());
			ps.setInt(14, bean.getHasNewTypeTrainingCertification());
			ps.setInt(15, bean.getHasNewTypeCertification());
			ps.setInt(16, bean.getHasNoCertification());
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

	public int updateFarmerSkillInfo(FarmerSkillInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update farmer_skill_info set is_trained = ?, apply_type = ?, certification_grade = ?, certification_date = ?, certification_department = ?, certification_title = ?, other_training_time = ?, has_national_certification = ?, national_certification_id_1 = ?, national_certification_grade_1 = ?, national_certification_id_2 = ?, national_certification_grade_2 = ?, has_new_type_training_certification = ?, has_new_type_certification = ?, has_no_certification = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getIsTrained());
			ps.setString(2, bean.getApplyType());
			ps.setString(3, bean.getCertificationGrade());
			ps.setTimestamp(4, bean.getCertificationDate());
			ps.setString(5, bean.getCertificationDepartment());
			ps.setString(6, bean.getCertificationTitle());
			ps.setInt(7, bean.getOtherTrainingTime());
			ps.setInt(8, bean.getHasNationalCertification());
			ps.setInt(9, bean.getNationalCertificationId1());
			ps.setInt(10, bean.getNationalCertificationGrade1());
			ps.setInt(11, bean.getNationalCertificationId2());
			ps.setInt(12, bean.getNationalCertificationGrade2());
			ps.setInt(13, bean.getHasNewTypeTrainingCertification());
			ps.setInt(14, bean.getHasNewTypeCertification());
			ps.setInt(15, bean.getHasNoCertification());
			ps.setInt(16, bean.getStatus());
			ps.setInt(17, bean.getModifyUser());
			ps.setString(18, bean.getMemo());

			ps.setInt(19, bean.getDeclareId());

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

	public FarmerSkillInfoBean getFarmerSkillInfoByKey(int declareId) {
		FarmerSkillInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_skill_info where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new FarmerSkillInfoBean();
				bean = BeanKit.resultSet2Bean(rs, FarmerSkillInfoBean.class);
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

	public PageResultBean<FarmerSkillInfoBean> getFarmerSkillInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<FarmerSkillInfoBean> result = new PageResultBean<FarmerSkillInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_skill_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, FarmerSkillInfoBean.class, pageBean,
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

}
