package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.console.declare.dao.IndustryTypeDao;
import com.lpmas.sfhn.declare.bean.IndustryInfoBean;
import com.lpmas.sfhn.declare.bean.IndustryTypeBean;

public class IndustryTypeBusiness {
	public int addIndustryType(IndustryTypeBean bean) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.insertIndustryType(bean);
	}

	public int updateIndustryType(IndustryTypeBean bean) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.updateIndustryType(bean);
	}

	public IndustryTypeBean getIndustryTypeByKey(int typeId) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.getIndustryTypeByKey(typeId);
	}

	public PageResultBean<IndustryTypeBean> getIndustryTypePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		IndustryTypeDao dao = new IndustryTypeDao();
		return dao.getIndustryTypePageListByMap(condMap, pageBean);
	}

	public List<IndustryTypeBean> getIndustryTypeAllList() {
		IndustryTypeDao dao = new IndustryTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getIndustryTypeListByMap(condMap);
	}

	public ReturnMessageBean verifyIndustryType(IndustryTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getTypeId() > 0 && bean.getStatus() == Constants.STATUS_NOT_VALID) {
			IndustryInfoBusiness industryInfoBusiness = new IndustryInfoBusiness();
			List<IndustryInfoBean> industryInfoList = industryInfoBusiness.getIndustryInfoListByType(bean.getTypeId());
			if (!industryInfoList.isEmpty()) {
				result.setMessage("该类型下包含产业信息，不能设置为无效");
			}
		}
		return result;
	}

}