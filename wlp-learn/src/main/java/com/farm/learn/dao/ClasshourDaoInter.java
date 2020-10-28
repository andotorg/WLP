package com.farm.learn.dao;

import com.farm.learn.domain.ClassHour;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface ClasshourDaoInter  {
  
 public void deleteEntity(ClassHour classhour) ;
  
 public ClassHour getEntity(String classhourid) ;
  
 public  ClassHour insertEntity(ClassHour classhour);
  
 public int getAllListNum();
  
 public void editEntity(ClassHour classhour);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<ClassHour> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}