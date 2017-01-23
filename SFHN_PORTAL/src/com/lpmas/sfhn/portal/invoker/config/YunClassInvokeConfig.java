package com.lpmas.sfhn.portal.invoker.config;

import java.util.HashSet;
import java.util.Set;

import com.lpmas.framework.util.PropertiesKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;

public class YunClassInvokeConfig {

	// 权鉴信息
	public static String YUN_FLAG = "AMSTV";
	public static String YUN_VERSION = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"YUN_SERVER_VERSION") + "."
			+ PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME, "YUN_APPID_VERSION");
	public static String YUN_APPID = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"YUN_APPID");
	public static String YUN_HOST = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"YUN_HOST");
	public static String YUN_SECRETKEY = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"YUN_SECRETKEY");
	public static String DEBUG_YUN_APPID = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"DEBUG_YUN_APPID");
	public static String DEBUG_YUN_HOST = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
			"DEBUG_YUN_HOST");
	public static String DEBUG_YUN_SECRETKEY = PropertiesKit
			.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME, "DEBUG_YUN_SECRETKEY");

	// 要调用的应用服务名
	public static final String YUN_SERVICE_ADD_CLASS = "course.classroom.add";
	public static final String YUN_SERVICE_ADD_USER_TO_CLASS = "course.classroom.member.add";
	public static final String YUN_SERVICE_DELETE_USER_TO_CLASS = "course.classroom.member.delete";
	public static final String YUN_SERVICE_CLASSROOM_LIVE_STATUS = "course.live.classroom.status";
	public static final String YUN_SERVICE_BATCH_LEARNING_TIME = "course.statistics.batchuser.learning";
	public static final String YUN_SERVICE_ADD_TEACHER = "course.classroom.teacher.async";
	public static Set<String> YUN_SERVICE_SET = new HashSet<String>();

	// 调用时的配置信息
	public static int MAX_ATTEMPT_COUNT = 1;

	// 是否DEBUG模式
	public static boolean IS_DEBUG_MODE = true;

	// 默认是
	static {
		initIsDebugMode();
		initMaxAttemptCount();
		initYunServiceSet();
	}

	private static void initMaxAttemptCount() {
		try {
			MAX_ATTEMPT_COUNT = Integer.valueOf(
					PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME, "MAX_ATTEMPT_COUNT"));
		} catch (Exception e) {
			MAX_ATTEMPT_COUNT = 1;
		}
	}

	private static void initIsDebugMode() {
		String idDebugModeStr = PropertiesKit.getBundleProperties(SfhnPortalConfig.YUN_CLASS_PROP_FILE_NAME,
				"IS_DEBUG_MODE");
		if (StringKit.isValid(idDebugModeStr)) {
			idDebugModeStr.trim().toLowerCase();
			if (idDebugModeStr.equals("true")) {
				IS_DEBUG_MODE = true;
			} else if (idDebugModeStr.equals("false")) {
				IS_DEBUG_MODE = false;
			}
		}
	}

	private static void initYunServiceSet() {
		YUN_SERVICE_SET = new HashSet<String>();
		YUN_SERVICE_SET.add(YUN_SERVICE_ADD_CLASS);
		YUN_SERVICE_SET.add(YUN_SERVICE_ADD_USER_TO_CLASS);
	}

}
