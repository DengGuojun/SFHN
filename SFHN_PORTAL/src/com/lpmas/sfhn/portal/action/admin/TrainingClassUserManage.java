package com.lpmas.sfhn.portal.action.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.tools.portal.PortalKit;
import com.lpmas.framework.util.ListKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;
import com.lpmas.sfhn.portal.bean.FileInfoBean;
import com.lpmas.sfhn.portal.business.FileInfoBusiness;
import com.lpmas.sfhn.portal.business.GovernmentOrganizationInfoBusiness;
import com.lpmas.sfhn.portal.business.MessageInfoBusiness;
import com.lpmas.sfhn.portal.business.NicknameDisplayHelper;
import com.lpmas.sfhn.portal.business.OrganizationUserBusiness;
import com.lpmas.sfhn.portal.business.TrainingClassUserBusiness;
import com.lpmas.sfhn.portal.config.FileInfoConfig;
import com.lpmas.sfhn.portal.config.SfhnPortalConfig;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.FarmerInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryTypeBusiness;

@WebServlet("/sfhn/admin/TrainingClassUserManage.do")
public class TrainingClassUserManage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrainingClassUserManage() {
		super();
		// TODO Auto-generated constructor stub
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
		int declareId = ParamKit.getIntParameter(request, "declareId", 0);

		String isAuth = TrainingClassUserConfig.HAS_NOT_AUTH;
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		// 从Mongo中获取相应的数据
		try {
			DeclareReportBean declareReportBean = declareReportBusiness
					.getDeclareReportByKey(String.valueOf(declareId));
			if (declareReportBean == null) {
				HttpResponseKit.alertMessage(response, "学员对象非法", HttpResponseKit.ACTION_HISTORY_BACK);
				return;
			}
			// 查是否认定
			TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
			TrainingClassUserBean trainingClassUserBean = trainingClassUserBusiness
					.getTrainingClassUserByDeclare(declareReportBean.getDeclareId());
			if (trainingClassUserBean != null && trainingClassUserBean.getAuthResult() == Constants.STATUS_VALID) {
				isAuth = TrainingClassUserConfig.HAS_AUTH;
			}
			FarmerInfoBusiness farmerInfoBusiness = new FarmerInfoBusiness();
			FarmerInfoBean farmerInfoBean = farmerInfoBusiness.getFarmerInfoByKey(declareId);
			if (farmerInfoBean != null) {
				// 获取附件信息
				FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
				HashMap<String, String> condMap = new HashMap<String, String>();
				condMap.put("fileType", String.valueOf(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SHEET));
				condMap.put("infoType", String.valueOf(InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO));
				condMap.put("fileName", farmerInfoBean.getIdentityNumber());
				condMap.put("status", String.valueOf(Constants.STATUS_VALID));
				List<FileInfoBean> fileInfoList = fileInfoBusiness.getFileInfoListByMap(condMap);
				if (!fileInfoList.isEmpty()) {
					request.setAttribute("fileId", fileInfoList.get(0).getFileId());
				}
			}
			// 获取产业类型列表
			IndustryTypeBusiness industryTypeBusiness = new IndustryTypeBusiness();
			List<IndustryTypeBean> industryTypeList = industryTypeBusiness.getIndustryTypeAllList();
			// 转换成MAP,ID是KEY
			Map<Integer, String> industryTypeMap = ListKit.list2Map(industryTypeList, "typeId", "typeName");

			SsoClientHelper helper = new SsoClientHelper(request, response);
			int clientUserId = helper.getUserId();

			// 查出这个人是什么身份
			OrganizationUserBusiness business = new OrganizationUserBusiness();
			OrganizationUserBean orgBean = business.getOrganizationUserByUserId(clientUserId);
			boolean isGovernment = false;
			if (orgBean != null && InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()) {
				isGovernment = true;
			}
			// 处理待办通知
			MessageInfoBusiness messageInfoBusiness = new MessageInfoBusiness();
			int unreadMessageCount = messageInfoBusiness.getUnreadMessageCount(orgBean);
			request.setAttribute("unreadMessageCount", unreadMessageCount);

			// 查政府部门级别
			if (InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION == orgBean.getInfoType()) {
				GovernmentOrganizationInfoBusiness governmentOrgInfoBusiness = new GovernmentOrganizationInfoBusiness();
				GovernmentOrganizationInfoBean governmentOrgInfoBean = governmentOrgInfoBusiness
						.getGovernmentOrganizationInfoByKey(orgBean.getOrganizationId());
				if (governmentOrgInfoBean != null) {
					request.setAttribute("GovernmentOrgInfoBean", governmentOrgInfoBean);
				}
			}

			request.setAttribute("isGovernment", isGovernment);
			NicknameDisplayHelper displayHelper = new NicknameDisplayHelper();
			request.setAttribute("UserName", displayHelper.getUserDisplayNicknameByUserId(helper));

			request.setAttribute("IndustryTypeMap", industryTypeMap);
			request.setAttribute("DeclareReportBean", declareReportBean);
			request.setAttribute("isAuth", isAuth);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PortalKit.forwardPage(request, response, SfhnPortalConfig.ADMIN_PAGE_PATH + "TrainingClassUserManage.jsp");
	}
}
