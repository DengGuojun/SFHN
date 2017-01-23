package com.lpmas.sfhn.console.declare.business;

import com.lpmas.sfhn.console.declare.dao.DeclareImageDao;
import com.lpmas.sfhn.declare.bean.DeclareImageBean;

public class DeclareImageBusiness {

	public DeclareImageBean getDeclareImageByKey(int declareId, String imageType) {
		DeclareImageDao dao = new DeclareImageDao();
		return dao.getDeclareImageByKey(declareId, imageType);
	}

}