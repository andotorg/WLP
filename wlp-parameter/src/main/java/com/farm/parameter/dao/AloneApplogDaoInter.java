package com.farm.parameter.dao;

import org.hibernate.Session;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.parameter.domain.AloneApplog;

import java.util.List;
import java.util.Map;

 
public interface AloneApplogDaoInter  {
	 
 	public void deleteEntity(AloneApplog entity) ;
	 
	public AloneApplog getEntity(String id) ;
	 
	public AloneApplog insertEntity(AloneApplog entity);
	 
	public int getAllListNum();
	 
	public void editEntity(AloneApplog entity);
	 
	public Session getSession();
	 
	public DataResult runSqlQuery(DataQuery query);
	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<AloneApplog> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
}