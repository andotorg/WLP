package com.wcp.question.dao;

import com.wcp.question.domain.Questionplus;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface QuestionplusDaoInter  {
  
 public void deleteEntity(Questionplus questionplus) ;
  
 public Questionplus getEntity(String questionplusid) ;
  
 public  Questionplus insertEntity(Questionplus questionplus);
  
 public int getAllListNum();
  
 public void editEntity(Questionplus questionplus);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Questionplus> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}