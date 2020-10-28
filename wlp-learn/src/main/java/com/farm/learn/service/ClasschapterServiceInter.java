package com.farm.learn.service;

import com.farm.learn.domain.ClassChapter;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface ClasschapterServiceInter {
	 
	public ClassChapter insertClasschapterEntity(ClassChapter entity, LoginUser user);

	 
	public ClassChapter editClasschapterEntity(ClassChapter entity, LoginUser user);

	 
	public void deleteClasschapterEntity(String id, LoginUser user);

	 
	public ClassChapter getClasschapterEntity(String id);

	 
	public DataQuery createClasschapterSimpleQuery(DataQuery query);

	 
	public List<ClassChapter> getChapters(String classid);
}