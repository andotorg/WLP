package com.farm.learn.controller;

import com.farm.learn.domain.Top;
import com.farm.learn.service.TopServiceInter;
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
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

 
@RequestMapping("/top")
@Controller
public class TopController extends WebUtils {
	private final static Logger log = Logger.getLogger(TopController.class);
	@Resource
	private TopServiceInter topServiceImpl;

	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addSort(new DBSort("SORT", "DESC"));
			DataResult result = topServiceImpl.createTopSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "PSTATE");
			result.runDictionary("1:课程,0:自定义", "MODEL");
			result.runDictionary("1:推荐阅读,2:置顶大图", "TYPE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Top entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = topServiceImpl.editTopEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/toDocTopChooseClass")
	public ModelAndView toDocTopChooseDoc(String typeid) {
		return ViewMode.getInstance().putAttr("typeid", typeid).returnModelAndView("learn/chooseClassWin");
	}
	 
	@RequestMapping("/formatSort")
	@ResponseBody
	public Map<String, Object> formatSort(String ids, HttpSession session) {
		try {
			int n=1;
			for (String id : parseIds(ids)) {
				topServiceImpl.updateSort(id,n++);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Top entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = topServiceImpl.insertTopEntity(entity, getCurrentUser(session));
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
				topServiceImpl.deleteTopEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("learn/TopResult");
	}

	 
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", topServiceImpl.getTopEntity(ids)).returnModelAndView("learn/TopForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("learn/TopForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", topServiceImpl.getTopEntity(ids)).returnModelAndView("learn/TopForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("learn/TopForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("learn/TopForm");
		}
	}
}
