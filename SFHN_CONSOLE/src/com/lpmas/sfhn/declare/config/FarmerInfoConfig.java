package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class FarmerInfoConfig {

	public static final String EDUCATION_LEVEL_NO_EDUCATED = "NO_EDUCATED";// 未上过学
	public static final String EDUCATION_LEVEL_PRIMARY_SCHOOL = "PRIMARY_SCHOOL";// 小学
	public static final String EDUCATION_LEVEL_MIDDLE_SCHOOL = "MIDDLE_SCHOOL";// 初中
	public static final String EDUCATION_LEVEL_HEIGHT_SCHOOL = "HEIGHT_SCHOOL";// 高中/中专
	public static final String EDUCATION_LEVEL_COLLEGE = "COLLEGE";// 大专
	public static final String EDUCATION_LEVEL_UNIVERSITY_AND_ABOVE = "UNIVERSITY_AND_ABOVE";// 大学及以上
	public static List<StatusBean<String, String>> EDUCATION_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> EDUCATION_LEVEL_MAP = new LinkedHashMap<String, String>();

	public static final String PERSONNEL_CATEGORY_LARGE_REEDING = "LARGE_REEDING";// 种养大户
	public static final String PERSONNEL_CATEGORY_FAMILY_FARMER = "FAMILY_FARMER";// 家庭农场经营者
	public static final String PERSONNEL_CATEGORY_COOPERATIVES_BACKBONE = "COOPERATIVES_BACKBONE";// 农民合作社骨干
	public static final String PERSONNEL_CATEGORY_AGRICULTURAL_ENTERPRISE_BOSS = "AGRIBUSINESS_LEADER";// 农业企业负责人
	public static final String PERSONNEL_CATEGORY_STUDENTS_ENTREPRENEURSHIP = "STUDENTS_ENTREPRENEURSHIP";// 创业大学生
	public static final String PERSONNEL_CATEGORY_MIGRANT_WORKERS = "MIGRANT_WORKERS";// 返乡农名工
	public static final String PERSONNEL_CATEGORY_VETERAN = "VETERAN";// 退伍军人
	public static final String PERSONNEL_CATEGORY_SCALE_AQUACULTURE_OPERATORS = "SCALE_AQUACULTURE_OPERATORS";// 规模养殖经营者
	public static final String PERSONNEL_CATEGORY_COLLEGE_GRADUATES = "COLLEGE_GRADUATES";// 中高职毕业生
	public static final String PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER = "AGRICULTURAL_SERVICE_ORGANIZER";// 农业社会化服务组织服务能手
	// 生产经营型人员类别
	public static List<StatusBean<String, String>> PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP = new LinkedHashMap<String, String>();
	// 青年农场主人员类别
	public static List<StatusBean<String, String>> YOUNG_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> YOUNG_FARMER_PERSONNEL_CATEGORY_MAP = new LinkedHashMap<String, String>();
	// 带头人人员类别
	public static List<StatusBean<String, String>> LEADER_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> LEADER_FARMER_PERSONNEL_CATEGORY_MAP = new LinkedHashMap<String, String>();

	public static final String POLITICAL_STATUS_CPC = "CPC";
	public static final String POLITICAL_STATUS_CYL = "CYL";
	public static final String POLITICAL_STATUS_PEOPLE = "PEOPLE";
	public static final String POLITICAL_STATUS_OTHER = "OTHER";
	public static List<StatusBean<String, String>> POLITICAL_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> POLITICAL_STATUS_MAP = new LinkedHashMap<String, String>();

	// 证书等级
	public static final String CERTIFICATION_LEVEL_HIGH = "HIGH";
	public static final String CERTIFICATION_LEVEL_INTERMEDIATE = "INTERMEDIATE";
	public static final String CERTIFICATION_LEVEL_LOW = "LOW";
	public static List<StatusBean<String, String>> CERTIFICATION_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> CERTIFICATION_LEVEL_MAP = new LinkedHashMap<String, String>();

	// 农民登记或职称证书
	public static final String FARMER_TITLE_SENIOR_TECHNICIAN = "SENIOR_TECHNICIAN";
	public static final String FARMER_TITLE_TECHNICIAN = "TECHNICIAN";
	public static final String FARMER_TITLE_JUNIOR_TECHNICIAN = "JUNIOR_TECHNICIAN";
	public static final String FARMER_TITLE_TECHNICIAN_STAFF = "TECHNICIAN_STAFF";
	public static List<StatusBean<String, String>> FARMER_TITLE_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> FARMER_TITLE_MAP = new LinkedHashMap<String, String>();
	static {
		initEducationLevelList();
		initEducationLevelMap();

		initPersonnelCategoryList();
		initPersonnelCategoryMap();

		initPoliticalStatuslist();
		initPoliticalStatusMap();

		initCertificationLevelList();
		initCertificationLevelMAP();

		initFarmerTitleList();
		initFarmerTitleMap();
	}

	public static void initEducationLevelList() {
		EDUCATION_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_NO_EDUCATED, "未上过学"));
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_PRIMARY_SCHOOL, "小学"));
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_MIDDLE_SCHOOL, "初中"));
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_HEIGHT_SCHOOL, "高中/中专"));
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_COLLEGE, "大专"));
		EDUCATION_LEVEL_LIST.add(new StatusBean<String, String>(EDUCATION_LEVEL_UNIVERSITY_AND_ABOVE, "大学及以上"));
	}

	public static void initEducationLevelMap() {
		EDUCATION_LEVEL_MAP = StatusKit.toMap(EDUCATION_LEVEL_LIST);
	}

	public static void initPersonnelCategoryList() {
		// 生产经营型人员类别初始化
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_LARGE_REEDING, "种养大户"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_SCALE_AQUACULTURE_OPERATORS, "规模养殖经营者"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_FAMILY_FARMER, "家庭农场经营者"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_AGRICULTURAL_ENTERPRISE_BOSS, "农业企业负责人"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_COOPERATIVES_BACKBONE, "农民合作社骨干"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_STUDENTS_ENTREPRENEURSHIP, "创业大学生"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_COLLEGE_GRADUATES, "中高职毕业生"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_MIGRANT_WORKERS, "返乡农民工"));
		PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_VETERAN, "退伍军人"));

		// 青年农场主人员类别初始化
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_LARGE_REEDING, "种养大户"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_SCALE_AQUACULTURE_OPERATORS, "规模养殖经营者"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_FAMILY_FARMER, "家庭农场经营者"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_COOPERATIVES_BACKBONE, "农民合作社骨干"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_STUDENTS_ENTREPRENEURSHIP, "创业大学生"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_COLLEGE_GRADUATES, "中高职毕业生"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_MIGRANT_WORKERS, "返乡农民工"));
		YOUNG_FARMER_PERSONNEL_CATEGORY_LIST.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_VETERAN, "退伍军人"));

		// 带头人人员类别初始化
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST = new ArrayList<StatusBean<String, String>>();
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_LARGE_REEDING, "种养大户"));
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_SCALE_AQUACULTURE_OPERATORS, "规模养殖经营者"));
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_FAMILY_FARMER, "家庭农场经营者"));
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_COOPERATIVES_BACKBONE, "农民合作社骨干"));
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST
				.add(new StatusBean<String, String>(PERSONNEL_CATEGORY_AGRICULTURAL_ENTERPRISE_BOSS, "农业企业负责人"));
		LEADER_FARMER_PERSONNEL_CATEGORY_LIST.add(
				new StatusBean<String, String>(PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER, "农业社会化服务组织服务能手"));
	}

	public static void initPersonnelCategoryMap() {
		// 生产经营型人员类别初始化
		PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP = StatusKit.toMap(PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST);
		// 青年农场主人员类别初始化
		YOUNG_FARMER_PERSONNEL_CATEGORY_MAP = StatusKit.toMap(YOUNG_FARMER_PERSONNEL_CATEGORY_LIST);
		// 带头人人员类别初始化
		LEADER_FARMER_PERSONNEL_CATEGORY_MAP = StatusKit.toMap(LEADER_FARMER_PERSONNEL_CATEGORY_LIST);
	}

	public static void initPoliticalStatuslist() {
		POLITICAL_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		POLITICAL_STATUS_LIST.add(new StatusBean<String, String>(POLITICAL_STATUS_CPC, "中共党员"));
		POLITICAL_STATUS_LIST.add(new StatusBean<String, String>(POLITICAL_STATUS_CYL, "共青团员"));
		POLITICAL_STATUS_LIST.add(new StatusBean<String, String>(POLITICAL_STATUS_PEOPLE, "群众"));
		POLITICAL_STATUS_LIST.add(new StatusBean<String, String>(POLITICAL_STATUS_OTHER, "其他"));
	}

	public static void initPoliticalStatusMap() {
		POLITICAL_STATUS_MAP = StatusKit.toMap(POLITICAL_STATUS_LIST);
	}

	public static void initCertificationLevelList() {
		CERTIFICATION_LEVEL_LIST = new ArrayList<StatusBean<String, String>>();
		CERTIFICATION_LEVEL_LIST.add(new StatusBean<String, String>(CERTIFICATION_LEVEL_HIGH, "高级"));
		CERTIFICATION_LEVEL_LIST.add(new StatusBean<String, String>(CERTIFICATION_LEVEL_INTERMEDIATE, "中级"));
		CERTIFICATION_LEVEL_LIST.add(new StatusBean<String, String>(CERTIFICATION_LEVEL_LOW, "低级"));

	}

	public static void initCertificationLevelMAP() {
		CERTIFICATION_LEVEL_MAP = StatusKit.toMap(CERTIFICATION_LEVEL_LIST);
	}

	public static void initFarmerTitleList() {
		FARMER_TITLE_LIST = new ArrayList<StatusBean<String, String>>();
		FARMER_TITLE_LIST.add(new StatusBean<String, String>(FARMER_TITLE_SENIOR_TECHNICIAN, "农民高级技师"));
		FARMER_TITLE_LIST.add(new StatusBean<String, String>(FARMER_TITLE_TECHNICIAN, "农民技师"));
		FARMER_TITLE_LIST.add(new StatusBean<String, String>(FARMER_TITLE_JUNIOR_TECHNICIAN, "农民助理技师"));
		FARMER_TITLE_LIST.add(new StatusBean<String, String>(FARMER_TITLE_TECHNICIAN_STAFF, "农民技术人员"));
	}

	public static void initFarmerTitleMap() {
		FARMER_TITLE_MAP = StatusKit.toMap(FARMER_TITLE_LIST);
	}
}
