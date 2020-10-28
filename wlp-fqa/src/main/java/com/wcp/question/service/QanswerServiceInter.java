package com.wcp.question.service;

import com.wcp.question.domain.Qanswer;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface QanswerServiceInter {
	 
	public Qanswer insertQanswerEntity(Qanswer entity, LoginUser user);

	 
	public Qanswer editQanswerEntity(Qanswer entity, LoginUser user);

	 
	public void deleteQanswerEntity(String id, LoginUser user);

	 
	public Qanswer getQanswerEntity(String id);

	 
	public DataQuery createQanswerSimpleQuery(DataQuery query);

	 
	public List<Qanswer> getQanswersWaitByQuestion(String questionid);
	
	 
	public List<Qanswer> getQanswersAllByQuestion(String questionid);
	 
	public int getQanswerNumByQuestionid(String questionid);

	 
	public int approveOf(String qanswerid, LoginUser currentUser);

	 
	public int oppose(String qanswerid, LoginUser currentUser);

	 
	public void reply(String questionid, String text, String qanswerid, LoginUser currentUser);

	 
	public void delAnswer(String qanswerid, LoginUser currentUser);

	 
	public void delAnswerSuper(String qanswerid, LoginUser currentUser);


	 
	public void chooseBestAnswerSuper(String qanswerid, LoginUser currentUser);

	 
	public DataResult getUserAnswers(String userid, int pagesize, Integer pagenum);

}