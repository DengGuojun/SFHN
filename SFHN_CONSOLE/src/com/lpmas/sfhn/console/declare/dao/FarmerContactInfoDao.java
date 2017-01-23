package com.lpmas.sfhn.console.declare.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;

public class FarmerContactInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerContactInfoDao.class);

	public FarmerContactInfoBean getFarmerContactInfoByKey(int declareId) {
		FarmerContactInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_contact_info where declare_id = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);
			ps.setInt(2, Constants.STATUS_VALID);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new FarmerContactInfoBean();
				bean = BeanKit.resultSet2Bean(rs, FarmerContactInfoBean.class);
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
