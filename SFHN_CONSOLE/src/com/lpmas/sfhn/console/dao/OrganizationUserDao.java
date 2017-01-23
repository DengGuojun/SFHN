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
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;

public class OrganizationUserDao {
	private static Logger log = LoggerFactory.getLogger(OrganizationUserDao.class);

	public int insertOrganizationUser(OrganizationUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into organization_user ( organization_id, user_id, info_type, create_time, create_user) value( ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrganizationId());
			ps.setInt(2, bean.getUserId());
			ps.setInt(3, bean.getInfoType());
			ps.setInt(4, bean.getCreateUser());

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

	public int updateOrganizationUser(OrganizationUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update organization_user set modify_time = now(), modify_user = ? where organization_id = ? and user_id = ? and info_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getModifyUser());

			ps.setInt(2, bean.getOrganizationId());
			ps.setInt(3, bean.getUserId());
			ps.setInt(4, bean.getInfoType());

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

	public int updateOrganizationUserByCondition(OrganizationUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update organization_user set modify_time = now(), modify_user = ?, user_id = ? where organization_id = ? and info_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getModifyUser());
			ps.setInt(2, bean.getUserId());
			ps.setInt(3, bean.getOrganizationId());
			ps.setInt(4, bean.getInfoType());

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

	public OrganizationUserBean getOrganizationUserByKey(int organizationId, int userId, int infoType) {
		OrganizationUserBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from organization_user where organization_id = ? and user_id = ? and info_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, organizationId);
			ps.setInt(2, userId);
			ps.setInt(3, infoType);

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

	public OrganizationUserBean getOrganizationUserByKey(int organizationId, int infoType) {
		OrganizationUserBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from organization_user where organization_id = ? and info_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, organizationId);
			ps.setInt(2, infoType);

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

	public PageResultBean<OrganizationUserBean> getOrganizationUserPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<OrganizationUserBean> result = new PageResultBean<OrganizationUserBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from organization_user";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by info_type,user_id,organization_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, OrganizationUserBean.class,
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

	public List<OrganizationUserBean> getOrganizationUserListByMap(HashMap<String, String> condMap) {
		List<OrganizationUserBean> result = new ArrayList<OrganizationUserBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from organization_user";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}
			String infoType = condMap.get("infoType");
			if (StringKit.isValid(infoType)) {
				condList.add("info_type = ?");
				paramList.add(infoType);
			}
			String orderQuery = "order by info_type,user_id,organization_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, OrganizationUserBean.class,
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

	public int deleteOrganizationUser(OrganizationUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from organization_user where organization_id = ? and user_id = ? and info_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrganizationId());
			ps.setInt(2, bean.getUserId());
			ps.setInt(3, bean.getInfoType());

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

}
