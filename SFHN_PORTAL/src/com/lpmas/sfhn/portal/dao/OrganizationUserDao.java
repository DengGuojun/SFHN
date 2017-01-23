package com.lpmas.sfhn.portal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class OrganizationUserDao {
	private static Logger log = LoggerFactory.getLogger(OrganizationUserDao.class);

	public OrganizationUserBean getOrganizationUserByUserId(int userId) {
		OrganizationUserBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from organization_user where user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new OrganizationUserBean();
				bean = BeanKit.resultSet2Bean(rs, OrganizationUserBean.class);
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
