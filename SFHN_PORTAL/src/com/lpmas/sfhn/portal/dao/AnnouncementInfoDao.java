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
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class AnnouncementInfoDao {
	private static Logger log = LoggerFactory.getLogger(AnnouncementInfoDao.class);

	public int insertAnnouncementInfo(AnnouncementInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into announcement_info ( organization_type, organization_id, announcement_title, announcement_content, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrganizationType());
			ps.setInt(2, bean.getOrganizationId());
			ps.setString(3, bean.getAnnouncementTitle());
			ps.setString(4, bean.getAnnouncementContent());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getCreateUser());
			ps.setString(7, bean.getMemo());

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

	public int updateAnnouncementInfo(AnnouncementInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update announcement_info set organization_type = ?, organization_id = ?, announcement_title = ?, announcement_content = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where announcement_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getOrganizationType());
			ps.setInt(2, bean.getOrganizationId());
			ps.setString(3, bean.getAnnouncementTitle());
			ps.setString(4, bean.getAnnouncementContent());
			ps.setInt(5, bean.getStatus());
			ps.setInt(6, bean.getModifyUser());
			ps.setString(7, bean.getMemo());

			ps.setInt(8, bean.getAnnouncementId());

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

	public AnnouncementInfoBean getAnnouncementInfoByKey(int announcementId) {
		AnnouncementInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from announcement_info where announcement_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, announcementId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AnnouncementInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AnnouncementInfoBean.class);
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

	public PageResultBean<AnnouncementInfoBean> getAnnouncementInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AnnouncementInfoBean> result = new PageResultBean<AnnouncementInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from announcement_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String organizationType = condMap.get("organizationType");
			if (StringKit.isValid(organizationType)) {
				condList.add("organization_type = ?");
				paramList.add(organizationType);
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

			String orderQuery = "order by announcement_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AnnouncementInfoBean.class,
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

}
