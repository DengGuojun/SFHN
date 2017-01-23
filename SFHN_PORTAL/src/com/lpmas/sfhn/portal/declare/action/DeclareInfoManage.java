package com.lpmas.sfhn.portal.declare.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.web.HttpResponseKit;

/**
 * Servlet implementation class DeclareProtalIndex
 */
@WebServlet("/sfhn/DeclareInfoManage.do")
public class DeclareInfoManage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(DeclareInfoManage.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeclareInfoManage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpResponseKit.alertMessage(response, "暂不开放申报功能", HttpResponseKit.ACTION_HISTORY_BACK);
//		int declareType = ParamKit.getIntParameter(request, "declareType", 0);
//		if (declareType <= 0) {
//			HttpResponseKit.alertMessage(response, "申报类型必须填写", HttpResponseKit.ACTION_HISTORY_BACK);
//			return;
//		}
//		if (!DeclareInfoConfig.DECLARE_TYPE_MAP.containsKey(declareType)) {
//			HttpResponseKit.alertMessage(response, "申报类型非法", HttpResponseKit.ACTION_HISTORY_BACK);
//			return;
//		}
//		// 获取用户Id
//		SsoClientHelper helper = new SsoClientHelper(request, response, false);
//		int userId = helper.getUserId();
//
//		Map<String, Boolean> moduleFinishedMap = new LinkedHashMap<String, Boolean>();
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, DateKit.);
//		// 检查是否完成了填写
//		List<DeclareInfoBean> declareInfoList = declareInfoBusiness.getDeclareInfoListByUserId(userId);
//		for (DeclareInfoBean infoBean : declareInfoList) {
//			if ((infoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE))
//					&& infoBean.getDeclareType() != declareType) {
//				// 仅有的一个已经是等候审批状态
//				HttpResponseKit.alertMessage(response, "您已提交其他审核，请勿重复提交", HttpResponseKit.ACTION_HISTORY_BACK);
//				return;
//			}
//		}
//		int declareId = 0;
//		if (declareInfoBean != null) {
//			// 获取那些表示已经填写的
//			declareId = declareInfoBean.getDeclareId();
//			moduleFinishedMap = declareInfoBusiness.getModuleFinishedMapByCondition(declareId, declareType);
//		} else {
//			declareInfoBean = new DeclareInfoBean();
//			declareInfoBean.setDeclareType(declareType);
//			declareInfoBean.setUserId(userId);
//
//			moduleFinishedMap.put(DeclareInfoConfig.MODULE_FARMER_INFO, false);
//			moduleFinishedMap.put(DeclareInfoConfig.MODULE_FARMER_CONTACT_INFO, false);
//			moduleFinishedMap.put(DeclareInfoConfig.MODULE_FARMER_SKILL_INFO, false);
//			// 是专业的就要农务工作信息，其他要填农务经营情况
//			if (declareType == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER
//					|| declareType == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
//				// 要填农务工作信息
//				moduleFinishedMap.put(DeclareInfoConfig.MODULE_FARMER_JOB_INFO, false);
//			} else {
//				// 要填农务经营情况
//				moduleFinishedMap.put(DeclareInfoConfig.MODULE_FARMER_INDUSTRY_INFO, false);
//			}
//		}
//		// 设值
//		request.setAttribute("DeclareInfoBean", declareInfoBean);
//		request.setAttribute("ModuleFinishedMap", moduleFinishedMap);
//		// 转发
//		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "declare/DeclareInfoManage.jsp");
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
//
//		int declareId = ParamKit.getIntParameter(request, "declareId", 0);
//		if (declareId <= 0) {
//			messageBean.setMessage("请先填写资料再提交审核!");
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 获取用户ID
//		SsoClientHelper helper = new SsoClientHelper(request, response, false);
//		int userId = helper.getUserId();
//
//		// 获得申报信息
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//		if (declareInfoBean == null || declareInfoBean.getDeclareId() != declareId) {
//			messageBean.setMessage("申报ID非法");
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 检查是否完成了填写
//		List<DeclareInfoBean> declareInfoList = declareInfoBusiness.getDeclareInfoListByUserId(userId);
//		for (DeclareInfoBean infoBean : declareInfoList) {
//			if (infoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE)) {
//				// 仅有的一个已经是等候审批状态
//				messageBean.setMessage("申报已经提交,请勿重复提交");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//		}
//
//		// 检验模块填写完成情况
//		Map<String, Boolean> moduleFinishedMap = declareInfoBusiness.getModuleFinishedMapByCondition(declareId,
//				declareType);
//		String verifyResult = declareInfoBusiness.verifyModule(moduleFinishedMap);
//		if (StringKit.isValid(verifyResult)) {
//			messageBean.setMessage(verifyResult);
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 检验身份证是否存在
//		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
//		ReturnMessageBean verifyMessage = farmerInfoBusiness.checkHasExistIdentityNumber(declareInfoBean);
//		if (StringKit.isValid(verifyMessage.getMessage())) {
//			messageBean.setMessage(verifyMessage.getMessage());
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 完成检查，UPDATE状态
//		int result = 0;
//		declareInfoBean.setModifyUser(userId);
//		declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE);
//		DeclareInfoHelper declareInfoHelper = new DeclareInfoHelper();
//		declareInfoBean.setDeclareYear(String.valueOf(declareInfoHelper.getDeclareYear()));
//
//		DeclareReportHandler handler = new DeclareReportHandler();
//		try {
//			handler.createDeclareReport(declareInfoBean, 0);
//			result = Constants.STATUS_VALID;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("审核操作失败，总表记录创建失败", e);
//		}
//		if (result > 0) {
//			messageBean.setCode(Constants.STATUS_VALID);
//			messageBean.setMessage("操作成功");
//		} else {
//			messageBean.setMessage("操作失败");
//		}
//		HttpResponseKit.printJson(request, response, messageBean, "");
//		return;
	}

}
