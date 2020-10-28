package com.farm.authority.dao;

import com.farm.authority.domain.Actiontree;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface ActiontreeDaoInter {
	 
	public void deleteEntity(Actiontree actiontree);

	 
	public Actiontree getEntity(String actiontreeid);

	 
	public Actiontree insertEntity(Actiontree actiontree);

	 
	public int getAllListNum();

	 
	public void editEntity(Actiontree actiontree);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Actiontree> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public List<Actiontree> getAllSubNodes(String treeNodeId);
}