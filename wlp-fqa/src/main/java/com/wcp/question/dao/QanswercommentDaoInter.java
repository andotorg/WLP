package com.wcp.question.dao;

import com.wcp.question.domain.Qanswercomment;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface QanswercommentDaoInter  {
  
 public void deleteEntity(Qanswercomment qanswercomment) ;
  
 public Qanswercomment getEntity(String qanswercommentid) ;
  
 public  Qanswercomment insertEntity(Qanswercomment qanswercomment);
  
 public int getAllListNum();
  
 public void editEntity(Qanswercomment qanswercomment);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Qanswercomment> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}