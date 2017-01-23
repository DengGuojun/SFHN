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
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class ProcessLogDao {
	private static Logger log = LoggerFactory.getLogger(ProcessLogDao.class);

	public int insertProcessLog(ProcessLogBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into process_log ( process_type, info_type, info_id, operator_type, operator_id, process_content, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getProcessType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId());
			ps.setInt(4, bean.getOperatorType());
			ps.setInt(5, bean.getOperatorId());
			ps.setString(6, bean.getProcessContent());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getCreateUser());
			ps.setString(9, bean.getMemo());

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

	public int updateProcessLog(ProcessLogBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update process_log set process_type = ?, info_type = ?, info_id = ?, operator_type = ?, operator_id = ?, process_content = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where log_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getProcessType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId());
			ps.setInt(4, bean.getOperatorType());
			ps.setInt(5, bean.getOperatorId());
			ps.setString(6, bean.getProcessContent());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getModifyUser());
			ps.setString(9, bean.getMemo());

			ps.setInt(10, bean.getLogId());

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

	public ProcessLogBean getProcessLogByKey(int logId) {
		ProcessLogBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from process_log where log_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, logId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new ProcessLogBean();
				bean = BeanKit.resultSet2Bean(rs, ProcessLogBean.class);
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

	public PageResultBean<ProcessLogBean> getProcessLogPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<ProcessLogBean> result = new PageResultBean<ProcessLogBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from process_log";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by log_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, ProcessLogBean.class, pageBean, db);
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
	
	public List<ProcessLogBean> getProcessLogListByMap(HashMap<String, String> condMap) {
		List<ProcessLogBean> result = new ArrayList<ProcessLogBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from process_log";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String processType = condMap.get("processType");
			if (StringKit.isValid(processType)) {
				condList.add("process_type = ?");
				paramList.add(processType);
			}
			String infoType = condMap.get("infoType");
			if (StringKit.isValid(infoType)) {
				condList.add("info_type = ?");
				paramList.add(infoType);
			}
			String infoId = condMap.get("infoId");
			if (StringKit.isValid(infoId)) {
				condList.add("info_id = ?");
				paramList.add(infoId);
			}
			String operatorType = condMap.get("operatorType");
			if (StringKit.isValid(operatorType)) {
				condList.add("operator_type = ?");
				paramList.add(operatorType);
			}
			String operatorId = condMap.get("operatorId");
			if (StringKit.isValid(operatorId)) {
				condList.add("operator_id = ?");
				paramList.add(operatorId);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by log_id asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, ProcessLogBean.class, db);
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
