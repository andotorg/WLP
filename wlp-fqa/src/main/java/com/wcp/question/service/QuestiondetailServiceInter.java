package com.wcp.question.service;

import com.wcp.question.domain.Questiondetail;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface QuestiondetailServiceInter{
   
  public Questiondetail insertQuestiondetailEntity(Questiondetail entity,LoginUser user);
   
  public Questiondetail editQuestiondetailEntity(Questiondetail entity,LoginUser user);
   
  public void deleteQuestiondetailEntity(String id,LoginUser user);
   
  public Questiondetail getQuestiondetailEntity(String id);
   
  public DataQuery createQuestiondetailSimpleQuery(DataQuery query);
}