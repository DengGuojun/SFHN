package com.lpmas.sfhn.portal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.StatusKit;

public class TrainingClassInfoConfig {

	// 培训班状态
	public static final String CLASS_STATUS_EDIT = "EDIT";
	public static final String CLASS_STATUS_WAIT_APPROVE_PROVINCE = "WAIT_APPROVE_PROVINCE";
	public static final String CLASS_STATUS_WAIT_APPROVE_CITY = "WAIT_APPROVE_CITY";
	public static final String CLASS_STATUS_WAIT_APPROVE_REGION = "WAIT_APPROVE_REGION";
	public static final String CLASS_STATUS_WAIT_APPROVE = "WAIT_APPROVE";//非数据库状态
	public static final String CLASS_STATUS_REJECTED_REGION = "REJECTED_REGION";
	public static final String CLASS_STATUS_REJECTED_CITY = "REJECTED_CITY";
	public static final String CLASS_STATUS_REJECTED_PROVINCE = "REJECTED_PROVINCE";
	public static final String CLASS_STATUS_REJECTED = "REJECTED";//非数据库状态
	public static final String CLASS_STATUS_APPROVED = "APPROVED";
	public static final String CLASS_STATUS_OPENED = "OPENED";
	public static List<StatusBean<String, String>> CLASS_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> CLASS_STATUS_MAP = new HashMap<String, String>();
	public static List<StatusBean<String, String>> CLASS_STATUS_SELECTION_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> CLASS_STATUS_SELECTION_MAP = new HashMap<String, String>();
	
	//开班状态(非数据库状态)
	public static final String OPEN_STATUS_NOT_APPROVED = "NOT_APPROVED";
	public static final String OPEN_STATUS_NOT_OPENED = "NOT_OPENED";
	public static final String OPEN_STATUS_OPENED = "OPENED";
	public static final String OPEN_STATUS_FINISHED = "FINISHED";
	public static List<StatusBean<String, String>> OPEN_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> OPEN_STATUS_MAP = new HashMap<String, String>();

	// 培训班验收状态
	public static final String ACCEPT_STATUS_EDIT = "EDIT";
	public static final String ACCEPT_STATUS_WAIT_APPROVE_PROVINCE = "WAIT_APPROVE_PROVINCE";
	public static final String ACCEPT_STATUS_WAIT_APPROVE_CITY = "WAIT_APPROVE_CITY";
	public static final String ACCEPT_STATUS_WAIT_APPROVE_REGION = "WAIT_APPROVE_REGION";
	public static final String ACCEPT_STATUS_WAIT_APPROVE = "WAIT_APPROVE";//非数据库状态
	public static final String ACCEPT_STATUS_REJECTED_REGION = "REJECTED_REGION";
	public static final String ACCEPT_STATUS_REJECTED_CITY = "REJECTED_CITY";
	public static final String ACCEPT_STATUS_REJECTED_PROVINCE = "REJECTED_PROVINCE";
	public static final String ACCEPT_STATUS_REJECTED = "REJECTED";//非数据库状态
	public static final String ACCEPT_STATUS_APPROVED = "APPROVED";
	public static List<StatusBean<String, String>> ACCEPT_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> ACCEPT_STATUS_MAP = new HashMap<String, String>();
	public static List<StatusBean<String, String>> ACCEPT_STATUS_SELECTION_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> ACCEPT_STATUS_SELECTION_MAP = new HashMap<String, String>();

	// 成果上传状态
	public static List<StatusBean<Integer, String>> FILE_STATUS_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> FILE_STATUS_MAP = new HashMap<Integer, String>();

	// 培训班审批级别
	public static final int APPROVE_LEVEL_REGION = 1;
	public static final int APPROVE_LEVEL_CITY = 2;
	public static final int APPROVE_LEVEL_PROVINCE = 3;
	public static List<StatusBean<Integer, String>> APPROVE_LEVEL_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> APPROVE_LEVEL_MAP = new HashMap<Integer, String>();

	// 审批动作
	public static final String APPROVE_ACTION_PASS = "PASS";
	public static final String APPROVE_ACTION_FAIL = "FAIL";

	// 提交审批动作
	public static final String COMMIT_ACTION_COMMIT = "COMMIT";
	public static final String COMMIT_ACTION_CANCEL_COMMIT = "CANCEL_COMMIT";

	// 招生状态
	public static final String RECRUIT_START = "RECRUIT_START";
	public static final String RECRUIT_CONTINUE = "RECRUIT_CONTINUE";
	public static final String RECRUIT_FINISH = "RECRUIT_FINISH";
	public static List<StatusBean<String, String>> RECRUIT_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> RECRUIT_STATUS_MAP = new HashMap<String, String>();

	// 培训班种类
	public static final int CLASS_TYPE_OFFLINE = 0;
	public static final int CLASS_TYPE_ONLINE = 1;
	public static List<StatusBean<Integer, String>> CLASS_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> CLASS_TYPE_MAP = new HashMap<Integer, String>();

	static {
		initClassStatusList();
		initClassStatusMap();
		initClassStatusSelectionList();
		initClassStatusSelectionMap();
		
		initOpenStatusList();
		initOpenStatusMap();

		initAcceptStatusList();
		initAcceptStatusMap();
		initAcceptStatusSelectionList();
		initAcceptStatusSelectionMap();

		initApproveLevelList();
		initApproveLevelMap();

		initFileStatusList();
		initFileStatusMap();

		initRecruitStatusList();
		initRecruitStatusMap();

		initClassTypeList();
		initClassTypeMap();
	}

	private static void initClassStatusList() {
		CLASS_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_EDIT, "已撤回"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_WAIT_APPROVE_PROVINCE, "待省主管部门审批"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_WAIT_APPROVE_CITY, "待市主管部门审批"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_WAIT_APPROVE_REGION, "待县主管部门审批"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_REJECTED_REGION, "县主管部门驳回"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_REJECTED_CITY, "市主管部门驳回"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_REJECTED_PROVINCE, "省主管部门驳回"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_APPROVED, "审批通过"));
		CLASS_STATUS_LIST.add(new StatusBean<String, String>(CLASS_STATUS_OPENED, "开班中"));
	}

	private static void initClassStatusMap() {
		CLASS_STATUS_MAP = StatusKit.toMap(CLASS_STATUS_LIST);
	}
	
	private static void initClassStatusSelectionList() {
		CLASS_STATUS_SELECTION_LIST = new ArrayList<StatusBean<String, String>>();
		CLASS_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(CLASS_STATUS_EDIT, "已撤回"));
		CLASS_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(CLASS_STATUS_WAIT_APPROVE, "待审批"));
		CLASS_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(CLASS_STATUS_REJECTED, "审批不通过"));
		CLASS_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(CLASS_STATUS_APPROVED, "审批通过"));
	}

	private static void initClassStatusSelectionMap() {
		CLASS_STATUS_SELECTION_MAP = StatusKit.toMap(CLASS_STATUS_SELECTION_LIST);
	}
	
	private static void initOpenStatusList() {
		OPEN_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		OPEN_STATUS_LIST.add(new StatusBean<String, String>(OPEN_STATUS_NOT_APPROVED, "未审批通过"));
		OPEN_STATUS_LIST.add(new StatusBean<String, String>(OPEN_STATUS_NOT_OPENED, "未开班"));
		OPEN_STATUS_LIST.add(new StatusBean<String, String>(OPEN_STATUS_OPENED, "开班中"));
		OPEN_STATUS_LIST.add(new StatusBean<String, String>(OPEN_STATUS_FINISHED, "已结束"));
	}

	private static void initOpenStatusMap() {
		OPEN_STATUS_MAP = StatusKit.toMap(OPEN_STATUS_LIST);
	}

	private static void initAcceptStatusList() {
		ACCEPT_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_EDIT, "未提交验收"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_WAIT_APPROVE_PROVINCE, "待省级主管部门审批"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_WAIT_APPROVE_CITY, "待市级主管部门审批"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_WAIT_APPROVE_REGION, "待县级主管部门审批"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_REJECTED_REGION, "县主管部门驳回"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_REJECTED_CITY, "市主管部门驳回"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_REJECTED_PROVINCE, "省主管部门驳回"));
		ACCEPT_STATUS_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_APPROVED, "审批通过"));
	}

	private static void initAcceptStatusMap() {
		ACCEPT_STATUS_MAP = StatusKit.toMap(ACCEPT_STATUS_LIST);
	}
	
	private static void initAcceptStatusSelectionList() {
		ACCEPT_STATUS_SELECTION_LIST = new ArrayList<StatusBean<String, String>>();
		ACCEPT_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_EDIT, "未提交验收"));
		ACCEPT_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_WAIT_APPROVE, "待审批"));
		ACCEPT_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_REJECTED, "审批不通过"));
		ACCEPT_STATUS_SELECTION_LIST.add(new StatusBean<String, String>(ACCEPT_STATUS_APPROVED, "审批通过"));
	}

	private static void initAcceptStatusSelectionMap() {
		ACCEPT_STATUS_SELECTION_MAP = StatusKit.toMap(ACCEPT_STATUS_SELECTION_LIST);
	}

	private static void initApproveLevelList() {
		APPROVE_LEVEL_LIST = new ArrayList<StatusBean<Integer, String>>();
		APPROVE_LEVEL_LIST.add(new StatusBean<Integer, String>(APPROVE_LEVEL_REGION, "县级审批"));
		APPROVE_LEVEL_LIST.add(new StatusBean<Integer, String>(APPROVE_LEVEL_CITY, "市级审批"));
		APPROVE_LEVEL_LIST.add(new StatusBean<Integer, String>(APPROVE_LEVEL_PROVINCE, "省级审批"));
	}

	private static void initApproveLevelMap() {
		APPROVE_LEVEL_MAP = StatusKit.toMap(APPROVE_LEVEL_LIST);
	}

	private static void initFileStatusList() {
		FILE_STATUS_LIST = new ArrayList<StatusBean<Integer, String>>();
		FILE_STATUS_LIST.add(new StatusBean<Integer, String>(Constants.STATUS_NOT_VALID, "未提交"));
		FILE_STATUS_LIST.add(new StatusBean<Integer, String>(Constants.STATUS_VALID, "已提交"));

	}

	private static void initFileStatusMap() {
		FILE_STATUS_MAP = StatusKit.toMap(FILE_STATUS_LIST);
	}

	private static void initRecruitStatusList() {
		RECRUIT_STATUS_LIST = new ArrayList<StatusBean<String, String>>();
		RECRUIT_STATUS_LIST.add(new StatusBean<String, String>(RECRUIT_START, "培训机构核查生源情况"));
		RECRUIT_STATUS_LIST.add(new StatusBean<String, String>(RECRUIT_CONTINUE, "培训机构申请继续招生"));
		RECRUIT_STATUS_LIST.add(new StatusBean<String, String>(RECRUIT_FINISH, "完成招生"));
	}

	private static void initRecruitStatusMap() {
		RECRUIT_STATUS_MAP = StatusKit.toMap(RECRUIT_STATUS_LIST);
	}

	private static void initClassTypeList() {
		CLASS_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		CLASS_TYPE_LIST.add(new StatusBean<Integer, String>(CLASS_TYPE_ONLINE, "线上"));
		CLASS_TYPE_LIST.add(new StatusBean<Integer, String>(CLASS_TYPE_OFFLINE, "线下"));
	}

	private static void initClassTypeMap() {
		CLASS_TYPE_MAP = StatusKit.toMap(CLASS_TYPE_LIST);
	}
}
