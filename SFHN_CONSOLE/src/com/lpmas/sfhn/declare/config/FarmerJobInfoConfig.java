package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class FarmerJobInfoConfig {

	public static final String JOB_COMPANY_TYPE_LARGE_REEDING = "LARGE_REEDING";
	public static final String JOB_COMPANY_TYPE_FAMILY_FARMER = "FAMILY_FARMER";
	public static final String JOB_COMPANY_TYPE_COOPERATIVES = "COOPERATIVES";
	public static final String JOB_COMPANY_TYPE_ENTERPRISE = "ENTERPRISE";
	public static final String JOB_COMPANY_TYPE_GARDEN = "GARDEN";
	public static final String JOB_COMPANY_TYPE_OTHER = "OTHER";
	public static List<StatusBean<String, String>> JOB_COMPANY_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> JOB_COMPANY_TYPE_MAP = new LinkedHashMap<String, String>();
	
	static {
		initJobCompanyTypeList();
		initJobCompanyTypeMap();
	}
	
	public static void initJobCompanyTypeList() {
		JOB_COMPANY_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_LARGE_REEDING, "种养大户"));
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_FAMILY_FARMER, "家庭农场"));
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_COOPERATIVES, "农民合作社"));
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_ENTERPRISE, "农业企业"));
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_GARDEN, "农业园区"));
		JOB_COMPANY_TYPE_LIST.add(new StatusBean<String, String>(JOB_COMPANY_TYPE_OTHER, "其他"));
	}

	public static void initJobCompanyTypeMap() {
		JOB_COMPANY_TYPE_MAP = StatusKit.toMap(JOB_COMPANY_TYPE_LIST);
	}
}
