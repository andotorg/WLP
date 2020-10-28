package com.farm.quartz.dao;

import com.farm.quartz.domain.FarmQzScheduler;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface FarmQzSchedulerDaoInter  {
	 
 	public void deleteEntity(FarmQzScheduler entity) ;
	 
	public FarmQzScheduler getEntity(String id) ;
	 
	public FarmQzScheduler insertEntity(FarmQzScheduler entity);
	 
	public int getAllListNum();
	 
	public void editEntity(FarmQzScheduler entity);
	 
	public Session getSession();
	 
	public DataResult runSqlQuery(DataQuery query);
	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<FarmQzScheduler> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
	 
	public List<FarmQzScheduler> getAutoSchedulers();
}