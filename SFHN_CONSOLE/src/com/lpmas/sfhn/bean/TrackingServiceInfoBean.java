package com.lpmas.sfhn.bean;

import java.sql.Timestamp;
import com.lpmas.framework.annotation.FieldTag;

public class TrackingServiceInfoBean {
	@FieldTag(name = "服务ID")
	private int serviceId = 0;
	@FieldTag(name = "服务类型")
	private String serviceType = "";
	@FieldTag(name = "跟踪ID")
	private int trackingId = 0;
	@FieldTag(name = "跟踪标题")
	private String trackingTitle = "";
	@FieldTag(name = "服务内容")
	private String trackingContent = "";
	@FieldTag(name = "服务反馈")
	private String trackingFeedback = "";
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

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public int getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(int trackingId) {
		this.trackingId = trackingId;
	}

	public String getTrackingTitle() {
		return trackingTitle;
	}

	public void setTrackingTitle(String trackingTitle) {
		this.trackingTitle = trackingTitle;
	}

	public String getTrackingContent() {
		return trackingContent;
	}

	public void setTrackingContent(String trackingContent) {
		this.trackingContent = trackingContent;
	}

	public String getTrackingFeedback() {
		return trackingFeedback;
	}

	public void setTrackingFeedback(String trackingFeedback) {
		this.trackingFeedback = trackingFeedback;
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
}