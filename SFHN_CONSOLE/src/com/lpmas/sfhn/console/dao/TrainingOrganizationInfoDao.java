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
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;

public class TrainingOrganizationInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrainingOrganizationInfoDao.class);

	public int insertTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_organization_info ( organization_name, training_year, organization_type, province, city, region, representative_name, telephone, mobile, address, zip_code, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOrganizationName());
			ps.setString(2, bean.getTrainingYear());
			ps.setString(3, bean.getOrganizationType());
			ps.setString(4, bean.getProvince());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getRegion());
			ps.setString(7, bean.getRepresentativeName());
			ps.setString(8, bean.getTelephone());
			ps.setString(9, bean.getMobile());
			ps.setString(10, bean.getAddress());
			ps.setString(11, bean.getZipCode());
			ps.setInt(12, bean.getStatus());
			ps.setInt(13, bean.getCreateUser());
			ps.setString(14, bean.getMemo());

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

	public int updateTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_organization_info set organization_name = ?, training_year = ?, organization_type = ?, province = ?, city = ?, region = ?, representative_name = ?, telephone = ?, mobile = ?, address = ?, zip_code = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where organization_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOrganizationName());
			ps.setString(2, bean.getTrainingYear());
			ps.setString(3, bean.getOrganizationType());
			ps.setString(4, bean.getProvince());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getRegion());
			ps.setString(7, bean.getRepresentativeName());
			ps.setString(8, bean.getTelephone());
			ps.setString(9, bean.getMobile());
			ps.setString(10, bean.getAddress());
			ps.setString(11, bean.getZipCode());
			ps.setInt(12, bean.getStatus());
			ps.setInt(13, bean.getModifyUser());
			ps.setString(14, bean.getMemo());

			ps.setInt(15, bean.getOrganizationId());

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

	public TrainingOrganizationInfoBean getTrainingOrganizationInfoByKey(int organizationId) {
		TrainingOrganizationInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_organization_info where organization_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, organizationId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingOrganizationInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingOrganizationInfoBean.class);
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

	public PageResultBean<TrainingOrganizationInfoBean> getTrainingOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<TrainingOrganizationInfoBean> result = new PageResultBean<TrainingOrganizationInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_organization_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String organizationName = condMap.get("organizationName");
			if (StringKit.isValid(organizationName)) {
				condList.add("organization_name like ?");
				paramList.add("%" + organizationName + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by organization_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingOrganizationInfoBean.class,
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

	public List<TrainingOrganizationInfoBean> getTrainingOrganizationInfoListByMap(HashMap<String, String> condMap) {
		List<TrainingOrganizationInfoBean> result = new ArrayList<TrainingOrganizationInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_organization_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String organizationName = condMap.get("organizationName");
			if (StringKit.isValid(organizationName)) {
				condList.add("organization_name like ?");
				paramList.add("%" + organizationName + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by organization_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList,
					TrainingOrganizationInfoBean.class, db);
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
