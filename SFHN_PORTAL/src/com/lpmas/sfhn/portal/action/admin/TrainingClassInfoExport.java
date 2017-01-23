package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.excel.ExcelWriteBean;
import com.lpmas.framework.excel.WebExcelWriteKit;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.bean.TrainingClassInfoEntityBean;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassInfoBusiness;
import com.lpmas.sfhn.portal.business.TrainingOrganizationInfoBusiness;

@WebServlet("/sfhn/admin/TrainingClassInfoExport.do")
public class TrainingClassInfoExport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassInfoExport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// 获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();

		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		String classStatusSelection = ParamKit.getParameter(request, "classStatusSelection", "").trim();
		if (StringKit.isValid(classStatusSelection)) {
			condMap.put("classStatusSelection", classStatusSelection);
		}
		String acceptStatusSelection = ParamKit.getParameter(request, "acceptStatusSelection", "").trim();
		if (StringKit.isValid(acceptStatusSelection)) {
			condMap.put("acceptStatusSelection", acceptStatusSelection);
		}
		String openStatus = ParamKit.getParameter(request, "openStatus", "").trim();
		if (StringKit.isValid(openStatus)) {
			condMap.put("openStatus", openStatus);
		}

		int trainingType = ParamKit.getIntParameter(request, "trainingType", 0);
		if (trainingType > 0) {
			condMap.put("trainingType", String.valueOf(trainingType));
		}
		int trainingYear = ParamKit.getIntParameter(request, "trainingYear", 0);
		if (trainingYear > 0) {
			condMap.put("trainingYear", String.valueOf(trainingYear));
		}
		int industryBeltId = ParamKit.getIntParameter(request, "industryBeltId", 0);
		if (industryBeltId > 0) {
			condMap.put("industryBeltId", String.valueOf(industryBeltId));
		}
		String queryCity = ParamKit.getParameter(request, "queryCity", "").trim();
		if (StringKit.isValid(queryCity)) {
			condMap.put("city", queryCity);
		}
		String queryRegion = ParamKit.getParameter(request, "queryRegion", "").trim();
		if (StringKit.isValid(queryRegion)) {
			condMap.put("region", queryRegion);
		}
		String organizationId = ParamKit.getParameter(request, "organizationId", "").trim();
		if (StringKit.isValid(organizationId) && !organizationId.equals("0")) {
			condMap.put("organizationId", organizationId);
		}
		if (orgUserBean == null) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			// 用户是政府机构
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 根据政府机构的机构级别，获取对应的培训班信息
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
				// 省级机构
				condMap.put("province", governmentOrgBean.getProvince());
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				// 市级机构
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				// 区级机构，不能客制化筛选
				condMap.put("province", governmentOrgBean.getProvince());
				condMap.put("city", governmentOrgBean.getCity());
				condMap.put("region", governmentOrgBean.getRegion());
			}
		}else {
			//用户是培训机构
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				HttpResponseKit.alertMessage(response, "你没有该功能的操作权限", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			//根据培训机构的省市区，设置导出条件
			condMap.put("province", trainingOrgBean.getProvince());
			condMap.put("city", trainingOrgBean.getCity());
			condMap.put("region", trainingOrgBean.getRegion());
		}
		TrainingClassInfoBusiness trainingClassBusiness = new TrainingClassInfoBusiness();
		
		List<TrainingClassInfoEntityBean> classInfoList = new ArrayList<TrainingClassInfoEntityBean>();
		List<TrainingClassInfoBean> result = trainingClassBusiness.getTrainingClassInfoListByMap(condMap);
		for (TrainingClassInfoBean trainingClassInfoBean : result) {
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(trainingClassInfoBean.getOrganizationId());
			TrainingClassInfoEntityBean entityBean = new TrainingClassInfoEntityBean(trainingClassInfoBean,
					trainingOrgBean);
			classInfoList.add(entityBean);
		}
		List<List<Object>> contentList = new ArrayList<List<Object>>();
		List<Object> tempList = null;
		for (TrainingClassInfoEntityBean bean : classInfoList) {
			tempList = new ArrayList<Object>();
			tempList.add(bean.getClassName());
			tempList.add(MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) + " "
					+ bean.getClassPeopleQuantity() + "人");
			tempList.add(bean.getOrganizationName());
			tempList.add(DateKit.formatTimestamp(bean.getTrainingBeginTime(), DateKit.DEFAULT_DATE_FORMAT));
			tempList.add(bean.getProvince() + "-" + bean.getCity() + "-" + bean.getRegion());
			contentList.add(tempList);
		}
		List<String> headerList = new ArrayList<String>();
		headerList.add("培训班名称");
		headerList.add("培育类型");
		headerList.add("培训机构");
		headerList.add("开班时间");
		headerList.add("地区");
		ExcelWriteBean excelWriteBean = new ExcelWriteBean();
		excelWriteBean.setFileName("培训班列表");
		excelWriteBean.setFileType("xlsx");
		excelWriteBean.setHeaderList(headerList);
		excelWriteBean.setContentList(contentList);
		WebExcelWriteKit webExcelWriteKit = new WebExcelWriteKit();
		webExcelWriteKit.outputExcel(excelWriteBean, request, response);

	}

}
