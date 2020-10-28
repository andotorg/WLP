package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswerplus;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QanswerplusDaoInter;
import com.wcp.question.service.QanswerplusServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
 
@Service
public class QanswerplusServiceImpl implements QanswerplusServiceInter{
  @Resource
  private QanswerplusDaoInter  qanswerplusDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QanswerplusServiceImpl.class);
  @Override
  @Transactional
  public Qanswerplus insertQanswerplusEntity(Qanswerplus entity,LoginUser user) {
    
    
    
    
    
    
    
    
    return qanswerplusDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Qanswerplus editQanswerplusEntity(Qanswerplus entity,LoginUser user) {
    
    Qanswerplus entity2 = qanswerplusDaoImpl.getEntity(entity.getId());
    
    
    
    entity2.setCtime(entity.getCtime());
    entity2.setDescription(entity.getDescription());
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setId(entity.getId());
    qanswerplusDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQanswerplusEntity(String id,LoginUser user) {
    
    qanswerplusDaoImpl.deleteEntity(qanswerplusDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Qanswerplus getQanswerplusEntity(String id) {
    
    if (id == null){return null;}
    return qanswerplusDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQanswerplusSimpleQuery(DataQuery query) {
    
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QANSWER_PLUS",
            "ID,CTIME,DESCRIPTION,ANSWERID");
    return dbQuery;
  }
  
  public QanswerplusDaoInter getQanswerplusDaoImpl() {
    return qanswerplusDaoImpl;
  }

  public void setQanswerplusDaoImpl(QanswerplusDaoInter dao) {
    this.qanswerplusDaoImpl= dao;
  }

}
