package com.wcp.question.controller;

import com.wcp.question.domain.Qanswer;
import com.wcp.question.service.QanswerServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

 
@RequestMapping("/qanswer")
@Controller
public class QanswerController extends WebUtils {
	private final static Logger log = Logger.getLogger(QanswerController.class);
	@Resource
	QanswerServiceInter qanswerServiceImpl;

	public QanswerServiceInter getQanswerServiceImpl() {
		return qanswerServiceImpl;
	}

	public void setQanswerServiceImpl(QanswerServiceInter qanswerServiceImpl) {
		this.qanswerServiceImpl = qanswerServiceImpl;
	}

	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = qanswerServiceImpl.createQanswerSimpleQuery(query).search();
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		}
	}

	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Qanswer entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = qanswerServiceImpl.editQanswerEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(),e).returnObjMode();
		}
	}

	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Qanswer entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = qanswerServiceImpl.insertQanswerEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(),e).returnObjMode();
		}
	}

	 
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				qanswerServiceImpl.deleteQanswerEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("question/QanswerResult");
	}

	 
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", qanswerServiceImpl.getQanswerEntity(ids))
						.returnModelAndView("question/QanswerForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("question/QanswerForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", qanswerServiceImpl.getQanswerEntity(ids))
						.returnModelAndView("question/QanswerForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("question/QanswerForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e).returnModelAndView("question/QanswerForm");
		}
	}
}
