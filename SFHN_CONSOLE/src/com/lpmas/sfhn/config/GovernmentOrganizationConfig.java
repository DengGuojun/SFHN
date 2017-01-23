package com.lpmas.sfhn.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class GovernmentOrganizationConfig {

	// 机构级别
	public static final int ORGANIZATION_LEVEL_PROVINCE = 1;// 省
	public static final int ORGANIZATION_LEVEL_CITY = 2;// 市
	public static final int ORGANIZATION_LEVEL_REGION = 3;// 区
	public static List<StatusBean<Integer, String>> ORGANIZATION_LEVEL_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> ORGANIZATION_LEVEL_MAP = new HashMap<Integer, String>();

	static {
		initOrganizationLevelList();
		initOrganizationLevelMap();
	}

	private static void initOrganizationLevelList() {
		ORGANIZATION_LEVEL_LIST = new ArrayList<StatusBean<Integer, String>>();
		ORGANIZATION_LEVEL_LIST.add(new StatusBean<Integer, String>(ORGANIZATION_LEVEL_PROVINCE, "省级"));
		ORGANIZATION_LEVEL_LIST.add(new StatusBean<Integer, String>(ORGANIZATION_LEVEL_CITY, "市级"));
		ORGANIZATION_LEVEL_LIST.add(new StatusBean<Integer, String>(ORGANIZATION_LEVEL_REGION, "区级"));
	}

	private static void initOrganizationLevelMap() {
		ORGANIZATION_LEVEL_MAP = StatusKit.toMap(ORGANIZATION_LEVEL_LIST);
	}
}
