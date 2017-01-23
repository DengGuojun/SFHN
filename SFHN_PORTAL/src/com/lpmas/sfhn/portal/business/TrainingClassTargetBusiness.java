package com.lpmas.sfhn.portal.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.NumeralOperationKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.bean.TrainingClassTargetBean;
import com.lpmas.sfhn.bean.TrainingClassUserBean;
import com.lpmas.sfhn.bean.TrainingOrganizationInfoBean;
import com.lpmas.sfhn.config.GovernmentOrganizationConfig;
import com.lpmas.sfhn.declare.config.DeclareInfoConfig;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.dao.TrainingClassTargetDao;

public class TrainingClassTargetBusiness {

	public List<TrainingClassTargetBean> getTrainingClassTargetListByMap(HashMap<String, String> condMap) {
		TrainingClassTargetDao dao = new TrainingClassTargetDao();
		return dao.getTrainingClassTargetListByMap(condMap);
	}

	// 统计各地任务和完成情况
	public List<String> getDeclareInfoTargetCountList(TrainingOrganizationInfoBean trainingOrganizationInfoBean,
			HashMap<String, String> condMap, int level) {
		// 统计不同类型的开班数目
		int youngFarmerCount = 0;
		int productFarmerCount = 0;
		int technicalFarmerCount = 0;
		int serviceFarmerCount = 0;
		int leaderFarmerCount = 0;
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<String, String> trainingClassCondMap = new HashMap<String, String>();
		trainingClassCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		trainingClassCondMap.put("province", condMap.get("province"));
		trainingClassCondMap.put("city", condMap.get("city"));
		trainingClassCondMap.put("region", condMap.get("region"));
		trainingClassCondMap.put("trainingYear", condMap.get("trainingYear"));
		trainingClassCondMap.put("acceptStatus", TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
		trainingClassCondMap.put("organizationId", String.valueOf(trainingOrganizationInfoBean.getOrganizationId()));
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(trainingClassCondMap);
		if (!trainingClassInfoList.isEmpty()) {
			for (TrainingClassInfoBean trainingClassInfoBean : trainingClassInfoList) {
				List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
						.getTrainingClassUserListByClassId(trainingClassInfoBean.getClassId());
				int tempCount = 0;
				if (!trainingClassUserList.isEmpty()) {
					tempCount = trainingClassUserList.size();
				}

				// 现代青年农场主
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					youngFarmerCount = youngFarmerCount + tempCount;
				}
				// 生产经营职业农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					productFarmerCount = productFarmerCount + tempCount;
				}
				// 专业技能型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
					technicalFarmerCount = technicalFarmerCount + tempCount;
				}
				// 专业服务型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
					serviceFarmerCount = serviceFarmerCount + tempCount;
				}
				// 现代农业经营带头人
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					leaderFarmerCount = leaderFarmerCount + tempCount;
				}
			}

		}
		int generalConut = youngFarmerCount + productFarmerCount + technicalFarmerCount + serviceFarmerCount
				+ leaderFarmerCount;
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		// 记录目标数
		int youngFarmerTarget = 0;
		int productFarmerTarget = 0;
		int technicalFarmerTarget = 0;
		int serviceFarmerTarget = 0;
		int leaderFarmerTarget = 0;
		TrainingClassTargetBusiness trainingClassTargetBusiness = new TrainingClassTargetBusiness();
		List<TrainingClassTargetBean> trainingClassTargetList = trainingClassTargetBusiness
				.getTrainingClassTargetListByMap(condMap);
		boolean flag = true;
		if (!trainingClassTargetList.isEmpty()) {
			for (TrainingClassTargetBean trainingClassTargetBean : trainingClassTargetList) {
				if (level == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE) {
					flag = !trainingClassTargetBean.getProvince().isEmpty()
							&& trainingClassTargetBean.getCity().isEmpty()
							&& trainingClassTargetBean.getRegion().isEmpty();
				} else if (level == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY) {
					flag = !trainingClassTargetBean.getProvince().isEmpty()
							&& !trainingClassTargetBean.getCity().isEmpty()
							&& trainingClassTargetBean.getRegion().isEmpty();
				} else if(level == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION){
					flag = !trainingClassTargetBean.getProvince().isEmpty()
							&& !trainingClassTargetBean.getCity().isEmpty()
							&& !trainingClassTargetBean.getRegion().isEmpty();
				}
				if (flag) {
					// 现代青年农场主
					if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
						youngFarmerTarget = trainingClassTargetBean.getTargetQuantity();
					}
					// 生产经营职业农民
					if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
						productFarmerTarget = trainingClassTargetBean.getTargetQuantity();
					}
					// 专业技能型农民
					if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
						technicalFarmerTarget = trainingClassTargetBean.getTargetQuantity();
					}
					// 专业服务型农民
					if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
						serviceFarmerTarget = trainingClassTargetBean.getTargetQuantity();
					}
					// 现代农业经营带头人
					if (trainingClassTargetBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
						leaderFarmerTarget = trainingClassTargetBean.getTargetQuantity();
					}
				}
			}

		}
		int generalTarget = youngFarmerTarget + productFarmerTarget + technicalFarmerTarget + serviceFarmerTarget
				+ leaderFarmerTarget;
		// 计算百分比
		String youngFarmerPercent = "0";
		String productFarmerPercent = "0";
		String technicalFarmerPercent = "0";
		String serviceFarmerPercent = "0";
		String leaderFarmerPercent = "0";
		String generalPercent = "0";
		if (youngFarmerTarget != 0) {
			youngFarmerPercent = NumeralOperationKit.calculatePercent(youngFarmerCount, youngFarmerTarget, 1);
		}
		if (productFarmerTarget != 0) {
			productFarmerPercent = NumeralOperationKit.calculatePercent(productFarmerCount, productFarmerTarget, 1);
		}
		if (technicalFarmerTarget != 0) {
			technicalFarmerPercent = NumeralOperationKit.calculatePercent(technicalFarmerCount, technicalFarmerTarget,
					1);
		}
		if (serviceFarmerTarget != 0) {
			serviceFarmerPercent = NumeralOperationKit.calculatePercent(serviceFarmerCount, serviceFarmerTarget, 1);
		}
		if (leaderFarmerTarget != 0) {
			leaderFarmerPercent = NumeralOperationKit.calculatePercent(leaderFarmerCount, leaderFarmerTarget, 1);
		}
		if (generalTarget != 0) {
			generalPercent = NumeralOperationKit.calculatePercent(generalConut, generalTarget, 1);
		}
		// 放入显示target的List
		List<String> tempList = new ArrayList<String>();
		tempList.add(String.valueOf(level));
		// 所有
		tempList.add(String.valueOf(generalTarget));
		tempList.add(String.valueOf(generalConut));
		tempList.add(generalPercent + "%");
		// 生产经营
		tempList.add(String.valueOf(productFarmerTarget));
		tempList.add(String.valueOf(productFarmerCount));
		tempList.add(productFarmerPercent + "%");
		// 专业技能
		tempList.add(String.valueOf(technicalFarmerTarget));
		tempList.add(String.valueOf(technicalFarmerCount));
		tempList.add(technicalFarmerPercent + "%");
		// 社会服务
		tempList.add(String.valueOf(serviceFarmerTarget));
		tempList.add(String.valueOf(serviceFarmerCount));
		tempList.add(serviceFarmerPercent + "%");
		// 青年农场主
		tempList.add(String.valueOf(youngFarmerTarget));
		tempList.add(String.valueOf(youngFarmerCount));
		tempList.add(youngFarmerPercent + "%");
		// 带头人
		tempList.add(String.valueOf(leaderFarmerTarget));
		tempList.add(String.valueOf(leaderFarmerCount));
		tempList.add(leaderFarmerPercent + "%");
		return tempList;
	}

	public List<String> getDeclareInfoTargetCountList(TrainingOrganizationInfoBean trainingOrganizationInfoBean,
			HashMap<String, String> condMap, List<String> tempList) {
		// 统计不同类型的开班数目
		TrainingClassInfoBusiness trainingClassInfoBusiness = new TrainingClassInfoBusiness();
		TrainingClassUserBusiness trainingClassUserBusiness = new TrainingClassUserBusiness();
		HashMap<String, String> trainingClassCondMap = new HashMap<String, String>();
		trainingClassCondMap.put("status", String.valueOf(Constants.STATUS_VALID));
		trainingClassCondMap.put("province", condMap.get("province"));
		trainingClassCondMap.put("city", condMap.get("city"));
		trainingClassCondMap.put("region", condMap.get("region"));
		trainingClassCondMap.put("trainingYear", condMap.get("trainingYear"));
		trainingClassCondMap.put("acceptStatus", TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
		trainingClassCondMap.put("organizationId", String.valueOf(trainingOrganizationInfoBean.getOrganizationId()));
		List<TrainingClassInfoBean> trainingClassInfoList = trainingClassInfoBusiness
				.getTrainingClassInfoListByMap(trainingClassCondMap);
		if (!trainingClassInfoList.isEmpty()) {
			for (TrainingClassInfoBean trainingClassInfoBean : trainingClassInfoList) {
				List<TrainingClassUserBean> trainingClassUserList = trainingClassUserBusiness
						.getTrainingClassUserListByClassId(trainingClassInfoBean.getClassId());
				int tempCount = 0;
				if (!trainingClassUserList.isEmpty()) {
					tempCount = trainingClassUserList.size();
				}

				// 现代青年农场主
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {
					tempList.set(14, String.valueOf(Integer.parseInt(tempList.get(14)) + tempCount));
				}
				// 生产经营职业农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {
					tempList.set(5, String.valueOf(Integer.parseInt(tempList.get(5)) + tempCount));
				}
				// 专业技能型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER) {
					tempList.set(8, String.valueOf(Integer.parseInt(tempList.get(8)) + tempCount));
				}
				// 专业服务型农民
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {
					tempList.set(11, String.valueOf(Integer.parseInt(tempList.get(11)) + tempCount));
				}
				// 现代农业经营带头人
				if (trainingClassInfoBean.getTrainingType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {
					tempList.set(17, String.valueOf(Integer.parseInt(tempList.get(17)) + tempCount));
				}
			}

		}
		int youngFarmerCount = Integer.parseInt(tempList.get(14));
		int productFarmerCount = Integer.parseInt(tempList.get(5));
		int technicalFarmerCount = Integer.parseInt(tempList.get(8));
		int serviceFarmerCount = Integer.parseInt(tempList.get(11));
		int leaderFarmerCount = Integer.parseInt(tempList.get(17));
		int generalConut = youngFarmerCount + productFarmerCount + technicalFarmerCount + serviceFarmerCount
				+ leaderFarmerCount;
		tempList.set(2, String.valueOf(generalConut));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		// 记录目标数
		int youngFarmerTarget = Integer.parseInt(tempList.get(13));
		int productFarmerTarget = Integer.parseInt(tempList.get(4));
		int technicalFarmerTarget = Integer.parseInt(tempList.get(7));
		int serviceFarmerTarget = Integer.parseInt(tempList.get(10));
		int leaderFarmerTarget = Integer.parseInt(tempList.get(16));
		int generalTarget = Integer.parseInt(tempList.get(1));
		// 计算百分比
		String youngFarmerPercent = "0";
		String productFarmerPercent = "0";
		String technicalFarmerPercent = "0";
		String serviceFarmerPercent = "0";
		String leaderFarmerPercent = "0";
		String generalPercent = "0";
		if (youngFarmerTarget != 0) {
			youngFarmerPercent = NumeralOperationKit.calculatePercent(youngFarmerCount, youngFarmerTarget, 1);
		}
		if (productFarmerTarget != 0) {
			productFarmerPercent = NumeralOperationKit.calculatePercent(productFarmerCount, productFarmerTarget, 1);
		}
		if (technicalFarmerTarget != 0) {
			technicalFarmerPercent = NumeralOperationKit.calculatePercent(technicalFarmerCount, technicalFarmerTarget,
					1);
		}
		if (serviceFarmerTarget != 0) {
			serviceFarmerPercent = NumeralOperationKit.calculatePercent(serviceFarmerCount, serviceFarmerTarget, 1);
		}
		if (leaderFarmerTarget != 0) {
			leaderFarmerPercent = NumeralOperationKit.calculatePercent(leaderFarmerCount, leaderFarmerTarget, 1);
		}
		if (generalTarget != 0) {
			generalPercent = NumeralOperationKit.calculatePercent(generalConut, generalTarget, 1);
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
