package com.lpmas.sfhn.console.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.web.ReturnMessageBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.console.dao.OrganizationUserDao;

public class OrganizationUserBusiness {
	public int addOrganizationUser(OrganizationUserBean bean) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.insertOrganizationUser(bean);
	}

	public int updateOrganizationUser(OrganizationUserBean bean) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.updateOrganizationUser(bean);
	}

	public int updateOrganizationUserByCondition(OrganizationUserBean bean) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.updateOrganizationUserByCondition(bean);
	}

	public OrganizationUserBean getOrganizationUserByKey(int organizationId, int userId, int organizationType) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.getOrganizationUserByKey(organizationId, userId, organizationType);
	}

	public List<OrganizationUserBean> getOrganizationUserListByMap(HashMap<String, String> condMap) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.getOrganizationUserListByMap(condMap);
	}

	public ReturnMessageBean verifyOrganizationUser(int organizationId, int userId, int organizationType) {
		ReturnMessageBean result = new ReturnMessageBean();
		OrganizationUserBean organizationUserBean = getOrganizationUserByKey(organizationId, userId, organizationType);
		if (organizationUserBean != null) {
			result.setMessage("已存在相同客户ID,请重新输入");
		}
		for (OrganizationUserBean bean : getOrganizationUserAllList()) {
			if (userId == bean.getUserId()) {
				result.setMessage("已存在相同客户ID,请重新输入");
			}
		}
		return result;
	}

	public List<OrganizationUserBean> getOrganizationUserAllList() {
		OrganizationUserDao dao = new OrganizationUserDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		return dao.getOrganizationUserListByMap(condMap);
	}

	public int deleteOrganizationUser(OrganizationUserBean bean) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.deleteOrganizationUser(bean);
	}

}