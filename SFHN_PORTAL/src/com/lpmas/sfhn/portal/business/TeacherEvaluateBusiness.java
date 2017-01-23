package com.lpmas.sfhn.portal.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.sfhn.portal.bean.TeacherEvaluateBean;
import com.lpmas.sfhn.portal.dao.TeacherEvaluateDao;

public class TeacherEvaluateBusiness {
	public int addTeacherEvaluate(TeacherEvaluateBean bean) {
		TeacherEvaluateDao dao = new TeacherEvaluateDao();
		return dao.insertTeacherEvaluate(bean);
	}

	public int updateTeacherEvaluate(TeacherEvaluateBean bean) {
		TeacherEvaluateDao dao = new TeacherEvaluateDao();
		return dao.updateTeacherEvaluate(bean);
	}

	public TeacherEvaluateBean getTeacherEvaluateByKey(int evaluateId) {
		TeacherEvaluateDao dao = new TeacherEvaluateDao();
		return dao.getTeacherEvaluateByKey(evaluateId);
	}

	public PageResultBean<TeacherEvaluateBean> getTeacherEvaluatePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		TeacherEvaluateDao dao = new TeacherEvaluateDao();
		return dao.getTeacherEvaluatePageListByMap(condMap, pageBean);
	}
	
	public List<TeacherEvaluateBean> getTeacherEvaluateListByMap(HashMap<String, String> condMap) {
		TeacherEvaluateDao dao = new TeacherEvaluateDao();
		return dao.getTeacherEvaluateListByMap(condMap);
	}

}