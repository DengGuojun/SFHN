package com.lpmas.sfhn.portal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class MessageInfoConfig {

	public static final int MESSAGE_TYPE_NEW_CLASS_INFO = 1;
	public static final int MESSAGE_TYPE_REJECT_CLASS_INFO = 2;
	public static final int MESSAGE_TYPE_APPROVE_CLASS_INFO = 3;
	public static final int MESSAGE_TYPE_NEW_CLASS_ACCEPT = 4;
	public static final int MESSAGE_TYPE_REJECT_CLASS_ACCEPT = 5;
	public static final int MESSAGE_TYPE_APPROVE_CLASS_ACCEPT = 6;
	public static final int MESSAGE_TYPE_CANDIDATE_LIST = 7;
	public static final int MESSAGE_TYPE_CANDIDATE_REQUEST = 8;
	public static final int MESSAGE_TYPE_ADMIN_ANNOUNCEMENT = 9; // 行政公告
	public static List<StatusBean<Integer, String>> MESSAGE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> MESSAGE_TYPE_MAP = new HashMap<Integer, String>();

	static {
		initMessageTypeList();
		initMessageTypeMap();
	}

	private static void initMessageTypeList() {
		MESSAGE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_NEW_CLASS_INFO, "新开班申请"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_REJECT_CLASS_INFO, "开班申请驳回"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_APPROVE_CLASS_INFO, "审核"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_NEW_CLASS_ACCEPT, "新验收申请"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_REJECT_CLASS_ACCEPT, "项目验收驳回"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_APPROVE_CLASS_ACCEPT, "审核"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_ADMIN_ANNOUNCEMENT, "发布新公告"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_CANDIDATE_LIST, "新增学员名单"));
		MESSAGE_TYPE_LIST.add(new StatusBean<Integer, String>(MESSAGE_TYPE_CANDIDATE_REQUEST, "继续招生申请"));
	}

	private static void initMessageTypeMap() {
		MESSAGE_TYPE_MAP = StatusKit.toMap(MESSAGE_TYPE_LIST);
	}
}
