package com.farm.sfile.dao;

import com.farm.sfile.domain.FileBase;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface FileBaseDaoInter {
	 
	public void deleteEntity(FileBase filebase);

	 
	public FileBase getEntity(String filebaseid);

	 
	public FileBase insertEntity(FileBase filebase);

	 
	public int getAllListNum();

	 
	public void editEntity(FileBase filebase);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<FileBase> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public void delRelationsViewTables(String fileid);
}