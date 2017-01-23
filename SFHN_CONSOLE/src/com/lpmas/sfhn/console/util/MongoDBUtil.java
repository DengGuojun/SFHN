package com.lpmas.sfhn.console.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.JsonKit;
import com.lpmas.sfhn.console.config.SfhnMongoConfig;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.result.UpdateResult;

public final class MongoDBUtil {

	private static Logger log = LoggerFactory.getLogger(MongoDBUtil.class);

	private static MongoDBUtil instance = new MongoDBUtil();
	private MongoClient mongoClient;

	private MongoDBUtil() {
	}

	public static MongoDBUtil getInstance() {
		return instance;
	}

	static {
		// 从配置文件中获取属性值
		ServerAddress serverAddress = new ServerAddress(SfhnMongoConfig.MONGO_IP,
				Integer.valueOf(SfhnMongoConfig.MONGO_PORT));
		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		addrs.add(serverAddress);
		MongoCredential credential = MongoCredential.createScramSha1Credential(SfhnMongoConfig.MONGO_USERNAME,
				SfhnMongoConfig.MONGO_DB_NAME, SfhnMongoConfig.MONGO_PASSWORD.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);
		// 通过连接认证获取MongoDB连接
		instance.mongoClient = new MongoClient(addrs, credentials);
		Builder options = new MongoClientOptions.Builder();
		options.connectionsPerHost(300);
		options.connectTimeout(15000);
		options.maxWaitTime(3000);
		options.socketTimeout(0);
		options.threadsAllowedToBlockForConnectionMultiplier(5000);
		options.writeConcern(WriteConcern.JOURNALED);
		options.build();
	}

	public static void close() {
		if (instance.mongoClient != null) {
			instance.mongoClient.close();
			instance.mongoClient = null;
		}
	}

	/**
	 * 获取DB实例
	 * 
	 */
	public static MongoDatabase getDB(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = instance.mongoClient.getDatabase(dbName);
			return database;
		}
		return null;
	}

	/**
	 * 获取collection对象
	 * 
	 */
	public static MongoCollection<Document> getCollection(String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		MongoCollection<Document> collection = instance.mongoClient.getDatabase(SfhnMongoConfig.MONGO_DB_NAME)
				.getCollection(collName);
		return collection;
	}

	public static int insertRecord(MongoCollection<Document> collection, Object object) {
		String json = JsonKit.toJson(object);
		Document doc = new Document();
		doc = Document.parse(json);
		InsertOneModel<Document> model = new InsertOneModel<>(doc);
		List<InsertOneModel<Document>> list = new ArrayList<InsertOneModel<Document>>();
		list.add(model);
		BulkWriteResult result = collection.bulkWrite(list);
		return result.getInsertedCount();
	}

	public static <E> int insertRecordList(MongoCollection<Document> collection, List<E> objectList) {
		List<InsertOneModel<Document>> list = new ArrayList<InsertOneModel<Document>>();
		for (Object object : objectList) {
			String json = JsonKit.toJson(object);
			Document doc = new Document();
			doc = Document.parse(json);
			InsertOneModel<Document> model = new InsertOneModel<>(doc);
			list.add(model);
		}
		BulkWriteResult result = collection.bulkWrite(list);
		return result.getInsertedCount();
	}

	public static <E> Object findById(MongoCollection<Document> collection, String id, Class<E> clazz) {
		E bean = null;
		Document document = collection.find(Filters.eq("_id", id)).first();
		if (document != null) {
			try {
				bean = document2Bean(document, clazz);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return bean;
	}

	public static int getCount(MongoCollection<Document> collection) {
		int count = (int) collection.count();
		return count;
	}

	public static long updateRecord(MongoCollection<Document> collection, Object object) {
		String json = JsonKit.toJson(object);
		Document document = Document.parse(json);
		Bson filter = Filters.eq("_id", document.get("_id"));
		UpdateResult result = collection.updateOne(filter, new Document("$set", document));
		return result.getModifiedCount();
	}

	public static int getTotalRecordResult(HashMap<String, Object> condMap,
			HashMap<String, HashMap<String, Object>> scopeMap, MongoCollection<Document> collection) {
		BasicDBObject searchCondition = new BasicDBObject();
		if (condMap.size() + scopeMap.size() > 1) {
			BasicDBList searchCondList = new BasicDBList();
			if (condMap.size() > 0) {
				for (String key : condMap.keySet()) {
					BasicDBObject cond = new BasicDBObject();
					cond.append(key, condMap.get(key));
					searchCondList.add(cond);
				}
			}
			if (scopeMap.size() > 0) {
				for (String key : scopeMap.keySet()) {
					BasicDBObject cond = new BasicDBObject();
					BasicDBObject scopeCond = new BasicDBObject();
					if (scopeMap.get(key).get("lt") != null) {
						scopeCond.append("$lt", scopeMap.get(key).get("lt"));
					}
					if (scopeMap.get(key).get("gt") != null) {
						scopeCond.append("$gt", scopeMap.get(key).get("gt"));
					}
					cond.append(key, scopeCond);
					searchCondList.add(cond);
				}
			}
			searchCondition.put("$and", searchCondList);
		} else if (condMap.size() > 0) {
			for (String key : condMap.keySet()) {
				searchCondition.append(key, condMap.get(key));
			}
		} else if (scopeMap.size() > 0) {
			for (String key : condMap.keySet()) {
				if (scopeMap.get(key).get("lt") != null) {
					searchCondition.append(key, new BasicDBObject("$lt", scopeMap.get("lt")));
				}
				if (scopeMap.get(key).get("gt") != null) {
					searchCondition.append(key, new BasicDBObject("$gt", scopeMap.get("gt")));
				}
			}
		}
		return (int) collection.count(searchCondition);
	}

	public static <E> List<E> getRecordListResult(HashMap<String, Object> condMap,
			HashMap<String, HashMap<String, Object>> scopeMap, Class<E> clazz, PageBean pageBean, String orderBy,
			boolean asc, MongoCollection<Document> collection) throws Exception {
		List<E> list = new ArrayList<E>();
		BasicDBObject searchCondition = new BasicDBObject();
		if (condMap.size() + scopeMap.size() > 1) {
			BasicDBList searchCondList = new BasicDBList();
			if (condMap.size() > 0) {
				for (String key : condMap.keySet()) {
					BasicDBObject cond = new BasicDBObject();
					cond.append(key, condMap.get(key));
					searchCondList.add(cond);
				}
			}
			if (scopeMap.size() > 0) {
				for (String key : scopeMap.keySet()) {
					BasicDBObject cond = new BasicDBObject();
					BasicDBObject scopeCond = new BasicDBObject();
					if (scopeMap.get(key).get("lt") != null) {
						scopeCond.append("$lt", scopeMap.get(key).get("lt"));
					}
					if (scopeMap.get(key).get("gt") != null) {
						scopeCond.append("$gt", scopeMap.get(key).get("gt"));
					}
					cond.append(key, scopeCond);
					searchCondList.add(cond);
				}
			}
			searchCondition.put("$and", searchCondList);
		} else if (condMap.size() > 0) {
			for (String key : condMap.keySet()) {
				searchCondition.append(key, condMap.get(key));
			}
		} else if (scopeMap.size() > 0) {
			for (String key : scopeMap.keySet()) {
				BasicDBObject scopeCond = new BasicDBObject();
				if (scopeMap.get(key).get("lt") != null) {
					scopeCond.append("$lt", scopeMap.get(key).get("lt"));
				}
				if (scopeMap.get(key).get("gt") != null) {
					scopeCond.append("$gt", scopeMap.get(key).get("gt"));
				}
				searchCondition.append(key, scopeCond);
			}
		}

		MongoCursor<Document> cursor = null;
		if (pageBean == null) {
			cursor = collection.find(searchCondition).sort(getOrderByBson(orderBy, asc)).iterator();
		} else {
			cursor = collection.find(searchCondition).sort(getOrderByBson(orderBy, asc))
					.skip((pageBean.getCurrentPageNumber()-1) * pageBean.getPageSize()).limit(pageBean.getPageSize())
					.iterator();
		}

		while (cursor != null && cursor.hasNext()) {
			Document document = cursor.next();
			E bean = document2Bean(document, clazz);
			list.add(bean);
		}
		cursor.close();
		return list;
	}

	public static <E> List<E> getRecordListResult(HashMap<String, Object> condMap,
			HashMap<String, HashMap<String, Object>> scopeMap, Class<E> clazz, String orderBy, boolean asc,
			MongoCollection<Document> collection) throws Exception {
		return getRecordListResult(condMap, scopeMap, clazz, null, orderBy, asc, collection);
	}

	public static <E> PageResultBean<E> getPageListResult(HashMap<String, Object> condMap,
			HashMap<String, HashMap<String, Object>> scopeMap, Class<E> clazz, PageBean pageBean, String orderBy,
			boolean asc, MongoCollection<Document> collection) throws Exception {
		PageResultBean<E> resultBean = new PageResultBean<E>();

		int total = getTotalRecordResult(condMap, scopeMap, collection);
		resultBean.setTotalRecordNumber(total);

		List<E> list = new ArrayList<E>();
		if (total > 0) {
			list = getRecordListResult(condMap, scopeMap, clazz, pageBean, orderBy, asc, collection);
		}
		resultBean.setRecordList(list);

		return resultBean;
	}

	private static Bson getOrderByBson(String orderBy, boolean asc) {
		int symbol = 1;
		if (asc) {
			symbol = -1;
		}
		return new BasicDBObject(orderBy, symbol);
	}

	private static <E> E document2Bean(Document document, Class<E> clazz) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Field> fieldList = BeanKit.getDeclaredFieldList(clazz);
		for (Field field : fieldList) {
			if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {// 如果字段是final或者static修饰符，不处理
				continue;
			}
			String fieldName = field.getName();
			Object value = document.get(fieldName);
			if (value != null) {
				map.put(field.getName(), value);
			}
		}
		E bean = clazz.newInstance();
		bean = BeanKit.map2Bean(map, clazz);
		return bean;
	}

}