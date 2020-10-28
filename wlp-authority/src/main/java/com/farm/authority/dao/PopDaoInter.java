package com.farm.authority.dao;

import com.farm.authority.domain.Pop;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface PopDaoInter  {
  
 public void deleteEntity(Pop pop) ;
  
 public Pop getEntity(String popid) ;
  
 public  Pop insertEntity(Pop pop);
  
 public int getAllListNum();
  
 public void editEntity(Pop pop);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Pop> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}