package com.lpmas.sfhn.portal.business;

import com.lpmas.sfhn.bean.GovernmentOrganizationInfoBean;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.InfoTypeConfig;
import com.lpmas.sfhn.portal.bean.AnnouncementInfoBean;
import com.lpmas.sfhn.portal.bean.MessageInfoBean;

public class MessageInfoHelper {

	public String getMessageTitle(MessageInfoBean bean) {
		String result = "";
		if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO
				|| bean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT
				|| bean.getInfoType() == InfoTypeConfig.INFO_TYPE_RECRUITMENT) {
			TrainingClassInfoBusiness classInfoBusiness = new TrainingClassInfoBusiness();
			TrainingClassInfoBean classInfoBean = classInfoBusiness.getTrainingClassInfoByKey(bean.getInfoId());
			result = classInfoBean.getClassName();
		} else if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT) {
			AnnouncementInfoBusiness announcementBusiness = new AnnouncementInfoBusiness();
			AnnouncementInfoBean announcementBean = announcementBusiness.getAnnouncementInfoByKey(bean.getInfoId());
			result = announcementBean.getAnnouncementTitle();
		}
		return result;
	}

	public String getSendOrganizationName(MessageInfoBean bean) {
		String result = "";
		if (bean.getSendOrganizationType() == InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION) {
			GovernmentOrganizationInfoBusiness governmentOrgBusiness = new GovernmentOrganizationInfoBusiness();
			GovernmentOrganizationInfoBean governmentOrgBean = governmentOrgBusiness
					.getGovernmentOrganizationInfoByKey(bean.getSendOrganizationId());
			result = governmentOrgBean.getOrganizationName();
		} else {
			TrainingOrganizationInfoBusiness trainingOrgBusiness = new TrainingOrganizationInfoBusiness();
			TrainingOrganizationInfoBean trainingOrgBean = trainingOrgBusiness.getTrainingOrganizationInfoByKey(bean
					.getSendOrganizationId());
			result = trainingOrgBean.getOrganizationName();
		}
		return result;
	}

	public String getMessageInfoDetailUrl(MessageInfoBean bean) {
		String result = "";
		if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO) {
			result = "TrainingClassInfoProcess.do?classId=" + bean.getInfoId() + "&messageId=" + bean.getMessageId();
		} else if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_ACCEPT) {
			result = "TrainingClassAcceptProcess.do?classId=" + bean.getInfoId() + "&messageId=" + bean.getMessageId();
		} else if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT) {
			AnnouncementInfoBusiness announcementBusiness = new AnnouncementInfoBusiness();
			AnnouncementInfoBean announcementBean = announcementBusiness.getAnnouncementInfoByKey(bean.getInfoId());
			result = "AnnouncementInfoView.do?announcementId=" + announcementBean.getAnnouncementId() + "&messageId="
					+ bean.getMessageId();
		} else if (bean.getInfoType() == InfoTypeConfig.INFO_TYPE_RECRUITMENT) {
			result = "TrainingClassRecruitProcess.do?classId=" + bean.getInfoId() + "&messageId=" + bean.getMessageId();
		}
		return result;

	}
}
