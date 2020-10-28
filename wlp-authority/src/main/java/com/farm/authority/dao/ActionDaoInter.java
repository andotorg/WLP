package com.farm.authority.dao;

import com.farm.authority.domain.Action;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface ActionDaoInter {
	 
	public void deleteEntity(Action action);

	 
	public Action getEntity(String actionid);

	 
	public Action insertEntity(Action action);

	 
	public int getAllListNum();

	 
	public void editEntity(Action action);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Action> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public Action getEntityByKey(String authkey);

	 
	public List<Action> getAllEntity();
}