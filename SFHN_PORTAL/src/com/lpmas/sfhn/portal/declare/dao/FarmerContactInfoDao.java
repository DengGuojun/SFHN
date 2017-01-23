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
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class FarmerContactInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerContactInfoDao.class);

	public int insertFarmerContactInfo(FarmerContactInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into farmer_contact_info ( declare_id, user_mobile, user_email, user_qq, user_wechat, family_person, province, city, region, address, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getDeclareId());
			ps.setString(2, bean.getUserMobile());
			ps.setString(3, bean.getUserEmail());
			ps.setString(4, bean.getUserQq());
			ps.setString(5, bean.getUserWechat());
			ps.setInt(6, bean.getFamilyPerson());
			ps.setString(7, bean.getProvince());
			ps.setString(8, bean.getCity());
			ps.setString(9, bean.getRegion());
			ps.setString(10, bean.getAddress());
			ps.setInt(11, bean.getStatus());
			ps.setInt(12, bean.getCreateUser());
			ps.setString(13, bean.getMemo());

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

	public int updateFarmerContactInfo(FarmerContactInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update farmer_contact_info set user_mobile = ?, user_email = ?, user_qq = ?, user_wechat = ?, family_person = ?, province = ?, city = ?, region = ?, address = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getUserMobile());
			ps.setString(2, bean.getUserEmail());
			ps.setString(3, bean.getUserQq());
			ps.setString(4, bean.getUserWechat());
			ps.setInt(5, bean.getFamilyPerson());
			ps.setString(6, bean.getProvince());
			ps.setString(7, bean.getCity());
			ps.setString(8, bean.getRegion());
			ps.setString(9, bean.getAddress());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getModifyUser());
			ps.setString(12, bean.getMemo());

			ps.setInt(13, bean.getDeclareId());

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

	public FarmerContactInfoBean getFarmerContactInfoByKey(int declareId) {
		FarmerContactInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_contact_info where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

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

	public FarmerContactInfoBean getFarmerContactInfoByUserMobileAndStatus(String userMobile, int status) {
		FarmerContactInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_contact_info where user_mobile = ? and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, userMobile);
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

	public PageResultBean<FarmerContactInfoBean> getFarmerContactInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<FarmerContactInfoBean> result = new PageResultBean<FarmerContactInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_contact_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, FarmerContactInfoBean.class,
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
