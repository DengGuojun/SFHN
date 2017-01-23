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
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class MessageInfoDao {
	private static Logger log = LoggerFactory.getLogger(MessageInfoDao.class);

	public int insertMessageInfo(MessageInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into message_info ( message_type, info_type, info_id, send_organization_type, send_organization_id, receive_organization_type, receive_organization_id, message_content, is_read, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getMessageType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId());
			ps.setInt(4, bean.getSendOrganizationType());
			ps.setInt(5, bean.getSendOrganizationId());
			ps.setInt(6, bean.getReceiveOrganizationType());
			ps.setInt(7, bean.getReceiveOrganizationId());
			ps.setString(8, bean.getMessageContent());
			ps.setInt(9, bean.getIsRead());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getCreateUser());
			ps.setString(12, bean.getMemo());

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

	public int updateMessageInfo(MessageInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update message_info set message_type = ?, info_type = ?, info_id = ?, send_organization_type = ?, send_organization_id = ?, receive_organization_type = ?, receive_organization_id = ?, message_content = ?, is_read = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where message_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getMessageType());
			ps.setInt(2, bean.getInfoType());
			ps.setInt(3, bean.getInfoId());
			ps.setInt(4, bean.getSendOrganizationType());
			ps.setInt(5, bean.getSendOrganizationId());
			ps.setInt(6, bean.getReceiveOrganizationType());
			ps.setInt(7, bean.getReceiveOrganizationId());
			ps.setString(8, bean.getMessageContent());
			ps.setInt(9, bean.getIsRead());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getModifyUser());
			ps.setString(12, bean.getMemo());

			ps.setInt(13, bean.getMessageId());

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

	public MessageInfoBean getMessageInfoByKey(int messageId) {
		MessageInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from message_info where message_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, messageId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MessageInfoBean();
				bean = BeanKit.resultSet2Bean(rs, MessageInfoBean.class);
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

	public PageResultBean<MessageInfoBean> getMessageInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<MessageInfoBean> result = new PageResultBean<MessageInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from message_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String messageType = condMap.get("messageType");
			if (StringKit.isValid(messageType)) {
				condList.add("message_type = ?");
				paramList.add(messageType);
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
			String sendOrganizationType = condMap.get("sendOrganizationType");
			if (StringKit.isValid(sendOrganizationType)) {
				condList.add("send_organization_type = ?");
				paramList.add(sendOrganizationType);
			}
			String sendOrganizationId = condMap.get("sendOrganizationId");
			if (StringKit.isValid(sendOrganizationId)) {
				condList.add("send_organization_id = ?");
				paramList.add(sendOrganizationId);
			}
			String receiveOrganizationType = condMap.get("receiveOrganizationType");
			if (StringKit.isValid(receiveOrganizationType)) {
				condList.add("receive_organization_type = ?");
				paramList.add(receiveOrganizationType);
			}
			String receiveOrganizationId = condMap.get("receiveOrganizationId");
			if (StringKit.isValid(receiveOrganizationId)) {
				condList.add("receive_organization_id = ?");
				paramList.add(receiveOrganizationId);
			}
			String isRead = condMap.get("isRead");
			if (StringKit.isValid(isRead)) {
				condList.add("is_read = ?");
				paramList.add(isRead);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String orderQuery = "order by message_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor
					.getPageResult(sql, orderQuery, condList, paramList, MessageInfoBean.class, pageBean, db);
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
	
	public List<MessageInfoBean> getMessageInfoListByMap(HashMap<String, String> condMap) {
		List<MessageInfoBean> result = new ArrayList<MessageInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from message_info";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String messageType = condMap.get("messageType");
			if (StringKit.isValid(messageType)) {
				condList.add("message_type = ?");
				paramList.add(messageType);
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
			String sendOrganizationType = condMap.get("sendOrganizationType");
			if (StringKit.isValid(sendOrganizationType)) {
				condList.add("send_organization_type = ?");
				paramList.add(sendOrganizationType);
			}
			String sendOrganizationId = condMap.get("sendOrganizationId");
			if (StringKit.isValid(sendOrganizationId)) {
				condList.add("send_organization_id = ?");
				paramList.add(sendOrganizationId);
			}
			String receiveOrganizationType = condMap.get("receiveOrganizationType");
			if (StringKit.isValid(receiveOrganizationType)) {
				condList.add("receive_organization_type = ?");
				paramList.add(receiveOrganizationType);
			}
			String receiveOrganizationId = condMap.get("receiveOrganizationId");
			if (StringKit.isValid(receiveOrganizationId)) {
				condList.add("receive_organization_id = ?");
				paramList.add(receiveOrganizationId);
			}
			String isRead = condMap.get("isRead");
			if (StringKit.isValid(isRead)) {
				condList.add("is_read = ?");
				paramList.add(isRead);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by create_time desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, MessageInfoBean.class, db);
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
