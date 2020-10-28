package com.wcp.question.service.impl;

import com.wcp.question.domain.Questionplus;

import org.apache.log4j.Logger;
import com.wcp.question.dao.QuestionplusDaoInter;
import com.wcp.question.service.QuestionplusServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

 
@Service
public class QuestionplusServiceImpl implements QuestionplusServiceInter {
	@Resource
	private QuestionplusDaoInter questionplusDaoImpl;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(QuestionplusServiceImpl.class);

	@Override
	@Transactional
	public Questionplus insertQuestionplusEntity(Questionplus entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return questionplusDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Questionplus editQuestionplusEntity(Questionplus entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		Questionplus entity2 = questionplusDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setCtime(entity.getCtime());
		entity2.setDescription(entity.getDescription());
		entity2.setQuestionid(entity.getQuestionid());
		entity2.setId(entity.getId());
		questionplusDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteQuestionplusEntity(String id, LoginUser user) {
		Questionplus plus=questionplusDaoImpl.getEntity(id);
		questionplusDaoImpl.deleteEntity(plus);
//		List<String> files = FarmDocFiles.getFilesIdFromHtml(plus.getDescription());
//		for (String fileid : files) {
//			farmFileManagerImpl.cancelFile(fileid);
//			farmFileManagerImpl.delFile(fileid, user);
//		}
	}

	@Override
	@Transactional
	public Questionplus getQuestionplusEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return questionplusDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createQuestionplusSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WCP_QUESTION_PLUS", "ID,CTIME,DESCRIPTION,QUESTIONID");
		return dbQuery;
	}

	// ----------------------------------------------------------------------------------
	public QuestionplusDaoInter getQuestionplusDaoImpl() {
		return questionplusDaoImpl;
	}

	public void setQuestionplusDaoImpl(QuestionplusDaoInter dao) {
		this.questionplusDaoImpl = dao;
	}

	@Override
	public List<Questionplus> getQuestionplusByQuestionId(String questionid) {
		List<Questionplus> list = questionplusDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("QUESTIONID", questionid, "=")).toList());
		Collections.sort(list, new Comparator<Questionplus>() {
			@Override
			public int compare(Questionplus o1, Questionplus o2) {
				return o1.getCtime().compareTo(o2.getCtime());
			}
		});
		return list;
	}
}
