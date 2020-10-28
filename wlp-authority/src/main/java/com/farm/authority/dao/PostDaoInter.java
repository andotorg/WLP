package com.farm.authority.dao;

import com.farm.authority.domain.Post;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface PostDaoInter {
	 
	public void deleteEntity(Post post);

	 
	public Post getEntity(String postid);

	 
	public Post insertEntity(Post post);

	 
	public int getAllListNum();

	 
	public void editEntity(Post post);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Post> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);
}