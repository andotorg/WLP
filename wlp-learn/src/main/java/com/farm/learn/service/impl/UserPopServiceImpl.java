package com.farm.learn.service.impl;

import com.farm.learn.service.UserPopServiceInter;

import org.springframework.stereotype.Service;

import com.farm.core.auth.domain.LoginUser;

 
@Service
public class UserPopServiceImpl implements UserPopServiceInter {

	@Override
	public boolean isEditClassByTypeid(String typeid, LoginUser user) {
		// 当前只要是管理员都可以編輯课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isEditClass(String classid, LoginUser user) {
		// 当前只要是管理员都可以編輯课程
		if (user != null && user.getType().equals("3")) {
			return true;
		}
		return false;
	}

}
