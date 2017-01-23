package com.lpmas.sfhn.portal.invoker.business;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YunClassInvokeExecutor {

	private static int POOL_SIZE = 5;
	private static ExecutorService executor = Executors.newScheduledThreadPool(POOL_SIZE);
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";

	private YunClassInvokeExecutor() {

	}

	public static void attachAsync(Runnable task) {
		executor.submit(task);
	}

}
