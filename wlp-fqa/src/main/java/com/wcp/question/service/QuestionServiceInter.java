package com.wcp.question.service;

import com.wcp.question.domain.Qanswer;
import com.wcp.question.domain.Question;
import com.wcp.question.domain.Questionplus;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.Map;

import com.farm.core.auth.domain.LoginUser;

 
public interface QuestionServiceInter {

	 
	public Question editQuestion(Question entity, LoginUser user);

	 
	public void deleteQuestionEntity(String id, LoginUser user);

	 
	public Question getQuestionEntity(String id);

	 
	public DataQuery createQuestionSimpleQuery(DataQuery query);

	 
	public Question creatQuestion(String title, String typeid, String text, String knowtag, String fileId, int price,
			boolean isanonymous, LoginUser currentUser);


	 
	public DataResult getTypeFqas(LoginUser user, String typeid, int pagesize, Integer pagenum);

	 
	public Qanswer addQanswer(String questionid, String content,boolean isAnonymous, LoginUser currentUser);

	 
	public void addQuestionClosely(String questionid, String text, LoginUser loginUser);

	 
	public Questionplus getQuestionClosely(String closelyid);

	 
	public Questionplus editQuestionClosely(String closelyid, String text, LoginUser currentUser);

	 
	public void visit(String questionid, LoginUser currentUser, String currentIp);

	 
	public void refreshAnswersNum(String questionid);


	 
	public DataResult getUserQuestions(String userid, int pagesize, Integer pagenum);


	 
	public DataResult getHotQuestionByFinish(int size);

	 
	public DataResult getHotQuestionByWaiting(int size,LoginUser user);

	 
	public DataResult getHotQuestion(int size,LoginUser user);

	 
	public Map<String, Integer> getStatNum();

	 
	public void deleteQuestionEntityByLogic(String questionid, LoginUser currentUser);

	 
	public void chooseBestAnswer(String qanswerid, LoginUser currentUser);

	 
	public void move2Type(String questionids, String typeId, LoginUser currentUser);

	 
	public Question editQuestion(String id, String title, String typeid, String text, String fqatag, String fileId,
			boolean isanonymous, LoginUser currentUser);


}