package com.farm.parameter.service;

import com.farm.core.auth.domain.LoginUser;
import com.farm.parameter.domain.AloneApplog;

 
public interface AloneApplogServiceInter {
	 
	public AloneApplog insertEntity(AloneApplog entity, LoginUser user);

	 
	public AloneApplog editEntity(AloneApplog entity, LoginUser user);

	 
	public void deleteEntity(String entity, LoginUser user);

	 
	public AloneApplog getEntity(String id);

	 
	public AloneApplog log(String describes, String appuser, String level, String method, String classname, String ip);

	 
	public void deleteByDate(String ctime, String etime);
}