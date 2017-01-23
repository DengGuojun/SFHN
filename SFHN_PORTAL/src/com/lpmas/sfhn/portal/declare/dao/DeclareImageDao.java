package com.lpmas.sfhn.portal.declare.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.declare.bean.DeclareImageBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class DeclareImageDao {
	private static Logger log = LoggerFactory.getLogger(DeclareImageDao.class);

	public int insertDeclareImage(DeclareImageBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into declare_image ( declare_id, image_type, image_path, status, create_time, create_user) value( ?, ?, ?, ?, now(), ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getDeclareId());
			ps.setString(2, bean.getImageType());
			ps.setString(3, bean.getImagePath());
			ps.setInt(4, bean.getStatus());
			ps.setInt(5, bean.getCreateUser());

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

	public int updateDeclareImage(DeclareImageBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update declare_image set image_path = ?, status = ?, modify_time = now(), modify_user = ? where declare_id = ? and image_type = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getImagePath());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());

			ps.setInt(4, bean.getDeclareId());
			ps.setString(5, bean.getImageType());

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

	public DeclareImageBean getDeclareImageByKey(int declareId, String imageType) {
		DeclareImageBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_image where declare_id = ? and image_type = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);
			ps.setString(2, imageType);
			ps.setInt(3, Constants.STATUS_VALID);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new DeclareImageBean();
				bean = BeanKit.resultSet2Bean(rs, DeclareImageBean.class);
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

	public PageResultBean<DeclareImageBean> getDeclareImagePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<DeclareImageBean> result = new PageResultBean<DeclareImageBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from declare_image";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id,image_type desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, DeclareImageBean.class, pageBean,
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

}
