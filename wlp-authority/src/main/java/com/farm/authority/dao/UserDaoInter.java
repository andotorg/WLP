package com.farm.authority.dao;

import com.farm.authority.domain.User;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface UserDaoInter {
	 
	public void deleteEntity(User user);

	 
	public User getEntity(String userid);

	 
	public User insertEntity(User user);

	 
	public int getAllListNum();

	 
	public void editEntity(User user);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<User> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	public List<User> findUserByLoginName(String loginname);

	public List<User> findUserByLoginName(String loginname, String userId);

	public Integer getUsersNum();

	 
	public List<String> getAllUserIds(String userState);
}