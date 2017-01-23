package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.ccp.client.SmsMessageClient;
import com.lpmas.ccp.server.sms.bean.SmsMessageBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.crypto.MD5;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.ow.passport.user.bean.UserRegBean;
import com.lpmas.ow.passport.user.service.bean.UserRegAddRequestBean;
import com.lpmas.ow.passport.user.service.bean.UserRegQueryRequestBean;
import com.lpmas.ow.passport.user.service.client.UserServiceClient;
import com.lpmas.region.bean.CityInfoBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.bean.RegionInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.dao.TeacherInfoDao;
import com.lpmas.sfhn.portal.invoker.bean.TeacherAddBean;
import com.lpmas.sfhn.portal.invoker.config.YunClassGenderConfig;

public class TeacherInfoBusiness {
	public int addTeacherInfo(TeacherInfoBean bean) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.insertTeacherInfo(bean);
	}

	public int updateTeacherInfo(TeacherInfoBean bean) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.updateTeacherInfo(bean);
	}

	public TeacherInfoBean getTeacherInfoByKey(int teacherId) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.getTeacherInfoByKey(teacherId);
	}

	public TeacherInfoBean getTeacherInfoByUserId(int userId) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.getTeacherInfoByUserId(userId);
	}

	public TeacherInfoBean getTeacherInfoByMobileAndStatus(String teacherMobile, int status) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.getTeacherInfoByMobileAndStatus(teacherMobile, status);
	}

	public PageResultBean<TeacherInfoBean> getTeacherInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.getTeacherInfoPageListByMap(condMap, pageBean);
	}

	// 验证教师信息的正确性
	public ReturnMessageBean verifyTeacherInfo(TeacherInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getTeacherName())) {
			result.setMessage("请输入教师姓名");
		} else if (!StringKit.isValid(bean.getIdentityNumber())) {
			result.setMessage("身份证号码不能为空");
		} else if (!StringKit.isValid(bean.getTeacherMobile())) {
			result.setMessage("手机号码不能为空");
		} else if (!NumberKit.isAllDigit(bean.getTeacherMobile())) {
			result.setMessage("手机号码必须是数字");
		} else if (!StringKit.isValid(bean.getProvince()) && !StringKit.isValid(bean.getCity())
				&& !StringKit.isValid(bean.getRegion())) {
			result.setMessage("地区不能为空");
		} else if (isExistsTeacherInfo(bean)) {
			result.setMessage("系统已存在该师资信息");
		}
		return result;
	}

	public TeacherInfoBean getTeacherInfoByUserIdAndStatus(int status, int userId) {
		TeacherInfoDao dao = new TeacherInfoDao();
		return dao.getTeacherInfoByUserIdAndStatus(status, userId);
	}

	// 判断是否存在相同的用户ID
	public boolean isExistsTeacherInfo(TeacherInfoBean bean) {
		TeacherInfoDao dao = new TeacherInfoDao();
		TeacherInfoBean existsBean = dao.getTeacherInfoByUserIdAndStatus(Constants.STATUS_VALID, bean.getUserId());
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getTeacherId() == bean.getTeacherId()) {
				return false;
			}
		}
		return true;
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

	public TeacherAddBean teacherInfo2TeacherAddBean(TeacherInfoBean bean, String activationCode) {
		TeacherAddBean teacherAddBean = new TeacherAddBean();
		teacherAddBean.setUserId(String.valueOf(bean.getUserId()));
		teacherAddBean.setPhone(bean.getTeacherMobile());
		teacherAddBean.setIdcard(bean.getIdentityNumber());
		teacherAddBean.setActivationCode(activationCode);
		teacherAddBean.setName(bean.getTeacherName());
		teacherAddBean.setAge(String.valueOf(bean.getTeacherAge()));

		RegionServiceClient client = new RegionServiceClient();
		ProvinceInfoBean provinceBean = client.getProvinceInfoByName(bean.getProvince());
		teacherAddBean.setProvinceCode(provinceBean != null ? provinceBean.getProvinceCode() : "");
		CityInfoBean cityBean = client.getCityInfoByName(bean.getCity());
		teacherAddBean.setCityCode(cityBean != null ? cityBean.getCityCode() : "");
		RegionInfoBean regionBean = client.getRegionInfoByName(bean.getRegion());
		teacherAddBean.setCountyCode(regionBean != null ? regionBean.getRegionCode() : "");

		teacherAddBean.setSex(YunClassGenderConfig.GENDER_MAP.get(bean.getTeacherGender()));
		MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
		MajorInfoBean majorInfoBean = majorInfoBusiness.getMajorInfoByKey(bean.getMajorId());
		teacherAddBean.setProfession(majorInfoBean.getMajorName());
		teacherAddBean.setPrimaryTeachCourse(bean.getMainCourse());
		return teacherAddBean;
	}

	public static void main(String[] args) {
		int result = 0;
		String mobile = "15387581535";
		// 根据手机号获取userId
		UserServiceClient client = new UserServiceClient();
		// 先检查是否存在uesrId
		UserRegQueryRequestBean queryRequestBean = new UserRegQueryRequestBean();
		queryRequestBean.setUserLoginPhone(mobile);
		ReturnMessageBean returnMessage = client.getUserReg(queryRequestBean);
		if (null != returnMessage && returnMessage.getCode() == Constants.STATUS_VALID) {
			UserRegBean userRegBean = (UserRegBean) returnMessage.getContent();
			if (userRegBean != null && userRegBean.getUserId() != 0) {
				System.out.println(userRegBean.getUserId());
				result = 1;
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
			bean.setStoreId(23);
			bean.setPhoneAuthCode(MD5.getMD5(mobile + "s3fs3jha"));
			returnMessage = client.addUserReg(bean);
			if (null != returnMessage && returnMessage.getCode() == Constants.STATUS_VALID) {
				System.out.println("创建成功");
				result = Integer.valueOf((String) returnMessage.getContent());
				System.out.println(result);
				// 手机发送短信通知用户
				//notifyUser("13874969388", 23);
			}
		}
	}

}