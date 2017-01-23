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
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingOrganizationInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrainingOrganizationInfoDao.class);

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

	public List<TrainingOrganizationInfoBean> getTrainingOrganizationInfoListByMap(HashMap<String, String> condMap) {
		List<TrainingOrganizationInfoBean> list = new ArrayList<TrainingOrganizationInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_organization_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
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
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by organization_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList,
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
		return list;
	}

}
