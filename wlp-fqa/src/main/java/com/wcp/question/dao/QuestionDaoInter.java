package com.wcp.question.dao;

import com.wcp.question.domain.Question;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

 
public interface QuestionDaoInter {
	 
	public void deleteEntity(Question question);

	 
	public Question getEntity(String questionid);

	 
	public Question insertEntity(Question question);

	 
	public int getAllListNum();

	 
	public void editEntity(Question question);

	 
	public Session getSession();

	 
	public DataResult runSqlQuery(DataQuery query);

	 
	public void deleteEntitys(List<DBRule> rules);

	 
	public List<Question> selectEntitys(List<DBRule> rules);

	 
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	 
	public int countEntitys(List<DBRule> rules);

	 
	public void delExpertQuestion(String questionid);
}