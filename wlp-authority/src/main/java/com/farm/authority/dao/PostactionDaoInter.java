package com.farm.authority.dao;

import com.farm.authority.domain.Postaction;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface PostactionDaoInter  {
  
 public void deleteEntity(Postaction postaction) ;
  
 public Postaction getEntity(String postactionid) ;
  
 public  Postaction insertEntity(Postaction postaction);
  
 public int getAllListNum();
  
 public void editEntity(Postaction postaction);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<Postaction> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}