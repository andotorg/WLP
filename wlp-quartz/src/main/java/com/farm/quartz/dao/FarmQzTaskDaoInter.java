package com.farm.quartz.dao;

import com.farm.quartz.domain.FarmQzTask;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface FarmQzTaskDaoInter  {
	 
 	public void deleteEntity(FarmQzTask entity) ;
	 
	public FarmQzTask getEntity(String id) ;
	 
	public FarmQzTask insertEntity(FarmQzTask entity);
	 
	public int getAllListNum();
	 
	public void editEntity(FarmQzTask entity);
	 
	public Session getSession();
	 
	public DataResult runSqlQuery(DataQuery query);
	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<FarmQzTask> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
}