package com.farm.learn.service;

import com.farm.learn.domain.ClassClassType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
 
public interface ClassclasstypeServiceInter{
   
  public ClassClassType insertClassclasstypeEntity(ClassClassType entity,LoginUser user);
   
  public ClassClassType editClassclasstypeEntity(ClassClassType entity,LoginUser user);
   
  public void deleteClassclasstypeEntity(String id,LoginUser user);
   
  public ClassClassType getClassclasstypeEntity(String id);
   
  public DataQuery createClassclasstypeSimpleQuery(DataQuery query);
}