package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.ActiveCodeInfoBean;
import com.lpmas.sfhn.config.ActiveCodeInfoConfig;
import com.lpmas.sfhn.portal.bean.TeacherInfoBean;
import com.lpmas.sfhn.portal.dao.ActiveCodeInfoDao;
import com.lpmas.sfhn.portal.declare.business.DeclareInfoBusiness;

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

	public ActiveCodeInfoBean getActiveCodeInfoByCondition(String province, String city, String region,
			String trainingYear, String usageStatus) {
		ActiveCodeInfoDao dao = new ActiveCodeInfoDao();
		return dao.getActiveCodeInfoByCondition(province, city, region, trainingYear, usageStatus);
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

	public ReturnMessageBean bindActiveCodeWithUser(String province, String city, String region, String trainingYear,
			int userType, int userId, int modifyUser) {
		synchronized (ActiveCodeInfoBusiness.class) {
			ReturnMessageBean result = new ReturnMessageBean();
			// 检查是否农民或者教师
			if (userType == ActiveCodeInfoConfig.USER_TYPE_FARMER) {
				// 农民的话检查是不是老师
				TeacherInfoBusiness teacherInfoBusiness = new TeacherInfoBusiness();
				TeacherInfoBean teacherInfoBean = teacherInfoBusiness.getTeacherInfoByUserId(userId);
				if (teacherInfoBean != null) {
					result.setMessage("已经成为教师，不能参加新型职业农民培训");
					return result;
				}
			} else if (userType == ActiveCodeInfoConfig.USER_TYPE_TEACHER) {
				// 老师的话检查是不是农民
				DeclareInfoBusiness declareInfoBusiness = new DeclareInfoBusiness();
				int count = declareInfoBusiness.getDeclareInfoListByUserId(userId).size();
				if (count > 0) {
					result.setMessage("已经参加新型职业农民培训，不能成为教师");
					return result;
				}
			} else {
				result.setMessage("人员类型不正确");
				return result;
			}

			String fixProvince = province == null ? "" : province.trim();
			String fixCity = city == null ? "" : city.trim();
			String fixRegion = region == null ? "" : region.trim();
			String fixTrainingYear = trainingYear == null ? "" : trainingYear.trim();

			// 判断是否已经绑定了激活码
			Map<String, String> condMap = new HashMap<String, String>();
			condMap.put("userType", String.valueOf(userType));
			condMap.put("userId", String.valueOf(userId));
			condMap.put("trainingYear", fixTrainingYear);
			List<ActiveCodeInfoBean> activeCodeInfoList = getActiveCodeInfoListByMap(condMap);
			if (!activeCodeInfoList.isEmpty()) {
				// 有已经绑定的激活码，取出并返回
				result.setCode(Constants.STATUS_VALID);
				result.setMessage("激活码绑定成功");
				result.setContent(activeCodeInfoList.get(0).getActiveCode());
				return result;
			}

			// 根据条件查出一个当前未使用的激活码
			ActiveCodeInfoBean bean = getActiveCodeInfoByCondition(fixProvince, fixCity, fixRegion, trainingYear,
					ActiveCodeInfoConfig.USAGE_STATUS_UNUSE);
			// 检查是否有可用激活码
			if (bean == null) {
				result.setMessage("没有可用的激活码");
				return result;
			}
			// 占用该激活码
			bean.setUserId(userId);
			bean.setUserType(userType);
			bean.setUsageStatus(ActiveCodeInfoConfig.USAGE_STATUS_USED);
			bean.setModifyUser(modifyUser);
			if (updateActiveCodeInfo(bean) > 0) {
				result.setCode(Constants.STATUS_VALID);
				result.setMessage("激活码绑定成功");
				result.setContent(bean.getActiveCode());
			} else {
				result.setMessage("激活码绑定失败");
			}

			return result;
		}
	}

}