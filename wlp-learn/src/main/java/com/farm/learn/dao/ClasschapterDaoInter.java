package com.farm.learn.dao;

import com.farm.learn.domain.ClassChapter;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


 
public interface ClasschapterDaoInter  {
  
 public void deleteEntity(ClassChapter classchapter) ;
  
 public ClassChapter getEntity(String classchapterid) ;
  
 public  ClassChapter insertEntity(ClassChapter classchapter);
  
 public int getAllListNum();
  
 public void editEntity(ClassChapter classchapter);
  
 public Session getSession();
  
 public DataResult runSqlQuery(DataQuery query);
  
 public void deleteEntitys(List<DBRule> rules);

  
 public List<ClassChapter> selectEntitys(List<DBRule> rules);

  
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
  
 public int countEntitys(List<DBRule> rules);
}