package com.lpmas.sfhn.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class TrainingOrganizationConfig {

	//培训机构类型
	public static final String ORGANIZATION_TYPE_NGONLINE = "NGONLINE";// 农广校
	public static final String ORGANIZATION_TYPE_VOCATIONAL_COLLEGES = "VOCATIONAL_COLLEGES";// 农业职业学院
	public static final String ORGANIZATION_TYPE_MECHANIZATION_COLLEGES = "MECHANIZATION_COLLEGES";// 农机化学校
	public static final String ORGANIZATION_TYPE_AGRICULTURAL_EXTENSION_ORG = "AGRICULTURAL_EXTENSION_ORG";// 农技推广服务机构
	public static final String ORGANIZATION_TYPE_AGRICULTURAL_HIGH_SCHOOL = "AGRICULTURAL_HIGH_SCHOOL";// 农业高校
	public static final String ORGANIZATION_TYPE_AGRICULTURAL_ACADMY = "AGRICULTURAL_ACADMY";// 农科院所
	public static final String ORGANIZATION_TYPE_ADMIN_DEPARTMENT = "ADMIN_DEPARTMENT";// 农业行政主管部门
	public static final String ORGANIZATION_TYPE_COOPERATIVE = "COOPERATIVE";// 农民合作社
	public static final String ORGANIZATION_TYPE_CORPORATE_CHAMPION = "CORPORATE_CHAMPION";// 龙头企业
	public static final String ORGANIZATION_TYPE_OTHER_PUBLIC_INSTITUTION = "OTHER_PUBLIC_INSTITUTION";// 其他公办机构
	public static final String ORGANIZATION_TYPE_OTHER_PRIVATE_ENTERPRISE = "OTHER_PRIVATE_ENTERPRISE";// 其他民办机构
	public static List<StatusBean<String, String>> ORGANIZATION_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> ORGANIZATION_TYPE_MAP = new HashMap<String, String>();


	

	static {
		initOrganizationTypeList();
		initOrganizationTypeMap();
	}

	private static void initOrganizationTypeList() {
		ORGANIZATION_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_NGONLINE, "农广校"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_VOCATIONAL_COLLEGES, "农业职业学院"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_MECHANIZATION_COLLEGES, "农机化学校"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_AGRICULTURAL_EXTENSION_ORG, "农技推广服务机构"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_AGRICULTURAL_HIGH_SCHOOL, "农业高校"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_AGRICULTURAL_ACADMY, "农科院所"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_ADMIN_DEPARTMENT, "农业行政主管部门"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_COOPERATIVE, "农民合作社"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_CORPORATE_CHAMPION, "龙头企业"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_OTHER_PUBLIC_INSTITUTION, "其他公办机构"));
		ORGANIZATION_TYPE_LIST.add(new StatusBean<String, String>(ORGANIZATION_TYPE_OTHER_PRIVATE_ENTERPRISE, "其他民办机构"));
	}

	private static void initOrganizationTypeMap() {
		ORGANIZATION_TYPE_MAP = StatusKit.toMap(ORGANIZATION_TYPE_LIST);
	}
}
