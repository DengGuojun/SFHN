package com.lpmas.sfhn.console.dao;

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
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.console.factory.SfhnDBFactory;

public class MajorInfoDao {
	private static Logger log = LoggerFactory.getLogger(MajorInfoDao.class);

	public int insertMajorInfo(MajorInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into major_info ( major_name, type_id, status, create_time, create_user, memo) value( ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMajorName());
			ps.setInt(2, bean.getTypeId());
			ps.setInt(3, bean.getStatus());
			ps.setInt(4, bean.getCreateUser());
			ps.setString(5, bean.getMemo());

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

	public int updateMajorInfo(MajorInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update major_info set major_name = ?, type_id = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where major_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMajorName());
			ps.setInt(2, bean.getTypeId());
			ps.setInt(3, bean.getStatus());
			ps.setInt(4, bean.getModifyUser());
			ps.setString(5, bean.getMemo());
			ps.setInt(6, bean.getMajorId());

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

	public MajorInfoBean getMajorInfoByKey(int majorId) {
		MajorInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_info where major_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, majorId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorInfoBean();
				bean = BeanKit.resultSet2Bean(rs, MajorInfoBean.class);
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

	public PageResultBean<MajorInfoBean> getMajorInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<MajorInfoBean> result = new PageResultBean<MajorInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
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

			String majorName = condMap.get("majorName");
			if (StringKit.isValid(majorName)) {
				condList.add("major_name like ? ");
				paramList.add("%" + majorName + "%");
			}

			String orderQuery = "order by major_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, MajorInfoBean.class, pageBean, db);
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

	public List<MajorInfoBean> getMajorInfoListByMap(HashMap<String, String> condMap) {
		List<MajorInfoBean> result = new ArrayList<MajorInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String majorId = condMap.get("majorId");
			if (StringKit.isValid(majorId)) {
				condList.add("major_id = ?");
				paramList.add(majorId);
			}
			String typeId = condMap.get("typeId");
			if (StringKit.isValid(typeId)) {
				condList.add("type_id = ?");
				paramList.add(typeId);
			}
			String memo = condMap.get("memo");
			if (StringKit.isValid(memo)) {
				condList.add("memo = ?");
				paramList.add(memo);
			}
			String orderQuery = "order by major_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, MajorInfoBean.class, db);
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

	// 获取在同一专业类下类型的专业信息
	public MajorInfoBean getMajorInfoByNameAndSatus(String majorName, int typeId) {
		MajorInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_info where major_name = ? and status = ? and type_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, majorName);
			ps.setInt(2, Constants.STATUS_VALID);
			ps.setInt(3, typeId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorInfoBean();
				bean = BeanKit.resultSet2Bean(rs, MajorInfoBean.class);
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
