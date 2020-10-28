package com.wcp.question.service.impl;

import com.wcp.question.domain.Qanswer;
import com.wcp.question.domain.Qanswercomment;
import com.wcp.question.domain.Question;
import com.wcp.question.domain.Questionplus;
import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;
import com.farm.util.web.FarmproHotnum;
import com.farm.util.web.HtmlUtils;
import com.farm.util.web.WebVisitBuff;
import com.farm.wcp.ekca.OperateEvent;
import com.farm.wcp.ekca.UpdateType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.wcp.question.dao.QuestionDaoInter;
import com.wcp.question.service.QanswerServiceInter;
import com.wcp.question.service.QuestionServiceInter;
import com.wcp.question.service.QuestionplusServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.LinkMessage;
import com.farm.core.inter.domain.Message;
import com.farm.core.inter.impl.AppOpratorHandle;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;

 
@Service
public class QuestionServiceImpl implements QuestionServiceInter {
	@Resource
	private QuestionDaoInter questionDaoImpl;
	@Resource
	private QanswerServiceInter qanswerServiceImpl;
	@Resource
	private QuestionplusServiceInter questionPlusServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	private static final Logger log = Logger.getLogger(QuestionServiceImpl.class);

	@Override
	@Transactional
	public Question editQuestion(Question entity, LoginUser user) {
		Question question = editQuestionEntity(entity, user);
		return question;
	}

	 
	private Question editQuestionEntity(Question entity, LoginUser user) {
		Question entity2 = questionDaoImpl.getEntity(entity.getId());
		String oldState = entity2.getPstate();
		String newState = entity.getPstate();
		entity2.setAnsweringnum(entity.getAnsweringnum());
		entity2.setVisitnum(entity.getVisitnum());
		entity2.setPraiseno(entity.getPraiseno());
		entity2.setPraiseyes(entity.getPraiseyes());
		entity2.setAttentions(entity.getAttentions());
		entity2.setAnswers(entity.getAnswers());
		entity2.setAnonymous(entity.getAnonymous());
		entity2.setEndtime(entity.getEndtime());
		entity2.setPubtime(entity.getPubtime());
		entity2.setDescription(entity.getDescription());
		entity2.setTitle(entity.getTitle());
		entity2.setTypeid(entity.getTypeid());
		entity2.setPrice(entity.getPrice());
		entity2.setPcontent(entity.getPcontent());
		entity2.setTags(entity.getTags());
		entity2.setQanswerid(entity.getQanswerid());
		entity2.setPstate(entity.getPstate());
		// farmFileManagerImpl.updateFileState(entity2.getImgid(),
		// entity.getImgid(), user,
		// FILE_APPLICATION_TYPE.FQA_IMG.getValue());
		entity2.setImgid(entity.getImgid());
		entity2.setCuser(entity.getCuser());
		entity2.setCusername(entity.getCusername());
		entity2.setCtime(entity.getCtime());
		// List<String> files =
		// FarmDocFiles.getFilesIdFromHtml(entity2.getDescription());
		// for (String id : files) {
		// farmFileManagerImpl.submitFile(id,
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_FILE.getValue());
		// }
		questionDaoImpl.editEntity(entity2);
		if (oldState.equals("2") && newState.equals("1")) {
			// 将所有的答案设置为备选
			List<Qanswer> qanswers = qanswerServiceImpl.getQanswersAllByQuestion(entity2.getId());
			for (Qanswer answer : qanswers) {
				answer.setPstate("1");
				qanswerServiceImpl.editQanswerEntity(answer, user);
			}
		}
		// 此处多处引用，不记录日志
		return entity2;
	}

	private void editQuetionRuninfo(Question question) {
		Question entity2 = questionDaoImpl.getEntity(question.getId());
		entity2.setAnsweringnum(question.getAnsweringnum());
		entity2.setVisitnum(question.getVisitnum());
		entity2.setPraiseno(question.getPraiseno());
		entity2.setPraiseyes(question.getPraiseyes());
		entity2.setHotnum(question.getHotnum());
		questionDaoImpl.editEntity(entity2);
	}

	@Override
	@Transactional
	public void deleteQuestionEntity(String id, LoginUser user) {
		Question question = questionDaoImpl.getEntity(id);
		questionDaoImpl.deleteEntity(question);
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getDel(user.getId()));
	}

	@Override
	@Transactional
	public Question getQuestionEntity(String id) {
		if (id == null) {
			return null;
		}
		Question question = questionDaoImpl.getEntity(id);
		if (question == null) {
			// farmDocIndexManagerImpl.delLuceneIndex(id);
			throw new RuntimeException("该问答不存在!");
		}
		return question;
	}

	@Override
	@Transactional
	public DataQuery createQuestionSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WCP_QUESTION a left join FARM_DOCTYPE b on a.TYPEID=b.ID left join FARM_EXPERT_QUESTION c on c.QUESTIONID=a.id and c.PSTATE='1' left join FARM_EXPERT d on d.id=c.expertid left join ALONE_AUTH_USER e on d.userid=e.id",
				"a.ID AS ID,a.HOTNUM AS HOTNUM,e.name as expertname, a.ANSWERINGNUM AS ANSWERINGNUM, a.VISITNUM AS VISITNUM, a.PRAISENO AS PRAISENO, a.PRAISEYES AS PRAISEYES, a.ATTENTIONS AS ATTENTIONS, a.ANSWERS AS ANSWERS, a.ANONYMOUS AS ANONYMOUS, a.ENDTIME AS ENDTIME, a.PUBTIME AS PUBTIME, a.DESCRIPTION AS DESCRIPTION, a.TITLE AS TITLE, a.TYPEID AS TYPEID, a.PRICE AS PRICE, a.PCONTENT AS PCONTENT, a.TAGS AS TAGS, a.PSTATE AS PSTATE, a.CUSER AS CUSER, a.CUSERNAME AS CUSERNAME, a.CTIME AS CTIME, b. NAME AS TYPENAME");
		dbQuery.addRule(new DBRule("a.PSTATE", "0", "!="));
		return dbQuery;
	}

	@Override
	@Transactional
	public Question creatQuestion(String title, String typeid, String text, String knowtag, String fileId, int price,
			boolean isanonymous, LoginUser currentUser) {
		// 1.创建问题实体
		Question question = new Question();
		question.setAnsweringnum(0);
		question.setVisitnum(0);
		question.setPraiseno(0);
		question.setPraiseyes(0);
		question.setAttentions(0);
		question.setAnswers(0);
		question.setHotnum(0);
		question.setAnonymous(isanonymous ? "1" : "0");
		question.setEndtime(null);
		question.setPubtime(TimeTool.getTimeDate14());
		question.setDescription(text);
		question.setTitle(title);
		question.setTypeid(typeid);
		question.setPrice(price);
		if (fileId != null && !fileId.isEmpty()) {
			question.setImgid(fileId);
		}
		if (text != null) {
			String html = HtmlUtils.HtmlRemoveTag(text);
			question.setShortdesc(html.length() > 256 ? html.substring(0, 256) : html);
		}
		question.setPcontent(null);
		question.setTags(knowtag);
		question.setPstate("1");
		question.setCuser(currentUser.getId());
		question.setCusername(currentUser.getName());
		question.setCtime(TimeTool.getTimeDate14());
		question.setEtime(TimeTool.getTimeDate14());
		question.setLastvtime(TimeTool.getTimeDate12());
		questionDaoImpl.insertEntity(question);
		// 提交预览图附件为已用
		// farmFileManagerImpl.submitFile(question.getImgid(),
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_IMG.getValue());
		// List<String> files = FarmDocFiles.getFilesIdFromHtml(text);
		// for (String id : files) {
		// farmFileManagerImpl.submitFile(id,
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_FILE.getValue());
		// }
		// Fqa fqa = initFqa(question);
		// FqaBrief fqabrief = fqa.getBrief();
		// farmDocIndexManagerImpl.addLuceneIndex(fqabrief);
		// 
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getNew(currentUser.getId()));
		return question;
	}

	@Override
	@Transactional
	public DataResult getTypeFqas(LoginUser user, String typeid, int pagesize, Integer pagenum) {
		// DataQuerys.wipeVirus(typeid);
		// String typeids = "'NONE'";
		// for (String id : farmDocTypeManagerImpl.getUserReadTypeIds(user)) {
		// if (typeids == null) {
		// typeids = "'" + id + "'";
		// } else {
		// typeids = typeids + "," + "'" + id + "'";
		// }
		// }
		// FarmDoctype type = farmDocTypeManagerImpl.getType(typeid);
		// DataQuery query = DataQuery.init(new DataQuery(),
		// "WCP_QUESTION a LEFT JOIN farm_doctype d ON d.ID = a.TYPEID LEFT JOIN
		// ALONE_AUTH_USER e ON e.ID = a.CUSER",
		// "A.ID AS QUESTIONID,A.PRICE as PRICE,A.PSTATE AS PSTATE,A.ANONYMOUS
		// AS ANONYMOUS,A.QANSWERID AS QANSWERID, A.TITLE AS TITLE, A.SHORTDESC
		// AS SHORTDESC, A.CUSERNAME AS CUSERNAME, A.PUBTIME AS PUBTIME, A.TAGS
		// AS TAGS, A.IMGID AS IMGID, A.VISITNUM AS VISITNUM, A.PRAISEYES AS
		// PRAISEYES, A.PRAISENO AS PRAISENO, A.HOTNUM AS HOTNUM, A.ANSWERS AS
		// ANSWERS, D.ID AS TYPEID, D. NAME AS TYPENAME, E.ID AS USERID, E. NAME
		// AS USERNAME, E.IMGID AS USERIMGID");
		// query.addSort(new DBSort("a.etime", "desc"));
		// query.setCurrentPage(pagenum);
		// query.addRule(new DBRule("a.PSTATE", "0", "!="));
		// if (type != null) {
		// query.addRule(new DBRule("d.TREECODE", type.getTreecode(), "like-"));
		// }
		// query.addSqlRule("and (d.id in (" + typeids + ") or d.READPOP='0')");
		// query.setPagesize(pagesize);
		DataResult docs = null;
		// try {
		// docs = query.search();
		// } catch (SQLException e) {
		// log.error(e.toString());
		// return DataResult.getInstance();
		// }
		// for (Map<String, Object> map : docs.getResultList()) {
		// if ("1".equals(map.get("ANONYMOUS"))) {
		// 
		// map.put("CUSERNAME", "匿名");
		// map.put("USERIMGID", "匿名");
		// map.put("USERNAME", "匿名");
		// map.put("USERID", null);
		// }
		// if (map.get("USERIMGID") != null &&
		// !map.get("USERIMGID").toString().isEmpty()) {
		// map.put("PHOTOURL",
		// farmFileManagerImpl.getPhotoURL(map.get("USERIMGID").toString()));
		// } else {
		// map.put("PHOTOURL", farmFileManagerImpl.getPhotoURL("NONE"));
		// }
		// }
		return docs;
	}

	@Override
	@Transactional
	public Qanswer addQanswer(String questionid, String content, boolean isAnonymous, LoginUser currentUser) {
		Qanswer qanswer = new Qanswer();
		qanswer.setQuestionid(questionid);
		qanswer.setDescription(content);
		qanswer.setAnsweringnum(0);
		qanswer.setPraiseno(0);
		qanswer.setAnonymous(isAnonymous ? "1" : "0");
		qanswer.setPraiseyes(0);
		qanswer = qanswerServiceImpl.insertQanswerEntity(qanswer, currentUser);
		Question question = getQuestionEntity(questionid);
		// List<String> files =
		// FarmDocFiles.getFilesIdFromHtml(qanswer.getDescription());
		// for (String id : files) {
		// farmFileManagerImpl.submitFile(id,
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_ANSWER_FILE.getValue());
		// }
		// Message messageMaster = new Message(TYPE_KEY.question_answer);
		// messageMaster.initTitle().setString(question.getTitle());
		// messageMaster.initText().setString(question.getTitle());
		// messageMaster.addLink(new LinkMessage("webquest/fqa/Pub" + questionid
		// + ".html", question.getTitle()));
		// usermessageServiceImpl.sendMessage(messageMaster,
		// question.getCuser());
		// AppOpratorHandle.getInstance().recordOperate(OperateEvent.getAnswerEvent(),
		// currentUser != null ? currentUser.getId() : null, currentUser != null
		// ? currentUser.getIp() : null,
		// null, questionid, null, qanswer.getId(), qanswer.getCuser(), null);
		return qanswer;
	}

	@Override
	@Transactional
	public void addQuestionClosely(String questionid, String text, LoginUser loginUser) {
		Questionplus entity = new Questionplus();
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setDescription(text);
		entity.setQuestionid(questionid);
		entity = questionPlusServiceImpl.insertQuestionplusEntity(entity, loginUser);
		Question question = getQuestionEntity(questionid);
		// Fqa fqa = initFqa(question);
		// FqaBrief fqabrief = fqa.getBrief();
		// List<String> files = FarmDocFiles.getFilesIdFromHtml(text);
		// for (String id : files) {
		// farmFileManagerImpl.submitFile(id,
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_CLOSELY_FILE.getValue());
		// }
		// farmDocIndexManagerImpl.delLuceneIndex(questionid);
		// farmDocIndexManagerImpl.addLuceneIndex(fqabrief);
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getUpdate(loginUser.getId()));
		// AppOpratorHandle.getInstance().recordOperate(OperateEvent.getQuestionCloselyEvent(),
		// loginUser != null ? loginUser.getId() : null, loginUser != null ?
		// loginUser.getIp() : null, null,
		// question.getId(), null, question.getId(), question.getCuser(), null);
	}

	 
	private int countHotNum(Question question) {
		int hotnum = FarmproHotnum.getHotnum(question.getLastvtime(), question.getHotnum(), 1,
				Integer.valueOf(FarmParameterService.getInstance().getParameter("config.doc.hot.weight")));
		return hotnum;
	}

	@Override
	@Transactional
	public void visit(String questionid, LoginUser user, String currentIp) {
		WebVisitBuff vbuff = WebVisitBuff.getInstance("FQA", 1000);
		String userId = "noLogin";
		if (user != null) {
			userId = user.getId();
		}
		Question question = getQuestionEntity(questionid);
		if (vbuff.canVisite(questionid + currentIp + userId)) {
			question.setVisitnum(question.getVisitnum() + 1);
		}
		question.setHotnum(countHotNum(question));
		question.setLastvtime(TimeTool.getTimeDate12());
		editQuetionRuninfo(question);
		AppOpratorHandle.getInstance().recordOperate(OperateEvent.getVisitFqaEvent(question.getTitle()),
				user != null ? user.getId() : null, currentIp, null, question.getId(), null, question.getId(),
				question.getCuser(), null);
	}

	@Override
	@Transactional
	public void refreshAnswersNum(String questionid) {
		Question question = getQuestionEntity(questionid);
		int num = qanswerServiceImpl.getQanswerNumByQuestionid(questionid);
		question.setAnswers(num);
		questionDaoImpl.editEntity(question);
	}

	@Override
	public DataResult getUserQuestions(String userid, int pagesize, Integer pagenum) {
		DataQuery query = DataQuery.getInstance(pagenum, "ID, TITLE, PUBTIME, CUSERNAME, VISITNUM, ANSWERS,PSTATE",
				"WCP_QUESTION");
		query.addRule(new DBRule("PSTATE", "0", "!="));
		query.addRule(new DBRule("CUSER", userid, "="));
		query.addSort(new DBSort("PUBTIME", "desc"));
		query.setDistinct(true);
		query.setPagesize(pagesize);
		try {
			return query.search();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public DataResult getHotQuestionByFinish(int size) {
		try {
			DataQuery query = DataQuery.getInstance(1, "ID, TITLE, PUBTIME, CUSERNAME, VISITNUM, ANSWERS,PSTATE",
					"WCP_QUESTION");
			query.setPagesize(size);
			query.addRule(new DBRule("PSTATE", "2", "="));
			query.addSort(new DBSort("HOTNUM", "DESC"));
			return query.search();
		} catch (Exception e) {
			return DataResult.getInstance();
		}
	}

	@Override
	public DataResult getHotQuestionByWaiting(int size, LoginUser user) {
		try {
			String typeids = "'NONE'";
			// for (String id : farmDocTypeManagerImpl.getUserReadTypeIds(user))
			// {
			// if (typeids == null) {
			// typeids = "'" + id + "'";
			// } else {
			// typeids = typeids + "," + "'" + id + "'";
			// }
			// }
			String popSql1 = null;
			{// 1分类 公开权限或者用户拥有该分类
				if (user != null) {
					popSql1 = " and (b.READPOP='0' or (b.READPOP!='0' and b.id in (" + typeids + ")))";
				} else {
					popSql1 = " and b.READPOP='0'";
				}
			}
			DataQuery query = DataQuery.getInstance(1,
					"A.ID AS ID, A.TITLE AS TITLE, A.PUBTIME AS PUBTIME, A.CUSERNAME AS CUSERNAME, A.VISITNUM AS VISITNUM, A.ANSWERS AS ANSWERS, A.PSTATE AS PSTATE, A.ANONYMOUS AS ANONYMOUS",
					"WCP_QUESTION a left join FARM_DOCTYPE b on a.TYPEID =b.ID");
			query.setPagesize(size);
			query.addRule(new DBRule("a.PSTATE", "1", "="));
			query.addSort(new DBSort("HOTNUM", "DESC"));
			query.addSqlRule(popSql1);
			return query.search();
		} catch (Exception e) {
			return DataResult.getInstance();
		}
	}

	@Override
	public DataResult getHotQuestion(int size, LoginUser user) {
		try {
			String typeids = "'NONE'";
			// for (String id : farmDocTypeManagerImpl.getUserReadTypeIds(user))
			// {
			// if (typeids == null) {
			// typeids = "'" + id + "'";
			// } else {
			// typeids = typeids + "," + "'" + id + "'";
			// }
			// }
			String popSql1 = null;
			{// 1分类 公开权限或者用户拥有该分类
				if (user != null) {
					popSql1 = " and (b.READPOP='0' or (b.READPOP!='0' and b.id in (" + typeids + ")))";
				} else {
					popSql1 = " and b.READPOP='0'";
				}
			}
			DataQuery query = DataQuery.getInstance(1,
					"A.ID AS ID, A.TITLE AS TITLE, A.PUBTIME AS PUBTIME, A.CUSERNAME AS CUSERNAME, A.VISITNUM AS VISITNUM, A.ANSWERS AS ANSWERS, A.PSTATE AS PSTATE, A.ANONYMOUS AS ANONYMOUS",
					"WCP_QUESTION a left join FARM_DOCTYPE b on a.TYPEID =b.ID");
			query.setPagesize(size);
			query.addRule(new DBRule("a.PSTATE", "0", "!="));
			query.addSort(new DBSort("HOTNUM", "DESC"));
			query.addSqlRule(popSql1);
			return query.search();
		} catch (Exception e) {
			return DataResult.getInstance();
		}
	}

	@Override
	@Transactional
	public Map<String, Integer> getStatNum() {
		Map<String, Integer> map = new HashMap<>();
		List<DBRule> rules1 = DBRule.addRule(new ArrayList<DBRule>(), "PSTATE", "0", "!=");
		map.put("ALLQUESTION", questionDaoImpl.countEntitys(rules1));
		List<DBRule> rules2 = DBRule.addRule(new ArrayList<DBRule>(), "PSTATE", "2", "=");
		map.put("FINISHQUESTION", questionDaoImpl.countEntitys(rules2));
		List<DBRule> rules3 = DBRule.addRule(new ArrayList<DBRule>(), "PSTATE", "1", "=");
		map.put("WAITINGQUESTION", questionDaoImpl.countEntitys(rules3));
		return map;
	}

	@Override
	@Transactional
	public void deleteQuestionEntityByLogic(String questionid, LoginUser currentUser) {
		Question question = getQuestionEntity(questionid);
		question.setPstate("0");
		editQuestionEntity(question, currentUser);
		// farmDocIndexManagerImpl.delLuceneIndex(questionid);
		//// 删除问答的时候也删除附件
		// if (StringUtils.isNotBlank(question.getImgid())) {
		// farmFileManagerImpl.cancelFile(question.getImgid());
		// farmFileManagerImpl.delFile(question.getImgid(), currentUser);
		// }
		// 删除所有追加提问的附件
		List<Questionplus> pluss = questionPlusServiceImpl.getQuestionplusByQuestionId(question.getId());
		// for (Questionplus plus : pluss) {
		// List<String> files =
		// FarmDocFiles.getFilesIdFromHtml(plus.getDescription());
		// for (String id : files) {
		// farmFileManagerImpl.cancelFile(id);
		// farmFileManagerImpl.delFile(id, currentUser);
		// }
		// }
		// 删除所有回答问题的附件
		List<Qanswer> answers = qanswerServiceImpl.getQanswersAllByQuestion(question.getId());
		// for (Qanswer answer : answers) {
		// List<String> files =
		// FarmDocFiles.getFilesIdFromHtml(answer.getDescription());
		// for (String id : files) {
		// farmFileManagerImpl.cancelFile(id);
		// farmFileManagerImpl.delFile(id, currentUser);
		// }
		// }
		// 删除所有内容附件
		// List<String> files =
		// FarmDocFiles.getFilesIdFromHtml(question.getDescription());
		// for (String id : files) {
		// farmFileManagerImpl.cancelFile(id);
		// farmFileManagerImpl.delFile(id, currentUser);
		// }
		// 刪除關聯的專家提問信息
		questionDaoImpl.delExpertQuestion(questionid);
		// 记录删除问题的操作
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getDel(currentUser.getId()));
	}

	@Override
	@Transactional
	public void chooseBestAnswer(String qanswerid, LoginUser currentUser) {
		Qanswer qanswer = qanswerServiceImpl.getQanswerEntity(qanswerid);
		List<Qanswer> answers = qanswerServiceImpl.getQanswersAllByQuestion(qanswer.getQuestionid());
		// 将选中答案设置为未选
		for (Qanswer answer : answers) {
			if (answer.getPstate().equals("2")) {
				answer.setPstate("1");
				qanswerServiceImpl.editQanswerEntity(answer, currentUser);
			}
		}
		Question question = questionDaoImpl.getEntity(qanswer.getQuestionid());
		if (question.getQanswerid() != null && !question.getQanswerid().isEmpty()) {
			throw new RuntimeException("该问题已经选择答案！");
		}
		// 問題綁定id
		question.setQanswerid(qanswerid);
		question.setPstate("2");
		questionDaoImpl.editEntity(question);
		// 回答標記爲已綁定
		qanswer.setPstate("2");
		qanswerServiceImpl.editQanswerEntity(qanswer, currentUser);
		// Fqa fqa = initFqa(question);
		// FqaBrief fqabrief = fqa.getBrief();
		// farmDocIndexManagerImpl.delLuceneIndex(question.getId());
		// farmDocIndexManagerImpl.addLuceneIndex(fqabrief);

		// AppOpratorHandle.getInstance().recordOperate(OperateEvent.getChooseAnswerEvent(question.getPrice()),
		// currentUser != null ? currentUser.getId() : null, currentUser != null
		// ? currentUser.getIp() : null,
		// null, qanswer.getQuestionid(), null, qanswer.getId(),
		// qanswer.getCuser(), null);
	}

	@Override
	@Transactional
	public void move2Type(String questionids, String typeId, LoginUser currentUser) {
		if (questionids == null || questionids.isEmpty()) {
			throw new RuntimeException("无法获取参数：questionids");
		}
		if (typeId == null || typeId.isEmpty()) {
			throw new RuntimeException("无法获取参数：typeId");
		}
		String[] questionidArr = questionids.split(",");
		for (String questionid : questionidArr) {
			Question question = getQuestionEntity(questionid);
			question.setTypeid(typeId);
			editQuestionEntity(question, currentUser);
			// FqaBrief brief = initFqa(question).getBrief();
			// List<FqaBrief> briefs = new ArrayList<>();
			// briefs.add(brief);
			// 
			// farmDocIndexManagerImpl.delLuceneIndexsByFqa(briefs);
			// farmDocIndexManagerImpl.addLuceneIndexsByFqa(briefs);
			// 
			// FqaOpratorUtils.syncQuestion(question,
			// UpdateType.getUpdate(currentUser.getId()));
		}
	}

	@Override
	@Transactional
	public Question editQuestion(String id, String title, String typeid, String text, String fqatag, String fileId,
			boolean isanonymous, LoginUser currentUser) {
		Question question = getQuestionEntity(id);
		question.setTitle(title);
		question.setTypeid(typeid);
		question.setDescription(text);
		if (text != null) {
			String html = HtmlUtils.HtmlRemoveTag(text);
			question.setShortdesc(html.length() > 256 ? html.substring(0, 256) : html);
		}
		question.setTags(fqatag);
		question.setImgid(fileId);
		question.setAnonymous(isanonymous ? "1" : "0");
		question = editQuestionEntity(question, currentUser);
		// Fqa fqa = initFqa(question);
		// FqaBrief fqabrief = fqa.getBrief();
		// farmDocIndexManagerImpl.delLuceneIndex(question.getId());
		// farmDocIndexManagerImpl.addLuceneIndex(fqabrief);
		// 
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getUpdate(currentUser.getId()));
		return question;
	}

	@Override
	@Transactional
	public Questionplus getQuestionClosely(String closelyid) {
		return questionPlusServiceImpl.getQuestionplusEntity(closelyid);
	}

	@Override
	@Transactional
	public Questionplus editQuestionClosely(String closelyid, String text, LoginUser currentUser) {
		Questionplus closely = questionPlusServiceImpl.getQuestionplusEntity(closelyid);
		closely.setDescription(text);
		// List<String> files = FarmDocFiles.getFilesIdFromHtml(text);
		// for (String id : files) {
		// farmFileManagerImpl.submitFile(id,
		// FarmFileManagerInter.FILE_APPLICATION_TYPE.FQA_CLOSELY_FILE.getValue());
		// }
		questionPlusServiceImpl.editQuestionplusEntity(closely, currentUser);
		// 记录操作事件
		// Question question = getQuestionEntity(closely.getQuestionid());
		// FqaOpratorUtils.syncQuestion(question,
		// UpdateType.getUpdate(currentUser.getId()));
		return closely;
	}
}
