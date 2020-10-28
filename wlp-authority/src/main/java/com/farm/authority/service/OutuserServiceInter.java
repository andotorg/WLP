package com.farm.authority.service;

import com.farm.authority.domain.Outuser;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

 
public interface OutuserServiceInter {
	 
	public Outuser insertOutuserEntity(Outuser entity, LoginUser user);

	 
	public Outuser editOutuserEntity(Outuser entity);

	 
	public void deleteOutuserEntity(String id, LoginUser user);

	 
	public Outuser getOutuserEntity(String id);

	 
	public DataQuery createOutuserSimpleQuery(DataQuery query);


	 
	public Outuser getOutuserByAccountId(String accountId);

	 
	public Outuser getOutuserByUserid(String readUserId, String contentLimitlike);

	 
	public LoginUser getLocalUserByAccountId(String accountId);

	 
	public Outuser creatOutUser(String accountId, String name, String content);
}