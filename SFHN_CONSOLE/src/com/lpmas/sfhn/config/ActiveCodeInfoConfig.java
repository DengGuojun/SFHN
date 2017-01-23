package com.lpmas.sfhn.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class ActiveCodeInfoConfig {

	public static final String USAGE_STATUS_UNUSE = "UNUSE";
	public static final String USAGE_STATUS_USED = "USED";
	public static final String USAGE_STATUS_ACTIVATED = "ACTIVATED";
	public static List<StatusBean<String, String>> USAGE_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> USAGE_STATUS_MAP = new HashMap<String, String>();

	public static final int USER_TYPE_FARMER = 1;// 农民
	public static final int USER_TYPE_TEACHER = 2;// 教师
	public static List<StatusBean<Integer, String>> USER_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> USER_TYPE_MAP = new HashMap<Integer, String>();

	static {
		initUsageStatusList();
		initUsageStatusMap();

		initUserTypeList();
		initUserTypeMap();
	}

	private static void initUsageStatusList() {
		USAGE_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		USAGE_STATUS_LIST.add(new StatusBean<String, String>(USAGE_STATUS_UNUSE, "未使用"));
		USAGE_STATUS_LIST.add(new StatusBean<String, String>(USAGE_STATUS_USED, "已使用"));
		USAGE_STATUS_LIST.add(new StatusBean<String, String>(USAGE_STATUS_ACTIVATED, "已激活"));
	}

	private static void initUsageStatusMap() {
		USAGE_STATUS_MAP = StatusKit.toMap(USAGE_STATUS_LIST);
	}

	private static void initUserTypeList() {
		USER_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		USER_TYPE_LIST.add(new StatusBean<Integer, String>(USER_TYPE_FARMER, "农民"));
		USER_TYPE_LIST.add(new StatusBean<Integer, String>(USER_TYPE_TEACHER, "教师"));
	}

	private static void initUserTypeMap() {
		USER_TYPE_MAP = StatusKit.toMap(USER_TYPE_LIST);
	}
}
