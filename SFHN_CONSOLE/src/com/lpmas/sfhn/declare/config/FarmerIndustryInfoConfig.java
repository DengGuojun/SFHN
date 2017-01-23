package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class FarmerIndustryInfoConfig {
	// 地区类型
	public static final int AREA_TYPE_PLAIN = 1;
	public static final int AREA_TYPE_HILL = 2;
	public static final int AREA_TYPE_MOUNTAIN = 3;
	public static List<StatusBean<Integer, String>> AREA_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> AREA_TYPE_MAP = new HashMap<Integer, String>();

	// 经济区域类型
	public static final int ECONOMIC_AREA_TYPE_AGRICULTURE = 1;
	public static final int ECONOMIC_AREA_TYPE_FORESTRY = 2;
	public static final int ECONOMIC_AREA_TYPE_HERDING = 3;
	public static final int ECONOMIC_AREA_TYPE_FISHERY = 4;
	public static final int ECONOMIC_AREA_TYPE_OTHER = 5;
	public static List<StatusBean<Integer, String>> ECONOMIC_AREA_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> ECONOMIC_AREA_TYPE_MAP = new HashMap<Integer, String>();

	// 家庭农场类型
	public static final String FAMILY_FARM_TYPE_INDIVIDUAL_BUSINESS = "INDIVIDUAL_BUSINESS";
	public static final String FAMILY_FARM_TYPE_INDIVIDUAL_PROPRIETORSHIP = "INDIVIDUAL_PROPRIETORSHIP";
	public static final String FAMILY_FARM_TYPE_LIMITED_LIABILITY_COMPANY = "LIMITED_LIABILITY_COMPANY";
	public static List<StatusBean<String, String>> FAMILY_FARM_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> FAMILY_FARM_TYPE_MAP = new LinkedHashMap<String, String>();

	// 示范型农场级别
	public static final String FARM_LEVEL_PROVINCIAL = "PROVINCIAL";
	public static final String FARM_LEVEL_CITY = "CITY";
	public static final String FARM_LEVEL_COUNTY = "COUNTY";
	public static List<StatusBean<String, String>> FARM_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> FARM_LEVEL_MAP = new LinkedHashMap<String, String>();
	static {
		initAreaTypeList();
		initAreaTypeMap();

		initEconomicAreaTypeList();
		initEconomicAreaTypeMap();

		initFamilyFarmTypeList();
		initFamilyFarmTypeMAP();

		initFarmLevelList();
		initFarmLevelMAP();
	}

	public static void initAreaTypeList() {
		AREA_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		AREA_TYPE_LIST.add(new StatusBean<Integer, String>(AREA_TYPE_PLAIN, "平原"));
		AREA_TYPE_LIST.add(new StatusBean<Integer, String>(AREA_TYPE_HILL, "丘陵"));
		AREA_TYPE_LIST.add(new StatusBean<Integer, String>(AREA_TYPE_MOUNTAIN, "山区"));
	}

	public static void initAreaTypeMap() {
		AREA_TYPE_MAP = StatusKit.toMap(AREA_TYPE_LIST);
	}

	public static void initEconomicAreaTypeList() {
		ECONOMIC_AREA_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		ECONOMIC_AREA_TYPE_LIST.add(new StatusBean<Integer, String>(ECONOMIC_AREA_TYPE_AGRICULTURE, "农业"));
		ECONOMIC_AREA_TYPE_LIST.add(new StatusBean<Integer, String>(ECONOMIC_AREA_TYPE_FORESTRY, "林业"));
		ECONOMIC_AREA_TYPE_LIST.add(new StatusBean<Integer, String>(ECONOMIC_AREA_TYPE_HERDING, "牧业"));
		ECONOMIC_AREA_TYPE_LIST.add(new StatusBean<Integer, String>(ECONOMIC_AREA_TYPE_FISHERY, "渔业"));
		ECONOMIC_AREA_TYPE_LIST.add(new StatusBean<Integer, String>(ECONOMIC_AREA_TYPE_OTHER, "其他"));
	}

	public static void initEconomicAreaTypeMap() {
		ECONOMIC_AREA_TYPE_MAP = StatusKit.toMap(ECONOMIC_AREA_TYPE_LIST);
	}

	public static void initFamilyFarmTypeList() {
		FAMILY_FARM_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		FAMILY_FARM_TYPE_LIST.add(new StatusBean<String, String>(FAMILY_FARM_TYPE_INDIVIDUAL_BUSINESS, "个体工商户"));
		FAMILY_FARM_TYPE_LIST.add(new StatusBean<String, String>(FAMILY_FARM_TYPE_INDIVIDUAL_PROPRIETORSHIP, "个体独资企业"));
		FAMILY_FARM_TYPE_LIST.add(new StatusBean<String, String>(FAMILY_FARM_TYPE_LIMITED_LIABILITY_COMPANY, "有限责任公司"));

	}

	public static void initFamilyFarmTypeMAP() {
		FAMILY_FARM_TYPE_MAP = StatusKit.toMap(FAMILY_FARM_TYPE_LIST);
	}

	public static void initFarmLevelList() {
		FARM_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
		FARM_LEVEL_LIST.add(new StatusBean<String, String>(FARM_LEVEL_PROVINCIAL, "省级"));
		FARM_LEVEL_LIST.add(new StatusBean<String, String>(FARM_LEVEL_CITY, "市级"));
		FARM_LEVEL_LIST.add(new StatusBean<String, String>(FARM_LEVEL_COUNTY, "县级"));

	}

	public static void initFarmLevelMAP() {
		FARM_LEVEL_MAP = StatusKit.toMap(FARM_LEVEL_LIST);
	}
}