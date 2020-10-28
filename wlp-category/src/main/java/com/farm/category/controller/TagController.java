package com.farm.category.controller;

import com.farm.category.domain.Tag;
import com.farm.category.domain.TagType;
import com.farm.category.service.TagServiceInter;
import com.farm.category.service.TagtypeServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：标签控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/tag")
@Controller
public class TagController extends WebUtils {
	private final static Logger log = Logger.getLogger(TagController.class);
	@Resource
	private TagServiceInter tagServiceImpl;
	@Resource
	private TagtypeServiceInter tagTypeServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String typeids, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (StringUtils.isNotBlank(typeids)) {
				query.addRule(new DBRule("TYPEID", typeids, "="));
			}
			DataResult result = tagServiceImpl.createTagSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Tag entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = tagServiceImpl.editTagEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Tag entity, HttpSession session) {
		try {
			int sort = entity.getSort();
			for (String name : parseIds(entity.getName())) {
				entity.setName(name);
				entity.setSort(sort++);
				entity = tagServiceImpl.insertTagEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				tagServiceImpl.deleteTagEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String ids, HttpSession session) {
		return ViewMode.getInstance().putAttr("TYPEIDS", ids).returnModelAndView("category/TagResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String typeids) {
		try {
			TagType tagtype = null;
			Tag tag = null;
			if (StringUtils.isNotBlank(ids)) {
				tag = tagServiceImpl.getTagEntity(ids);
			}
			if (StringUtils.isNotBlank(typeids)) {
				tagtype = tagTypeServiceImpl.getTagtypeEntity(typeids);
			}
			if (tagtype == null && tag != null && StringUtils.isNotBlank(tag.getTypeid())) {
				tagtype = tagTypeServiceImpl.getTagtypeEntity(tag.getTypeid());
			}
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("tagtype", tagtype).putAttr("pageset", pageset)
						.putAttr("entity", tag).returnModelAndView("category/TagForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("tagtype", tagtype).putAttr("pageset", pageset)
						.returnModelAndView("category/TagForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("tagtype", tagtype).putAttr("pageset", pageset)
						.putAttr("entity", tag).returnModelAndView("category/TagForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("category/TagForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("category/TagForm");
		}
	}
}
