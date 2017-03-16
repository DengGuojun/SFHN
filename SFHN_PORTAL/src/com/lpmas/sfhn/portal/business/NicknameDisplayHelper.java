package com.lpmas.sfhn.portal.business;

import com.lpmas.ow.passport.sso.business.SsoClientHelper;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;

public class NicknameDisplayHelper {

	public String getUserDisplayNicknameByUserId(SsoClientHelper helper) {
		StringBuffer sb = new StringBuffer();
		// 查出这个USER的类型
		OrganizationUserBusiness organizationUserBusiness = new OrganizationUserBusiness();
		OrganizationUserBean userBean = organizationUserBusiness.getOrganizationUserByUserId(helper.getUserId());
		if (userBean != null) {
			// 判断是培训机构还是政府
			if (userBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
				// 政府 管理部门的用 地域名+科教管理部门+昵称，如长沙县科教管理部门郑科
				GovernmentOrganizationInfoBusiness governmentOrganizationInfoBusiness = new GovernmentOrganizationInfoBusiness();
				GovernmentOrganizationInfoBean govBean = governmentOrganizationInfoBusiness
						.getGovernmentOrganizationInfoByKey(userBean.getOrganizationId());
				if (govBean != null) {
					sb.append(govBean.getOrganizationName());
				}
			} else if (userBean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
				// 培训机构用 地域名+培训机构名，如长沙市隆平现代农服
				TrainingOrganizationInfoBusiness trainingOrganizationInfoBusiness = new TrainingOrganizationInfoBusiness();
				TrainingOrganizationInfoBean trainingOrganizationInfoBean = trainingOrganizationInfoBusiness
						.getTrainingOrganizationInfoByKey(userBean.getOrganizationId());
				if (trainingOrganizationInfoBean != null) {
					sb.append(trainingOrganizationInfoBean.getProvince());
					sb.append(trainingOrganizationInfoBean.getCity());
					sb.append(trainingOrganizationInfoBean.getRegion());
					sb.append("-");
					sb.append(trainingOrganizationInfoBean.getOrganizationName());
				}
			}
		}
		return sb.toString();
	}

}
