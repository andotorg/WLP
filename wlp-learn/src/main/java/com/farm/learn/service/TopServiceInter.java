package com.farm.learn.service;

import com.farm.learn.domain.Top;
import com.farm.core.sql.query.DataQuery;

import java.io.FileNotFoundException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface TopServiceInter {
	 
	public Top insertTopEntity(Top entity, LoginUser user) throws FileNotFoundException;

	 
	public Top editTopEntity(Top entity, LoginUser user) throws FileNotFoundException;

	 
	public void updateSort(String id, int i);

	 
	public void deleteTopEntity(String id, LoginUser user);

	 
	public Top getTopEntity(String id);

	 
	public DataQuery createTopSimpleQuery(DataQuery query);

	 
	public List<Top> allTops(LoginUser currentUser);
}