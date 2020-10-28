package com.wcp.question.service;

import com.wcp.question.domain.Qanswerplus;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface QanswerplusServiceInter{
   
  public Qanswerplus insertQanswerplusEntity(Qanswerplus entity,LoginUser user);
   
  public Qanswerplus editQanswerplusEntity(Qanswerplus entity,LoginUser user);
   
  public void deleteQanswerplusEntity(String id,LoginUser user);
   
  public Qanswerplus getQanswerplusEntity(String id);
   
  public DataQuery createQanswerplusSimpleQuery(DataQuery query);
}