package com.lpmas.sfhn.portal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.bean.TrainingClassInfoBean;
import com.lpmas.sfhn.portal.config.TrainingClassInfoConfig;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class TrainingClassInfoDao {
	private static Logger log = LoggerFactory.getLogger(TrainingClassInfoDao.class);

	public int insertTrainingClassInfo(TrainingClassInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into training_class_info ( class_name, organization_id, edu_class_id, training_type, class_type, industry_belt_id, training_year, class_people_quantity, training_begin_time, training_end_time, training_time, training_schedule, registration_end_time, province, city, region, recruit_status, accept_status, class_status, sync_status, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getClassName());
			ps.setInt(2, bean.getOrganizationId());
			ps.setInt(3, bean.getEduClassId());
			ps.setInt(4, bean.getTrainingType());
			ps.setInt(5, bean.getClassType());
			ps.setInt(6, bean.getIndustryBeltId());
			ps.setString(7, bean.getTrainingYear());
			ps.setInt(8, bean.getClassPeopleQuantity());
			ps.setTimestamp(9, bean.getTrainingBeginTime());
			ps.setTimestamp(10, bean.getTrainingEndTime());
			ps.setString(11, bean.getTrainingTime());
			ps.setString(12, bean.getTrainingSchedule());
			ps.setTimestamp(13, bean.getRegistrationEndTime());
			ps.setString(14, bean.getProvince());
			ps.setString(15, bean.getCity());
			ps.setString(16, bean.getRegion());
			ps.setString(17, bean.getRecruitStatus());
			ps.setString(18, bean.getAcceptStatus());
			ps.setString(19, bean.getClassStatus());
			ps.setInt(20, bean.getSyncStatus());
			ps.setInt(21, bean.getStatus());
			ps.setInt(22, bean.getCreateUser());
			ps.setString(23, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateTrainingClassInfo(TrainingClassInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update training_class_info set class_name = ?, organization_id = ?, edu_class_id = ?, training_type = ?, class_type = ?, industry_belt_id = ?, training_year = ?, class_people_quantity = ?, training_begin_time = ?, training_end_time = ?, training_time = ?, training_schedule = ?, registration_end_time = ?, province = ?, city = ?, region = ?, recruit_status = ?, accept_status = ?, class_status = ?, sync_status = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where class_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getClassName());
			ps.setInt(2, bean.getOrganizationId());
			ps.setInt(3, bean.getEduClassId());
			ps.setInt(4, bean.getTrainingType());
			ps.setInt(5, bean.getClassType());
			ps.setInt(6, bean.getIndustryBeltId());
			ps.setString(7, bean.getTrainingYear());
			ps.setInt(8, bean.getClassPeopleQuantity());
			ps.setTimestamp(9, bean.getTrainingBeginTime());
			ps.setTimestamp(10, bean.getTrainingEndTime());
			ps.setString(11, bean.getTrainingTime());
			ps.setString(12, bean.getTrainingSchedule());
			ps.setTimestamp(13, bean.getRegistrationEndTime());
			ps.setString(14, bean.getProvince());
			ps.setString(15, bean.getCity());
			ps.setString(16, bean.getRegion());
			ps.setString(17, bean.getRecruitStatus());
			ps.setString(18, bean.getAcceptStatus());
			ps.setString(19, bean.getClassStatus());
			ps.setInt(20, bean.getSyncStatus());
			ps.setInt(21, bean.getStatus());
			ps.setInt(22, bean.getModifyUser());
			ps.setString(23, bean.getMemo());

			ps.setInt(24, bean.getClassId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public TrainingClassInfoBean getTrainingClassInfoByKey(int classId) {
		TrainingClassInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_info where class_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, classId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new TrainingClassInfoBean();
				bean = BeanKit.resultSet2Bean(rs, TrainingClassInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public PageResultBean<TrainingClassInfoBean> getTrainingClassInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<TrainingClassInfoBean> result = new PageResultBean<TrainingClassInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String trainingType = condMap.get("trainingType");
			if (StringKit.isValid(trainingType)) {
				condList.add("training_type = ?");
				paramList.add(trainingType);
			}
			String province = condMap.get("province");
			if (StringKit.isValid(province)) {
				condList.add("province = ?");
				paramList.add(province);
			}
			String city = condMap.get("city");
			if (StringKit.isValid(city)) {
				condList.add("city = ?");
				paramList.add(city);
			}
			String region = condMap.get("region");
			if (StringKit.isValid(region)) {
				condList.add("region = ?");
				paramList.add(region);
			}
			String acceptStatus = condMap.get("acceptStatus");
			if (StringKit.isValid(acceptStatus)) {
				condList.add("accept_status = ?");
				paramList.add(acceptStatus);
			}
			String acceptStatusSelection = condMap.get("acceptStatusSelection");
			if (StringKit.isValid(acceptStatusSelection)) {
				if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
				}
			}
			String classStatus = condMap.get("classStatus");
			if (StringKit.isValid(classStatus)) {
				condList.add("class_status = ?");
				paramList.add(classStatus);
			}
			String classStatusSelection = condMap.get("classStatusSelection");
			if (StringKit.isValid(classStatusSelection)) {
				if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)) {
					condList.add("class_status = ?");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
					condList.add("(class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String openStatus = condMap.get("openStatus");
			if (StringKit.isValid(openStatus)) {
				if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_APPROVED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_OPENED)) {
					condList.add("class_status = ? ");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_OPENED)) {
					condList.add("(class_status = ? and now() < training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_FINISHED)) {
					condList.add("(class_status = ? and now() > training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String registrationEndTime = condMap.get("registrationEndTime");
			if (StringKit.isValid(registrationEndTime)) {
				condList.add("registration_end_time >= ?");
				paramList.add(registrationEndTime);
			}
			String noEdit = condMap.get("noEdit");
			if (StringKit.isValid(noEdit)) {
				condList.add("class_status != ?");
				paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String trainingYear = condMap.get("trainingYear");
			if (StringKit.isValid(trainingYear)) {
				condList.add("training_year = ?");
				paramList.add(trainingYear);
			}
			String industryBeltId = condMap.get("industryBeltId");
			if (StringKit.isValid(industryBeltId)) {
				condList.add("industry_belt_id = ?");
				paramList.add(industryBeltId);
			}
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}
			String orderQuery = "order by memo desc,class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, TrainingClassInfoBean.class,
					pageBean, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public List<TrainingClassInfoBean> getTrainingClassInfoListByMap(HashMap<String, String> condMap) {
		List<TrainingClassInfoBean> result = new ArrayList<TrainingClassInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String classType = condMap.get("classType");
			if (StringKit.isValid(classType)) {
				condList.add("class_type = ?");
				paramList.add(classType);
			}
			String trainingType = condMap.get("trainingType");
			if (StringKit.isValid(trainingType)) {
				condList.add("training_type = ?");
				paramList.add(trainingType);
			}
			String province = condMap.get("province");
			if (StringKit.isValid(province)) {
				condList.add("province = ?");
				paramList.add(province);
			}
			String city = condMap.get("city");
			if (StringKit.isValid(city)) {
				condList.add("city = ?");
				paramList.add(city);
			}
			String region = condMap.get("region");
			if (StringKit.isValid(region)) {
				condList.add("region = ?");
				paramList.add(region);
			}
			String classStatus = condMap.get("classStatus");
			if (StringKit.isValid(classStatus)) {
				condList.add("class_status = ?");
				paramList.add(classStatus);
			}
			String classStatusSelection = condMap.get("classStatusSelection");
			if (StringKit.isValid(classStatusSelection)) {
				if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)) {
					condList.add("class_status = ?");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
					condList.add("(class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String openStatus = condMap.get("openStatus");
			if (StringKit.isValid(openStatus)) {
				if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_APPROVED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_OPENED)) {
					condList.add("class_status = ? ");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_OPENED)) {
					condList.add("(class_status = ? and now() < training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_FINISHED)) {
					condList.add("(class_status = ? and now() > training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String acceptStatusSelection = condMap.get("acceptStatusSelection");
			if (StringKit.isValid(acceptStatusSelection)) {
				if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
				}
			}
			String acceptStatus = condMap.get("acceptStatus");
			if (StringKit.isValid(acceptStatus)) {
				condList.add("accept_status = ?");
				paramList.add(acceptStatus);
			}
			String trainingYear = condMap.get("trainingYear");
			if (StringKit.isValid(trainingYear)) {
				condList.add("training_year = ?");
				paramList.add(trainingYear);
			}
			String registrationEndTime = condMap.get("registrationEndTime");
			if (StringKit.isValid(registrationEndTime)) {
				condList.add("registration_end_time >= ?");
				paramList.add(registrationEndTime);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String industryBeltId = condMap.get("industryBeltId");
			if (StringKit.isValid(industryBeltId)) {
				condList.add("industry_belt_id = ?");
				paramList.add(industryBeltId);
			}
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}

			String orderQuery = "order by class_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, TrainingClassInfoBean.class,
					db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int getTrainingClassInfoCountByMap(HashMap<String, String> condMap) {
		int result = 0;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from training_class_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			String trainingType = condMap.get("trainingType");
			if (StringKit.isValid(trainingType)) {
				condList.add("training_type = ?");
				paramList.add(trainingType);
			}
			String province = condMap.get("province");
			if (StringKit.isValid(province)) {
				condList.add("province = ?");
				paramList.add(province);
			}
			String city = condMap.get("city");
			if (StringKit.isValid(city)) {
				condList.add("city = ?");
				paramList.add(city);
			}
			String region = condMap.get("region");
			if (StringKit.isValid(region)) {
				condList.add("region = ?");
				paramList.add(region);
			}
			String classStatusSelection = condMap.get("classStatusSelection");
			if (StringKit.isValid(classStatusSelection)) {
				if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)) {
					condList.add("class_status = ?");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
				} else if (classStatusSelection.equals(TrainingClassInfoConfig.CLASS_STATUS_APPROVED)) {
					condList.add("(class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String openStatus = condMap.get("openStatus");
			if (StringKit.isValid(openStatus)) {
				if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_APPROVED)) {
					condList.add("(class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ? or class_status = ?)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_EDIT);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_NOT_OPENED)) {
					condList.add("class_status = ? ");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_APPROVED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_OPENED)) {
					condList.add("(class_status = ? and now() < training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				} else if (openStatus.equals(TrainingClassInfoConfig.OPEN_STATUS_FINISHED)) {
					condList.add("(class_status = ? and now() > training_end_time)");
					paramList.add(TrainingClassInfoConfig.CLASS_STATUS_OPENED);
				}
			}
			String acceptStatusSelection = condMap.get("acceptStatusSelection");
			if (StringKit.isValid(acceptStatusSelection)) {
				if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED)) {
					condList.add("(accept_status = ? or accept_status = ? or accept_status = ?)");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY);
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION);
				} else if (acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED)) {
					condList.add("accept_status = ?");
					paramList.add(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED);
				}
			}
			String acceptStatus = condMap.get("acceptStatus");
			if (StringKit.isValid(acceptStatus)) {
				condList.add("accept_status = ?");
				paramList.add(acceptStatus);
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getTotalRecordResult(sql, condList, paramList, db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

}
