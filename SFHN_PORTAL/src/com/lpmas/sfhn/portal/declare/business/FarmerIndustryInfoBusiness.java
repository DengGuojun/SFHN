package com.lpmas.sfhn.portal.declare.business;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.lpmas.framework.annotation.FieldTag;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ReflectKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerIndustryInfoConfig;
import com.lpmas.sfhn.declare.config.FarmerInfoConfig;
import com.lpmas.sfhn.portal.declare.dao.FarmerIndustryInfoDao;

public class FarmerIndustryInfoBusiness {
	public int addFarmerIndustryInfo(FarmerIndustryInfoBean bean) {
		FarmerIndustryInfoDao dao = new FarmerIndustryInfoDao();
		return dao.insertFarmerIndustryInfo(bean);
	}

	public int updateFarmerIndustryInfo(FarmerIndustryInfoBean bean) {
		FarmerIndustryInfoDao dao = new FarmerIndustryInfoDao();
		return dao.updateFarmerIndustryInfo(bean);
	}

	public FarmerIndustryInfoBean getFarmerIndustryInfoByKey(int declareId) {
		FarmerIndustryInfoDao dao = new FarmerIndustryInfoDao();
		return dao.getFarmerIndustryInfoByKey(declareId);
	}

	public PageResultBean<FarmerIndustryInfoBean> getFarmerIndustryInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerIndustryInfoDao dao = new FarmerIndustryInfoDao();
		return dao.getFarmerIndustryInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerIndustryInfo(FarmerIndustryInfoBean bean, int declareType) {
		ReturnMessageBean result = new ReturnMessageBean();
		// 现代青年农场主
		if (declareType == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
			if (!StringKit.isValid(bean.getFarmerType())) {
				result.setMessage("人员类别必须填写");
			} else if (bean.getFarmerType().equals(FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER)) {
				if (bean.getFamilyPerson() == 0) {
					result.setMessage("家庭总人口必须填写且不能为0");
				} else if (bean.getFamilyWorkingPerson() == 0) {
					result.setMessage("家庭从事产业人数必须填写且不能为0");
				} else if (!StringKit.isValid(bean.getFamilyFarmType())) {
					result.setMessage("家庭农场类型必须填写");
				} else if (bean.getLastYearFamilyIncome() == 0) {
					result.setMessage("上年度家庭收入必须填写且不能为0");
				} else if (bean.getFarmLandScale() == 0) {
					result.setMessage("土地经营规模必须填写且不能为0");
				} else if (bean.getLastYearIncome() == 0) {
					result.setMessage("上年度产业收入必填且不能为0");
				} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
						|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
					result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
				} else if (bean.getIsExampleFamilyFarm() == Constants.STATUS_VALID
						&& (!StringKit.isValid(bean.getExampleFarmLevel())
								|| !FarmerIndustryInfoConfig.FARM_LEVEL_MAP.containsKey(bean.getExampleFarmLevel()))) {
					result.setMessage("示范性农场级别必须填写");
				}
			} else if (!StringKit.isValid(bean.getIndustryProvince()) || !StringKit.isValid(bean.getIndustryCity())
					|| !StringKit.isValid(bean.getIndustryRegion())) {
				result.setMessage("产业所在地必须填写");
			} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
					|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
				result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
			} else if (bean.getLastYearIncome() == 0) {
				result.setMessage("上年度产业收入必须填写且不能为0");
			} else if (bean.getAreaType() == 0) {
				result.setMessage("地区类型必须填写");
			} else if (bean.getEconomicAreaType() == 0) {
				result.setMessage("经济区域类型必须填写");
			}
		}
		// 生产经营职业农民
		if (declareType == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
			if (!StringKit.isValid(bean.getFarmerType())) {
				result.setMessage("人员类别必须填写");
			} else if (bean.getFarmerType().equals(FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER)) {
				if (bean.getFamilyPerson() == 0) {
					result.setMessage("家庭总人口必须填写且不能为0");
				} else if (bean.getFamilyWorkingPerson() == 0) {
					result.setMessage("家庭从事产业人数必须填写且不能为0");
				} else if (!StringKit.isValid(bean.getFamilyFarmType())) {
					result.setMessage("家庭农场类型必须填写");
				} else if (bean.getLastYearFamilyIncome() == 0) {
					result.setMessage("上年度家庭收入必须填写且不能为0");
				} else if (bean.getFarmLandScale() == 0) {
					result.setMessage("土地经营规模必须填写且不能为0");
				} else if (bean.getLastYearIncome() == 0) {
					result.setMessage("上年度产业收入必填且不能为0");
				} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
						|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
					result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
				} else if (bean.getIsExampleFamilyFarm() == Constants.STATUS_VALID
						&& (!StringKit.isValid(bean.getExampleFarmLevel())
								|| !FarmerIndustryInfoConfig.FARM_LEVEL_MAP.containsKey(bean.getExampleFarmLevel()))) {
					result.setMessage("示范性农场级别必须填写");
				}
			} else if (!StringKit.isValid(bean.getIndustryProvince()) || !StringKit.isValid(bean.getIndustryCity())
					|| !StringKit.isValid(bean.getIndustryRegion())) {
				result.setMessage("产业所在地必须填写");
			} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
					|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
				result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
			} else if (bean.getLastYearIncome() == 0) {
				result.setMessage("上年度产业收入必须填写且不能为0");
			}
		}
		// 现代农业经营带头人
		if (declareType == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
			if (!StringKit.isValid(bean.getFarmerType())) {
				result.setMessage("人员类别必须填写");
			} else if (bean.getFarmerType().equals(FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER)) {
				if (bean.getFamilyPerson() == 0) {
					result.setMessage("家庭总人口必须填写且不能为0");
				} else if (bean.getFamilyWorkingPerson() == 0) {
					result.setMessage("家庭从事产业人数必须填写且不能为0");
				} else if (!StringKit.isValid(bean.getFamilyFarmType())) {
					result.setMessage("家庭农场类型必须填写");
				} else if (bean.getLastYearFamilyIncome() == 0) {
					result.setMessage("上年度家庭收入必须填写且不能为0");
				} else if (bean.getLastYearIncome() == 0) {
					result.setMessage("上年度产业收入必填且不能为0");
				} else if (bean.getFarmLandScale() == 0) {
					result.setMessage("土地经营规模必须填写且不能为0");
				} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
						|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
					result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
				} else if (bean.getIsExampleFamilyFarm() == Constants.STATUS_VALID
						&& (!StringKit.isValid(bean.getExampleFarmLevel())
								|| !FarmerIndustryInfoConfig.FARM_LEVEL_MAP.containsKey(bean.getExampleFarmLevel()))) {
					result.setMessage("示范性农场级别必须填写");
				}
			} else if (bean.getFarmerType()
					.equals(FarmerInfoConfig.PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER)) {
				if (!StringKit.isValid(bean.getServiceScale())) {
					result.setMessage("服务规模必须填写");
				} else if (bean.getJobId() == 0) {
					result.setMessage("从事工作岗位必须填写");
				} else if (bean.getIncome() == 0) {
					result.setMessage("年收入必须填写且不能为0");
				} else if (bean.getExperience() == 0) {
					result.setMessage("从业年限必须填写且不能为0");
				}
			} else {
				if (!StringKit.isValid(bean.getIndustryProvince()) || !StringKit.isValid(bean.getIndustryCity())
						|| !StringKit.isValid(bean.getIndustryRegion())) {
					result.setMessage("产业所在地必须填写");
				} else if ((bean.getIndustryId1() == 0) || (bean.getIndustryTypeId1() == 0)
						|| (bean.getIndustryScale1() == 0) || (bean.getExperience1() == 0)) {
					result.setMessage("主体产业1,产业规模/从事年限必须填写且不能为0");
				} else if (bean.getLastYearIncome() == 0) {
					result.setMessage("上年度产业收入必须填写且不能为0");
				}
			}
		}

		// 对所有数值类型作非负判断
		for (Field field : BeanKit.getDeclaredFieldList(bean)) {
			Object value = ReflectKit.getPropertyValue(bean, field.getName());
			if (value != null) {
				if (value instanceof Integer) {
					if (((Integer) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				} else if (value instanceof Double) {
					if (((Double) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				}
			}
		}
		return result;
	}

}