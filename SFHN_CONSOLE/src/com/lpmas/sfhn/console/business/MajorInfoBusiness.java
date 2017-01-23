package com.lpmas.sfhn.console.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.MajorInfoBean;
import com.lpmas.sfhn.console.dao.MajorInfoDao;

public class MajorInfoBusiness {
	public int addMajorInfo(MajorInfoBean bean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.insertMajorInfo(bean);
	}

	public int updateMajorInfo(MajorInfoBean bean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.updateMajorInfo(bean);
	}

	public MajorInfoBean getMajorInfoByKey(int majorId) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByKey(majorId);
	}

	public PageResultBean<MajorInfoBean> getMajorInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoPageListByMap(condMap, pageBean);
	}

	public List<MajorInfoBean> getMajorInfoListByTypeId(int typeId) {
		MajorInfoDao dao = new MajorInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeId", String.valueOf(typeId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorInfoListByMap(condMap);
	}

	public ReturnMessageBean verifyMajorInfo(MajorInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业名称不能为空");
		} else if (bean.getTypeId() <= 0) {
			result.setMessage("专业类型必须选择");
		} else if (bean.getStatus() == Constants.STATUS_VALID) {
			// 修改状态
			if (isExistsMajorInfoName(bean)) {
				result.setMessage("已存在相同的专业信息");
			}
		} else if (bean.getStatus() == Constants.STATUS_NOT_VALID) {
			// 修改为无效状态 判断是否有补习班在引用 training_class_info
		}
		return result;
	}

	// 判断是否存在相同的专业信息
	public boolean isExistsMajorInfoName(MajorInfoBean bean) {
		MajorInfoBean existsBean = getMajorInfoByNameAndSatus(bean.getMajorName(), bean.getTypeId());
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getMajorId() == bean.getMajorId()) {
				return false;
			}
		}
		return true;
	}

	// 查询是否存在相同的专业(在同一专业类型下)
	public MajorInfoBean getMajorInfoByNameAndSatus(String majorName, int typeId) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByNameAndSatus(majorName, typeId);
	}

	public List<MajorInfoBean> getMajorInfoAllList() {
		MajorInfoDao dao = new MajorInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorInfoListByMap(condMap);
	}
	
	public List<MajorInfoBean> getMajorInfoListByMap(HashMap<String, String> condMap) {
		MajorInfoDao dao = new MajorInfoDao();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorInfoListByMap(condMap);
	}

}