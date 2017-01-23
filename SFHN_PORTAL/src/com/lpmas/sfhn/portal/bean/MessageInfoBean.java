package com.lpmas.sfhn.portal.bean;

import java.sql.Timestamp;

import com.lpmas.framework.annotation.FieldTag;

public class MessageInfoBean {
	@FieldTag(name = "消息ID")
	private int messageId = 0;
	@FieldTag(name = "消息类型")
	private int messageType = 0;
	@FieldTag(name = "信息类型")
	private int infoType = 0;
	@FieldTag(name = "信息ID")
	private int infoId = 0;
	@FieldTag(name = "发送机构类型")
	private int sendOrganizationType = 0;
	@FieldTag(name = "发送机构ID")
	private int sendOrganizationId = 0;
	@FieldTag(name = "接收机构类型")
	private int receiveOrganizationType = 0;
	@FieldTag(name = "接收机构ID")
	private int receiveOrganizationId = 0;
	@FieldTag(name = "消息内容")
	private String messageContent = "";
	@FieldTag(name = "是否已读")
	private int isRead = 0;
	@FieldTag(name = "状态")
	private int status = 0;
	@FieldTag(name = "创建时间")
	private Timestamp createTime = null;
	@FieldTag(name = "创建用户")
	private int createUser = 0;
	@FieldTag(name = "修改时间")
	private Timestamp modifyTime = null;
	@FieldTag(name = "修改用户")
	private int modifyUser = 0;
	@FieldTag(name = "备注")
	private String memo = "";

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getInfoType() {
		return infoType;
	}

	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getSendOrganizationType() {
		return sendOrganizationType;
	}

	public void setSendOrganizationType(int sendOrganizationType) {
		this.sendOrganizationType = sendOrganizationType;
	}

	public int getSendOrganizationId() {
		return sendOrganizationId;
	}

	public void setSendOrganizationId(int sendOrganizationId) {
		this.sendOrganizationId = sendOrganizationId;
	}

	public int getReceiveOrganizationType() {
		return receiveOrganizationType;
	}

	public void setReceiveOrganizationType(int receiveOrganizationType) {
		this.receiveOrganizationType = receiveOrganizationType;
	}

	public int getReceiveOrganizationId() {
		return receiveOrganizationId;
	}

	public void setReceiveOrganizationId(int receiveOrganizationId) {
		this.receiveOrganizationId = receiveOrganizationId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(int modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}