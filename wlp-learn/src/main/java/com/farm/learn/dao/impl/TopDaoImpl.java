package  com.farm.learn.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.learn.domain.Top;
import com.farm.learn.dao.TopDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

 
@Repository
public class TopDaoImpl  extends HibernateSQLTools<Top> implements  TopDaoInter {
  @Resource(name = "sessionFactory")
  private SessionFactory sessionFatory;
  
  @Override
  public void deleteEntity(Top top) {
    
    Session session=sessionFatory.getCurrentSession();
    session.delete(top);
  }
  @Override
  public int getAllListNum(){
    
    Session session= sessionFatory.getCurrentSession();
    SQLQuery sqlquery= session.createSQLQuery("select count(*) from farm_code_field");
    BigInteger num=(BigInteger)sqlquery.list().get(0);
    return num.intValue() ;
  }
  @Override
  public Top getEntity(String topid) {
    
    Session session= sessionFatory.getCurrentSession();
    return (Top)session.get(Top.class, topid);
  }
  @Override
  public Top insertEntity(Top top) {
    
    Session session= sessionFatory.getCurrentSession();
    session.save(top);
    return top;
  }
  @Override
  public void editEntity(Top top) {
    
    Session session= sessionFatory.getCurrentSession();
    session.update(top);
  }
  
  @Override
  public Session getSession() {
    
    return sessionFatory.getCurrentSession();
  }
  @Override
  public DataResult runSqlQuery(DataQuery query){
    
    try {
      return query.search(sessionFatory.getCurrentSession());
    } catch (Exception e) {
      return null;
    }
  }
  @Override
  public void deleteEntitys(List<DBRule> rules) {
    
    deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
  }

  @Override
  public List<Top> selectEntitys(List<DBRule> rules) {
    
    return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
  }

  @Override
  public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
    
   updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
  }
  
  @Override
  public int countEntitys(List<DBRule> rules) {
    
   return countSqlFromFunction(sessionFatory.getCurrentSession(), rules);
  }
  
  public SessionFactory getSessionFatory() {
    return sessionFatory;
  }

  public void setSessionFatory(SessionFactory sessionFatory) {
    this.sessionFatory = sessionFatory;
  }
 @Override
	protected Class<?> getTypeClass() {
		return Top.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}
}
