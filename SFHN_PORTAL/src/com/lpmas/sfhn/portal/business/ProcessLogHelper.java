package com.lpmas.sfhn.portal.business;

import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.ProcessLogBean;
import com.lpmas.sfhn.portal.config.MessageInfoConfig;

public class ProcessLogHelper {

	public String getProcessTypeDescription(ProcessLogBean bean) {
		String result = "";
		if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_INFO) {
			result = "培训机构提交开班申请";
		} else if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_NEW_CLASS_ACCEPT) {
			result = "培训机构提交项目验收申请";
		} else if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_REJECT_CLASS_INFO
				|| bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_REJECT_CLASS_ACCEPT) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(bean.getOperatorId());
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				result = "县主管部门驳回";
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				result = "市主管部门驳回";
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
				result = "省主管部门驳回";
			}
		} else if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_APPROVE_CLASS_INFO
				|| bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_APPROVE_CLASS_ACCEPT) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(bean.getOperatorId());
			if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION) {
				result = "县主管部门审批";
			} else if (governmentOrgBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
				result = "市主管部门审批";
			} else {
				result = "完成";
			}

		} else if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_LIST) {
			if (bean.getOperatorType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
				result = "县主管部门上传名单";
			}
		} else if (bean.getProcessType() == MessageInfoConfig.MESSAGE_TYPE_CANDIDATE_REQUEST) {
			if (bean.getOperatorType() == InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION) {
				result = "申请继续招生";
			}
		}
		return result;
	}

	public String getOperatorOrganizationName(ProcessLogBean bean) {
		String result = "";
		if (bean.getOperatorType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(bean.getOperatorId());
			result = governmentOrgBean.getOrganizationName();
		} else {
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(bean
					.getOperatorId());
			result = trainingOrgBean.getOrganizationName();
		}
		return result;
	}

}
