package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.ccp.client.SmsMessageClient;
import com.lpmas.ccp.server.sms.bean.SmsMessageBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.crypto.MD5;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.user.bean.UserRegBean;
import com.lpmas.ow.passport.user.service.bean.UserRegAddRequestBean;
import com.lpmas.ow.passport.user.service.bean.UserRegQueryRequestBean;
import com.lpmas.ow.passport.user.service.client.UserServiceClient;
import com.lpmas.region.bean.CityInfoBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.bean.RegionInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.portal.config.TrainingClassUserConfig;
import com.lpmas.sfhn.portal.dao.TrainingClassUserDao;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;
import com.lpmas.sfhn.portal.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.portal.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomMemberAddBean;

public class TrainingClassUserBusiness {
	public int addTrainingClassUser(TrainingClassUserBean bean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.insertTrainingClassUser(bean);
	}

	public int updateTrainingClassUser(TrainingClassUserBean bean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.updateTrainingClassUser(bean);
	}

	public int updateTrainingClassUserByDeclare(TrainingClassUserBean bean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.updateTrainingClassUserByDeclare(bean);
	}

	public TrainingClassUserBean getTrainingClassUserByKey(int classId, int declareId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserByKey(classId, declareId);
	}

	public PageResultBean<TrainingClassUserBean> getTrainingClassUserPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserPageListByMap(condMap, pageBean);
	}

	public ClassRoomMemberAddBean getClassRoomMemberAddBeanByCondition(int userId, int classId) {
		ClassRoomMemberAddBean result = null;
		result = new ClassRoomMemberAddBean();
		result.setClassroomId(String.valueOf(classId));
		result.setUserId(String.valueOf(userId));
		return result;
	}

	public List<TrainingClassUserBean> getTrainingClassUserListByClassId(int classId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("userStatus", TrainingClassUserConfig.USER_STATUS_APPROVE);
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("classId", String.valueOf(classId));
		return dao.getTrainingClassUserListByMap(condMap);
	}

	public List<TrainingClassUserBean> getTrainingClassUserListByCondition(int classId, int userId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("userStatus", TrainingClassUserConfig.USER_STATUS_APPROVE);
		condMap.put("classId", String.valueOf(classId));
		condMap.put("userId", String.valueOf(userId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getTrainingClassUserListByMap(condMap);
	}

	public TrainingClassUserBean getTrainingClassUserByDeclare(int declareId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserByDeclare(declareId);
	}

	public PageResultBean<TrainingClassUserBean> getTrainingClassUserPageListByClassIdList(HashMap<String, String> condMap, PageBean pageBean,
			List<Integer> classInfoIdList) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserPageListByClassIdList(condMap, pageBean, classInfoIdList);
	}

	public ClassRoomMemberAddBean trainingClassUser2MemberAddBean(TrainingClassUserBean bean) {
		ClassRoomMemberAddBean memberAddBean = new ClassRoomMemberAddBean();
		memberAddBean.setClassroomId(String.valueOf(bean.getClassId()));
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		DeclareReportBean declareReportBean = declareReportBusiness.getDeclareReportByKey(String.valueOf(bean.getDeclareId()));
		if (declareReportBean != null) {
			memberAddBean.setUserId(String.valueOf(declareReportBean.getUserId()));
		}
		memberAddBean.setPhone(declareReportBean.getUserMobile());
		RegionServiceClient client = new RegionServiceClient();
		ProvinceInfoBean provinceBean = client.getProvinceInfoByName(declareReportBean.getProvince());
		memberAddBean.setProvinceCode(provinceBean != null ? provinceBean.getProvinceCode() : "");
		CityInfoBean cityBean = client.getCityInfoByName(declareReportBean.getCity());
		memberAddBean.setCityCode(cityBean != null ? cityBean.getCityCode() : "");
		RegionInfoBean regionBean = client.getRegionInfoByName(declareReportBean.getRegion());
		memberAddBean.setCountyCode(regionBean != null ? regionBean.getRegionCode() : "");
		memberAddBean.setIdcard(declareReportBean.getIdentityNumber());
		memberAddBean.setTrueName(declareReportBean.getUserName());
		IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
		IndustryInfoBean industryInfoBean = industryInfoBusiness.getIndustryInfoByKey(declareReportBean.getIndustryId1());
		String industryName = industryInfoBean != null ? industryInfoBean.getIndustryName() : "";
		memberAddBean.setTradeName(industryName);
		return memberAddBean;
	}

	public ClassRoomMemberAddBean trainingClassUser2MemberDeleteBean(TrainingClassUserBean bean) {
		ClassRoomMemberAddBean memberAddBean = new ClassRoomMemberAddBean();
		memberAddBean.setClassroomId(String.valueOf(bean.getClassId()));
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean declareInfoBean = declareInfoBusiness.getDeclareInfoByKey(bean.getDeclareId());
		if (declareInfoBean != null) {
			memberAddBean.setUserId(String.valueOf(declareInfoBean.getUserId()));
		}
		return memberAddBean;
	}

	public int getUserIdByUserClient(String mobile) {
		int result = 0;
		int storeId = 23;// 湘农云的StoreID
		// 根据手机号获取userId
		UserServiceClient client = new UserServiceClient();
		// 先检查是否存在uesrId
		UserRegQueryRequestBean queryRequestBean = new UserRegQueryRequestBean();
		queryRequestBean.setUserLoginPhone(mobile);
		ReturnMessageBean returnMessage = client.getUserReg(queryRequestBean);
		if (null != returnMessage && returnMessage.getCode() == Constants.STATUS_VALID) {
			UserRegBean userRegBean = (UserRegBean) returnMessage.getContent();
			if (userRegBean != null && userRegBean.getUserId() != 0) {
				result = userRegBean.getUserId();
			}
		}
		if (result == 0) {
			String userPassword = mobile.substring(mobile.length() - 6, mobile.length()); // defaultPassword
			UserRegAddRequestBean bean = new UserRegAddRequestBean();
			bean.setUserLoginPhone(mobile);
			bean.setUserPwd(userPassword);
			bean.setUserFrom(1);
			bean.setRegSource("SFHN_web");
			bean.setUserName(mobile);
			bean.setStoreId(storeId);
			bean.setPhoneAuthCode(MD5.getMD5(mobile + "s3fs3jha"));
			returnMessage = client.addUserReg(bean);
			if (null != returnMessage && returnMessage.getCode() == Constants.STATUS_VALID) {
				result = Integer.valueOf((String) returnMessage.getContent());
				// 手机发送短信通知用户
				notifyUser(mobile, storeId);
			}
		}
		return result;
	}

	private static void notifyUser(String mobile, int storeId) {
		HashMap<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("storeId", storeId);
		contentMap.put("templateId", 8);
		contentMap.put("indentyAccount", mobile);
		contentMap.put("userPwd", mobile.substring(mobile.length() - 6, mobile.length()));

		SmsMessageBean smsInfoBean = new SmsMessageBean();
		smsInfoBean.setData(contentMap);
		smsInfoBean.setMode("TEMPLATE");
		smsInfoBean.setStoreId(storeId);
		smsInfoBean.setTemplateId(8);
		smsInfoBean.setToMobileNumber(mobile);
		smsInfoBean.setToUserId("");
		smsInfoBean.setContent("");
		SmsMessageClient smsSenderClient = new SmsMessageClient();
		try {
			smsSenderClient.send(smsInfoBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}