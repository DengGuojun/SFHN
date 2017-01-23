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
import com.lpmas.sfhn.bean.IndustryBeltInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;


public class IndustryBeltInfoDao {
	private static Logger log = LoggerFactory.getLogger(IndustryBeltInfoDao.class);


	public IndustryBeltInfoBean getIndustryBeltInfoByKey(int beltId) {
		IndustryBeltInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from industry_belt_info where belt_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, beltId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new IndustryBeltInfoBean();
				bean = BeanKit.resultSet2Bean(rs, IndustryBeltInfoBean.class);
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

	public PageResultBean<IndustryBeltInfoBean> getIndustryBeltInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<IndustryBeltInfoBean> result = new PageResultBean<IndustryBeltInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from industry_belt_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String beltName = condMap.get("beltName");
			if (StringKit.isValid(beltName)) {
				condList.add("belt_name like ? ");
				paramList.add("%" + beltName + "%");
			}

			String orderQuery = "order by belt_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, IndustryBeltInfoBean.class,
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
	
	public List<IndustryBeltInfoBean> getIndustryBeltInfoListByMap(HashMap<String, String> condMap) {
		List<IndustryBeltInfoBean> list = new ArrayList<IndustryBeltInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql =  "select * from industry_belt_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by belt_id asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			list = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, IndustryBeltInfoBean.class, db);
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
