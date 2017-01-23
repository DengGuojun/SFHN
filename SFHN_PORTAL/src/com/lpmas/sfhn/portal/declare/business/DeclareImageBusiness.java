package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.declare.bean.DeclareImageBean;
import com.lpmas.sfhn.declare.config.DeclareImageInfoConfig;
import com.lpmas.sfhn.portal.declare.dao.DeclareImageDao;

public class DeclareImageBusiness {
	public int addDeclareImage(DeclareImageBean bean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.insertDeclareImage(bean);
	}

	public int updateDeclareImage(DeclareImageBean bean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.updateDeclareImage(bean);
	}

	public DeclareImageBean getDeclareImageByKey(int declareId, String imageType) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.getDeclareImageByKey(declareId, imageType);
	}

	public PageResultBean<DeclareImageBean> getDeclareImagePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.getDeclareImagePageListByMap(condMap, pageBean);
	}

	public ReturnMessageBean verifyDeclareImageBean(DeclareImageBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getImageType())
				|| !bean.getImageType().trim().equals(DeclareImageInfoConfig.IMG_TYPE_ONE_INCH)) {
			result.setMessage("图片类型错误");
		} else if (!StringKit.isValid(bean.getImagePath())) {
			result.setMessage("图片路径必须填写");
		}
		return result;
	}

}