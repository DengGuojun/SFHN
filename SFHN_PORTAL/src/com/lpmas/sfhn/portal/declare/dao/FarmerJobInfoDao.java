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
import com.lpmas.sfhn.declare.bean.FarmerJobInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class FarmerJobInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerJobInfoDao.class);

	public int insertFarmerJobInfo(FarmerJobInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into farmer_job_info (declare_id, job_type, job_id, experience, income, company_type, job_province, job_city, job_region, status, create_time, create_user, memo) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1,  bean.getDeclareId());
			ps.setInt(2, bean.getJobType());
			ps.setInt(3, bean.getJobId());
			ps.setDouble(4, bean.getExperience());
			ps.setDouble(5, bean.getIncome());
			ps.setString(6, bean.getCompanyType());
			ps.setString(7, bean.getJobProvince());
			ps.setString(8, bean.getJobCity());
			ps.setString(9, bean.getJobRegion());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getCreateUser());
			ps.setString(12, bean.getMemo());

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

	public int updateFarmerJobInfo(FarmerJobInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update farmer_job_info set job_type = ?, job_id = ?, experience = ?, income = ?, company_type = ?, job_province = ?, job_city = ?, job_region = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getJobType());
			ps.setInt(2, bean.getJobId());
			ps.setDouble(3, bean.getExperience());
			ps.setDouble(4, bean.getIncome());
			ps.setString(5, bean.getCompanyType());
			ps.setString(6, bean.getJobProvince());
			ps.setString(7, bean.getJobCity());
			ps.setString(8, bean.getJobRegion());
			ps.setInt(9, bean.getStatus());
			ps.setInt(10, bean.getModifyUser());
			ps.setString(11, bean.getMemo());

			ps.setInt(12, bean.getDeclareId());

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

	public FarmerJobInfoBean getFarmerJobInfoByKey(int declareId) {
		FarmerJobInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_job_info where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new FarmerJobInfoBean();
				bean = BeanKit.resultSet2Bean(rs, FarmerJobInfoBean.class);
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

	public PageResultBean<FarmerJobInfoBean> getFarmerJobInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<FarmerJobInfoBean> result = new PageResultBean<FarmerJobInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_job_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, FarmerJobInfoBean.class, pageBean,
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
