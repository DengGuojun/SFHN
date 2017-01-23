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
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class IndustryInfoDao {
	private static Logger log = LoggerFactory.getLogger(IndustryInfoDao.class);

	public List<IndustryInfoBean> getIndustryInfoListByMap(HashMap<String, String> condMap) {
		List<IndustryInfoBean> result = new ArrayList<IndustryInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from industry_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String typeId = condMap.get("typeId");
			if (StringKit.isValid(typeId)) {
				condList.add("type_id = ?");
				paramList.add(typeId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by industry_id";
			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList,
					IndustryInfoBean.class, db);
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
	
	public IndustryInfoBean getIndustryInfoByKey(int industryId) {
		IndustryInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from industry_info where industry_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, industryId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new IndustryInfoBean();
				bean = BeanKit.resultSet2Bean(rs, IndustryInfoBean.class);
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
