package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;
import com.lpmas.sfhn.portal.dao.MessageInfoDao;

public class MessageInfoBusiness {
	public int addMessageInfo(MessageInfoBean bean) {
		MessageInfoDao dao = new MessageInfoDao();
		return dao.insertMessageInfo(bean);
	}

	public int updateMessageInfo(MessageInfoBean bean) {
		MessageInfoDao dao = new MessageInfoDao();
		return dao.updateMessageInfo(bean);
	}

	public MessageInfoBean getMessageInfoByKey(int messageId) {
		MessageInfoDao dao = new MessageInfoDao();
		return dao.getMessageInfoByKey(messageId);
	}

	public PageResultBean<MessageInfoBean> getMessageInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		MessageInfoDao dao = new MessageInfoDao();
		return dao.getMessageInfoPageListByMap(condMap, pageBean);
	}

	public List<MessageInfoBean> getMessageInfoListByMap(HashMap<String, String> condMap) {
		MessageInfoDao dao = new MessageInfoDao();
		return dao.getMessageInfoListByMap(condMap);
	}

	public int getUnreadMessageCount(OrganizationUserBean orgUserBean) {
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("receiveOrganizationType", String.valueOf(orgUserBean.getInfoType()));
		condMap.put("receiveOrganizationId", String.valueOf(orgUserBean.getOrganizationId()));
		condMap.put("isRead", String.valueOf(Constants.STATUS_NOT_VALID));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		MessageInfoDao dao = new MessageInfoDao();
		List<MessageInfoBean> messageInfoList = dao.getMessageInfoListByMap(condMap);
		return messageInfoList.size();
	}

}