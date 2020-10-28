package com.farm.learn.service;

import com.farm.learn.domain.ClassTag;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface ClasstagServiceInter{
   
  public ClassTag insertClasstagEntity(ClassTag entity,LoginUser user);
   
  public ClassTag editClasstagEntity(ClassTag entity,LoginUser user);
   
  public void deleteClasstagEntity(String id,LoginUser user);
   
  public ClassTag getClasstagEntity(String id);
   
  public DataQuery createClasstagSimpleQuery(DataQuery query);
}