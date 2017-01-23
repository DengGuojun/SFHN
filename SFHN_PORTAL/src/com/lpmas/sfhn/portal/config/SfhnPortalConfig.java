package com.lpmas.sfhn.portal.config;

import com.lpmas.framework.config.Constants;

public class SfhnPortalConfig {
	
	public static final String APP_ID = "SFHN";

	public static final String SFHN_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/sfhn_config";
	public static final String YUN_CLASS_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/yun_class_invoke_config";
	public static final String SFHN_MONGO_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/sfhn_mongo_config";

	public static final Integer DEFAULT_PAGE_NUM = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 20;

	public static final String ERROR_PAGE = Constants.PAGE_PATH + "common/error_page.jsp";
	public static final String ADMIN_PAGE_PATH = Constants.PAGE_PATH + "sfhn/{0}/admin/";
	public static final String USER_PAGE_PATH = Constants.PAGE_PATH + "sfhn/{0}/user/";

}
