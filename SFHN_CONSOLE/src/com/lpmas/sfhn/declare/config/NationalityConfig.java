package com.lpmas.sfhn.declare.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.StatusKit;

public class NationalityConfig {

	// 民族

	public static final String NATIONALITY_HAN_ZU = "01";// 01 汉族
	public static final String NATIONALITY_MENG_GU_ZU = "02";// 02 蒙古族
	public static final String NATIONALITY_HUI_ZU = "03";// 03 回族
	public static final String NATIONALITY_ZANG_ZU = "04";// 04 藏族
	public static final String NATIONALITY_WEI_WU_ER_ZU = "05";// 05 维吾尔族
	public static final String NATIONALITY_MIAO_ZU = "06";// 06 苗族
	public static final String NATIONALITY_YI_ZU = "07";// 07 彝族
	public static final String NATIONALITY_ZHUANG_ZU = "08";// 08 壮族
	public static final String NATIONALITY_BU_YI_ZU = "09";// 09 布依族
	public static final String NATIONALITY_CHAO_XIAN_ZU = "10";// 10 朝鲜族
	public static final String NATIONALITY_MAN_ZU_ZU = "11";// 11 满族
	public static final String NATIONALITY_DONG_ZU = "12";// 12 侗族
	public static final String NATIONALITY_YAO_ZU = "13";// 13 瑶族
	public static final String NATIONALITY_BAI_ZU = "14";// 14 白族
	public static final String NATIONALITY_TU_JIA_ZU = "15";// 15 土家族
	public static final String NATIONALITY_HA_NI_ZU = "16";// 16 哈尼族
	public static final String NATIONALITY_HA_SA_KE_ZU = "17";// 17 哈萨克族
	public static final String NATIONALITY_DAI_ZU = "18";// 18 傣族
	public static final String NATIONALITY_LI_ZU = "19";// 19 黎族
	public static final String NATIONALITY_LI_LI_ZU = "20";// 20 僳僳族
	public static final String NATIONALITY_WA_ZU = "21";// 21 佤族
	public static final String NATIONALITY_SHE_ZU = "22";// 22 畲族
	public static final String NATIONALITY_GAO_SHAN_ZU = "22";// 23 高山族
	public static final String NATIONALITY_LA_HU_ZU = "24";// 24 拉祜族
	public static final String NATIONALITY_SHUI_ZU = "25";// 25 水族
	public static final String NATIONALITY_DONG_XIANG_ZU = "26";// 26 东乡族
	public static final String NATIONALITY_NA_XI_ZU = "27";// 27 纳西族
	public static final String NATIONALITY_JING_PO_ZU = "28";// 28 景颇族
	public static final String NATIONALITY_KE_ER_KE_ZI_ZU = "29";// 29 柯尔克孜族
	public static final String NATIONALITY_TU_ZU = "30";// 30 土族
	public static final String NATIONALITY_DA_WO_ER_ZU = "31";// 31 达斡尔族
	public static final String NATIONALITY_MU_LAO_ZU = "32";// 32 仫佬族
	public static final String NATIONALITY_QIANG_ZU = "33";// 33 羌族
	public static final String NATIONALITY_BU_LANG_ZU = "34";// 34 布朗族
	public static final String NATIONALITY_SA_LA_ZU = "35";// 35 撒拉族
	public static final String NATIONALITY_MAO_NAN_ZU = "36";// 36 毛难族
	public static final String NATIONALITY_GE_LAO_ZU = "37";// 37 仡佬族
	public static final String NATIONALITY_XI_BO_ZU = "38";// 38 锡伯族
	public static final String NATIONALITY_A_CHANG_ZU = "39";// 39 阿昌族
	public static final String NATIONALITY_PU_MI_ZU = "40";// 40 普米族
	public static final String NATIONALITY_TA_JI_KE_ZU = "41";// 41 塔吉克族
	public static final String NATIONALITY_NU_ZU = "42";// 42 怒族
	public static final String NATIONALITY_WU_ZI_BIE_KE_ZU = "43";// 43 乌孜别克族
	public static final String NATIONALITY_E_LUO_SI_ZU = "44";// 44 俄罗斯族
	public static final String NATIONALITY_E_WEN_KE_ZU = "45";// 45 鄂温克族
	public static final String NATIONALITY_BENG_LONG_ZU = "46";// 46 崩龙族
	public static final String NATIONALITY_BAO_AN_ZU = "47";// 47 保安族
	public static final String NATIONALITY_YU_GU_ZU = "48";// 48 裕固族
	public static final String NATIONALITY_JING_ZU = "49";// 49 京族
	public static final String NATIONALITY_TA_TA_ER_ZU = "50";// 50 塔塔尔族
	public static final String NATIONALITY_DU_LONG_ZU = "51";// 51 独龙族
	public static final String NATIONALITY_E_LUN_CHUN_ZU = "52";// 52 鄂伦春族
	public static final String NATIONALITY_HE_ZHE_ZU_ZU = "53";// 53 赫哲族
	public static final String NATIONALITY_MEN_BA_ZU = "54";// 54 门巴族
	public static final String NATIONALITY_LUO_BA_ZU = "55";// 55 珞巴族
	public static final String NATIONALITY_JI_NUO_ZU = "56";// 56 基诺族
	public static List<StatusBean<String, String>> NATIONALITY_LIST = new ArrayList<StatusBean<String, String>>();
	public static Map<String, String> NATIONALITY_MAP = new HashMap<String, String>();

	static {
		initNationalityList();
		initNationalityMap();
	}

	private static void initNationalityList() {
		NATIONALITY_LIST = new ArrayList<StatusBean<String, String>>();
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_HAN_ZU, "汉族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MENG_GU_ZU, "蒙古族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_HUI_ZU, "回族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_ZANG_ZU, "藏族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_WEI_WU_ER_ZU, "维吾尔族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MIAO_ZU, "苗族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_YI_ZU, "彝族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_ZHUANG_ZU, "壮族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_BU_YI_ZU, "布依族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_CHAO_XIAN_ZU, "朝鲜族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MAN_ZU_ZU, "满族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_DONG_ZU, "侗族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_YAO_ZU, "瑶族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_BAI_ZU, "白族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_TU_JIA_ZU, "土家族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_HA_NI_ZU, "哈尼族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_HA_SA_KE_ZU, "哈萨克族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_DAI_ZU, "傣族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_LI_ZU, "黎族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_LI_LI_ZU, "僳僳族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_WA_ZU, "佤族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_SHE_ZU, "畲族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_GAO_SHAN_ZU, "高山族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_LA_HU_ZU, "拉祜族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_SHUI_ZU, "水族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_DONG_XIANG_ZU, "东乡族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_NA_XI_ZU, "纳西族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_JING_PO_ZU, "景颇族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_KE_ER_KE_ZI_ZU, "柯尔克孜族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_TU_ZU, "土族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_DA_WO_ER_ZU, "达斡尔族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MU_LAO_ZU, "仫佬族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_QIANG_ZU, "羌族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_BU_LANG_ZU, "布朗族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_SA_LA_ZU, "撒拉族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MAO_NAN_ZU, "毛难族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_GE_LAO_ZU, "仡佬族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_XI_BO_ZU, "锡伯族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_A_CHANG_ZU, "阿昌族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_PU_MI_ZU, "普米族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_TA_JI_KE_ZU, "塔吉克族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_NU_ZU, "怒族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_WU_ZI_BIE_KE_ZU, "乌孜别克族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_E_LUO_SI_ZU, "俄罗斯族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_E_WEN_KE_ZU, "鄂温克族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_BENG_LONG_ZU, "崩龙族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_BAO_AN_ZU, "保安族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_YU_GU_ZU, "裕固族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_JING_ZU, "京族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_TA_TA_ER_ZU, "塔塔尔族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_DU_LONG_ZU, "独龙族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_E_LUN_CHUN_ZU, "鄂伦春族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_HE_ZHE_ZU_ZU, "赫哲族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_MEN_BA_ZU, "门巴族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_LUO_BA_ZU, "珞巴族"));
		NATIONALITY_LIST.add(new StatusBean<String, String>(NATIONALITY_JI_NUO_ZU, "基诺族"));
	}

	private static void initNationalityMap() {
		NATIONALITY_MAP = StatusKit.toMap(NATIONALITY_LIST);
	}
}
