package com.lpmas.sfhn.portal.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.NumeralOperationKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;

public class TrainingClassUserCountBusiness {
	public List<String> getTrainingClassUserCountList(TrainingOrganizationInfoBean trainingOrganizationInfoBean,
			String level) {
		// 统计不同类型的开班人数
		int youngFarmerCount = 0;
		int productFarmerCount = 0;
		int technicalFarmerCount = 0;
		int serviceFarmerCount = 0;
		int leaderFarmerCount = 0;
		// 认定人数
		int youngFarmerAuth = 0;
		int productFarmerAuth = 0;
		int technicalFarmerAuth = 0;
		int serviceFarmerAuth = 0;
		int leaderFarmerAuth = 0;
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<String, String> trainingClassCondMap = new HashMap<String, String>();
		trainingClassCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		trainingClassCondMap.put("acceptStatus", TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
		trainingClassCondMap.put("organizationId", String.valueOf(trainingOrganizationInfoBean.getOrganizationId()));
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(trainingClassCondMap);
		if (!trainingClassInfoList.isEmpty()) {
			for (TrainingClassInfoBean trainingClassInfoBean : trainingClassInfoList) {
				List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
						.getTrainingClassUserListByClassId(trainingClassInfoBean.getClassId());
				int tempCount = 0;
				int tempAuth = 0;
				if (!trainingClassUserList.isEmpty()) {
					tempCount = trainingClassUserList.size();
					for (TrainingClassUserBean trainingClassUserBean : trainingClassUserList) {
						if (trainingClassUserBean.getAuthResult() == Constants.STATUS_VALID) {
							++tempAuth;
						}
					}
				}

				// 现代青年农场主
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					youngFarmerCount = youngFarmerCount + tempCount;
					youngFarmerAuth = youngFarmerAuth + tempAuth;
				}
				// 生产经营职业农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					productFarmerCount = productFarmerCount + tempCount;
					productFarmerAuth = productFarmerAuth + tempAuth;
				}
				// 专业技能型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
					technicalFarmerCount = technicalFarmerCount + tempCount;
					technicalFarmerAuth = technicalFarmerAuth + tempAuth;
				}
				// 专业服务型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
					serviceFarmerCount = serviceFarmerCount + tempCount;
					serviceFarmerAuth = serviceFarmerAuth + tempAuth;
				}
				// 现代农业经营带头人
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					leaderFarmerCount = leaderFarmerCount + tempCount;
					leaderFarmerAuth = leaderFarmerAuth + tempAuth;
				}
			}

		}
		int generalConut = youngFarmerCount + productFarmerCount + technicalFarmerCount + serviceFarmerCount
				+ leaderFarmerCount;
		int generalAuth = youngFarmerAuth + productFarmerAuth + technicalFarmerAuth + serviceFarmerAuth
				+ leaderFarmerAuth;

		// 计算百分比
		String youngFarmerPercent = "0";
		String productFarmerPercent = "0";
		String technicalFarmerPercent = "0";
		String serviceFarmerPercent = "0";
		String leaderFarmerPercent = "0";
		String generalPercent = "0";
		if (youngFarmerCount != 0) {
			youngFarmerPercent = NumeralOperationKit.calculatePercent(youngFarmerAuth, youngFarmerCount, 1);
		}
		if (productFarmerCount != 0) {
			productFarmerPercent = NumeralOperationKit.calculatePercent(productFarmerAuth, productFarmerCount, 1);
		}
		if (technicalFarmerCount != 0) {
			technicalFarmerPercent = NumeralOperationKit.calculatePercent(technicalFarmerAuth, technicalFarmerCount, 1);
		}
		if (serviceFarmerCount != 0) {
			serviceFarmerPercent = NumeralOperationKit.calculatePercent(serviceFarmerAuth, serviceFarmerCount, 1);
		}
		if (leaderFarmerCount != 0) {
			leaderFarmerPercent = NumeralOperationKit.calculatePercent(leaderFarmerAuth, leaderFarmerCount, 1);
		}
		if (generalConut != 0) {
			generalPercent = NumeralOperationKit.calculatePercent(generalAuth, generalConut, 1);
		}
		// 放入显示target的List
		List<String> tempList = new ArrayList<String>();
		tempList.add(level);
		// 所有
		tempList.add(String.valueOf(generalAuth));
		tempList.add(String.valueOf(generalConut));
		tempList.add(generalPercent + "%");
		// 生产经营
		tempList.add(String.valueOf(productFarmerAuth));
		tempList.add(String.valueOf(productFarmerCount));
		tempList.add(productFarmerPercent + "%");
		// 专业技能
		tempList.add(String.valueOf(technicalFarmerAuth));
		tempList.add(String.valueOf(technicalFarmerCount));
		tempList.add(technicalFarmerPercent + "%");
		// 社会服务
		tempList.add(String.valueOf(serviceFarmerAuth));
		tempList.add(String.valueOf(serviceFarmerCount));
		tempList.add(serviceFarmerPercent + "%");
		// 青年农场主
		tempList.add(String.valueOf(youngFarmerAuth));
		tempList.add(String.valueOf(youngFarmerCount));
		tempList.add(youngFarmerPercent + "%");
		// 带头人
		tempList.add(String.valueOf(leaderFarmerAuth));
		tempList.add(String.valueOf(leaderFarmerCount));
		tempList.add(leaderFarmerPercent + "%");
		return tempList;
	}

	public List<String> getTrainingClassUserCountList(TrainingOrganizationInfoBean trainingOrganizationInfoBean,
			List<String> tempList) {
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<String, String> trainingClassCondMap = new HashMap<String, String>();
		trainingClassCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		trainingClassCondMap.put("acceptStatus", TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
		trainingClassCondMap.put("organizationId", String.valueOf(trainingOrganizationInfoBean.getOrganizationId()));
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(trainingClassCondMap);
		if (!trainingClassInfoList.isEmpty()) {
			for (TrainingClassInfoBean trainingClassInfoBean : trainingClassInfoList) {
				List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
						.getTrainingClassUserListByClassId(trainingClassInfoBean.getClassId());
				int tempCount = 0;
				int tempAuth = 0;
				if (!trainingClassUserList.isEmpty()) {
					tempCount = trainingClassUserList.size();
					for (TrainingClassUserBean trainingClassUserBean : trainingClassUserList) {
						if (trainingClassUserBean.getAuthResult() == Constants.STATUS_VALID) {
							++tempAuth;
						}
					}
				}

				// 现代青年农场主
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					tempList.set(14, String.valueOf(Integer.parseInt(tempList.get(14)) + tempCount));
					tempList.set(13, String.valueOf(Integer.parseInt(tempList.get(13)) + tempAuth));
				}
				// 生产经营职业农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					tempList.set(5, String.valueOf(Integer.parseInt(tempList.get(5)) + tempCount));
					tempList.set(4, String.valueOf(Integer.parseInt(tempList.get(4)) + tempAuth));
				}
				// 专业技能型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
					tempList.set(8, String.valueOf(Integer.parseInt(tempList.get(8)) + tempCount));
					tempList.set(7, String.valueOf(Integer.parseInt(tempList.get(7)) + tempAuth));
				}
				// 专业服务型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
					tempList.set(11, String.valueOf(Integer.parseInt(tempList.get(11)) + tempCount));
					tempList.set(10, String.valueOf(Integer.parseInt(tempList.get(10)) + tempAuth));
				}
				// 现代农业经营带头人
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					tempList.set(17, String.valueOf(Integer.parseInt(tempList.get(17)) + tempCount));
					tempList.set(16, String.valueOf(Integer.parseInt(tempList.get(16)) + tempAuth));
				}
			}

		}
		// 统计不同类型的开班人数
		int youngFarmerCount = Integer.parseInt(tempList.get(14));
		int productFarmerCount = Integer.parseInt(tempList.get(5));
		int technicalFarmerCount = Integer.parseInt(tempList.get(8));
		int serviceFarmerCount = Integer.parseInt(tempList.get(11));
		int leaderFarmerCount = Integer.parseInt(tempList.get(17));
		// 认定人数
		int youngFarmerAuth = Integer.parseInt(tempList.get(13));
		int productFarmerAuth = Integer.parseInt(tempList.get(4));
		int technicalFarmerAuth = Integer.parseInt(tempList.get(7));
		int serviceFarmerAuth = Integer.parseInt(tempList.get(10));
		int leaderFarmerAuth = Integer.parseInt(tempList.get(16));
		int generalConut = youngFarmerCount + productFarmerCount + technicalFarmerCount + serviceFarmerCount
				+ leaderFarmerCount;
		int generalAuth = youngFarmerAuth + productFarmerAuth + technicalFarmerAuth + serviceFarmerAuth
				+ leaderFarmerAuth;
		tempList.set(2, String.valueOf(generalConut));
		tempList.set(1, String.valueOf(generalAuth));

		// 计算百分比
		String youngFarmerPercent = "0";
		String productFarmerPercent = "0";
		String technicalFarmerPercent = "0";
		String serviceFarmerPercent = "0";
		String leaderFarmerPercent = "0";
		String generalPercent = "0";
		if (youngFarmerCount != 0) {
			youngFarmerPercent = NumeralOperationKit.calculatePercent(youngFarmerAuth, youngFarmerCount, 1);
		}
		if (productFarmerCount != 0) {
			productFarmerPercent = NumeralOperationKit.calculatePercent(productFarmerAuth, productFarmerCount, 1);
		}
		if (technicalFarmerCount != 0) {
			technicalFarmerPercent = NumeralOperationKit.calculatePercent(technicalFarmerAuth, technicalFarmerCount, 1);
		}
		if (serviceFarmerCount != 0) {
			serviceFarmerPercent = NumeralOperationKit.calculatePercent(serviceFarmerAuth, serviceFarmerCount, 1);
		}
		if (leaderFarmerCount != 0) {
			leaderFarmerPercent = NumeralOperationKit.calculatePercent(leaderFarmerAuth, leaderFarmerCount, 1);
		}
		if (generalConut != 0) {
			generalPercent = NumeralOperationKit.calculatePercent(generalAuth, generalConut, 1);
		}
		tempList.set(15, youngFarmerPercent + "%");
		tempList.set(6, productFarmerPercent + "%");
		tempList.set(9, technicalFarmerPercent + "%");
		tempList.set(12, serviceFarmerPercent + "%");
		tempList.set(18, leaderFarmerPercent + "%");
		tempList.set(3, generalPercent + "%");
		return tempList;
	}
}
