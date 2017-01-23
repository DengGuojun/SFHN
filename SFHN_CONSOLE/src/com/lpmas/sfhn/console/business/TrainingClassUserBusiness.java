package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.region.bean.CityInfoBean;
import com.lpmas.region.bean.ProvinceInfoBean;
import com.lpmas.region.bean.RegionInfoBean;
import com.lpmas.region.client.RegionServiceClient;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.console.dao.TrainingClassUserDao;
import com.lpmas.sfhn.console.declare.business.DeclareReportBusiness;
import com.lpmas.sfhn.console.declare.business.IndustryInfoBusiness;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.portal.invoker.bean.ClassRoomMemberAddBean;

public class TrainingClassUserBusiness {

	public int updateTrainingClassUser(TrainingClassUserBean bean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.updateTrainingClassUser(bean);
	}

	public TrainingClassUserBean getTrainingClassUserByKey(int classId, int declareId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserByKey(classId, declareId);
	}

	public PageResultBean<TrainingClassUserBean> getTrainingClassUserPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserPageListByMap(condMap, pageBean);
	}

	public ClassRoomMemberAddBean trainingClassUser2MemberAddBean(TrainingClassUserBean bean, String activationCode) {
		ClassRoomMemberAddBean memberAddBean = new ClassRoomMemberAddBean();
		memberAddBean.setClassroomId(String.valueOf(bean.getClassId()));
		DeclareReportBusiness declareReportBusiness = new DeclareReportBusiness();
		DeclareReportBean declareReportBean = declareReportBusiness.getDeclareReportByKey(String.valueOf(bean
				.getDeclareId()));
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
		memberAddBean.setActivationCode(activationCode);
		return memberAddBean;
	}

	public TrainingClassUserBean getTrainingClassUserByDeclare(int declareId) {
		TrainingClassUserDao dao = new TrainingClassUserDao();
		return dao.getTrainingClassUserByDeclare(declareId);
	}

}