package com.wcp.question.dao;

import com.wcp.question.domain.Qanswerplus;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface QanswerplusDaoInter  {
  
 public void deleteEntity(Qanswerplus qanswerplus) ;
  
 public Qanswerplus getEntity(String qanswerplusid) ;
  
 public  Qanswerplus insertEntity(Qanswerplus qanswerplus);
  
 public int getAllListNum();
  
 public void editEntity(Qanswerplus qanswerplus);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Qanswerplus> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}