package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class DeclareInfoConfig {

	// 填写模块
	public static final String MODULE_FARMER_INFO = "FarmerInfo";
	public static final String MODULE_FARMER_CONTACT_INFO = "FarmerContactInfo";
	public static final String MODULE_FARMER_SKILL_INFO = "FarmerSkillInfo";
	public static final String MODULE_FARMER_INDUSTRY_INFO = "FarmerIndustryInfo";
	public static final String MODULE_FARMER_JOB_INFO = "FarmerJobInfo";
	public static List<StatusBean<String, String>> DECLARE_MODULE_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> DECLARE_MODULE_MAP = new HashMap<String, String>();

	// 申报类型
	public static final int DECLARE_TYPE_YOUNG_FARMER = 1;
	public static final int DECLARE_TYPE_PRODUCT_FARMER = 2;
	public static final int DECLARE_TYPE_TECHNICAL_FARMER = 3;
	public static final int DECLARE_TYPE_SERVICE_FARMER = 4;
	public static final int DECLARE_TYPE_LEADER_FARMER = 5;
	public static List<StatusBean<Integer, String>> DECLARE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> DECLARE_TYPE_MAP = new HashMap<Integer, String>();

	// 进度状态
	public static final String DECLARE_STATUS_EDIT = "EDIT";
	public static final String DECLARE_STATUS_WAIT_APPROVE = "WAIT_APPROVE";
	public static List<StatusBean<String, String>> DECLARE_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> DECLARE_STATUS_MAP = new HashMap<String, String>();

	// 申请方式
	public static final String APPLY_TYPE_PERSONAL = "PERSONAL";
	public static final String APPLY_TYPE_ENTERPRISES_TRAIN_RECOMMEND = "ENTERPRISES_TRAIN_RECOMMEND";
	public static final String APPLY_TYPE_DEPARTMENT_RECONMEND = "DEPARTMENT_RECONMEND";
	public static List<StatusBean<String, String>> APPLY_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
	public static HashMap<String, String> APPLY_TYPE_MAP = new HashMap<String, String>();
	static {
		initDeclareTypeList();
		initDeclareTypeMap();

		initDeclareStatusList();
		initDeclareStatusMap();

		initModuleList();
		initModuleMap();

		initApplyTypeList();
		initApplyTypeMap();
	}

	private static void initDeclareTypeList() {
		DECLARE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		DECLARE_TYPE_LIST.add(new StatusBean<Integer, String>(DECLARE_TYPE_YOUNG_FARMER, "现代青年农场主"));
		DECLARE_TYPE_LIST.add(new StatusBean<Integer, String>(DECLARE_TYPE_PRODUCT_FARMER, "生产经营职业农民"));
		DECLARE_TYPE_LIST.add(new StatusBean<Integer, String>(DECLARE_TYPE_TECHNICAL_FARMER, "专业技能型农民"));
		DECLARE_TYPE_LIST.add(new StatusBean<Integer, String>(DECLARE_TYPE_SERVICE_FARMER, "专业服务型农民"));
		DECLARE_TYPE_LIST.add(new StatusBean<Integer, String>(DECLARE_TYPE_LEADER_FARMER, "现代农业经营带头人"));
	}

	private static void initDeclareTypeMap() {
		DECLARE_TYPE_MAP = StatusKit.toMap(DECLARE_TYPE_LIST);
	}

	private static void initModuleList() {
		DECLARE_MODULE_LIST = new ArrayList<StatusBean<String, String>>();
		DECLARE_MODULE_LIST.add(new StatusBean<String, String>(MODULE_FARMER_INFO, "基本信息"));
		DECLARE_MODULE_LIST.add(new StatusBean<String, String>(MODULE_FARMER_CONTACT_INFO, "联系信息"));
		DECLARE_MODULE_LIST.add(new StatusBean<String, String>(MODULE_FARMER_SKILL_INFO, "申请培训信息"));
		DECLARE_MODULE_LIST.add(new StatusBean<String, String>(MODULE_FARMER_INDUSTRY_INFO, "生产经营状况"));
		DECLARE_MODULE_LIST.add(new StatusBean<String, String>(MODULE_FARMER_JOB_INFO, "农务工作信息"));
	}

	private static void initModuleMap() {
		DECLARE_MODULE_MAP = StatusKit.toMap(DECLARE_MODULE_LIST);
	}

	private static void initDeclareStatusList() {
		DECLARE_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		DECLARE_STATUS_LIST.add(new StatusBean<String, String>(DECLARE_STATUS_EDIT, "编辑"));
		DECLARE_STATUS_LIST.add(new StatusBean<String, String>(DECLARE_STATUS_WAIT_APPROVE, "待审批"));
	}

	private static void initDeclareStatusMap() {
		DECLARE_STATUS_MAP = StatusKit.toMap(DECLARE_STATUS_LIST);
	}

	private static void initApplyTypeList() {
		APPLY_TYPE_LIST = new ArrayList<StatusBean<String, String>>();
		APPLY_TYPE_LIST.add(new StatusBean<String, String>(APPLY_TYPE_PERSONAL, "个人申请"));
		APPLY_TYPE_LIST.add(new StatusBean<String, String>(APPLY_TYPE_ENTERPRISES_TRAIN_RECOMMEND, "农业企业及培训单位联合推荐"));
		APPLY_TYPE_LIST.add(new StatusBean<String, String>(APPLY_TYPE_DEPARTMENT_RECONMEND, "主管部门推荐"));

	}

	private static void initApplyTypeMap() {
		APPLY_TYPE_MAP = StatusKit.toMap(APPLY_TYPE_LIST);
	}
}
