package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassTag;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClasstagDaoInter;
import com.farm.learn.service.ClasstagServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
 
@Service
public class ClasstagServiceImpl implements ClasstagServiceInter{
  @Resource
  private ClasstagDaoInter  classtagDaoImpl;

  private static final Logger log = Logger.getLogger(ClasstagServiceImpl.class);
  @Override
  @Transactional
  public ClassTag insertClasstagEntity(ClassTag entity,LoginUser user) {
    
    
    
    
    
    
    
    
    return classtagDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public ClassTag editClasstagEntity(ClassTag entity,LoginUser user) {
    
    ClassTag entity2 = classtagDaoImpl.getEntity(entity.getId());
    
    
    
    entity2.setClassid(entity.getClassid());
    entity2.setTagid(entity.getTagid());
    entity2.setId(entity.getId());
    classtagDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteClasstagEntity(String id,LoginUser user) {
    
    classtagDaoImpl.deleteEntity(classtagDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public ClassTag getClasstagEntity(String id) {
    
    if (id == null){return null;}
    return classtagDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createClasstagSimpleQuery(DataQuery query) {
    
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WLP_L_CLASSTAG",
            "ID,CLASSID,TAGID");
    return dbQuery;
  }

}
