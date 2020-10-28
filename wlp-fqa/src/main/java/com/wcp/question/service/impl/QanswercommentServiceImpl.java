package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswercomment;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QanswercommentDaoInter;
import com.wcp.question.service.QanswercommentServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
 
@Service
public class QanswercommentServiceImpl implements QanswercommentServiceInter{
  @Resource
  private QanswercommentDaoInter  qanswercommentDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QanswercommentServiceImpl.class);
  @Override
  @Transactional
  public Qanswercomment insertQanswercommentEntity(Qanswercomment entity,LoginUser user) {
    
    
    
    
    
    
    
    
    return qanswercommentDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Qanswercomment editQanswercommentEntity(Qanswercomment entity,LoginUser user) {
    
    Qanswercomment entity2 = qanswercommentDaoImpl.getEntity(entity.getId());
    
    
    
    entity2.setDescription(entity.getDescription());
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setPstate(entity.getPstate());
    entity2.setCuser(entity.getCuser());
    entity2.setCusername(entity.getCusername());
    entity2.setCtime(entity.getCtime());
    entity2.setId(entity.getId());
    qanswercommentDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQanswercommentEntity(String id,LoginUser user) {
    
    qanswercommentDaoImpl.deleteEntity(qanswercommentDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Qanswercomment getQanswercommentEntity(String id) {
    
    if (id == null){return null;}
    return qanswercommentDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQanswercommentSimpleQuery(DataQuery query) {
    
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QANSWER_COMMENT",
            "ID,DESCRIPTION,ANSWERID,PSTATE,CUSER,CUSERNAME,CTIME");
    return dbQuery;
  }
  
  public QanswercommentDaoInter getQanswercommentDaoImpl() {
    return qanswercommentDaoImpl;
  }

  public void setQanswercommentDaoImpl(QanswercommentDaoInter dao) {
    this.qanswercommentDaoImpl= dao;
  }

}
