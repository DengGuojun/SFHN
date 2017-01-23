package com.lpmas.sfhn.console.declare.business;

import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.sfhn.console.util.MongoDBUtil;
import com.lpmas.sfhn.declare.bean.DeclareReportBean;
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

	public PageResultBean<DeclareReportBean> getDeclareReportPageListByMap(HashMap<String, String> condMap,
			HashMap<String, HashMap<String, String>> scopeMap, PageBean pageBean) throws Exception {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String userName = condMap.get("userName");
		if (StringKit.isValid(userName)) {
			queryMap.put("userName", userName);
		}
		// 范围条件处理
		HashMap<String, HashMap<String, Object>> queryScopeMap = new HashMap<String, HashMap<String, Object>>();

		return MongoDBUtil.getPageListResult(queryMap, queryScopeMap, DeclareReportBean.class, pageBean, "declareId",
				true, collection);
	}

	public List<DeclareReportBean> getDeclareReportListByMap(HashMap<String, String> condMap,
			HashMap<String, HashMap<String, String>> scopeMap) throws Exception {
		MongoCollection<Document> collection = MongoDBUtil.getCollection(collectionName);
		// 条件处理
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		String userName = condMap.get("userName");
		if (StringKit.isValid(userName)) {
			queryMap.put("userName", userName);
		}
		// 范围条件处理
		HashMap<String, HashMap<String, Object>> queryScopeMap = new HashMap<String, HashMap<String, Object>>();

		return MongoDBUtil.getRecordListResult(queryMap, queryScopeMap, DeclareReportBean.class, "declareId", true,
				collection);
	}

}
