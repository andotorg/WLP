package com.wcp.question.service;

import com.wcp.question.domain.Qanswercomment;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface QanswercommentServiceInter{
   
  public Qanswercomment insertQanswercommentEntity(Qanswercomment entity,LoginUser user);
   
  public Qanswercomment editQanswercommentEntity(Qanswercomment entity,LoginUser user);
   
  public void deleteQanswercommentEntity(String id,LoginUser user);
   
  public Qanswercomment getQanswercommentEntity(String id);
   
  public DataQuery createQanswercommentSimpleQuery(DataQuery query);
}