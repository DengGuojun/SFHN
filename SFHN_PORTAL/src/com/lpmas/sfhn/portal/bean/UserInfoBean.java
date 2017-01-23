package com.lpmas.sfhn.portal.bean;

public class UserInfoBean {

	private int userId = 0;
	private int userType = 0;
	private String userName = "";
	private String userMobile = "";

	public UserInfoBean() {
	}

	public UserInfoBean(int userId, int userType, String userName, String userMobile) {
		this.userId = userId;
		this.userType = userType;
		this.userName = userName;
		this.userMobile = userMobile;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

}
