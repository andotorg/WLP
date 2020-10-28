package com.wcp.question.controller;

import com.wcp.question.domain.Question;
import com.wcp.question.service.QanswerServiceInter;
import com.wcp.question.service.QanswercommentServiceInter;
import com.wcp.question.service.QuestionServiceInter;
import com.wcp.question.service.QuestionplusServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.report.FarmReport;
import com.farm.util.web.FarmFormatUnits;
import com.farm.util.web.HtmlUtils;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

 
@RequestMapping("/question")
@Controller
public class QuestionController extends WebUtils {
	private final static Logger log = Logger.getLogger(QuestionController.class);
	@Resource
	private QuestionServiceInter questionServiceImpl;
	@Resource
	private QuestionplusServiceInter questionPlusServiceImpl;
	@Resource
	private QanswerServiceInter qanswerServiceImpl;
	@Resource
	private QanswercommentServiceInter qanswerCommentServiceImpl;
	public QuestionServiceInter getQuestionServiceImpl() {
		return questionServiceImpl;
	}

	public void setQuestionServiceImpl(QuestionServiceInter questionServiceImpl) {
		this.questionServiceImpl = questionServiceImpl;
	}
	 
	@RequestMapping("/exportExcel")
	public void download(String ruleText, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			DataQuery query = new DataQuery();
			query.setPagesize(5000);
			query.setRuleText(ruleText);
			Map<String, Object> result = queryall(query, request, session);
			FarmReport.newInstance("fqaList.xls").addParameter("result", result.get("rows")).generateForHttp(response,
					"fqaList");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request, HttpSession session) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (!getCurrentUserOrThrowException(session).getType().equals("3")) {
				// 过滤分类管理权限
				// query.addSqlRule(
				// " and A.TYPEID in ("
				// + FarmFormatUnits.getSqlInStrs(
				// farmDocTypeManagerImpl.getPopTypeIds(getCurrentUser(session),
				// FuncPOP.manage))
				// + ")");
			}
			{
				// 处理时间查询
				DBRule statTime = query.getAndRemoveRule("STARTTIME");
				DBRule endTime = query.getAndRemoveRule("ENDTIME");
				String startT = null;
				String endT = null;
				if (statTime != null) {
					startT = StringUtils.isNotBlank(statTime.getValue()) ? statTime.getValue() : null;
				}
				if (endTime != null) {
					endT = StringUtils.isNotBlank(endTime.getValue()) ? endTime.getValue() : null;
				}
				if (startT != null) {
					startT = startT.replaceAll("-", "") + "000000";
					query.addRule(new DBRule("A.PUBTIME", startT, ">="));
				}
				if (endT != null) {
					endT = endT.replaceAll("-", "") + "999999";
					query.addRule(new DBRule("A.PUBTIME", endT, "<="));
				}
			}
			query.setDefaultSort(new DBSort("a.CTIME", "desc"));
			DataResult result = questionServiceImpl.createQuestionSimpleQuery(query).search();
			result.runDictionary("1:提问中,0:删除,2:完成", "PSTATE");
			result.runDictionary("1:匿名,0:否", "ANONYMOUS");
			result.runformatTime("PUBTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/zwquery")
	@ResponseBody
	public Map<String, Object> zwqueryall(DataQuery query, String ids, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = DataQuery.init(query, "WCP_QUESTION_PLUS", "ID,QUESTIONID,DESCRIPTION,CTIME")
					.addRule(new DBRule("QUESTIONID", ids, "=")).search();
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					row.put("DESCRIPTION", HtmlUtils.HtmlRemoveTag(row.get("DESCRIPTION").toString()));
				}
			});
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/hdquery")
	@ResponseBody
	public Map<String, Object> hdqueryall(DataQuery query, String ids, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = DataQuery
					.init(query, "WCP_QANSWER", "ID,QUESTIONID,ANONYMOUS,DESCRIPTION,CTIME,PSTATE,CUSERNAME")
					.addRule(new DBRule("QUESTIONID", ids, "=")).addRule(new DBRule("PSTATE", "0", "!=")).search();
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			result.runDictionary("1:备选,2:采纳", "PSTATE");
			result.runDictionary("1:匿名,0:否", "ANONYMOUS");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					row.put("DESCRIPTION", HtmlUtils.HtmlRemoveTag(row.get("DESCRIPTION").toString()));
				}
			});
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/plquery")
	@ResponseBody
	public Map<String, Object> plqueryall(DataQuery query, String ids, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = DataQuery
					.init(query, "WCP_QANSWER a left join WCP_QANSWER_COMMENT b on a.ID=b.ANSWERID",
							"b.ID as ID,b.CUSERNAME as CUSERNAME,b.CTIME as CTIME,b.DESCRIPTION as DESCRIPTION ")
					.addRule(new DBRule("a.QUESTIONID", ids, "=")).addRule(new DBRule("a.PSTATE", "0", "!="))
					.addSqlRule(" and b.ID is not null").search();
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					if (row.get("DESCRIPTION") != null) {
						row.put("DESCRIPTION", HtmlUtils.HtmlRemoveTag(row.get("DESCRIPTION").toString()));
					}
				}
			});
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Question entity, HttpSession session) {
		try {
			Question newQuestion = questionServiceImpl.getQuestionEntity(entity.getId());
			newQuestion.setImgid(entity.getImgid());
			newQuestion.setTitle(entity.getTitle());
			newQuestion.setPstate(entity.getPstate());
			if (!entity.getPstate().equals("2")) {
				newQuestion.setQanswerid(null);
			}
			newQuestion.setTags(entity.getTags());
			entity = questionServiceImpl.editQuestion(newQuestion, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Question entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			// entity = questionServiceImpl.insertQuestionEntity(entity,
			// getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				questionServiceImpl.deleteQuestionEntityByLogic(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("question/QuestionResult");
	}


	 
	@RequestMapping("/zwform")
	public ModelAndView zwview(RequestMode pageset, String ids) {
		try {
			return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("question/QuestionFormZwManage");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("question/QuestionFormZwManage");
		}
	}

	 
	@RequestMapping("/hdform")
	public ModelAndView hdview(RequestMode pageset, String ids) {
		try {
			return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("question/QuestionFormHdManage");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("question/QuestionFormHdManage");
		}
	}

	 
	@RequestMapping("/plform")
	public ModelAndView plview(RequestMode pageset, String ids) {
		try {
			return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("question/QuestionFormPlManage");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("question/QuestionFormPlManage");
		}
	}

	 
	@RequestMapping("/delzw")
	@ResponseBody
	public Map<String, Object> delzwSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				questionPlusServiceImpl.deleteQuestionplusEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/delhd")
	@ResponseBody
	public Map<String, Object> delhdSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				qanswerServiceImpl.delAnswerSuper(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/delpl")
	@ResponseBody
	public Map<String, Object> delplSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				qanswerCommentServiceImpl.deleteQanswercommentEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/chooseAnswer")
	@ResponseBody
	public Map<String, Object> chooseAnswer(String id, HttpSession session) {
		try {
			qanswerServiceImpl.chooseBestAnswerSuper(id, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/chooseTree")
	public ModelAndView chooseTypeTree(RequestMode pageset, String ids) {
		return ViewMode.getInstance().returnModelAndView("question/FqatypeChooseTreeWin");
	}

	 
	@RequestMapping("/move2Type")
	@ResponseBody
	public Map<String, Object> move2Type(String questionids, String typeId, HttpSession session) {
		try {
			questionServiceImpl.move2Type(questionids, typeId, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.toString());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/questionChooseQuery")
	@ResponseBody
	public Map<String, Object> QuestionChooseQuery(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = questionServiceImpl.createQuestionSimpleQuery(query).search();
			result.runDictionary("1:提问中,0:删除,2:完成", "PSTATE");
			result.runDictionary("1:匿名,0:否", "ANONYMOUS");
			result.runformatTime("PUBTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/questionChooseGridPage")
	public ModelAndView QuestionChooseGridPage(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("question/FqaChooseGridWin");
	}
}
