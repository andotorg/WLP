package  com.wcp.question.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wcp.question.domain.Qanswer;
import com.wcp.question.dao.QanswerDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

 
@Repository
public class QanswerDaoImpl  extends HibernateSQLTools<Qanswer> implements  QanswerDaoInter {
  @Resource(name = "sessionFactory")
  private SessionFactory sessionFatory;
  
  @Override
  public void deleteEntity(Qanswer qanswer) {
    
    Session session=sessionFatory.getCurrentSession();
    session.delete(qanswer);
  }
  @Override
  public int getAllListNum(){
    
    Session session= sessionFatory.getCurrentSession();
    SQLQuery sqlquery= session.createSQLQuery("select count(*) from farm_code_field");
    BigInteger num=(BigInteger)sqlquery.list().get(0);
    return num.intValue() ;
  }
  @Override
  public Qanswer getEntity(String qanswerid) {
    
    Session session= sessionFatory.getCurrentSession();
    return (Qanswer)session.get(Qanswer.class, qanswerid);
  }
  @Override
  public Qanswer insertEntity(Qanswer qanswer) {
    
    Session session= sessionFatory.getCurrentSession();
    session.save(qanswer);
    return qanswer;
  }
  @Override
  public void editEntity(Qanswer qanswer) {
    
    Session session= sessionFatory.getCurrentSession();
    session.update(qanswer);
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
  public List<Qanswer> selectEntitys(List<DBRule> rules) {
    
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
		return Qanswer.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}
}
