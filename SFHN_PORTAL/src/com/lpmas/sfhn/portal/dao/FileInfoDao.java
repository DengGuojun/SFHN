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
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class FileInfoDao {
	private static Logger log = LoggerFactory.getLogger(FileInfoDao.class);

	public int insertFileInfo(FileInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into file_info ( file_type, info_type, info_id_1, info_id_2, info_id_3, file_name, file_format, file_path, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getFileType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId1());
			ps.setInt(4, bean.getInfoId2());
			ps.setInt(5, bean.getInfoId3());
			ps.setString(6, bean.getFileName());
			ps.setString(7, bean.getFileFormat());
			ps.setString(8, bean.getFilePath());
			ps.setInt(9, bean.getStatus());
			ps.setInt(10, bean.getCreateUser());
			ps.setString(11, bean.getMemo());

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

	public int updateFileInfo(FileInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update file_info set file_type = ?, info_type = ?, info_id_1 = ?, info_id_2 = ?, info_id_3 = ?, file_name = ?, file_format = ?, file_path = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where file_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getFileType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId1());
			ps.setInt(4, bean.getInfoId2());
			ps.setInt(5, bean.getInfoId3());
			ps.setString(6, bean.getFileName());
			ps.setString(7, bean.getFileFormat());
			ps.setString(8, bean.getFilePath());
			ps.setInt(9, bean.getStatus());
			ps.setInt(10, bean.getModifyUser());
			ps.setString(11, bean.getMemo());

			ps.setInt(12, bean.getFileId());

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

	public FileInfoBean getFileInfoByKey(int fileId) {
		FileInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from file_info where file_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, fileId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new FileInfoBean();
				bean = BeanKit.resultSet2Bean(rs, FileInfoBean.class);
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

	public PageResultBean<FileInfoBean> getFileInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<FileInfoBean> result = new PageResultBean<FileInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from file_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String fileType = condMap.get("fileType");
			if (StringKit.isValid(fileType)) {
				condList.add("file_type = ?");
				paramList.add(fileType);
			}
			String infoType = condMap.get("infoType");
			if (StringKit.isValid(infoType)) {
				condList.add("info_type = ?");
				paramList.add(infoType);
			}
			String infoId1 = condMap.get("infoId1");
			if (StringKit.isValid(infoId1)) {
				condList.add("info_id_1 = ?");
				paramList.add(infoId1);
			}
			String infoId2 = condMap.get("infoId2");
			if (StringKit.isValid(infoId2)) {
				condList.add("info_id_2 = ?");
				paramList.add(infoId2);
			}
			String infoId3 = condMap.get("infoId3");
			if (StringKit.isValid(infoId3)) {
				condList.add("info_id_3 = ?");
				paramList.add(infoId3);
			}
			String filePath = condMap.get("filePath");
			if (StringKit.isValid(filePath)) {
				condList.add("file_path = ?");
				paramList.add(filePath);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			
			String orderQuery = "order by file_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, FileInfoBean.class, pageBean, db);
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

	public List<FileInfoBean> getFileInfoListByMap(HashMap<String, String> condMap) {
		List<FileInfoBean> result = new ArrayList<FileInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from file_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String fileType = condMap.get("fileType");
			if (StringKit.isValid(fileType)) {
				condList.add("file_type = ?");
				paramList.add(fileType);
			}
			String infoType = condMap.get("infoType");
			if (StringKit.isValid(infoType)) {
				condList.add("info_type = ?");
				paramList.add(infoType);
			}
			String infoId1 = condMap.get("infoId1");
			if (StringKit.isValid(infoId1)) {
				condList.add("info_id_1 = ?");
				paramList.add(infoId1);
			}
			String infoId2 = condMap.get("infoId2");
			if (StringKit.isValid(infoId2)) {
				condList.add("info_id_2 = ?");
				paramList.add(infoId2);
			}
			String infoId3 = condMap.get("infoId3");
			if (StringKit.isValid(infoId3)) {
				condList.add("info_id_3 = ?");
				paramList.add(infoId3);
			}
			String filePath = condMap.get("filePath");
			if (StringKit.isValid(filePath)) {
				condList.add("file_path = ?");
				paramList.add(filePath);
			}
			String fileName = condMap.get("fileName");
			if (StringKit.isValid(fileName)) {
				condList.add("file_name = ?");
				paramList.add(fileName);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by file_id asc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, FileInfoBean.class, db);
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
