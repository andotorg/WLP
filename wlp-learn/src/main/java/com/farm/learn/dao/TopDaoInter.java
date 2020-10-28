package com.farm.learn.dao;

import com.farm.learn.domain.Top;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface TopDaoInter  {
  
 public void deleteEntity(Top top) ;
  
 public Top getEntity(String topid) ;
  
 public  Top insertEntity(Top top);
  
 public int getAllListNum();
  
 public void editEntity(Top top);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Top> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}