package com.lpmas.sfhn.console.config;

import com.lpmas.framework.util.PropertiesKit;

public class SfhnDBConfig {

	public static String DB_LINK_SFHN_W = PropertiesKit.getBundleProperties(SfhnConsoleConfig.SFHN_PROP_FILE_NAME,
			"DB_LINK_SFHN_W");

	public static String DB_LINK_SFHN_R = PropertiesKit.getBundleProperties(SfhnConsoleConfig.SFHN_PROP_FILE_NAME,
			"DB_LINK_SFHN_R");

}
