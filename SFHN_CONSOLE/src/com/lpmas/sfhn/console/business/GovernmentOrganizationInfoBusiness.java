package com.lpmas.sfhn.console.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.console.dao.GovernmentOrganizationInfoDao;

public class GovernmentOrganizationInfoBusiness {
	public int addGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.insertGovernmentOrganizationInfo(bean);
	}

	public int updateGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.updateGovernmentOrganizationInfo(bean);
	}

	public GovernmentOrganizationInfoBean getGovernmentOrganizationInfoByKey(int organizationId) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoByKey(organizationId);
	}

	public PageResultBean<GovernmentOrganizationInfoBean> getGovernmentOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getProvince() == "") {
			if (bean.getOrganizationLevel() == 3 || bean.getOrganizationLevel() == 2
					|| bean.getOrganizationLevel() == 1) {
				result.setMessage("省必须填写");
			}
		}
		if (bean.getCity() == "") {
			if (bean.getOrganizationLevel() == 3 || bean.getOrganizationLevel() == 2) {
				result.setMessage("市必须填写");
			}
		}
		if (bean.getRegion() == "") {
			if (bean.getOrganizationLevel() == 3) {
				result.setMessage("区必须填写");
			}
		}

		return result;
	}

}