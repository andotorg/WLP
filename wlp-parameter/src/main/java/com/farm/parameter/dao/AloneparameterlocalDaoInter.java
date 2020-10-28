package com.farm.parameter.dao;

import com.farm.parameter.domain.Aloneparameterlocal;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface AloneparameterlocalDaoInter {
	 
	public void deleteEntity(Aloneparameterlocal aloneparameterlocal);

	 
	public Aloneparameterlocal getEntity(String aloneparameterlocalid);

	 
	public Aloneparameterlocal insertEntity(
			Aloneparameterlocal aloneparameterlocal);

	 
	public int getAllListNum();

	 
	public void editEntity(Aloneparameterlocal aloneparameterlocal);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Aloneparameterlocal> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public Aloneparameterlocal getEntityByUser(String userId, String parameterId);
}