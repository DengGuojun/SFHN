package com.lpmas.sfhn.portal.config;

import com.lpmas.framework.util.PropertiesKit;

public class SfhnDBConfig {

	public static String DB_LINK_SFHN_W = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME,
			"DB_LINK_SFHN_W");

	public static String DB_LINK_SFHN_R = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME,
			"DB_LINK_SFHN_R");

}
