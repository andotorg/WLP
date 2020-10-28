package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswer;
import com.wcp.question.domain.Qanswercomment;
import com.wcp.question.domain.Question;
import com.farm.core.time.TimeTool;
import com.farm.wcp.ekca.OperateEvent;
import com.wcp.question.commons.FqaQanswerCache;
import com.wcp.question.dao.QanswerDaoInter;
import com.wcp.question.dao.QanswercommentDaoInter;
import com.wcp.question.dao.QuestionDaoInter;
import com.wcp.question.service.QanswerServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.LinkMessage;
import com.farm.core.inter.domain.Message;
import com.farm.core.inter.impl.AppOpratorHandle;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;

 
@Service
public class QanswerServiceImpl implements QanswerServiceInter {
	@Resource
	private QuestionDaoInter questionDaoImpl;
	@Resource
	private QanswerDaoInter qanswerDaoImpl;
	@Resource
	private QanswercommentDaoInter qanswercommentDaoImpl;

	@Override
	@Transactional
	public Qanswer insertQanswerEntity(Qanswer entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		if (entity.getPstate() == null) {
			entity.setPstate("1");
		}
		return qanswerDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Qanswer editQanswerEntity(Qanswer entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		Qanswer entity2 = qanswerDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPraiseyes(entity.getPraiseyes());
		entity2.setPraiseno(entity.getPraiseno());
		entity2.setAnsweringnum(entity.getAnsweringnum());
		entity2.setAdopttime(entity.getAdopttime());
		entity2.setDescription(entity.getDescription());
		entity2.setPcontent(entity.getPcontent());
		entity2.setQuestionid(entity.getQuestionid());
		entity2.setPstate(entity.getPstate());
		entity2.setCuser(entity.getCuser());
		entity2.setCusername(entity.getCusername());
		entity2.setCtime(entity.getCtime());
		entity2.setId(entity.getId());
		qanswerDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteQanswerEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		qanswerDaoImpl.deleteEntity(qanswerDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Qanswer getQanswerEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return qanswerDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createQanswerSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WCP_QANSWER",
				"ID,PRAISEYES,PRAISENO,ANSWERINGNUM,ADOPTTIME,DESCRIPTION,PCONTENT,QUESTIONID,PSTATE,CUSER,CUSERNAME,CTIME");
		return dbQuery;
	}

	@Override
	public List<Qanswer> getQanswersWaitByQuestion(String questionid) {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("QUESTIONID", questionid, "="));
		rules.add(new DBRule("PSTATE", "1", "="));
		List<Qanswer> list = qanswerDaoImpl.selectEntitys(rules);
		Collections.sort(list, new Comparator<Qanswer>() {
			@Override
			public int compare(Qanswer o1, Qanswer o2) {
				return o2.getPraiseyes().compareTo(o1.getPraiseyes());
			}
		});
		return list;
	}

	@Override
	public List<Qanswer> getQanswersAllByQuestion(String questionid) {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("QUESTIONID", questionid, "="));
		List<Qanswer> list = qanswerDaoImpl.selectEntitys(rules);
		Collections.sort(list, new Comparator<Qanswer>() {
			@Override
			public int compare(Qanswer o1, Qanswer o2) {
				return o2.getPraiseyes().compareTo(o1.getPraiseyes());
			}
		});
		return list;
	}

	@Override
	public int getQanswerNumByQuestionid(String questionid) {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("PSTATE", "0", "!="));
		rules.add(new DBRule("QUESTIONID", questionid, "="));
		return qanswerDaoImpl.countEntitys(rules);
	}

	@Override
	@Transactional
	public int approveOf(String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = qanswerDaoImpl.getEntity(qanswerid);
		if (!FqaQanswerCache.getInstance().add(currentUser.getId(), qanswerid)) {
			return qanswer.getPraiseyes();
		}
		qanswer.setPraiseyes(qanswer.getPraiseyes() + 1);
		qanswerDaoImpl.editEntity(qanswer);

		AppOpratorHandle.getInstance().recordOperate(OperateEvent.getEvaluateFqaEvent(1),
				currentUser != null ? currentUser.getId() : null, currentUser.getIp(), null, qanswer.getQuestionid(),
				null, qanswer.getId(), qanswer.getCuser(), null);
		return qanswer.getPraiseyes();
	}

	@Override
	@Transactional
	public int oppose(String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = qanswerDaoImpl.getEntity(qanswerid);
		if (!FqaQanswerCache.getInstance().add(currentUser.getId(), qanswerid)) {
			return qanswer.getPraiseno();
		}
		qanswer.setPraiseno(qanswer.getPraiseno() + 1);
		qanswerDaoImpl.editEntity(qanswer);
		AppOpratorHandle.getInstance().recordOperate(OperateEvent.getEvaluateFqaEvent(-1),
				currentUser != null ? currentUser.getId() : null, currentUser != null ? currentUser.getIp() : null,
				null, qanswer.getQuestionid(), null, qanswer.getId(), qanswer.getCuser(), null);
		return qanswer.getPraiseno();
	}

	@Override
	@Transactional
	public void reply(String questionid, String text, String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = getQanswerEntity(qanswerid);
		qanswer.setAnsweringnum(qanswer.getAnsweringnum() + 1);
		editQanswerEntity(qanswer, currentUser);
		Qanswercomment entity = new Qanswercomment();
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCuser(currentUser.getId());
		entity.setCusername(currentUser.getName());
		entity.setPstate("1");
		entity.setDescription(text);
		entity.setAnswerid(qanswerid);
		qanswercommentDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public void delAnswer(String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = getQanswerEntity(qanswerid);
		Question question = questionDaoImpl.getEntity(qanswer.getQuestionid());
		if (!currentUser.getId().equals(qanswer.getCuser())) {
			throw new RuntimeException("沒有刪除权限,非创建者!");
		}
		if (qanswer.getPstate().equals("2")) {
			throw new RuntimeException("无法删除，该答案被采纳!");
		}
		if (question.getPstate().equals("2")) {
			throw new RuntimeException("无法删除，该问题已经完成!");
		}
		qanswer.setPstate("0");
		editQanswerEntity(qanswer, currentUser);

		AppOpratorHandle.getInstance().recordOperate(OperateEvent.getDelAnswerEvent(),
				currentUser != null ? currentUser.getId() : null, currentUser != null ? currentUser.getIp() : null,
				null, qanswer.getQuestionid(), null, qanswer.getId(), qanswer.getCuser(), null);
	}

	@Override
	@Transactional
	public void delAnswerSuper(String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = getQanswerEntity(qanswerid);
		if (qanswer.getPstate().equals("2")) {
			throw new RuntimeException("无法删除，该答案被采纳!");
		}
		qanswer.setPstate("0");
		editQanswerEntity(qanswer, currentUser);
//
//		List<String> files = FarmDocFiles.getFilesIdFromHtml(qanswer.getDescription());
//		for (String fileid : files) {
//			farmFileManagerImpl.cancelFile(fileid);
//			farmFileManagerImpl.delFile(fileid, currentUser);
//		}
	}

	@Override
	@Transactional
	public void chooseBestAnswerSuper(String qanswerid, LoginUser currentUser) {
		// 新采纳答案
		Qanswer qanswerNEW = getQanswerEntity(qanswerid);
		List<Qanswer> answers = getQanswersAllByQuestion(qanswerNEW.getQuestionid());
		// 将选中答案设置为未选
		for (Qanswer answer : answers) {
			if (answer.getPstate().equals("2")) {
				answer.setPstate("1");
				editQanswerEntity(answer, currentUser);
			}
		}
		// 初始化为无答案的状态
		Question question = questionDaoImpl.getEntity(qanswerNEW.getQuestionid());
		if (question.getPstate().equals("2")) {
			// 原来的采纳答案
			Qanswer qanswerYL = getQanswerEntity(question.getQanswerid());
			qanswerYL.setPstate("1");
			editQanswerEntity(qanswerYL, currentUser);
		}
		{
			// 問題綁定id
			question.setQanswerid(qanswerid);
			question.setPstate("2");
			questionDaoImpl.editEntity(question);
		}
		{// 回答標記爲已綁定
			qanswerNEW.setPstate("2");
			editQanswerEntity(qanswerNEW, currentUser);
		}
	}

	@Override
	public DataResult getUserAnswers(String userid, int pagesize, Integer pagenum) {
		DataQuery query = DataQuery.getInstance(pagenum,
				"B.TITLE AS TITLE,B.ID AS QUESTIONID,B.PSTATE AS QUESTIONSTATE,B.CTIME AS CTIME,A.PSTATE as PSTATE",
				"WCP_QANSWER A LEFT JOIN WCP_QUESTION B ON A.QUESTIONID=B.ID");
		query.setDistinct(true);
		query.addRule(new DBRule("A.PSTATE", "0", "!="));
		query.addRule(new DBRule("B.PSTATE", "0", "!="));
		query.addRule(new DBRule("A.CUSER", userid, "="));
		query.addSort(new DBSort("B.CTIME", "desc"));
		query.setDistinct(true);
		query.setPagesize(pagesize);
		try {
			return query.search();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
