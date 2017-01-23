package com.lpmas.sfhn.portal.declare.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;

/**
 * Servlet implementation class DeclarePortalIndex
 */
@WebServlet("/sfhn/DeclarePortalIndex.do")
public class DeclarePortalIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DeclarePortalIndex.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclarePortalIndex() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//获取用户Id
		SsoClientHelper helper = new SsoClientHelper(request, response, false);
		int userId = helper.getUserId();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("userId", String.valueOf(userId));
		boolean isFinish = false;

		// 从Mongo中获取相应的数据
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		DeclareReportBean declareReportBean = new DeclareReportBean();
		try {
			List<DeclareReportBean> declareReportList = declareReportBusiness.getDeclareReportListByMap(condMap);
			if (!declareReportList.isEmpty() && declareReportList.size() > 0) {
				declareReportBean = declareReportList.get(0);
				isFinish = true;
			} else {
				declareReportBean.setStatus(Constants.STATUS_VALID);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (declareReportBean.getIndustryId1() != 0) {
			// 如果存在，则获取产业名称1
			IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
			IndustryInfoBean industryInfoBean = industryInfoBusiness
					.getIndustryInfoByKey(declareReportBean.getIndustryId1());
			request.setAttribute("OriginalIndustryName", industryInfoBean.getIndustryName());
		}
		// 获取产业类型列表
		IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
		List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
		request.setAttribute("IndustryTypeList", industryTypeList);
		request.setAttribute("DeclareReportBean", declareReportBean);
		String pagePath = SfhnPortalConfig.USER_PAGE_PATH + "declare/DeclarePortalIndex.jsp";
		if (isFinish) {
			pagePath = "TrainingClassInfoList.do?declareType=" + declareReportBean.getDeclareType();
		}else{
			HttpResponseKit.printMessage(response, "暂未开放报班功能");
			return;
		}
		PortalKit.forwardPage(request, response, pagePath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		ReturnMessageBean messageBean = new ReturnMessageBean();
//
//		// 获取申报类型
//		int declareType = ParamKit.getIntParameter(request, "declareType", 0);
//		if (declareType <= 0) {
//			messageBean.setMessage("申报类型非法");
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//		if (!DeclareInfoConfig.DECLARE_TYPE_MAP.containsKey(declareType)) {
//			messageBean.setMessage("申报类型非法");
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//		// 获取用户ID
//		SsoClientHelper helper = new SsoClientHelper(request, response, false);
//		int userId = helper.getUserId();
//		int declareId = 0;
//		int resultFarmerInfo = 0;
//		int resultFarmerContactInfo = 0;
//		int resultFarmerIndustryInfo = 0;
//		int resultMongo = 0;
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
//		FarmerInfoBean farmerInfoBean = new FarmerInfoBean();
//		String userName = ParamKit.getParameter(request, "userName", "");
//		farmerInfoBean.setUserName(userName);
//		String identityNumber = ParamKit.getParameter(request, "identityNumber", "");
//		farmerInfoBean.setIdentityNumber(identityNumber);
//		int userGender = ParamKit.getIntParameter(request, "userGender", 0);
//		farmerInfoBean.setUserGender(userGender);
//		FarmerContactInfoBean farmerContactInfoBean = new FarmerContactInfoBean();
//		FarmerContactInfoBusiness farmerContactInfoBusiness = new FarmerContactInfoBusiness();
//		String province = ParamKit.getParameter(request, "province", "");
//		String city = ParamKit.getParameter(request, "city", "");
//		String region = ParamKit.getParameter(request, "region", "");
//		String userMobile = ParamKit.getParameter(request, "userMobile", "");
//		FarmerIndustryInfoBusiness farmerIndustryInfoBusiness = new FarmerIndustryInfoBusiness();
//		FarmerIndustryInfoBean farmerIndustryInfoBean = new FarmerIndustryInfoBean();
//		int industryTypeId1 = ParamKit.getIntParameter(request, "industryTypeId1", 0);
//		int industryId1 = ParamKit.getIntParameter(request, "industryId1", 0);
//		double industryScale1 = ParamKit.getDoubleParameter(request, "industryScale1", 0.0);
//		String industryUnit1 = ParamKit.getParameter(request, "industryUnit1", "");
//		farmerIndustryInfoBean.setIndustryTypeId1(industryTypeId1);
//		farmerIndustryInfoBean.setIndustryId1(industryId1);
//		farmerIndustryInfoBean.setIndustryScale1(industryScale1);
//		farmerIndustryInfoBean.setIndustryUnit1(industryUnit1);
//
//		// 验证是不是老师
//		TeacherInfoBusiness teacherInfoBusiness = new TeacherInfoBusiness();
//		TeacherInfoBean teacherInfoBean = teacherInfoBusiness.getTeacherInfoByUserId(userId);
//		if (teacherInfoBean != null) {
//			messageBean.setMessage("教师不能参加新型职业农民申报");
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 检验身份证有无被使用
//		HashMap<String, String> condMap = new HashMap<String, String>();
//		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
//		condMap.put("identityNumber", identityNumber);
//		// 从Mongo中获取相应的数据
//		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
//		try {
//			List<DeclareReportBean> declareReportList = declareReportBusiness.getDeclareReportListByMap(condMap);
//			if (!declareReportList.isEmpty() && declareReportList.size() > 0) {
//				messageBean.setMessage("身份证重复");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		farmerContactInfoBean.setCity(city);
//		farmerContactInfoBean.setProvince(province);
//		farmerContactInfoBean.setRegion(region);
//		farmerContactInfoBean.setUserMobile(userMobile);
//		// 对所有数值类型作非负判断
//		for (Field field : BeanKit.getDeclaredFieldList(farmerIndustryInfoBean)) {
//			Object value = ReflectKit.getPropertyValue(farmerIndustryInfoBean, field.getName());
//			if (value != null) {
//				if (value instanceof Integer) {
//					if (((Integer) value) < 0) {
//						messageBean.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
//						HttpResponseKit.printJson(request, response, messageBean, "");
//						return;
//					}
//				} else if (value instanceof Double) {
//					if (((Double) value) < 0) {
//						messageBean.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
//						HttpResponseKit.printJson(request, response, messageBean, "");
//						return;
//					}
//				}
//			}
//		}
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//		if (declareInfoBean == null) {
//			// 先新建申报信息
//			declareInfoBean = new DeclareInfoBean();
//			declareInfoBean.setDeclareType(declareType);
//			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_EDIT);
//			declareInfoBean.setUserId(userId);
//			declareInfoBean.setStatus(Constants.STATUS_VALID);
//			declareInfoBean.setCreateUser(userId);
//			DeclareInfoHelper declareInfoHelper = new DeclareInfoHelper();
//			declareInfoBean.setDeclareYear(String.valueOf(declareInfoHelper.getDeclareYear()));
//			declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
//			if (declareId <= 0) {
//				messageBean.setMessage("操作失败");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//
//		} else {
//			// 检验是否已经是待审批状态
//			if (DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE.equals(declareInfoBean.getDeclareStatus())) {
//				messageBean.setMessage("已经是审核状态，不能修改");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//			declareId = declareInfoBean.getDeclareId();
//		}
//		// 判断农民信息是否新建
//		FarmerInfoBean dbExistFarmerInfoBean = farmerInfoBusiness.getFarmerInfoByKey(declareId);
//		if (dbExistFarmerInfoBean != null) {
//			// 修改农民信息
//			dbExistFarmerInfoBean.setModifyUser(userId);
//			dbExistFarmerInfoBean.setUserName(farmerInfoBean.getUserName());
//			dbExistFarmerInfoBean.setIdentityNumber(farmerInfoBean.getIdentityNumber());
//			dbExistFarmerInfoBean.setUserGender(farmerInfoBean.getUserGender());
//			resultFarmerInfo = farmerInfoBusiness.updateFarmerInfo(dbExistFarmerInfoBean);
//		} else {
//			// 新建农民信息
//			farmerInfoBean.setCreateUser(userId);
//			farmerInfoBean.setStatus(Constants.STATUS_VALID);
//			farmerInfoBean.setDeclareId(declareId);
//			resultFarmerInfo = farmerInfoBusiness.addFarmerInfo(farmerInfoBean);
//		}
//		// 判断联系信息是否新建
//		FarmerContactInfoBean dbExistFarmerContactInfoBean = farmerContactInfoBusiness
//				.getFarmerContactInfoByKey(declareId);
//		if (dbExistFarmerContactInfoBean != null) {
//			// 修改联系信息
//			dbExistFarmerContactInfoBean.setModifyUser(userId);
//			dbExistFarmerContactInfoBean.setProvince(province);
//			dbExistFarmerContactInfoBean.setCity(city);
//			dbExistFarmerContactInfoBean.setRegion(region);
//			dbExistFarmerContactInfoBean.setUserMobile(userMobile);
//			resultFarmerContactInfo = farmerContactInfoBusiness.updateFarmerContactInfo(dbExistFarmerContactInfoBean);
//		} else {
//			// 新建联系信息
//			farmerContactInfoBean.setCreateUser(userId);
//			farmerContactInfoBean.setStatus(Constants.STATUS_VALID);
//			farmerContactInfoBean.setDeclareId(declareId);
//			resultFarmerContactInfo = farmerContactInfoBusiness.addFarmerContactInfo(farmerContactInfoBean);
//		}
//		// 判断产业信息是否新建
//		FarmerIndustryInfoBean dbExistFarmerIndustryInfoBean = farmerIndustryInfoBusiness
//				.getFarmerIndustryInfoByKey(declareId);
//		if (dbExistFarmerIndustryInfoBean != null) {
//			dbExistFarmerIndustryInfoBean.setIndustryTypeId1(industryTypeId1);
//			dbExistFarmerIndustryInfoBean.setIndustryId1(industryId1);
//			dbExistFarmerIndustryInfoBean.setIndustryScale1(industryScale1);
//			dbExistFarmerIndustryInfoBean.setIndustryUnit1(industryUnit1);
//			dbExistFarmerIndustryInfoBean.setModifyUser(userId);
//			resultFarmerIndustryInfo = farmerIndustryInfoBusiness
//					.updateFarmerIndustryInfo(dbExistFarmerIndustryInfoBean);
//		} else {
//			// 新建产业信息
//			farmerIndustryInfoBean.setCreateUser(userId);
//			farmerIndustryInfoBean.setStatus(Constants.STATUS_VALID);
//			farmerIndustryInfoBean.setDeclareId(declareId);
//			resultFarmerIndustryInfo = farmerIndustryInfoBusiness.addFarmerIndustryInfo(farmerIndustryInfoBean);
//		}
//		// 新建工作表
//		FarmerJobInfoBean farmerJobInfoBean = new FarmerJobInfoBean();
//		farmerJobInfoBean.setDeclareId(declareId);
//		FarmerJobInfoBusiness farmerJobInfoBusiness = new FarmerJobInfoBusiness();
//		farmerJobInfoBusiness.addFarmerJobInfo(farmerJobInfoBean);
//		// 新建技能表
//		FarmerSkillInfoBean farmerSkillInfoBean = new FarmerSkillInfoBean();
//		farmerSkillInfoBean.setDeclareId(declareId);
//		FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
//		farmerSkillInfoBusiness.addFarmerSkillInfo(farmerSkillInfoBean);
//		// 插入mongo
//		DeclareReportHandler handler = new DeclareReportHandler();
//		try {
//			declareInfoBean.setDeclareId(declareId);
//			handler.createDeclareReport(declareInfoBean, 0);
//			resultMongo = Constants.STATUS_VALID;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("总表记录创建失败", e);
//		}
//		if (resultFarmerInfo < 0 || resultFarmerContactInfo < 0 || resultFarmerIndustryInfo < 0 || resultMongo <= 0) {
//			messageBean.setMessage("处理失败");
//		} else {
//			messageBean.setCode(Constants.STATUS_VALID);
//			// 设置declareType
//			messageBean.setMessage(String.valueOf(declareType));
//		}
//		HttpResponseKit.printJson(request, response, messageBean, "");
//		return;
	}
}