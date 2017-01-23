package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.DeclareInfoBean;
import com.lpmas.sfhn.declare.bean.FarmerInfoBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.declare.dao.FarmerInfoDao;

public class FarmerInfoBusiness {
	public int addFarmerInfo(FarmerInfoBean bean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.insertFarmerInfo(bean);
	}

	public int updateFarmerInfo(FarmerInfoBean bean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.updateFarmerInfo(bean);
	}

	public FarmerInfoBean getFarmerInfoByKey(int declareId) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoByKey(declareId);
	}

	public PageResultBean<FarmerInfoBean> getFarmerInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerInfoBean(FarmerInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		bean.setIdentityNumber(bean.getIdentityNumber().trim());
		if (!StringKit.isValid(bean.getUserName())) {
			result.setMessage("姓名必须填写");
		} else if (bean.getUserGender() == 0) {
			result.setMessage("性别必须填写");
		} else if (!StringKit.isValid(bean.getNationality())) {
			result.setMessage("民族必须填写");
		} else if (!StringKit.isValid(bean.getEducation())) {
			result.setMessage("文化程度必须填写");
		} else if (!StringKit.isValid(bean.getIdentityNumber())) {
			result.setMessage("身份证号必须填写");
		} else if (!StringKit.isValid(bean.getPoliticalStatus())) {
			result.setMessage("政治面貌必须填写");
		} else if (bean.getUserBirthday() == null) {
			result.setMessage("出生日期必须填写");
		}
		return result;
	}

	public ReturnMessageBean checkHasExistIdentityNumber(FarmerInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		// 检查身份证号码有无重复
		DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
		DeclareInfoBean exitDeclareInfoBean = null;
		// 查出已经提交审批的申报信息
		FarmerInfoDao dao = new FarmerInfoDao();
		// 先用身份证去查这个表
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("identityNumber", bean.getIdentityNumber());
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		List<FarmerInfoBean> farmerInfoList = dao.getFarmerInfoListByMap(condMap);
		for (FarmerInfoBean infoBean : farmerInfoList) {
			// 除非这个农民信息对应的申报信息是未提交的，否则不能继续填
			exitDeclareInfoBean = declareInfoBusiness.getDeclareInfoByKey(infoBean.getDeclareId());
			if (!exitDeclareInfoBean.getDeclareStatus().equals(DeclareInfoConfig.DECLARE_STATUS_EDIT)) {
				result.setMessage("对不起,该身份证号已被使用,不能重复使用");
				break;
			}
		}
		return result;
	}

	public ReturnMessageBean checkHasExistIdentityNumber(DeclareInfoBean bean) {
		FarmerInfoBean farmerInfoBean = getFarmerInfoByKey(bean.getDeclareId());
		return checkHasExistIdentityNumber(farmerInfoBean);
	}

	public List<FarmerInfoBean> getFarmerInfoListByMap(HashMap<String, String> condMap) {
		FarmerInfoDao dao = new FarmerInfoDao();
		return dao.getFarmerInfoListByMap(condMap);
	}

}