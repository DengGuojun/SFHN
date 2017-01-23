//package com.lpmas.sfhn.portal.declare.action;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.lpmas.framework.config.Constants;
//import com.lpmas.framework.tools.portal.PortalKit;
//import com.lpmas.framework.util.BeanKit;
//import com.lpmas.framework.util.StringKit;
//import com.lpmas.framework.web.HttpResponseKit;
//import com.lpmas.framework.web.ParamKit;
//import com.lpmas.framework.web.ReturnMessageBean;
//import com.lpmas.ow.passport.sso.business.SsoClientHelper;
//import com.lpmas.sfhn.declare.bean.DeclareImageBean;
//import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
//import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
//import com.lpmas.sfhn.declare.config.DeclareImageInfoConfig;
//import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
//import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
//import com.lpmas.sfhn.portal.declare.business.DeclareImageBusiness;
//import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
//import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;
//
///**
// * Servlet implementation class FarmerInfoManage
// */
//@WebServlet("/sfhn/FarmerInfoManage.do")
//public class FarmerInfoManage extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public FarmerInfoManage() {
//		super();
//		// TODO Auto-generated constructor stub
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
//		// 获取申报信息
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//
//		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
//		FarmerInfoBean farmerInfoBean = null;
//
//		DeclareImageBusiness declareImageBusiness = new DeclareImageBusiness();
//		DeclareImageBean imageBean = null;
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
//		if (declareInfoBean == null) {
//			declareInfoBean = new DeclareInfoBean();
//			farmerInfoBean = new FarmerInfoBean();
//			imageBean = new DeclareImageBean();
//		} else {
//			farmerInfoBean = farmerInfoBusiness.getFarmerInfoByKey(declareInfoBean.getDeclareId());
//			if (farmerInfoBean == null) {
//				farmerInfoBean = new FarmerInfoBean();
//			}
//			imageBean = declareImageBusiness.getDeclareImageByKey(declareInfoBean.getDeclareId(),
//					DeclareImageInfoConfig.IMG_TYPE_ONE_INCH);
//			if (imageBean == null) {
//				imageBean = new DeclareImageBean();
//			}
//		}
//
//		// 设值
//		request.setAttribute("DeclareInfoBean", declareInfoBean);
//		request.setAttribute("FarmerInfoBean", farmerInfoBean);
//		request.setAttribute("DeclareImageBean", imageBean);
//		// 转发
//		PortalKit.forwardPage(request, response, SfhnPortalConfig.USER_PAGE_PATH + "declare/FarmerInfoManage.jsp");
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
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
//		// 获取用户ID
//		SsoClientHelper helper = new SsoClientHelper(request, response, false);
//		int userId = helper.getUserId();
//
//		// 映射Bean并进行数据验证
//		ReturnMessageBean verifyMessage = null;
//		FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
//		FarmerInfoBean farmerInfoBean = BeanKit.request2Bean(request, FarmerInfoBean.class);
//		verifyMessage = farmerInfoBusiness.verifyFarmerInfoBean(farmerInfoBean);
//		if (StringKit.isValid(verifyMessage.getMessage())) {
//			messageBean.setMessage(verifyMessage.getMessage());
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//		// 检验身份证有无被使用
//		verifyMessage = farmerInfoBusiness.checkHasExistIdentityNumber(farmerInfoBean);
//		if (StringKit.isValid(verifyMessage.getMessage())) {
//			messageBean.setMessage(verifyMessage.getMessage());
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		DeclareImageBusiness declareImageBusiness = new DeclareImageBusiness();
//		DeclareImageBean imageBean = BeanKit.request2Bean(request, DeclareImageBean.class);
//		verifyMessage = declareImageBusiness.verifyDeclareImageBean(imageBean);
//		if (StringKit.isValid(verifyMessage.getMessage())) {
//			messageBean.setMessage(verifyMessage.getMessage());
//			HttpResponseKit.printJson(request, response, messageBean, "");
//			return;
//		}
//
//		// 查询申报信息
//		int declareId = 0;
//		int result = 0;
//		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
//		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByCondition(userId, declareType);
//		// 判断是更新还是创建
//		if (declareInfoBean != null) {
//			// 已存在申报信息
//			// 检验申报ID
//			if ((declareInfoBean.getDeclareId() != farmerInfoBean.getDeclareId())) {
//				messageBean.setMessage("申报ID不合法");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//			// 检验是否已经是待审批状态
//			if (DeclareInfoConfig.DECLARE_STATUS_WAIT_APPROVE.equals(declareInfoBean.getDeclareStatus())) {
//				messageBean.setMessage("已经是审核状态，不能修改");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//			declareId = declareInfoBean.getDeclareId();
//		} else {
//			// 先新建申报信息
//			declareInfoBean = new DeclareInfoBean();
//			declareInfoBean.setDeclareType(declareType);
//			declareInfoBean.setDeclareStatus(DeclareInfoConfig.DECLARE_STATUS_EDIT);
//			declareInfoBean.setUserId(userId);
//			declareInfoBean.setStatus(Constants.STATUS_VALID);
//			declareInfoBean.setCreateUser(userId);
//			declareId = declareInfoBusiness.addDeclareInfo(declareInfoBean);
//			if (declareId <= 0) {
//				messageBean.setMessage("操作失败");
//				HttpResponseKit.printJson(request, response, messageBean, "");
//				return;
//			}
//		}
//
//		// 判断申报图片是否新建
//		DeclareImageBean dbExistImageBean = declareImageBusiness.getDeclareImageByKey(declareId,
//				DeclareImageInfoConfig.IMG_TYPE_ONE_INCH);
//		if (dbExistImageBean != null) {
//			// 修改图片信息
//			imageBean.setModifyUser(userId);
//			result = declareImageBusiness.updateDeclareImage(imageBean);
//		} else {
//			// 新建图片信息
//			imageBean.setImageType(DeclareImageInfoConfig.IMG_TYPE_ONE_INCH);
//			imageBean.setStatus(Constants.STATUS_VALID);
//			imageBean.setDeclareId(declareId);
//			imageBean.setCreateUser(userId);
//			result = declareImageBusiness.addDeclareImage(imageBean);
//		}
//		// 判断农民信息是否新建
//		FarmerInfoBean dbExistFarmerInfoBean = farmerInfoBusiness.getFarmerInfoByKey(declareId);
//		if (dbExistFarmerInfoBean != null) {
//			// 修改农民信息
//			farmerInfoBean.setModifyUser(userId);
//			result = farmerInfoBusiness.updateFarmerInfo(farmerInfoBean);
//		} else {
//			// 新建农民信息
//			farmerInfoBean.setCreateUser(userId);
//			farmerInfoBean.setStatus(Constants.STATUS_VALID);
//			farmerInfoBean.setDeclareId(declareId);
//			result = farmerInfoBusiness.addFarmerInfo(farmerInfoBean);
//		}
//
//		if (result >= 0) {
//			messageBean.setCode(Constants.STATUS_VALID);
//			messageBean.setMessage("操作成功");
//		} else {
//			messageBean.setMessage("操作失败");
//		}
//		HttpResponseKit.printJson(request, response, messageBean, "");
//		return;
//	}
//
//}
