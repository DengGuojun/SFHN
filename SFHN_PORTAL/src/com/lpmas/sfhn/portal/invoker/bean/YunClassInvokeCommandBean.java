package com.lpmas.sfhn.portal.invoker.bean;

import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

public class YunClassInvokeCommandBean {

	private String service = "";
	private String method = "";
	private Object body = "";
	private Boolean isDebug = YunClassInvokeConfig.IS_DEBUG_MODE;

	public YunClassInvokeCommandBean() {
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Boolean getIsDebug() {
		return isDebug;
	}

}
