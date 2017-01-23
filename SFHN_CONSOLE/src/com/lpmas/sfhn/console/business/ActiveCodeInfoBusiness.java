package com.lpmas.sfhn.console.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.RandomKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.console.config.SfhnConsoleConfig;
import com.lpmas.sfhn.console.dao.ActiveCodeInfoDao;
import com.lpmas.sfhn.console.declare.business.DeclareInfoBusiness;

public class ActiveCodeInfoBusiness {
	public int addActiveCodeInfo(ActiveCodeInfoBean bean) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.insertActiveCodeInfo(bean);
	}

	public int updateActiveCodeInfo(ActiveCodeInfoBean bean) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.updateActiveCodeInfo(bean);
	}

	public ActiveCodeInfoBean getActiveCodeInfoByKey(int activeCodeId) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeInfoByKey(activeCodeId);
	}

	public ActiveCodeInfoBean getActiveCodeInfoByActiveCode(String activeCode) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeInfoByActiveCode(activeCode);
	}

	public List<ActiveCodeInfoBean> getActiveCodeInfoListByMap(Map<String, String> condMap) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeInfoListByMap(condMap);
	}
	
	public PageResultBean<ActiveCodeInfoBean> getActiveCodeInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeInfoPageListByMap(condMap, pageBean);
	}
	
	public int getActiveCodeCountByMap(HashMap<String, String> condMap) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeCountByMap(condMap);
	}
	

	public int generateActiveCode(String province, String city, String region, String trainingYear, int createUser) {
		// N位随机串做激活码
		String activeCode = RandomKit.getRandomString(RandomKit.CS_ALPHANUM, SfhnConsoleConfig.ACTIVE_CODE_LENGTH);
		while (true) {
			// 排除激活码重复
			if (getActiveCodeInfoByActiveCode(activeCode) != null) {
				activeCode = RandomKit.getRandomString(RandomKit.CS_ALPHANUM, SfhnConsoleConfig.ACTIVE_CODE_LENGTH);
			} else {
				break;
			}
		}

		ActiveCodeInfoBean activeCodeInfoBean = new ActiveCodeInfoBean();
		activeCodeInfoBean.setActiveCode(activeCode);
		activeCodeInfoBean.setProvince(province);
		activeCodeInfoBean.setCity(city);
		activeCodeInfoBean.setRegion(region);
		activeCodeInfoBean.setTrainingYear(trainingYear);
		activeCodeInfoBean.setStatus(Constants.STATUS_VALID);
		activeCodeInfoBean.setCreateUser(createUser);
		activeCodeInfoBean.setUsageStatus(ActiveCodeInfoConfig.USAGE_STATUS_UNUSE);

		return addActiveCodeInfo(activeCodeInfoBean);
	}

	public int batchGenerateActiveCode(String province, String city, String region, String trainingYear, int createUser,
			int count) {
		int successCount = 0;
		// 循环插入激活码
		for (int i = 0; i < count; i++) {
			if (generateActiveCode(province, city, region, trainingYear, createUser) > 0) {
				successCount++;
			} else {
				continue;
			}
		}
		return successCount;
	}

}