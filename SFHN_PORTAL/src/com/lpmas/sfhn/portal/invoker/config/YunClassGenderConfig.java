package com.lpmas.sfhn.portal.invoker.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class YunClassGenderConfig {

	public static final int GENDER_MALE = 1;// 男
	public static final int GENDER_FEMALE = 2;// 女

	public static List<StatusBean<Integer, String>> GENDER_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> GENDER_MAP = new HashMap<Integer, String>();

	static {
		init();
	}

	private static void init() {
		initGenderList();
		initGenderMap();
	}

	private static void initGenderList() {
		GENDER_LIST = new ArrayList<StatusBean<Integer, String>>();
		GENDER_LIST.add(new StatusBean<Integer, String>(GENDER_MALE, "male"));
		GENDER_LIST.add(new StatusBean<Integer, String>(GENDER_FEMALE, "female"));
	}

	private  static void initGenderMap() {
		GENDER_MAP = StatusKit.toMap(GENDER_LIST);
	}
}
