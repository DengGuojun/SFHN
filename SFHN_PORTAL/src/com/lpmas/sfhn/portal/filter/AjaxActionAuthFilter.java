package com.lpmas.sfhn.portal.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.util.SetKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.framework.web.UrlKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.ow.passport.sso.config.SsoClientConfig;
import com.lpmas.sfhn.portal.config.AjaxActionAuthCommandConfig;

public class AjaxActionAuthFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(AjaxActionAuthFilter.class);
	private static Set<String> excludeURISet = null;
	private static Set<String> filterMethod = null;
	private static Set<String> continueMethod = null;
	private static String ssoLogonUrl = "";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		SsoClientHelper portalHelper = new SsoClientHelper(httpRequest, httpResponse, false);

		String requestURI = httpRequest.getRequestURI();
		String requestMethod = httpRequest.getMethod();
		String header = httpRequest.getHeader("x-requested-with");

		// 判断是否需要拦截的方法
		if ((header != null && header.equalsIgnoreCase("XMLHttpRequest"))
				&& (filterMethod.contains(requestMethod.toLowerCase())
						|| filterMethod.contains(requestMethod.toUpperCase()))) {
			// 判断是否免检查URI
			if (!excludeURISet.contains(requestURI)) {
				// 判断有无登陆
				if (!portalHelper.isUserLogin() || portalHelper.isSessionTimeout(SsoClientConfig.LOGON_TIME_OUT)) {
					// 无登陆且不是免检查URI
					String queryString = httpRequest.getQueryString();
					String paramString = getParamString(httpRequest.getParameterMap());
					String returnQuery = "";
					if (queryString != null) {
						returnQuery = queryString + "&" + paramString;
					} else {
						returnQuery = paramString;
					}
					String targetUrl = httpRequest.getRequestURL() + "?" + returnQuery;
					targetUrl = ssoLogonUrl + UrlKit.urlEncode(targetUrl, "utf-8");
					// 组装RETURNMESSAGE返回
					ReturnMessageBean returnMessage = new ReturnMessageBean();
					returnMessage.setCode(HttpServletResponse.SC_FORBIDDEN);
					returnMessage.setCommand(AjaxActionAuthCommandConfig.COMMAMD_LOGON);
					returnMessage.setMessage(targetUrl);
					// 返回
					HttpResponseKit.printJson(httpRequest, httpResponse, returnMessage, "");
					return;
				}
			}
			// 继续往下走的方法不RETURN
			if (continueMethod.contains(requestMethod.toLowerCase())
					|| continueMethod.contains(httpRequest.getMethod().toUpperCase())) {
				chain.doFilter(request, response);
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 单点登录URL
		ssoLogonUrl = config.getInitParameter("SsoLogonUrl");
		log.info("get SsoLogonUrl:" + ssoLogonUrl);

		// 获取豁免URL
		String excludeUriStr = config.getInitParameter("excludeURI");
		log.info("get excludeURI:" + excludeUriStr);
		excludeURISet = SetKit.string2Set(excludeUriStr, ";");

		// 获取需要拦截的方法
		String filterMethodStr = config.getInitParameter("filterMethod");
		log.info("get filterMethod:" + filterMethodStr);
		filterMethod = SetKit.string2Set(filterMethodStr, ";");

		// 继续往下跑FILTER的方法
		String continueMethodStr = config.getInitParameter("continueMethod");
		log.info("get continueMethod:" + continueMethodStr);
		continueMethod = SetKit.string2Set(continueMethodStr, ";");
	}

	private String getParamString(Map<String, String[]> paramMap) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String[]> entry : paramMap.entrySet()) {
			for (String value : entry.getValue()) {
				sb.append(entry.getKey() + "=" + value + "&");
			}
		}
		String result = sb.toString();
		if (result.endsWith("&")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

}
