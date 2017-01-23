package com.lpmas.sfhn.portal.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.MapKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.region.bean.CityInfoBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.bean.RegionInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.dao.TrainingClassInfoDao;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomAddBean;

public class TrainingClassInfoBusiness {
	public int addTrainingClassInfo(TrainingClassInfoBean bean) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.insertTrainingClassInfo(bean);
	}

	public int updateTrainingClassInfo(TrainingClassInfoBean bean) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.updateTrainingClassInfo(bean);
	}

	public TrainingClassInfoBean getTrainingClassInfoByKey(int classId) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.getTrainingClassInfoByKey(classId);
	}

	public ClassRoomAddBean TrainingClassInfo2ClassRoomAddBean(TrainingClassInfoBean bean) {
		ClassRoomAddBean classRoomAddBean = new ClassRoomAddBean();
		classRoomAddBean.setTitle(bean.getClassName());
		classRoomAddBean
				.setCategory(MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP));
		classRoomAddBean.setAbout("");
		classRoomAddBean.setClassroomId(String.valueOf(bean.getClassId()));
		classRoomAddBean.setType(String.valueOf(bean.getClassType()));
		Date startDate = (Date) bean.getTrainingBeginTime();
		classRoomAddBean.setStartTime(String.valueOf(startDate.getTime() / 1000));
		Date endDate = (Date) bean.getTrainingEndTime();
		classRoomAddBean.setEndTime(String.valueOf(endDate.getTime() / 1000));

		RegionServiceClient client = new RegionServiceClient();
		ProvinceInfoBean provinceBean = client.getProvinceInfoByName(bean.getProvince());
		classRoomAddBean.setProvinceCode(provinceBean != null ? provinceBean.getProvinceCode() : "");
		CityInfoBean cityBean = client.getCityInfoByName(bean.getCity());
		classRoomAddBean.setCityCode(cityBean != null ? cityBean.getCityCode() : "");
		RegionInfoBean regionBean = client.getRegionInfoByName(bean.getRegion());
		classRoomAddBean.setRegionCode(regionBean != null ? regionBean.getRegionCode() : "");
		classRoomAddBean.setEducateYear(bean.getTrainingYear());
		return classRoomAddBean;
	}

	public PageResultBean<TrainingClassInfoBean> getTrainingClassInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.getTrainingClassInfoPageListByMap(condMap, pageBean);
	}

	public List<TrainingClassInfoBean> getTrainingClassInfoListByMap(HashMap<String, String> condMap) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.getTrainingClassInfoListByMap(condMap);
	}

	public List<TrainingClassInfoBean> getTrainingClassInfoListByCondition(int declareType, String province,
			String city, String region) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("trainingType", String.valueOf(declareType));
		condMap.put("province", province);
		condMap.put("city", city);
		condMap.put("region", region);
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		condMap.put("classStatusSelection", TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
		String currentYear = DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR);
		condMap.put("trainingYear", currentYear);
		condMap.put("orderBy", "registration_end_time desc");
		return dao.getTrainingClassInfoListByMap(condMap);
	}

	public int getTrainingClassInfoCountByMap(HashMap<String, String> condMap) {
		TrainingClassInfoDao dao = new TrainingClassInfoDao();
		return dao.getTrainingClassInfoCountByMap(condMap);
	}

	public boolean hasPermission(int classId, int userId) {
		OrganizationUserBusiness orgUserbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = orgUserbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			return false;
		}
		TrainingClassInfoBean classBean = getTrainingClassInfoByKey(classId);
		if (classBean == null) {
			return false;
		}
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			// 当用户是政府机构时
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				return false;
			}
			// 根据政府机构的机构级别判断权限
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
				return governmentOrgBean.getProvince().equals(classBean.getProvince());
			} else if (governmentOrgBean
					.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				return governmentOrgBean.getProvince().equals(classBean.getProvince())
						&& governmentOrgBean.getCity().equals(classBean.getCity());
			} else if (governmentOrgBean
					.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				return governmentOrgBean.getProvince().equals(classBean.getProvince())
						&& governmentOrgBean.getCity().equals(classBean.getCity())
						&& governmentOrgBean.getRegion().equals(classBean.getRegion());
			}
		} else if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
			// 用户是培训机构，查看ID是否是其所属
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness
					.getTrainingOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (trainingOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				return false;
			}
			return trainingOrgBean.getOrganizationId() == classBean.getOrganizationId();
		}
		return false;
	}

	public ReturnMessageBean verifyTrainingClassInfo(TrainingClassInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getClassName())) {
			result.setMessage("班级名称必须填写");
		} else if (bean.getTrainingType() == 0) {
			result.setMessage("培育类型必须填写");
		} else if (bean.getClassPeopleQuantity() <= 0) {
			result.setMessage("开班人数必须填写且必须大于0");
		} else if (bean.getTrainingBeginTime() == null) {
			result.setMessage("课程开始时间必须填写");
		} else if (bean.getTrainingEndTime() == null) {
			result.setMessage("课程结束时间必须填写");
		} else if (!StringKit.isValid(bean.getTrainingTime())) {
			result.setMessage("培育时间必须填写");
		} else if (!StringKit.isValid(bean.getTrainingSchedule())) {
			result.setMessage("行程安排必须填写");
		} else if (bean.getRegistrationEndTime() == null) {
			result.setMessage("报名截止时间必须填写");
		}
		return result;
	}

}