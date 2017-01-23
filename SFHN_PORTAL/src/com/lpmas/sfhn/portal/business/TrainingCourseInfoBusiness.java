package com.lpmas.sfhn.portal.business;

import java.util.HashMap;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.portal.bean.TrainingCourseInfoBean;
import com.lpmas.sfhn.portal.dao.TrainingCourseInfoDao;

public class TrainingCourseInfoBusiness {
	public int addTrainingCourseInfo(TrainingCourseInfoBean bean) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		return dao.insertTrainingCourseInfo(bean);
	}

	public int updateTrainingCourseInfo(TrainingCourseInfoBean bean) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		return dao.updateTrainingCourseInfo(bean);
	}

	public TrainingCourseInfoBean getTrainingCourseInfoByKey(int courseId) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		return dao.getTrainingCourseInfoByKey(courseId);
	}
	
	public TrainingCourseInfoBean getTrainingCourseInfoByName(String courseName) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		return dao.getTrainingCourseInfoByName(courseName);
	}

	public PageResultBean<TrainingCourseInfoBean> getTrainingCourseInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		return dao.getTrainingCourseInfoPageListByMap(condMap, pageBean);
	}
	
	public ReturnMessageBean verifyTrainingCourseInfo(TrainingCourseInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getCourseName())) {
			result.setMessage("请输入课程名称");
		} else if (bean.getMajorTypeId()==0) {
			result.setMessage("专业不能为空");
		} else if (bean.getMajorId()==0) {
			result.setMessage("专业不能为空");
		} else if (isExistsTrainingCourseInfo(bean)) {
			result.setMessage("系统已存在该课程信息");
		}
		return result;
	}

	
	public boolean isExistsTrainingCourseInfo(TrainingCourseInfoBean bean) {
		TrainingCourseInfoDao dao = new TrainingCourseInfoDao();
		TrainingCourseInfoBean existsBean = dao.getTrainingCourseInfoByName(bean.getCourseName());
		if (existsBean == null || existsBean.getStatus() == Constants.STATUS_NOT_VALID) {
			return false;
		} else {
			if (existsBean.getCourseId() == bean.getCourseId()) {
				return false;
			}
		}
		return true;
	}
}