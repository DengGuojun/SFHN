package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class FarmerSkillInfoConfig {

	public static final int SKILL_GRADE_HIGH_TECHNICIAN = 1;
	public static final int SKILL_GRADE_TECHNICIAN = 2;
	public static final int SKILL_GRADE_SENIOR = 3;
	public static final int SKILL_GRADE_MEDIUM = 4;
	public static final int SKILL_GRADE_JUNIOR = 5;
	public static List<StatusBean<Integer, String>> SKILL_GRADE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> SKILL_GRADE_MAP = new LinkedHashMap<Integer, String>();
	
	static {
		initSkillGradeList();
		initSkillGradeMap();
	}
	
	public static void initSkillGradeList() {
		SKILL_GRADE_LIST = new ArrayList<StatusBean<Integer, String>>();
		SKILL_GRADE_LIST.add(new StatusBean<Integer, String>(SKILL_GRADE_HIGH_TECHNICIAN, "高级技师"));
		SKILL_GRADE_LIST.add(new StatusBean<Integer, String>(SKILL_GRADE_TECHNICIAN, "技师"));
		SKILL_GRADE_LIST.add(new StatusBean<Integer, String>(SKILL_GRADE_SENIOR, "高级"));
		SKILL_GRADE_LIST.add(new StatusBean<Integer, String>(SKILL_GRADE_MEDIUM, "中级"));
		SKILL_GRADE_LIST.add(new StatusBean<Integer, String>(SKILL_GRADE_JUNIOR, "初级"));
	}

	public static void initSkillGradeMap() {
		SKILL_GRADE_MAP = StatusKit.toMap(SKILL_GRADE_LIST);
	}
}
