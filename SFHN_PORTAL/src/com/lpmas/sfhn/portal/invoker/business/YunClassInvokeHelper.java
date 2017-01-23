package com.lpmas.sfhn.portal.invoker.business;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.lpmas.framework.crypto.BASE64;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;

/***
 * 签名规则： 1：拼接CONTENT 2：用密钥SHA1 3：BASE64
 */
public class YunClassInvokeHelper {

	/**
	 * 1：拼接CONTENT content = {flag} + "\n"+ "appid=" +{appid}+ "\n" + "method="
	 * +{method}+ "\n" + "service=" +{service}+ "\n" + "time=" +{time}+ "\n"
	 * "version=" +{version}+ "\n"
	 * 
	 * @return
	 */
	private static String getContentStr(String method, String service, String timeStamp, boolean isDebug) {
		StringBuffer sb = new StringBuffer();
		sb.append(YunClassInvokeConfig.YUN_FLAG + "\n");
		if (isDebug) {
			// DEBUG模式
			sb.append("appid=" + YunClassInvokeConfig.DEBUG_YUN_APPID + "\n");
		} else {
			// 正式环境
			sb.append("appid=" + YunClassInvokeConfig.YUN_APPID + "\n");
		}
		sb.append("method=" + method + "\n");
		sb.append("service=" + service + "\n");
		sb.append("time=" + timeStamp + "\n");
		sb.append("version=" + YunClassInvokeConfig.YUN_VERSION + "\n");
		return sb.toString();
	}

	/**
	 * 用密钥SHA1 并BASE64
	 */
	public static String getSign(String method, String service, boolean isDebug) {
		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		String content = getContentStr(method, service, timeStamp, isDebug);
		byte[] shaResult = null;
		if (isDebug) {
			// DEBUG模式
			shaResult = getSha1(YunClassInvokeConfig.DEBUG_YUN_SECRETKEY, content);
		} else {
			// 正式环境
			shaResult = getSha1(YunClassInvokeConfig.YUN_SECRETKEY, content);
		}
		return YunClassInvokeConfig.YUN_FLAG + ":" + timeStamp + ":"
				+ URLEncoder.encode(BASE64.encodeBase64(shaResult));

	}

	public static byte[] getSha1(String key, String data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			return rawHmac;
		} catch (Exception e) {
			return null;
		}
	}

	public static StringBuffer getUrl(String method, String service, boolean isDebug) {
		// 获得SIGN
		String sign = YunClassInvokeHelper.getSign(method, service, isDebug);
		// 拼装URL
		StringBuffer url = new StringBuffer();
		if (isDebug) {
			url.append(YunClassInvokeConfig.DEBUG_YUN_HOST);
			url.append(service + "/");
			url.append(YunClassInvokeConfig.YUN_VERSION + "/");
			url.append(YunClassInvokeConfig.DEBUG_YUN_APPID + "/");
		} else {
			url.append(YunClassInvokeConfig.YUN_HOST);
			url.append(service + "/");
			url.append(YunClassInvokeConfig.YUN_VERSION + "/");
			url.append(YunClassInvokeConfig.YUN_APPID + "/");
		}
		url.append(sign);
		return url;
	}
}
