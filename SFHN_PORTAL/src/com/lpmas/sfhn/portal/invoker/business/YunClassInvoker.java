package com.lpmas.sfhn.portal.invoker.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.transfer.HttpClientKit;
import com.lpmas.framework.transfer.HttpClientResultBean;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeCommandBean;
import com.lpmas.sfhn.portal.invoker.bean.YunClassInvokeResponseBean;
import com.lpmas.sfhn.portal.invoker.config.YunClassInvokeConfig;
import com.lpmas.sfhn.portal.invoker.config.YunClassResponseStatusConfig;

public class YunClassInvoker implements Runnable {

	private YunClassInvokeCommandBean command;
	private YunClassInvokCallBack process;
	private HttpClientKit httpClientKit;
	private static Logger logger = LoggerFactory.getLogger(YunClassInvoker.class);

	public YunClassInvoker(YunClassInvokeCommandBean command, YunClassInvokCallBack process) {
		if (command == null || process == null) {
			throw new IllegalArgumentException();
		} else {
			this.command = command;
			this.process = process;
			httpClientKit = new HttpClientKit();
		}
	}

	@Override
	public void run() {
		// 获取请求URL
		StringBuffer url = YunClassInvokeHelper.getUrl(command.getMethod(), command.getService(), command.getIsDebug());
		// 尝试推送消息
		int count = 0;
		for (count = 0; count < YunClassInvokeConfig.MAX_ATTEMPT_COUNT; count++) {
			logger.debug("第" + (count + 1) + "次尝试请求云课堂URL:" + url.toString());
			logger.debug(JsonKit.toJson(command.getBody()));
			try {
				HttpClientResultBean httpResult = null;
				if (command.getMethod().equals(YunClassInvokeExecutor.HTTP_POST)) {
					String content = JsonKit.toJson(command.getBody());
					httpResult = httpClientKit.postContent(url.toString(), content, Constants.ENCODING_UNICODE);
				}else {
					logger.error("HTTP 方法不正确，只能是POST!");
					break;
				}
				if (httpResult.getResult()) {
					// 请求成功
					// 处理结果
					YunClassInvokeResponseBean responseBean = JsonKit.toBean(httpResult.getResultContent(),
							YunClassInvokeResponseBean.class);
					if (responseBean.getCode() == YunClassResponseStatusConfig.STATUS_SUCCESS) {
						// 云课堂那边已经请求成功了
						// 成功就要取出数据
						if (process.process(JsonKit.toJson(responseBean.getData()))) {
							logger.debug("第" + (count + 1) + "次请求云课堂URL:" + url.toString() + "结果处理成功");
							break;
						} else {
							logger.debug("第" + (count + 1) + "次请求云课堂URL:" + url.toString() + "结果处理失败");
						}
					} else {
						// 云课堂反馈请求失败
						logger.debug("第" + (count + 1) + "次请求云课堂URL:" + url.toString() + "失败,失败代码:"
								+ responseBean.getCode() + ",消息:" + responseBean.getMessage()+",LogId:"+responseBean.getLogid());
					}
				} else {
					logger.debug("第" + (count + 1) + "次请求云课堂URL:" + url.toString() + "失败");
				}
			} catch (Exception e) {
				logger.debug("第" + (count + 1) + "次请求云课堂URL:" + url.toString() + "失败,异常:" + e.getMessage());
			}
		}
		// 超出次数记ERROR
		if (count >= YunClassInvokeConfig.MAX_ATTEMPT_COUNT) {
			logger.error(command.getService() + "请求失败超出最大请求次数" + YunClassInvokeConfig.MAX_ATTEMPT_COUNT + "次");
		}

	}

}
