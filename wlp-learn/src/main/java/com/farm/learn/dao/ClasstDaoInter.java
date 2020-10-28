package com.farm.learn.dao;

import com.farm.learn.domain.Classt;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface ClasstDaoInter  {
  
 public void deleteEntity(Classt classt) ;
  
 public Classt getEntity(String classtid) ;
  
 public  Classt insertEntity(Classt classt);
  
 public int getAllListNum();
  
 public void editEntity(Classt classt);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Classt> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}