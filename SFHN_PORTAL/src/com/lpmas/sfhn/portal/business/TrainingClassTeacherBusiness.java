package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.portal.bean.TrainingClassTeacherBean;
import com.lpmas.sfhn.portal.declare.dao.TrainingClassTeacherDao;


public class TrainingClassTeacherBusiness {
	public int addTrainingClassTeacher(TrainingClassTeacherBean bean) {
		TrainingClassTeacherDao dao = new TrainingClassTeacherDao();
		return dao.insertTrainingClassTeacher(bean);
	}

	public int updateTrainingClassTeacher(TrainingClassTeacherBean bean) {
		TrainingClassTeacherDao dao = new TrainingClassTeacherDao();
		return dao.updateTrainingClassTeacher(bean);
	}

	public TrainingClassTeacherBean getTrainingClassTeacherByKey(int classId, int teacherId) {
		TrainingClassTeacherDao dao = new TrainingClassTeacherDao();
		return dao.getTrainingClassTeacherByKey(classId, teacherId);
	}

	public PageResultBean<TrainingClassTeacherBean> getTrainingClassTeacherPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		TrainingClassTeacherDao dao = new TrainingClassTeacherDao();
		return dao.getTrainingClassTeacherPageListByMap(condMap, pageBean);
	}
	
	public List<TrainingClassTeacherBean> getTrainingClassTeacherListByClassId(int classId) {
		TrainingClassTeacherDao dao = new TrainingClassTeacherDao();
		return dao.getTrainingClassTeacherListByClassId(classId);
	}

}