package com.lpmas.sfhn.portal.declare.dao;

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
import com.lpmas.sfhn.declare.bean.FarmerIndustryInfoBean;
import com.lpmas.sfhn.portal.factory.SfhnDBFactory;

public class FarmerIndustryInfoDao {
	private static Logger log = LoggerFactory.getLogger(FarmerIndustryInfoDao.class);

	public int insertFarmerIndustryInfo(FarmerIndustryInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into farmer_industry_info ( declare_id, industry_province, has_registed, is_example_family_farm, family_farm_type, example_farm_level, farmer_type, farm_land_scale, industry_city, industry_region, family_person, family_working_person, drive_farmer_number, area_type, economic_area_type, job_type, job_id, experience, income, service_scale, industry_type_id_1, industry_id_1, industry_scale_1, industry_unit_1, experience_1, industry_type_id_2, industry_id_2, industry_scale_2, industry_unit_2, experience_2, industry_type_id_3, industry_id_3, industry_scale_3, experience_3, industry_unit_3, last_year_income, last_year_family_income, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getDeclareId());
			ps.setString(2, bean.getIndustryProvince());
			ps.setInt(3, bean.getHasRegisted());
			ps.setInt(4, bean.getIsExampleFamilyFarm());
			ps.setString(5, bean.getFamilyFarmType());
			ps.setString(6, bean.getExampleFarmLevel());
			ps.setString(7, bean.getFarmerType());
			ps.setDouble(8, bean.getFarmLandScale());
			ps.setString(9, bean.getIndustryCity());
			ps.setString(10, bean.getIndustryRegion());
			ps.setInt(11, bean.getFamilyPerson());
			ps.setInt(12, bean.getFamilyWorkingPerson());
			ps.setInt(13, bean.getDriveFarmerNumber());
			ps.setInt(14, bean.getAreaType());
			ps.setInt(15, bean.getEconomicAreaType());
			ps.setInt(16, bean.getJobType());
			ps.setInt(17, bean.getJobId());
			ps.setDouble(18, bean.getExperience());
			ps.setDouble(19, bean.getIncome());
			ps.setString(20, bean.getServiceScale());
			ps.setInt(21, bean.getIndustryTypeId1());
			ps.setInt(22, bean.getIndustryId1());
			ps.setDouble(23, bean.getIndustryScale1());
			ps.setString(24, bean.getIndustryUnit1());
			ps.setDouble(25, bean.getExperience1());
			ps.setInt(26, bean.getIndustryTypeId2());
			ps.setInt(27, bean.getIndustryId2());
			ps.setDouble(28, bean.getIndustryScale2());
			ps.setString(29, bean.getIndustryUnit2());
			ps.setDouble(30, bean.getExperience2());
			ps.setInt(31, bean.getIndustryTypeId3());
			ps.setInt(32, bean.getIndustryId3());
			ps.setDouble(33, bean.getIndustryScale3());
			ps.setDouble(34, bean.getExperience3());
			ps.setString(35, bean.getIndustryUnit3());
			ps.setDouble(36, bean.getLastYearIncome());
			ps.setDouble(37, bean.getLastYearFamilyIncome());
			ps.setInt(38, bean.getStatus());
			ps.setInt(39, bean.getCreateUser());
			ps.setString(40, bean.getMemo());

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

	public int updateFarmerIndustryInfo(FarmerIndustryInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update farmer_industry_info set industry_province = ?, has_registed = ?, is_example_family_farm = ?, family_farm_type = ?, example_farm_level = ?, farmer_type = ?, farm_land_scale = ?, industry_city = ?, industry_region = ?, family_person = ?, family_working_person = ?, drive_farmer_number = ?, area_type = ?, economic_area_type = ?, job_type = ?, job_id = ?, experience = ?, income = ?, service_scale = ?, industry_type_id_1 = ?, industry_id_1 = ?, industry_scale_1 = ?, industry_unit_1 = ?, experience_1 = ?, industry_type_id_2 = ?, industry_id_2 = ?, industry_scale_2 = ?, industry_unit_2 = ?, experience_2 = ?, industry_type_id_3 = ?, industry_id_3 = ?, industry_scale_3 = ?, experience_3 = ?, industry_unit_3 = ?, last_year_income = ?, last_year_family_income = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getIndustryProvince());
			ps.setInt(2, bean.getHasRegisted());
			ps.setInt(3, bean.getIsExampleFamilyFarm());
			ps.setString(4, bean.getFamilyFarmType());
			ps.setString(5, bean.getExampleFarmLevel());
			ps.setString(6, bean.getFarmerType());
			ps.setDouble(7, bean.getFarmLandScale());
			ps.setString(8, bean.getIndustryCity());
			ps.setString(9, bean.getIndustryRegion());
			ps.setInt(10, bean.getFamilyPerson());
			ps.setInt(11, bean.getFamilyWorkingPerson());
			ps.setInt(12, bean.getDriveFarmerNumber());
			ps.setInt(13, bean.getAreaType());
			ps.setInt(14, bean.getEconomicAreaType());
			ps.setInt(15, bean.getJobType());
			ps.setInt(16, bean.getJobId());
			ps.setDouble(17, bean.getExperience());
			ps.setDouble(18, bean.getIncome());
			ps.setString(19, bean.getServiceScale());
			ps.setInt(20, bean.getIndustryTypeId1());
			ps.setInt(21, bean.getIndustryId1());
			ps.setDouble(22, bean.getIndustryScale1());
			ps.setString(23, bean.getIndustryUnit1());
			ps.setDouble(24, bean.getExperience1());
			ps.setInt(25, bean.getIndustryTypeId2());
			ps.setInt(26, bean.getIndustryId2());
			ps.setDouble(27, bean.getIndustryScale2());
			ps.setString(28, bean.getIndustryUnit2());
			ps.setDouble(29, bean.getExperience2());
			ps.setInt(30, bean.getIndustryTypeId3());
			ps.setInt(31, bean.getIndustryId3());
			ps.setDouble(32, bean.getIndustryScale3());
			ps.setDouble(33, bean.getExperience3());
			ps.setString(34, bean.getIndustryUnit3());
			ps.setDouble(35, bean.getLastYearIncome());
			ps.setDouble(36, bean.getLastYearFamilyIncome());
			ps.setInt(37, bean.getStatus());
			ps.setInt(38, bean.getModifyUser());
			ps.setString(39, bean.getMemo());

			ps.setInt(40, bean.getDeclareId());

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

	public FarmerIndustryInfoBean getFarmerIndustryInfoByKey(int declareId) {
		FarmerIndustryInfoBean bean = null;
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_industry_info where declare_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, declareId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new FarmerIndustryInfoBean();
				bean = BeanKit.resultSet2Bean(rs, FarmerIndustryInfoBean.class);
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

	public PageResultBean<FarmerIndustryInfoBean> getFarmerIndustryInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<FarmerIndustryInfoBean> result = new PageResultBean<FarmerIndustryInfoBean>();
		DBFactory dbFactory = new SfhnDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from farmer_industry_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by declare_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, FarmerIndustryInfoBean.class,
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

}
