package com.lpmas.sfhn.portal.invoker.bean;

public class YunClassInvokeResponseBean {

	private int code = 0;
	private String message = "";
	private Object data = "";
	private String logId = "";

	public YunClassInvokeResponseBean() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getLogid() {
		return logId;
	}

	public void setLogid(String logid) {
		this.logId = logid;
	}

}
