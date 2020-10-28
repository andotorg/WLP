package com.farm.learn.service;

import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.domain.ex.HourView;
import com.farm.sfile.exception.FileExNameException;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.farm.core.auth.domain.LoginUser;

 
public interface ClasstServiceInter {
	 
	public Classt insertClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException;

	 
	public Classt editClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException;

	 
	public void deleteClasstEntity(String id, LoginUser user);

	 
	public Classt getClasstEntity(String id);

	 
	public DataQuery createClasstSimpleQuery(DataQuery query);

	 
	public DataResult getTempClases(DataQuery query, LoginUser currentUser) throws SQLException;

	 
	public DataResult getPublicClases(DataQuery query, LoginUser currentUser) throws SQLException;

	 
	public ClassView getClassView(String classid, LoginUser currentUser);

	 
	public Classt editIntroduction(String classid, String text, LoginUser currentUser) throws FileNotFoundException;

	 
	public void publicClass(String classid, LoginUser currentUser);

	 
	public void tempClass(String classid, LoginUser currentUser);

	 
	public DataResult getNewClases(LoginUser currentUser) throws SQLException;

	 
	public DataResult getClases(DataQuery query, LoginUser currentUser) throws SQLException;

	 
	public HourView getUserCurrentHour(String classid, LoginUser currentUser) throws FileExNameException;

	 
	public HourView getUserHour(String hourid, LoginUser currentUser) throws FileExNameException;
}