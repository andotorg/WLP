package com.farm.wcp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.domain.ClassType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.util.web.FarmFormatUnits;
import com.farm.wcp.util.BootstrapTreeViews;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 分类服务
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/category")
@Controller
public class CategoryWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(CategoryWebController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;

	/**
	 * 抓走用户session中的一条数据(抓取并从session中删除) home/carrySession
	 * 
	 * @return
	 */
	@RequestMapping("/Publoadclasstype")
	@ResponseBody
	public Map<String, Object> loadClassTypeDate(String rootTypeid, HttpSession session) {
		ViewMode page = ViewMode.getInstance();
		List<ClassType> types = null;
		if (StringUtils.isNotBlank(rootTypeid)) {
			types = classTypeServiceImpl.getSubTypes(rootTypeid);
		} else {
			types = classTypeServiceImpl.getAllTypes();
		}
		page.putAttr("data", BootstrapTreeViews.initData(types));
		return page.returnObjMode();
	}

	/***
	 * 课程分类
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/Pubview")
	public ModelAndView Pubview(String typeid, Integer page, String difficulty, HttpServletRequest request,
			HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			DataQuery query = DataQuery.getInstance();
			List<ClassType> alltypes = classTypeServiceImpl.getAllTypes();
			if (page == null) {
				page = 1;
			}
			if (StringUtils.isNotBlank(difficulty)) {
				query.addRule(new DBRule("difficulty", difficulty, "="));
				view.putAttr("difficulty", difficulty);
			}
			if (StringUtils.isNotBlank(typeid)) {
				// 按照分類查詢
				ClassType ctype = classTypeServiceImpl.getClasstypeEntity(typeid);
				List<String> subtypeids = classTypeServiceImpl.getSubTypeids(typeid);
				String subWere = DataQuerys.getWhereInSubVals(subtypeids);
				query.addSqlRule(" and b.CLASSTYPEID in (" + subWere + ")");
				{
					// 处理参数回显
					view.putAttr("ctype", ctype);
					view.putAttr("typeid", typeid);
					if (subtypeids.size() > 0) {
						view.putAttr("roottypeid", FarmFormatUnits.SplitStringByLen(ctype.getTreecode(), 32).get(0));
					}
					
				}
			}
			query.setPagesize(9);
			query.setCurrentPage(page);
			DataResult newClasses = classTServiceImpl.getClases(query, getCurrentUser(session));
			return view.putAttr("newClasses", newClasses).putAttr("alltypes", alltypes)
					.returnModelAndView("web-simple/category/category-view");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

}
