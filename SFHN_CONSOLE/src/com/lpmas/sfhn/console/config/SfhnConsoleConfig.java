package com.lpmas.sfhn.console.config;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.PropertiesKit;

public class SfhnConsoleConfig {

	public static final String APP_ID = "SFHN";

	public static final String SFHN_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/sfhn_config";
	public static final String YUN_CLASS_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/yun_class_invoke_config";
	public static final String SFHN_MONGO_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/sfhn_mongo_config";

	public static final Integer DEFAULT_PAGE_NUM = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 20;

	public static final String ERROR_PAGE = Constants.PAGE_PATH + "common/error_page.jsp";
	public static final String PAGE_PATH = Constants.PAGE_PATH + "sfhn/";

	// 激活码长度的配置
	public static int ACTIVE_CODE_LENGTH = 8;

	static {
		initActiveCodeLength();
	}

	private static void initActiveCodeLength() {
		try {
			ACTIVE_CODE_LENGTH = Integer.valueOf(
					PropertiesKit.getBundleProperties(SfhnConsoleConfig.SFHN_PROP_FILE_NAME, "ACTIVE_CODE_LENGTH"));
		} catch (Exception e) {
			ACTIVE_CODE_LENGTH = 8;
		}
	}

}
