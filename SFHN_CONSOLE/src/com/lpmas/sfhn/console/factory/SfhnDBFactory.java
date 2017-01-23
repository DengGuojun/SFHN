package com.lpmas.sfhn.console.factory;

import java.sql.SQLException;

import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.MysqlDBExecutor;
import com.lpmas.framework.db.MysqlDBObject;
import com.lpmas.sfhn.console.config.SfhnDBConfig;

public class SfhnDBFactory extends DBFactory {

	public DBObject getDBObjectR() throws SQLException {
		return new MysqlDBObject(SfhnDBConfig.DB_LINK_SFHN_R);
	}

	public DBObject getDBObjectW() throws SQLException {
		return new MysqlDBObject(SfhnDBConfig.DB_LINK_SFHN_W);
	}

	@Override
	public DBExecutor getDBExecutor() {
		return new MysqlDBExecutor();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
}
