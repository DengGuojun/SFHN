package com.lpmas.sfhn.portal.config;

import com.lpmas.framework.util.PropertiesKit;

public class SfhnMongoConfig {

	public static String MONGO_IP = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_MONGO_PROP_FILE_NAME,
			"MONGO_IP");

	public static String MONGO_PORT = PropertiesKit
			.getBundleProperties(SfhnPortalConfig.SFHN_MONGO_PROP_FILE_NAME, "MONGO_PORT");

	public static String MONGO_USERNAME = PropertiesKit
			.getBundleProperties(SfhnPortalConfig.SFHN_MONGO_PROP_FILE_NAME, "MONGO_USERNAME");

	public static String MONGO_PASSWORD = PropertiesKit
			.getBundleProperties(SfhnPortalConfig.SFHN_MONGO_PROP_FILE_NAME, "MONGO_PASSWORD");

	public static String MONGO_DB_NAME = PropertiesKit
			.getBundleProperties(SfhnPortalConfig.SFHN_MONGO_PROP_FILE_NAME, "MONGO_DB_NAME");
}
