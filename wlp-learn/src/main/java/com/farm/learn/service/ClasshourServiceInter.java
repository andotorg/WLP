package com.farm.learn.service;

import com.farm.learn.domain.ClassHour;
import com.farm.core.sql.query.DataQuery;

import java.io.FileNotFoundException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface ClasshourServiceInter {
	 
	public ClassHour insertClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException;

	 
	public ClassHour editClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException;

	 
	public void deleteClasshourEntity(String id, LoginUser user);

	 
	public ClassHour getClasshourEntity(String id);

	 
	public DataQuery createClasshourSimpleQuery(DataQuery query);

	 
	public List<ClassHour> getHoursByClass(String classid);
}