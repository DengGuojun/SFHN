package com.lpmas.sfhn.console.declare.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;
import com.lpmas.sfhn.declare.bean.FarmerJobInfoBean;

public class FarmerJobInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerJobInfoDao.class);

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

}
