package com.lpmas.sfhn.portal.declare.business;

import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
import com.lpmas.sfhn.portal.util.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.MongoCollection;

public class DeclareReportBusiness {
	private String collectionName = "declare_report";

	public int insertDeclareReport(DeclareReportBean bean) {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		return MongoDBUtil.insertRecord(collection, bean);
	}

	public long updateDeclareReport(DeclareReportBean bean) {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		return MongoDBUtil.updateRecord(collection, bean);
	}

	public DeclareReportBean getDeclareReportByKey(String id) {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		DeclareReportBean bean = (DeclareReportBean) MongoDBUtil.findById(collection, id, DeclareReportBean.class);
		return bean;
	}

	public DeclareReportBean getDeclareReportByUserIdAndYear(int userId, String year) throws Exception {
		DeclareReportBean bean = null;
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("userId", String.valueOf(userId));
		condMap.put("declareYear", year);
		List<DeclareReportBean> declareReportList = getDeclareReportListByMap(condMap);
		if (!declareReportList.isEmpty()) {
			bean = declareReportList.get(0);
		}
		return bean;
	}

	public List<DeclareReportBean> getDeclareReportListByMap(HashMap<String, String> condMap) throws Exception {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String identityNumber = condMap.get("identityNumber");
		if (StringKit.isValid(identityNumber)) {
			queryMap.put("identityNumber", identityNumber);
		}
		String userId = condMap.get("userId");
		if (StringKit.isValid(userId)) {
			queryMap.put("userId", Integer.parseInt(userId));
		}
		String declareId = condMap.get("declareId");
		if (StringKit.isValid(declareId)) {
			queryMap.put("declareId", Integer.parseInt(declareId));
		}
		String classId = condMap.get("classId");
		if (StringKit.isValid(classId)) {
			queryMap.put("classId", Integer.parseInt(classId));
		}
		String industryTypeId = condMap.get("industryTypeId");
		if (StringKit.isValid(industryTypeId)) {
			queryMap.put("industryTypeId1", Integer.parseInt(industryTypeId));
		}
		String industryId = condMap.get("industryId");
		if (StringKit.isValid(industryId)) {
			queryMap.put("industryId1", Integer.parseInt(industryId));
		}
		String status = condMap.get("status");
		if (StringKit.isValid(status)) {
			queryMap.put("status", Integer.parseInt(status));
		}
		// 范围条件处理
		HashMap<String, HashMap<String, Object>> queryScopeMap = new HashMap<String, HashMap<String, Object>>();
		List<DeclareReportBean> bean = (List<DeclareReportBean>) MongoDBUtil.getRecordListResult(queryMap,
				queryScopeMap, DeclareReportBean.class, "declareId", true, collection);
		return bean;
	}

	public PageResultBean<DeclareReportBean> getDeclareReportPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) throws Exception {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String identityNumber = condMap.get("identityNumber");
		if (StringKit.isValid(identityNumber)) {
			queryMap.put("identityNumber", identityNumber);
		}
		String declareType = condMap.get("declareType");
		if (StringKit.isValid(declareType)) {
			queryMap.put("declareType", Integer.valueOf(declareType));
		}
		String declareYear = condMap.get("declareYear");
		if (StringKit.isValid(declareYear)) {
			queryMap.put("declareYear", declareYear);
		}
		String province = condMap.get("province");
		if (StringKit.isValid(province)) {
			queryMap.put("province", province);
		}
		String city = condMap.get("city");
		if (StringKit.isValid(city)) {
			queryMap.put("city", city);
		}
		String region = condMap.get("region");
		if (StringKit.isValid(region)) {
			queryMap.put("region", region);
		}
		String userId = condMap.get("userId");
		if (StringKit.isValid(userId)) {
			queryMap.put("userId", Integer.parseInt(userId));
		}
		String status = condMap.get("status");
		if (StringKit.isValid(status)) {
			queryMap.put("status", Integer.parseInt(status));
		}
		String classId = condMap.get("classId");
		if (Integer.valueOf(classId) >0) {
			queryMap.put("trainingClassInfoList", new BasicDBObject().append(QueryOperators.NE, null));
		}else{
			queryMap.put("trainingClassInfoList", null);
		}
		// 范围条件处理
		HashMap<String, HashMap<String, Object>> queryScopeMap = new HashMap<String, HashMap<String, Object>>();
		PageResultBean<DeclareReportBean> bean = (PageResultBean<DeclareReportBean>) MongoDBUtil.getPageListResult(
				queryMap, queryScopeMap, DeclareReportBean.class, pageBean, "declareId", true, collection);
		return bean;
	}
}
