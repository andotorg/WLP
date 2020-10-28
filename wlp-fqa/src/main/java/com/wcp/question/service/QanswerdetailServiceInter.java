package com.wcp.question.service;

import com.wcp.question.domain.Qanswerdetail;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface QanswerdetailServiceInter{
   
  public Qanswerdetail insertQanswerdetailEntity(Qanswerdetail entity,LoginUser user);
   
  public Qanswerdetail editQanswerdetailEntity(Qanswerdetail entity,LoginUser user);
   
  public void deleteQanswerdetailEntity(String id,LoginUser user);
   
  public Qanswerdetail getQanswerdetailEntity(String id);
   
  public DataQuery createQanswerdetailSimpleQuery(DataQuery query);
}