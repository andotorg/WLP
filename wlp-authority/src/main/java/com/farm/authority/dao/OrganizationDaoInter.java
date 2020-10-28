package com.farm.authority.dao;

import com.farm.authority.domain.Organization;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface OrganizationDaoInter {
	 
	public void deleteEntity(Organization organization);

	 
	public Organization getEntity(String organizationid);

	 
	public Organization insertEntity(Organization organization);

	 
	public int getAllListNum();

	 
	public void editEntity(Organization organization);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Organization> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public List<Organization> getAllSubNodes(String orgId);

	 
	public List<Organization> getList();
}