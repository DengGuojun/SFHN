package com.lpmas.sfhn.console.declare.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.excel.ExcelWriteBean;
import com.lpmas.framework.excel.WebExcelWriteKit;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryTypeBusiness;
import com.lpmas.sfhn.console.declare.business.JobInfoBusiness;
import com.lpmas.sfhn.console.declare.business.JobTypeBusiness;
import com.lpmas.sfhn.console.declare.config.DeclareReportHeaderConfig;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.declare.bean.JobInfoBean;
import com.lpmas.sfhn.declare.bean.JobTypeBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerIndustryInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerJobInfoConfig;
import com.lpmas.sfhn.declare.config.NationalityConfig;

@WebServlet("/sfhn/DeclareReportExport.do")
public class DeclareReportExport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(DeclareReportExport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareReportExport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap<String, String> condMap = new HashMap<String, String>();
		HashMap<String, HashMap<String, String>> scopeMap = new HashMap<String, HashMap<String, String>>();
		/*// 查国家资格证书
		NationalCertificationBusiness nationalCertificationBusiness = new NationalCertificationBusiness();
		List<NationalCertificationBean> nationalCertificationList = nationalCertificationBusiness
				.getNationalCertificationAllList();
		Map<Integer, NationalCertificationBean> nationalCertificationMap = new HashMap<Integer, NationalCertificationBean>();
		if (!nationalCertificationList.isEmpty()) {
			nationalCertificationMap = ListKit.list2Map(nationalCertificationList, "certificateId");
		}*/
		// 查产业类型和产业

		IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
		Map<Integer, IndustryTypeBean> industryTypeMap = new HashMap<Integer, IndustryTypeBean>();
		if (!industryTypeList.isEmpty()) {
			industryTypeMap = ListKit.list2Map(industryTypeList, "typeId");
		}

		IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
		List<IndustryInfoBean> industryInfoList = industryInfoBusiness.getIndustryInfoAllList();
		Map<Integer, IndustryInfoBean> industryInfoMap = new HashMap<Integer, IndustryInfoBean>();
		if (!industryInfoList.isEmpty()) {
			industryInfoMap = ListKit.list2Map(industryInfoList, "industryId");
		}

		// 从Mongo中获取相应的数据
		DeclareReportBusiness business = new DeclareReportBusiness();
		try {
			List<DeclareReportBean> result = business.getDeclareReportListByMap(condMap, scopeMap);
			List<List<Object>> contentList = new ArrayList<List<Object>>();
			List<Object> tempList = null;
			for (DeclareReportBean bean : result) {
				tempList = new ArrayList<Object>();
				tempList.add(bean.getDeclareId());
				tempList.add(DeclareInfoConfig.DECLARE_TYPE_MAP.get(bean.getDeclareType()));
				tempList.add(DeclareInfoConfig.DECLARE_STATUS_MAP.get(bean.getDeclareStatus()));
				tempList.add(bean.getUserName());
				if (bean.getUserGender() == 1) {
					tempList.add("男");
				} else {
					tempList.add("女");
				}
				tempList.add(bean.getImagePath());
				tempList.add(bean.getUserBirthday());
				tempList.add(NationalityConfig.NATIONALITY_MAP.get(bean.getNationality()));
				tempList.add(FarmerInfoConfig.EDUCATION_LEVEL_MAP.get(bean.getEducation()));
				tempList.add(bean.getIdentityNumber());
				tempList.add(FarmerInfoConfig.POLITICAL_STATUS_MAP.get(bean.getPoliticalStatus()));
				tempList.add(DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT));
				tempList.add(bean.getUserMobile());
				tempList.add(bean.getUserEmail());
				tempList.add(bean.getUserQq());
				tempList.add(bean.getUserWechat());
				tempList.add(bean.getProvince() + bean.getCity() + bean.getRegion());
				tempList.add(bean.getAddress());
				if (bean.getIsTrained() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				tempList.add(bean.getOtherTrainingTime());
				tempList.add(MapKit.getValueFromMap(bean.getApplyType(), DeclareInfoConfig.APPLY_TYPE_MAP));
				if (bean.getHasNewTypeCertification() == 1) {
					tempList.add("是");
					tempList.add(MapKit.getValueFromMap(bean.getCertificationGrade(),
							FarmerInfoConfig.CERTIFICATION_LEVEL_MAP));
					tempList.add(
							DateKit.formatTimestamp(bean.getCertificationDate(), DateKit.DEFAULT_DATE_TIME_FORMAT));
					tempList.add(bean.getCertificationDepartment());
				} else {
					tempList.add("否");
					tempList.add("");
					tempList.add("");
					tempList.add("");
				}
				tempList.add(MapKit.getValueFromMap(bean.getCertificationTitle(), FarmerInfoConfig.FARMER_TITLE_MAP));
				if (bean.getHasNoCertification() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				if (bean.getHasNewTypeTrainingCertification() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				if (bean.getHasNationalCertification() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				if (bean.getJobType() != 0) {
					JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
					JobTypeBean jobTypeBean = jobTypeBusiness.getJobTypeByKey(bean.getJobType());
					if (jobTypeBean != null) {
						tempList.add(jobTypeBean.getTypeName());
					}
				} else {
					tempList.add("");
				}
				if (bean.getJobName() != 0) {
					JobInfoBusiness jobInfoBusiness = new JobInfoBusiness();
					JobInfoBean jobInfoBean = jobInfoBusiness.getJobInfoByKey(bean.getJobName());
					if (jobInfoBean != null) {
						tempList.add(jobInfoBean.getJobName());
					}
				} else {
					tempList.add("");
				}
				tempList.add(bean.getExperience());
				tempList.add(bean.getIncome());
				tempList.add(FarmerJobInfoConfig.JOB_COMPANY_TYPE_MAP.get(bean.getCompanyType()));
				tempList.add(bean.getJobProvince() + bean.getJobCity() + bean.getJobRegion());
				tempList.add(bean.getIndustryProvince() + bean.getIndustryCity() + bean.getIndustryRegion());
				if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					tempList.add(MapKit.getValueFromMap(bean.getFarmerType(),
							FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_MAP));
				} else if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					tempList.add(MapKit.getValueFromMap(bean.getFarmerType(),
							FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP));
				} else if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					tempList.add(MapKit.getValueFromMap(bean.getFarmerType(),
							FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_MAP));
				} else {
					tempList.add("");
				}
				if (bean.getHasRegisted() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				if (bean.getIsExampleFamilyFarm() == 1) {
					tempList.add("是");
				} else {
					tempList.add("否");
				}
				tempList.add(
						MapKit.getValueFromMap(bean.getExampleFarmLevel(), FarmerIndustryInfoConfig.FARM_LEVEL_MAP));
				tempList.add(MapKit.getValueFromMap(bean.getFamilyFarmType(),
						FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP));
				tempList.add(bean.getFarmLandScale());
				tempList.add(bean.getFamilyPerson());
				tempList.add(bean.getFamilyWorkingPerson());
				tempList.add(FarmerIndustryInfoConfig.AREA_TYPE_MAP.get(bean.getAreaType()));
				tempList.add(FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_MAP.get(bean.getEconomicAreaType()));
				if (bean.getIndustryJobType() != 0) {
					JobTypeBusiness jobTypeBusiness = new JobTypeBusiness();
					JobTypeBean jobTypeBean = jobTypeBusiness.getJobTypeByKey(bean.getIndustryJobType());
					if (jobTypeBean != null) {
						tempList.add(jobTypeBean.getTypeName());
					}
				} else {
					tempList.add("");
				}
				if (bean.getIndustryJobId() != 0) {
					JobInfoBusiness jobInfoBusiness = new JobInfoBusiness();
					JobInfoBean jobInfoBean = jobInfoBusiness.getJobInfoByKey(bean.getIndustryJobId());
					if (jobInfoBean != null) {
						tempList.add(jobInfoBean.getJobName());
					}
				} else {
					tempList.add("");
				}
				tempList.add(bean.getIndustryExperience());
				tempList.add(bean.getIndustryIncome());
				tempList.add(bean.getServiceScale());
				if (bean.getIndustryTypeId1() != 0) {
					if (industryTypeMap.get(bean.getIndustryTypeId1()) != null) {
						tempList.add(industryTypeMap.get(bean.getIndustryTypeId1()).getTypeName());
					}
				} else {
					tempList.add("");
				}
				if (bean.getIndustryId1() != 0) {
					if (industryInfoMap.get(bean.getIndustryId1()) != null) {
						tempList.add(industryInfoMap.get(bean.getIndustryId1()).getIndustryName());
					}
				} else {
					tempList.add("");
				}
				tempList.add(bean.getIndustryScale1());
				tempList.add(bean.getExperience1());
				if (bean.getIndustryTypeId2() != 0) {
					if (industryTypeMap.get(bean.getIndustryTypeId2()) != null) {
						tempList.add(industryTypeMap.get(bean.getIndustryTypeId2()).getTypeName());
					}
				} else {
					tempList.add("");
				}
				if (bean.getIndustryId2() != 0) {
					if (industryInfoMap.get(bean.getIndustryId2()) != null) {
						tempList.add(industryInfoMap.get(bean.getIndustryId2()).getIndustryName());
					}
				} else {
					tempList.add("");
				}
				tempList.add(bean.getIndustryScale2());
				tempList.add(bean.getExperience2());
				if (bean.getIndustryTypeId3() != 0) {
					if (industryTypeMap.get(bean.getIndustryTypeId3()) != null) {
						tempList.add(industryTypeMap.get(bean.getIndustryTypeId3()).getTypeName());
					}
				} else {
					tempList.add("");
				}
				if (bean.getIndustryId3() != 0) {
					if (industryInfoMap.get(bean.getIndustryId3()) != null) {
						tempList.add(industryInfoMap.get(bean.getIndustryId3()).getIndustryName());
					}
				} else {
					tempList.add("");
				}
				tempList.add(bean.getIndustryScale3());
				tempList.add(bean.getExperience3());
				tempList.add(bean.getLastYearIncome());
				tempList.add(bean.getLastYearFamilyIncome());

				contentList.add(tempList);
			}
			ExcelWriteBean excelWriteBean = new ExcelWriteBean();
			excelWriteBean.setFileName("职业农民申请信息列表");
			excelWriteBean.setFileType("xlsx");
			excelWriteBean.setHeaderList(DeclareReportHeaderConfig.DECLARE_REPORT_HEADER_LIST);
			excelWriteBean.setContentList(contentList);
			WebExcelWriteKit webExcelWriteKit = new WebExcelWriteKit();
			webExcelWriteKit.outputExcel(excelWriteBean, request, response);

		} catch (Exception e) {
			log.error("", e);
		} finally {
		}

	}

}
