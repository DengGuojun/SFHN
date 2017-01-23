package com.lpmas.sfhn.portal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.PropertiesKit;
import com.lpmas.framework.util.StatusKit;
import com.lpmas.framework.util.StringKit;

public class TrainingClassUserConfig {

	private static Logger logger = LoggerFactory.getLogger(TrainingClassUserConfig.class);
	public static final String USER_STATUS_WAIT_TRAINING_APPROVE = "WAIT_TRAINING_APPROVE";
	public static final String USER_STATUS_WAIT_REGION_GOVERNMENT_APPROVE = "WAIT_REGION_GOVERNMENT_APPROVE";
	public static final String USER_STATUS_APPROVE = "APPROVE";
	public static final String USER_STATUS_NOT_APPROVE = "NOT_APPROVE";
	public static List<StatusBean<String, String>> USER_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> USER_STATUS_MAP = new HashMap<String, String>();

	public static boolean ENABLE_ORGANIZATION_TRAINING = true;
	public static int MAX_USER_APPLY_CLASS_COUNT = 1;
	public static int YEARS_UNABLE_TO_JOIN_CLASS = 3;

	// 是否报班
	public static final int SELECT_FALSE = 0;// 否
	public static final int SELECT_TRUE = 1;// 是

	// 是否认定
	public static final String HAS_AUTH = "已认定";
	public static final String HAS_NOT_AUTH = "未认定";

	public static List<StatusBean<Integer, String>> SELECT_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> SELECT_MAP = new HashMap<Integer, String>();
	static {
		initIsDebugMode();
		initUserStatusList();
		initUserStatusMap();
		initMaxUserApplyClassCount();
		initYearsUnableToJoinClass();
		initSelectList();
		initSelectMap();
	}

	private static void initIsDebugMode() {
		String idDebugModeStr = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME,
				"ENABLE_ORGANIZATION_TRAINING");
		if (StringKit.isValid(idDebugModeStr)) {
			idDebugModeStr.trim().toLowerCase();
			if (idDebugModeStr.equals("true")) {
				ENABLE_ORGANIZATION_TRAINING = true;
			} else if (idDebugModeStr.equals("false")) {
				ENABLE_ORGANIZATION_TRAINING = false;
			}
		}
	}

	private static void initUserStatusList() {
		USER_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		USER_STATUS_LIST.add(new StatusBean<String, String>(USER_STATUS_WAIT_TRAINING_APPROVE, "待培训机构审批"));
		USER_STATUS_LIST.add(new StatusBean<String, String>(USER_STATUS_WAIT_REGION_GOVERNMENT_APPROVE, "待县政府审批"));
		USER_STATUS_LIST.add(new StatusBean<String, String>(USER_STATUS_APPROVE, "审批通过"));
		USER_STATUS_LIST.add(new StatusBean<String, String>(USER_STATUS_NOT_APPROVE, "审批不通过"));
	}

	private static void initUserStatusMap() {
		USER_STATUS_MAP = StatusKit.toMap(USER_STATUS_LIST);
	}

	private static void initMaxUserApplyClassCount() {
		String maxUserApplyCountStr = PropertiesKit
				.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME, "MAX_USER_APPLY_CLASS_COUNT").trim();
		try {
			MAX_USER_APPLY_CLASS_COUNT = Integer.valueOf(maxUserApplyCountStr);
		} catch (Exception e) {
			MAX_USER_APPLY_CLASS_COUNT = 1;
			logger.error("每个农民最大可报班级数量配置错误，使用默认配置1");
		}
	}

	private static void initYearsUnableToJoinClass() {
		String yearsUnableToJoinClassStr = PropertiesKit
				.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME, "YEARS_UNABLE_TO_JOIN_CLASS").trim();
		try {
			YEARS_UNABLE_TO_JOIN_CLASS = Integer.valueOf(yearsUnableToJoinClassStr);
		} catch (Exception e) {
			YEARS_UNABLE_TO_JOIN_CLASS = 3;
			logger.error("N年内不可报班配置错误，使用默认配置3");
		}
	}

	private static void initSelectList() {
		SELECT_LIST = new ArrayList<StatusBean<Integer, String>>();
		SELECT_LIST.add(new StatusBean<Integer, String>(SELECT_TRUE, "已报班"));
		SELECT_LIST.add(new StatusBean<Integer, String>(SELECT_FALSE, "未报班"));
	}

	private static void initSelectMap() {
		SELECT_MAP = StatusKit.toMap(SELECT_LIST);
	}

}
