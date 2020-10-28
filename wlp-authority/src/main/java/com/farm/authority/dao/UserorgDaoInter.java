package com.farm.authority.dao;

import com.farm.authority.domain.Userorg;

import org.hibernate.Session;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.List;
import java.util.Map;

 
public interface UserorgDaoInter {
	 
	public void deleteEntity(Userorg userorg);

	 
	public Userorg getEntity(String userorgid);

	 
	public Userorg insertEntity(Userorg userorg);

	 
	public int getAllListNum();

	 
	public void editEntity(Userorg userorg);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Userorg> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	public Userorg getEntityByUserId(String id);
}