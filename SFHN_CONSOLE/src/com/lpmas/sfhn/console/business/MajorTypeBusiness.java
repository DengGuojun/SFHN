package com.lpmas.sfhn.console.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.bean.MajorTypeBean;
import com.lpmas.sfhn.console.dao.MajorTypeDao;

public class MajorTypeBusiness {
	public int addMajorType(MajorTypeBean bean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.insertMajorType(bean);
	}

	public int updateMajorType(MajorTypeBean bean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.updateMajorType(bean);
	}

	public MajorTypeBean getMajorTypeByKey(int majorId) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByKey(majorId);
	}

	public PageResultBean<MajorTypeBean> getMajorTypePageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypePageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyMajorType(MajorTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业类型名不能为空");
		} else if (bean.getStatus() == Constants.STATUS_VALID) {
			if (isExistsMajorTypeName(bean)) {
				result.setMessage("已存在相同的专业类型");
			}
		} else if (bean.getStatus() == Constants.STATUS_NOT_VALID) {
			MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
			List<MajorInfoBean> majorInfoList = majorInfoBusiness.getMajorInfoListByTypeId(bean.getMajorId());
			if (!majorInfoList.isEmpty()) {
				result.setMessage("该专业类型下包含专业信息，不能设置为无效");
			}
		}
		return result;
	}

	public List<MajorTypeBean> getMajorTypeAllList() {
		MajorTypeDao dao = new MajorTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorTypeListByMap(condMap);
	}

	// 判断是否存在相同的专业信息
	public boolean isExistsMajorTypeName(MajorTypeBean bean) {
		MajorTypeBean existsBean = getMajorTypeByNameAndSatus(bean);
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getMajorId() == bean.getMajorId()) {
				return false;
			}
		}
		return true;
	}

	// 查询是否存在相同的专业(在同一年份下,同一地方)
	public MajorTypeBean getMajorTypeByNameAndSatus(MajorTypeBean bean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByNameAndStatus(bean);
	}

	public List<MajorTypeBean> getMajorTypeListByMap(HashMap<String, String> condMap) {
		MajorTypeDao dao = new MajorTypeDao();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorTypeListByMap(condMap);
	}

}