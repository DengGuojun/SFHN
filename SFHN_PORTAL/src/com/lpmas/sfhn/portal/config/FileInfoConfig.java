package com.lpmas.sfhn.portal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.util.PropertiesKit;
import com.lpmas.framework.util.StatusKit;

public class FileInfoConfig {

	public static final String ALLOWED_FILE_TYPE = "jpg,jpeg,gif,png,doc,xls,docx,xlsx,zip,rar";// 设置允许上传的文件类型
	public static final int MAX_SIZE = 500 * 1024 * 1024; // 设置上传文件最大为500M
	public static final String ALLOWED_PIC_FILE_TYPE = "jpg,jpeg,gif,png";
	public static final String ALLOWED_WORD_FILE_TYPE = "doc,docx";
	public static final String ALLOWED_EXCEL_FILE_TYPE = "xls,xlsx";
	public static final String ALLOWED_ZIP_FILE_TYPE = "zip,rar";

	// 设置文件保存路径
	public static final String ADMIN_FILE_PATH = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME, "ADMIN_FILE_PATH");
	// 设置文件备份路径
	public static final String ADMIN_BACKUP_FILE_PATH = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME,
			"ADMIN_BACKUP_FILE_PATH");
	// 设置文件访问目录
	public static final String ADMIN_FILE_FOLDER = PropertiesKit.getBundleProperties(SfhnPortalConfig.SFHN_PROP_FILE_NAME, "ADMIN_FILE_FOLDER");

	// 申报培训班附件
	public static final int FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM = 1; // 培育工程项目申报表
	public static final int FILE_TYPE_ANNUAL_PLAN = 2; // 年度实施计划
	public static final int FILE_TYPE_TRAINING_GUIDE = 3; // 培训指南

	// 集中培训附件
	public static final int FILE_TYPE_TRAINING_MATERIAL_CATALOG = 4;// 教材封面及目录
	public static final int FILE_TYPE_INFORMATION_ACAUISITION_FORM = 5;// 认定信息采集表
	public static final int FILE_TYPE_VISIT_BASE_INTRODUCTION = 6; // 参观考察基地简介
	public static final int FILE_TYPE_CLASS_USER_SHEET = 7;// 学员培训台账
	public static final int FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET = 8;// 学员信息汇总表
	public static final int FILE_TYPE_CLASS_COMMITTEE_LIST = 9; // 班委会名单
	public static final int FILE_TYPE_CLASS_ATTENDANCE_LIST = 10; // 学员考勤表
	public static final int FILE_TYPE_CLASS_PHOTO = 11;// 培训现场照片
	public static final int FILE_TYPE_CLASS_SATISFACTION_SURVEY = 12;// 学员满意度测评汇总表
	public static final int FILE_TYPE_CLASS_EXAM_RESULT = 13;// 考试考核材料（成绩汇总表）
	public static final int FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL = 14; // 跟踪管理协议
	public static final int FILE_TYPE_TRACKING_SERVICE_SHEET = 15; // 跟踪服务对接表
	public static final int FILE_TYPE_TRAINING_FUND_USE_PLAN = 16;// 培育基金使用计划
	public static final int FILE_TYPE_IDENTITY_CARD_COPY = 17;// 身份证复印件
	public static final int FILE_TYPE_IDENTIFY_DECLARE_FORM = 18; // 认定申报表
	public static final int FILE_TYPE_TYPICAL_CLASS_USER = 19;// 典型学员
	public static final int FILE_TYPE_TRAINING_CLASS_SUMMARY = 20;// 培训班总结
	public static final int FILE_TYPE_TEACHER_APPOINTMENT = 27;// 教师聘书
	public static final int FILE_TYPE_TEACHER_INFORMATION = 28;// 培训教师师资信息
	public static final int FILE_TYPE_CERTIFICATE_OF_COMPLETION = 29;// 结业证书发放表
	public static final int FILE_TYPE_PROMOTIONAL_MATERIAL = 30;// 宣传材料
	public static List<StatusBean<Integer, String>> ALLOWED_FILE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> ALLOWED_FILE_TYPE_MAP = new HashMap<Integer, String>();

	// 附件格式
	public static final String FILE_FORMAT_PIC_OR_WORD = "图片、word或压缩包";
	public static final String FILE_FORMAT_WORD = "word或压缩包";
	public static final String FILE_FORMAT_PIC_OR_EXCEL = "图片、excel或压缩包";
	public static final String FILE_FORMAT_EXCEL = "excel或压缩包";

	// 田间实训附件
	public static final int FILE_TYPE_FIELD_TRAINING_PLAN = 21; // 田间实训计划
	public static final int FILE_TYPE_FIELD_TRAINING_PHOTO = 22; // 田间实训图片

	// 项目验收表
	public static final int FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM = 23;

	// 学员信息表
	public static final int FILE_TYPE_CLASS_USER_INFO_SHEET = 24;// 学员信息表

	// 公告附件
	public static final int FILE_TYPE_ANNOUNCEMENT_ATTACHMENT = 25;
	// 学员名单
	public static final int FILE_TYPE_CANDIDATE_LIST = 26;

	public static List<StatusBean<Integer, String>> FILE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static Map<Integer, String> FILE_TYPE_MAP = new HashMap<Integer, String>();

	static {
		initFileTypeList();
		initFileTypeMap();

		initAllowedFileTypeList();
		initAllowedFileTypeMap();
	}

	private static void initFileTypeList() {
		FILE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM, " 培育工程项目申报表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_ANNUAL_PLAN, "年度实施计划"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_GUIDE, "培训指南"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_MATERIAL_CATALOG, "教材封面及目录"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_INFORMATION_ACAUISITION_FORM, "认定信息采集表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_VISIT_BASE_INTRODUCTION, "参观考察基地简介"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_USER_SHEET, "学员培训台账"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET, "学员信息汇总表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_COMMITTEE_LIST, "班委会名单"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_ATTENDANCE_LIST, "学员考勤表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_PHOTO, "培训现场照片"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_SATISFACTION_SURVEY, "学员满意度测评汇总表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_EXAM_RESULT, "考试考核材料（成绩汇总表）"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL, "跟踪管理协议"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRACKING_SERVICE_SHEET, "跟踪服务对接表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_FUND_USE_PLAN, "培育基金使用计划"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_IDENTITY_CARD_COPY, "身份证复印件"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_IDENTIFY_DECLARE_FORM, "认定申报表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TYPICAL_CLASS_USER, "典型学员"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_CLASS_SUMMARY, "培训班总结"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_FIELD_TRAINING_PLAN, "田间实训计划"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_FIELD_TRAINING_PHOTO, "田间实训图片"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM, "项目验收表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_USER_INFO_SHEET, "学员信息表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_ANNOUNCEMENT_ATTACHMENT, "公告附件"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CANDIDATE_LIST, "学员名单"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TEACHER_APPOINTMENT, "教师聘书"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TEACHER_INFORMATION, "培训教师师资信息"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CERTIFICATE_OF_COMPLETION, "结业证书发放表"));
		FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_PROMOTIONAL_MATERIAL, "宣传材料"));
	}

	private static void initFileTypeMap() {
		FILE_TYPE_MAP = StatusKit.toMap(FILE_TYPE_LIST);
	}

	private static void initAllowedFileTypeList() {
		ALLOWED_FILE_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_MATERIAL_CATALOG,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_INFORMATION_ACAUISITION_FORM,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_VISIT_BASE_INTRODUCTION, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_USER_SHEET,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_EXCEL_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_COMMITTEE_LIST, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_ATTENDANCE_LIST,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_EXCEL_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_PHOTO,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_SATISFACTION_SURVEY, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CLASS_EXAM_RESULT,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_EXCEL_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_FUND_USE_PLAN, ALLOWED_EXCEL_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_IDENTITY_CARD_COPY,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_TYPICAL_CLASS_USER, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_TRAINING_CLASS_SUMMARY, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_TEACHER_APPOINTMENT,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST
				.add(new StatusBean<Integer, String>(FILE_TYPE_TEACHER_INFORMATION, ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_CERTIFICATE_OF_COMPLETION,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_EXCEL_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
		ALLOWED_FILE_TYPE_LIST.add(new StatusBean<Integer, String>(FILE_TYPE_PROMOTIONAL_MATERIAL,
				ALLOWED_PIC_FILE_TYPE + "," + ALLOWED_WORD_FILE_TYPE + "," + ALLOWED_ZIP_FILE_TYPE));
	}

	private static void initAllowedFileTypeMap() {
		ALLOWED_FILE_TYPE_MAP = StatusKit.toMap(ALLOWED_FILE_TYPE_LIST);
	}
}
