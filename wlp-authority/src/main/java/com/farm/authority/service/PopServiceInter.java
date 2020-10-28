package com.farm.authority.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.farm.authority.domain.Pop;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

 
public interface PopServiceInter {

	 
	public Pop getPopEntity(String id);

	 
	public DataQuery createPopSimpleQuery(DataQuery query);

	 
	public void addUserPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	 
	public void addOrgPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	 
	public void addPostPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	 
	public void delPop(String popId, LoginUser user);

	 
	public boolean isUserHaveTargetid(HttpSession session, String targetId, boolean defaultAble);

	 
	public List<String> getUserTargetid(String userid);
}