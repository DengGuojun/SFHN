package com.lpmas.sfhn.portal.business;

import com.lpmas.framework.config.Constants;
import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.OrganizationUserBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.TrainingClassAcceptBean;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

public class TrainingClassStatusHelper {

	/**
	 * 判断该用户所属的政府机构是否有权限审批培训班 必须满足：培训班待审核的级别与政府级别一致，并且培训班所在地区与政府地区一致
	 */
	public boolean hasApprovePermission(TrainingClassInfoBean classInfoBean, int userId) {
		boolean result = false;
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				result = false;
			} else {
				if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())
						&& classInfoBean.getCity().equals(governmentOrgBean.getCity())
						&& classInfoBean.getRegion().equals(governmentOrgBean.getRegion())) {
					result = true;
				} else if (classInfoBean.getClassStatus()
						.equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())
						&& classInfoBean.getCity().equals(governmentOrgBean.getCity())) {
					result = true;
				} else if (classInfoBean.getClassStatus().equals(
						TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * 获取培训班的下一级审批状态
	 */
	public String getNextApproveStatus(TrainingClassInfoBean classInfoBean) {
		String result = TrainingClassInfoConfig.CLASS_STATUS_APPROVED;
		// 如果目前是县级审批，则查询市级政府机构是否需要审批
		if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), classInfoBean.getCity(), null,
							GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
			if (governmentOrgBean != null && governmentOrgBean.getIsNeedAudit() == Constants.STATUS_VALID) {
				result = TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY;
			}
		}
		// 如果目前是市级审批，则查询省级政府机构是否需要审批
		if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), null, null,
							GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE);
			if (governmentOrgBean != null && governmentOrgBean.getIsNeedAudit() == Constants.STATUS_VALID) {
				result = TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE;
			}
		}
		return result;
	}

	/**
	 * 获取培训班的下一级审批状态
	 */
	public String getNextRejectStatus(TrainingClassInfoBean classInfoBean) {
		String result = "";
		if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)) {
			result = TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION;
		} else if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)) {
			result = TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY;
		} else if (classInfoBean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)) {
			result = TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE;
		}
		return result;
	}
	
	/**
	 * 判断该用户所属的政府机构是否有权限审批培训班 必须满足：培训班待审核的级别与政府级别一致，并且培训班所在地区与政府地区一致
	 */
	public boolean hasApprovePermission(TrainingClassAcceptBean classAcceptBean, int userId) {
		boolean result = false;
		OrganizationUserBusiness userOrgbusiness = new OrganizationUserBusiness();
		OrganizationUserBean orgUserBean = userOrgbusiness.getOrganizationUserByUserId(userId);
		if (orgUserBean.getInfoType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(orgUserBean.getOrganizationId());
			if (governmentOrgBean.getStatus() == Constants.STATUS_NOT_VALID) {
				result = false;
			} else {
				TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
				TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(classAcceptBean.getClassId());
				if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())
						&& classInfoBean.getCity().equals(governmentOrgBean.getCity())
						&& classInfoBean.getRegion().equals(governmentOrgBean.getRegion())) {
					result = true;
				} else if (classAcceptBean.getAcceptStatus()
						.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())
						&& classInfoBean.getCity().equals(governmentOrgBean.getCity())) {
					result = true;
				} else if (classAcceptBean.getAcceptStatus().equals(
						TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)
						&& governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE
						&& classInfoBean.getProvince().equals(governmentOrgBean.getProvince())) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * 获取培训班的下一级审批状态
	 */
	public String getNextApproveStatus(TrainingClassAcceptBean classAcceptBean) {
		String result = TrainingClassInfoConfig.CLASS_STATUS_APPROVED;
		TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(classAcceptBean.getClassId());
		// 如果目前是县级审批，则查询市级政府机构是否需要审批
		if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), classInfoBean.getCity(), null,
							GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY);
			if (governmentOrgBean != null && governmentOrgBean.getIsNeedAudit() == Constants.STATUS_VALID) {
				result = TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY;
			}
		}
		// 如果目前是市级审批，则查询省级政府机构是否需要审批
		if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByRegion(classInfoBean.getProvince(), null, null,
							GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE);
			if (governmentOrgBean != null && governmentOrgBean.getIsNeedAudit() == Constants.STATUS_VALID) {
				result = TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE;
			}
		}
		return result;
	}

	/**
	 * 获取培训班的下一级审批状态
	 */
	public String getNextRejectStatus(TrainingClassAcceptBean classAcceptBean) {
		String result = "";
		if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)) {
			result = TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION;
		} else if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)) {
			result = TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY;
		} else if (classAcceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)) {
			result = TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE;
		}
		return result;
	}
}
