package com.wcp.question.service.impl;

import com.wcp.question.domain.Questiondetail;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QuestiondetailDaoInter;
import com.wcp.question.service.QuestiondetailServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
 
@Service
public class QuestiondetailServiceImpl implements QuestiondetailServiceInter{
  @Resource
  private QuestiondetailDaoInter  questiondetailDaoImpl;

  @SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(QuestiondetailServiceImpl.class);
  @Override
  @Transactional
  public Questiondetail insertQuestiondetailEntity(Questiondetail entity,LoginUser user) {
    
    
    
    
    
    
    
    
    return questiondetailDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public Questiondetail editQuestiondetailEntity(Questiondetail entity,LoginUser user) {
    
    Questiondetail entity2 = questiondetailDaoImpl.getEntity(entity.getId());
    
    
    
    entity2.setVtype(entity.getVtype());
    entity2.setUserip(entity.getUserip());
    entity2.setQuestionid(entity.getQuestionid());
    entity2.setPstate(entity.getPstate());
    entity2.setCuser(entity.getCuser());
    entity2.setCusername(entity.getCusername());
    entity2.setCtime(entity.getCtime());
    entity2.setId(entity.getId());
    questiondetailDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteQuestiondetailEntity(String id,LoginUser user) {
    
    questiondetailDaoImpl.deleteEntity(questiondetailDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public Questiondetail getQuestiondetailEntity(String id) {
    
    if (id == null){return null;}
    return questiondetailDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createQuestiondetailSimpleQuery(DataQuery query) {
    
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WCP_QUESTION_DETAIL",
            "ID,VTYPE,USERIP,QUESTIONID,PSTATE,CUSER,CUSERNAME,CTIME");
    return dbQuery;
  }
  
  public QuestiondetailDaoInter getQuestiondetailDaoImpl() {
    return questiondetailDaoImpl;
  }

  public void setQuestiondetailDaoImpl(QuestiondetailDaoInter dao) {
    this.questiondetailDaoImpl= dao;
  }

}
