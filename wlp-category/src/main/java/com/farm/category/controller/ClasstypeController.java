package com.farm.category.controller;

import com.farm.category.domain.ClassType;
import com.farm.category.service.ClasstypeServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

import java.util.ArrayList;
import java.util.List;
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
 *功能：课程分类控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/classtype")
@Controller
public class ClasstypeController extends WebUtils {
	private final static Logger log = Logger.getLogger(ClasstypeController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			if (query.getQueryRule().size() == 0) {
				query.addRule(new DBRule("PARENTID", "NONE", "="));
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = classTypeServiceImpl.createClasstypeSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 组织机构节点
	 */
	@RequestMapping("/classtypeTree")
	@ResponseBody
	public Object examtypeTree(String id) {
		try {
			if (id == null) {
				// 如果是未传入id，就是根节点，就构造一个虚拟的上级节点
				id = "NONE";
				List<EasyUiTreeNode> list = new ArrayList<>();
				EasyUiTreeNode nodes = new EasyUiTreeNode("NONE", "考试分类", "open", "icon-customers");
				nodes.setChildren(
						EasyUiTreeNode.formatAsyncAjaxTree(
								EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "WLP_C_CLASSTYPE", "ID", "PARENTID", "NAME",
										"CTIME").getResultList(),
						EasyUiTreeNode
								.queryTreeNodeTow(id, "SORT", "WLP_C_CLASSTYPE", "ID", "PARENTID", "NAME", "CTIME")
								.getResultList(), "PARENTID", "ID", "NAME", "CTIME"));
				list.add(nodes);
				return list;
			}
			return EasyUiTreeNode.formatAsyncAjaxTree(
					EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "WLP_C_CLASSTYPE", "ID", "PARENTID", "NAME", "CTIME")
							.getResultList(),
					EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "WLP_C_CLASSTYPE", "ID", "PARENTID", "NAME", "CTIME")
							.getResultList(),
					"PARENTID", "ID", "NAME", "CTIME");
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
	public Map<String, Object> editSubmit(ClassType entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = classTypeServiceImpl.editClasstypeEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(ClassType entity, HttpSession session) {
		try {
			List<String> names = parseIds(entity.getName());
			int sort = entity.getSort();
			for (String name : names) {
				if (name.trim().length() > 32) {
					throw new RuntimeException("分类名称不能超过32个字符!");
				}
			}
			for (String name : names) {
				entity.setName(name.trim());
				entity.setSort(sort++);
				entity = classTypeServiceImpl.insertClasstypeEntity(entity, getCurrentUser(session));
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
				classTypeServiceImpl.deleteClasstypeEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("category/ClasstypeResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String parentId) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", classTypeServiceImpl.getClasstypeEntity(ids))
						.returnModelAndView("category/ClasstypeForm");
			}
			case (1): {// 新增
				ClassType parent = null;
				if (StringUtils.isNotBlank(parentId)) {
					parent = classTypeServiceImpl.getClasstypeEntity(parentId);
				}
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parent", parent)
						.returnModelAndView("category/ClasstypeForm");
			}
			case (2): {// 修改
				ClassType type = classTypeServiceImpl.getClasstypeEntity(ids);
				ClassType parent = null;
				if (StringUtils.isNotBlank(type.getParentid())) {
					parent = classTypeServiceImpl.getClasstypeEntity(type.getParentid());
				}
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parent", parent)
						.putAttr("entity", classTypeServiceImpl.getClasstypeEntity(ids))
						.returnModelAndView("category/ClasstypeForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("category/ClasstypeForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("category/ClasstypeForm");
		}
	}

	/**
	 * 跳转树选择页面
	 *
	 * @return
	 */
	@RequestMapping("/treeNodeTreeView")
	public ModelAndView forSend() {
		return ViewMode.getInstance().returnModelAndView("category/ClassTypeChooseTree");
	}

	/**
	 * 移动节点
	 *
	 * @return
	 */
	@RequestMapping("/moveTreeNodeSubmit")
	@ResponseBody
	public Map<String, Object> moveTreeNodeSubmit(String ids, String id, HttpSession session) {
		try {
			classTypeServiceImpl.moveTreeNode(ids, id, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
