package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.NumberKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.FarmerContactInfoBean;
import com.lpmas.sfhn.portal.declare.dao.FarmerContactInfoDao;

public class FarmerContactInfoBusiness {
	public int addFarmerContactInfo(FarmerContactInfoBean bean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.insertFarmerContactInfo(bean);
	}

	public int updateFarmerContactInfo(FarmerContactInfoBean bean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.updateFarmerContactInfo(bean);
	}

	public FarmerContactInfoBean getFarmerContactInfoByKey(int declareId) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoByKey(declareId);
	}

	public FarmerContactInfoBean getFarmerContactInfoByUserMobileAndStatus(String userMobile, int status) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoByUserMobileAndStatus(userMobile, status);
	}

	public PageResultBean<FarmerContactInfoBean> getFarmerContactInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerContactInfoDao dao = new FarmerContactInfoDao();
		return dao.getFarmerContactInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerContactInfo(FarmerContactInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getUserMobile())) {
			result.setMessage("手机号码必须填写");
		} else if (!StringKit.isValid(bean.getProvince()) || !StringKit.isValid(bean.getCity())
				|| !StringKit.isValid(bean.getRegion())) {
			result.setMessage("户籍所在地必须填写");
		} else if (!StringKit.isValid(bean.getAddress())) {
			result.setMessage("通讯地址必须填写");
		} else if (!NumberKit.isAllDigit(bean.getUserMobile())) {
			result.setMessage("手机号码必须填数字");
		}
		return result;
	}

}