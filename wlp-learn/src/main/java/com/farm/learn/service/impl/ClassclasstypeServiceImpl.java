package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassClassType;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.service.ClassclasstypeServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
 
@Service
public class ClassclasstypeServiceImpl implements ClassclasstypeServiceInter{
  @Resource
  private ClassclasstypeDaoInter  classclasstypeDaoImpl;

  private static final Logger log = Logger.getLogger(ClassclasstypeServiceImpl.class);
  @Override
  @Transactional
  public ClassClassType insertClassclasstypeEntity(ClassClassType entity,LoginUser user) {
    
    
    
    
    
    
    
    
    return classclasstypeDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public ClassClassType editClassclasstypeEntity(ClassClassType entity,LoginUser user) {
    
    ClassClassType entity2 = classclasstypeDaoImpl.getEntity(entity.getId());
    
    
    
    entity2.setClassid(entity.getClassid());
    entity2.setClasstypeid(entity.getClasstypeid());
    entity2.setId(entity.getId());
    classclasstypeDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteClassclasstypeEntity(String id,LoginUser user) {
    
    classclasstypeDaoImpl.deleteEntity(classclasstypeDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public ClassClassType getClassclasstypeEntity(String id) {
    
    if (id == null){return null;}
    return classclasstypeDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createClassclasstypeSimpleQuery(DataQuery query) {
    
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_L_CLASSCLASSTYPE",
            "ID,CLASSID,CLASSTYPEID");
    return dbQuery;
  }

}
