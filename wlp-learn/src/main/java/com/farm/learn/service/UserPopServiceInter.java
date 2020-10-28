package com.farm.learn.service;

import com.farm.core.auth.domain.LoginUser;

 
public interface UserPopServiceInter {
	public boolean isEditClassByTypeid(String typeid, LoginUser user);

	public boolean isEditClass(String classid, LoginUser user);
}