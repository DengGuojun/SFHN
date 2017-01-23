package com.lpmas.sfhn.portal.business;

import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.portal.dao.OrganizationUserDao;

public class OrganizationUserBusiness {

	public OrganizationUserBean getOrganizationUserByUserId(int userId) {
		OrganizationUserDao dao = new OrganizationUserDao();
		return dao.getOrganizationUserByUserId(userId);
	}

	public boolean isOrganizationUser(int userId) {
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean == null) {
			return false;
		}
		return true;
	}
	
}