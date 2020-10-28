package  com.farm.learn.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

 
@Repository
public class ClasshourDaoImpl  extends HibernateSQLTools<ClassHour> implements  ClasshourDaoInter {
  @Resource(name = "sessionFactory")
  private SessionFactory sessionFatory;
  
  @Override
  public void deleteEntity(ClassHour classhour) {
    
    Session session=sessionFatory.getCurrentSession();
    session.delete(classhour);
  }
  @Override
  public int getAllListNum(){
    
    Session session= sessionFatory.getCurrentSession();
    SQLQuery sqlquery= session.createSQLQuery("select count(*) from farm_code_field");
    BigInteger num=(BigInteger)sqlquery.list().get(0);
    return num.intValue() ;
  }
  @Override
  public ClassHour getEntity(String classhourid) {
    
    Session session= sessionFatory.getCurrentSession();
    return (ClassHour)session.get(ClassHour.class, classhourid);
  }
  @Override
  public ClassHour insertEntity(ClassHour classhour) {
    
    Session session= sessionFatory.getCurrentSession();
    session.save(classhour);
    return classhour;
  }
  @Override
  public void editEntity(ClassHour classhour) {
    
    Session session= sessionFatory.getCurrentSession();
    session.update(classhour);
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
  public List<ClassHour> selectEntitys(List<DBRule> rules) {
    
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
		return ClassHour.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}
}
