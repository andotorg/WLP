package com.farm.quartz.dao;

import com.farm.quartz.domain.FarmQzTrigger;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface FarmQzTriggerDaoInter  {
	 
 	public void deleteEntity(FarmQzTrigger entity) ;
	 
	public FarmQzTrigger getEntity(String id) ;
	 
	public FarmQzTrigger insertEntity(FarmQzTrigger entity);
	 
	public int getAllListNum();
	 
	public void editEntity(FarmQzTrigger entity);
	 
	public Session getSession();
	 
	public DataResult runSqlQuery(DataQuery query);
	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<FarmQzTrigger> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
}