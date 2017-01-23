package com.lpmas.sfhn.portal.declare.business;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.lpmas.framework.annotation.FieldTag;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.ReflectKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.FarmerJobInfoBean;
import com.lpmas.sfhn.portal.declare.dao.FarmerJobInfoDao;

public class FarmerJobInfoBusiness {
	public int addFarmerJobInfo(FarmerJobInfoBean bean) {
		FarmerJobInfoDao dao = new FarmerJobInfoDao();
		return dao.insertFarmerJobInfo(bean);
	}

	public int updateFarmerJobInfo(FarmerJobInfoBean bean) {
		FarmerJobInfoDao dao = new FarmerJobInfoDao();
		return dao.updateFarmerJobInfo(bean);
	}

	public FarmerJobInfoBean getFarmerJobInfoByKey(int declareId) {
		FarmerJobInfoDao dao = new FarmerJobInfoDao();
		return dao.getFarmerJobInfoByKey(declareId);
	}

	public PageResultBean<FarmerJobInfoBean> getFarmerJobInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		FarmerJobInfoDao dao = new FarmerJobInfoDao();
		return dao.getFarmerJobInfoPageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyFarmerJobInfo(FarmerJobInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (bean.getJobType() == 0 || bean.getJobId() == 0) {
			result.setMessage("从事工种/岗位必须填写");
		} else if (!StringKit.isValid(bean.getCompanyType())) {
			result.setMessage("从业单位类别必须填写");
		} else if (!StringKit.isValid(bean.getJobProvince()) || !StringKit.isValid(bean.getJobCity())
				|| !StringKit.isValid(bean.getJobRegion())) {
			result.setMessage("工作地点必须填写");
		} else if (bean.getExperience() == 0) {
			result.setMessage("从业年限必须填写且不能为0");
		} else if (bean.getIncome() == 0) {
			result.setMessage("从事工种/岗位年收入必须填写且不能为0");
		}
		// 对所有数值类型作非负判断
		for (Field field : BeanKit.getDeclaredFieldList(bean)) {
			Object value = ReflectKit.getPropertyValue(bean, field.getName());
			if (value != null) {
				if (value instanceof Integer) {
					if (((Integer) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				} else if (value instanceof Double) {
					if (((Double) value) < 0)
						result.setMessage(field.getAnnotation(FieldTag.class).name() + "不能小于0");
				}
			}
		}
		return result;
	}

}