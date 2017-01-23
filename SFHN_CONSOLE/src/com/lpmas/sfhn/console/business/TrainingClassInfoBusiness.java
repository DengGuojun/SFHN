package com.lpmas.sfhn.console.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.MapKit;
import com.lpmas.region.bean.CityInfoBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.bean.RegionInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.console.dao.TrainingClassInfoDao;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomAddBean;

public class TrainingClassInfoBusiness {

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

	

}