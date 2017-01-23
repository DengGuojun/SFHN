package com.lpmas.sfhn.portal.declare.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.declare.bean.NationalCertificationBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class NationalCertificationDao {
	private static Logger log = LoggerFactory.getLogger(NationalCertificationDao.class);

	public List<NationalCertificationBean> getNationalCertificationListByMap(HashMap<String, String> condMap) {
		List<NationalCertificationBean> result = new ArrayList<NationalCertificationBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from national_certification";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by certificate_id desc";
			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList,
					NationalCertificationBean.class, db);
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
