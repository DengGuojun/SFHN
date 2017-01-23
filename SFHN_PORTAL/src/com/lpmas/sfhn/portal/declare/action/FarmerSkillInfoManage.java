//package com.lpmas.sfhn.portal.declare.action;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.lpmas.framework.config.Constants;
//import com.lpmas.framework.tools.portal.PortalKit;
//import com.lpmas.framework.util.BeanKit;
//import com.lpmas.framework.util.ListKit;
//import com.lpmas.framework.util.StringKit;
//import com.lpmas.framework.web.HttpResponseKit;
//import com.lpmas.framework.web.ParamKit;
//import com.lpmas.framework.web.ReturnMessageBean;
//import com.lpmas.ow.passport.sso.business.SsoClientHelper;
//import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
//import com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean;
//import com.lpmas.sfhn.declare.bean.NationalCertificationBean;
//import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
//import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
//import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
//import com.lpmas.sfhn.portal.declare.business.FarmerSkillInfoBusiness;
//import com.lpmas.sfhn.portal.declare.business.NationalCertificationBusiness;
//
///**
// * Servlet implementation class FarmerSkillInfoManage
// */
//@WebServlet("/sfhn/FarmerSkillInfoManage.do")
//public class FarmerSkillInfoManage extends HttpServlet {
//	private static Logger log = LoggerFactory.getLogger(FarmerSkillInfoManage.class);
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public FarmerSkillInfoManage() {
//		super();
//	}
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
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
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//		FarmerSkillInfoBusiness farmerSkillInfoBusiness = new FarmerSkillInfoBusiness();
//		// 检查是否完成了填写
//		List<DeclareInfoBean> declareInfoList = declareInfoBusiness.getDeclareInfoListByUserId(userId);
//		for (DeclareInfoBean infoBean : declareInfoList) {
//			if ((infoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE))
//					&& infoBean.getDeclareType() != declareType) {
//				// 仅有的一个已经是等候审批状态
//				HttpResponseKit.alertMessage(response, "申报已经提交,请勿重复填写", HttpResponseKit.ACTION_HISTORY_BACK);
//				return;
//			}
//		}
//		if (declareInfoBean != null) {
//			FarmerSkillInfoBean skillInfoBean = farmerSkillInfoBusiness
//					.getFarmerSkillInfoByKey(declareInfoBean.getDeclareId());
//			if (skillInfoBean == null) {
//				// 新建
//				skillInfoBean = new FarmerSkillInfoBean();
//				skillInfoBean.setStatus(Constants.STATUS_VALID);
//			}
//			request.setAttribute("FarmerSkillInfoBean", skillInfoBean);
//		} else {
//			// 新建
//			FarmerSkillInfoBean skillInfoBean = new FarmerSkillInfoBean();
//			skillInfoBean.setStatus(Constants.STATUS_VALID);
//			request.setAttribute("FarmerSkillInfoBean", skillInfoBean);
//		}
//
//		// 获取国家资格证书数据
//		NationalCertificationBusiness nationalCertificationBusiness = new NationalCertificationBusiness();
//		List<NationalCertificationBean> ncList = nationalCertificationBusiness.getNationalCertificationAllList();
//		request.setAttribute("NationalCertificationList", ncList);
//		Map<Integer, String> nationalCertificationMap = ListKit.list2Map(ncList, "certificateId", "certificateName");
//		request.setAttribute("NationalCertificationMap", nationalCertificationMap);
//
//		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "declare/FarmerSkillInfoManage.jsp");
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		ReturnMessageBean messageBean = new ReturnMessageBean();
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
//		SsoClientHelper helper = new SsoClientHelper(request, response, false);
//		int userId = helper.getUserId();
//		FarmerSkillInfoBean bean = new FarmerSkillInfoBean();
//		try {
//			bean = BeanKit.request2Bean(request, FarmerSkillInfoBean.class);
//			if (bean.getNationalCertificationId1() > 0 && bean.getNationalCertificationGrade1() > 0) {
//				bean.setHasNationalCertification(Constants.STATUS_VALID);
//			}
//			FarmerSkillInfoBusiness business = new FarmerSkillInfoBusiness();
//			DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//			messageBean = business.verifyFarmerSkillInfo(bean);
//			if (StringKit.isValid(messageBean.getMessage())) {
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//
//			int result = 0;
//			DeclareInfoBean originalBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//			if (bean.getDeclareId() > 0 && originalBean != null) {
//				// 判断修改的表单数据是否属于用户自己拥有的申报表单数据
//				if (originalBean.getDeclareId() != bean.getDeclareId()) {
//					messageBean.setMessage("申报ID不合法");
//					HttpResponseKit.printJson(request, response, messageBean, "");
//					return;
//				}
//				// 检验是否已经是待审批状态
//				if (DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE.equals(originalBean.getDeclareStatus())) {
//					messageBean.setMessage("已经是审核状态，不能修改");
//					HttpResponseKit.printJson(request, response, messageBean, "");
//					return;
//				}
//				bean.setModifyUser(userId);
//				result = business.updateFarmerSkillInfo(bean);
//			} else {
//				int declareId = 0;
//				if (originalBean == null) {
//					// 新建申报对象
//					DeclareInfoBean declareInfoBean = new DeclareInfoBean();
//					declareInfoBean.setUserId(userId);
//					declareInfoBean.setDeclareType(declareType);
//					declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_EDIT);
//					declareInfoBean.setStatus(Constants.STATUS_VALID);
//					declareInfoBean.setCreateUser(userId);
//					declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
//				} else {
//					declareId = originalBean.getDeclareId();
//				}
//				if (declareId > 0) {
//					// 新建技能信息
//					bean.setDeclareId(declareId);
//					bean.setCreateUser(userId);
//					result = business.addFarmerSkillInfo(bean);
//				}
//
//			}
//
//			if (result >= 0) {
//				messageBean.setCode(Constants.STATUS_VALID);
//				messageBean.setMessage("处理成功");
//			} else {
//				messageBean.setMessage("处理失败");
//			}
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		} catch (Exception e) {
//			log.error("", e);
//		}
//	}
//}
