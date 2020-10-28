package com.wcp.question.dao;

import com.wcp.question.domain.Qanswer;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface QanswerDaoInter  {
  
 public void deleteEntity(Qanswer qanswer) ;
  
 public Qanswer getEntity(String qanswerid) ;
  
 public  Qanswer insertEntity(Qanswer qanswer);
  
 public int getAllListNum();
  
 public void editEntity(Qanswer qanswer);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Qanswer> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}