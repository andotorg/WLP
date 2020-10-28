package com.farm.learn.dao;

import com.farm.learn.domain.ClassClassType;
import org.hibernate.Session;
import org.hibernate.type.ClassType;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface ClassclasstypeDaoInter {
	 
	public void deleteEntity(ClassClassType classclasstype);

	 
	public ClassClassType getEntity(String classclasstypeid);

	 
	public ClassClassType insertEntity(ClassClassType classclasstype);

	 
	public int getAllListNum();

	 
	public void editEntity(ClassClassType classclasstype);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<ClassClassType> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	public List<ClassClassType> getEntityByClassId(String classid);
}