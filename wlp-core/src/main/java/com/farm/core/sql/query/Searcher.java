package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;

import com.farm.core.sql.result.DataResult;
import com.farm.util.spring.HibernateSessionFactory;

 
public class Searcher implements Runnable {

	private DataQuery query = null;
	private DataResult result = null;

	@SuppressWarnings("unused")
	private Searcher() {
	}

	public Searcher(DataQuery query) {
		this.query = query;
	}

	 
	@Override
	public void run() {
		Session session = HibernateSessionFactory.getSession();
		try {
			result = doSearch(session);
			// 启用缓存功能,将当前结果存入缓存
			String key = DataQuery.getQueryMD5Key(query);
			if (key != null) {
				result.setCtime(new Date());
				DataQuery.resultCache.put(key, result);
			}
		} finally {
			session.close();
		}
	}

	public DataResult doSearch(Session session) {
		try {
			result = HibernateQueryHandle.runDataQuery(session, this.query);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
