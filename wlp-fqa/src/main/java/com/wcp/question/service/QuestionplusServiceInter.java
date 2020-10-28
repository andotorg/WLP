package com.wcp.question.service;

import com.wcp.question.domain.Questionplus;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface QuestionplusServiceInter {
	 
	public Questionplus insertQuestionplusEntity(Questionplus entity, LoginUser user);

	 
	public Questionplus editQuestionplusEntity(Questionplus entity, LoginUser user);

	 
	public void deleteQuestionplusEntity(String id, LoginUser user);

	 
	public Questionplus getQuestionplusEntity(String id);

	 
	public DataQuery createQuestionplusSimpleQuery(DataQuery query);

	 
	public List<Questionplus> getQuestionplusByQuestionId(String questionid);
}