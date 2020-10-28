package com.farm.category.service.impl;

import com.farm.category.domain.ClassType;
import com.farm.core.time.TimeTool;
import com.farm.util.web.FarmFormatUnits;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.category.dao.ClasstypeDaoInter;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程分类服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasstypeServiceImpl implements ClasstypeServiceInter {
	@Resource
	private ClasstypeDaoInter classtypeDaoImpl;

	private static final Logger log = Logger.getLogger(ClasstypeServiceImpl.class);

	@Override
	@Transactional
	public void moveTreeNode(String ids, String targetId, LoginUser currentUser) {
		String[] idArray = ids.split(",");
		ClassType target = getClasstypeEntity(targetId);
		for (int i = 0; i < idArray.length; i++) {
			// 移动节点
			ClassType node = getClasstypeEntity(idArray[i]);
			if (target != null && target.getTreecode().indexOf(node.getTreecode()) >= 0) {
				throw new RuntimeException("不能够移动到其子节点下!");
			}
			if (target == null) {
				node.setParentid("NONE");
			} else {
				node.setParentid(targetId);
			}
			classtypeDaoImpl.editEntity(node);
			// 构造所有树TREECODE
			List<ClassType> list = classtypeDaoImpl.getAllSubNodes(idArray[i]);
			for (ClassType org : list) {
				initTreeCode(org.getId());
			}
		}
	}

	@Override
	@Transactional
	public ClassType insertClasstypeEntity(ClassType entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		if (StringUtils.isBlank(entity.getParentid())) {
			entity.setParentid("NONE");
		}
		entity.setTreecode("NONE");
		entity = classtypeDaoImpl.insertEntity(entity);
		initTreeCode(entity.getId());
		return entity;
	}

	private void initTreeCode(String treeNodeId) {
		ClassType node = getClasstypeEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			node.setTreecode(node.getId());
		} else {
			node.setTreecode(classtypeDaoImpl.getEntity(node.getParentid()).getTreecode() + node.getId());
		}
		classtypeDaoImpl.editEntity(node);
	}

	@Override
	@Transactional
	public ClassType editClasstypeEntity(ClassType entity, LoginUser user) {
		ClassType entity2 = classtypeDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setName(entity.getName());
		// entity2.setTreecode(entity.getTreecode());
		// entity2.setParentid(entity.getParentid());
		entity2.setSort(entity.getSort());
		entity2.setPcontent(entity.getPcontent());
		// entity2.setPstate(entity.getPstate());
		classtypeDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasstypeEntity(String id, LoginUser user) {
		if (classtypeDaoImpl.selectEntitys(DBRule.addRule(new ArrayList<DBRule>(), "parentid", id, "=")).size() > 0) {
			throw new RuntimeException("不能删除该节点，请先删除其子节点");
		}
		classtypeDaoImpl.deleteEntity(classtypeDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public ClassType getClasstypeEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classtypeDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasstypeSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_C_CLASSTYPE",
				"ID,NAME,TREECODE,PARENTID,SORT,PCONTENT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<ClassType> getAllTypes() {
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
		Collections.sort(types, new Comparator<ClassType>() {
			@Override
			public int compare(ClassType o1, ClassType o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return types;
	}

	@Override
	@Transactional
	public List<ClassType> getAllPathType(ClassType type) {
		List<String> ids = FarmFormatUnits.SplitStringByLen(type.getTreecode(), 32);
		List<ClassType> list = new ArrayList<>();
		for (String typeid : ids) {
			list.add(getClasstypeEntity(typeid));
		}
		return list;
	}

	@Override
	@Transactional
	public List<String> getSubTypeids(String classtypeId) {
		ClassType ctype = classtypeDaoImpl.getEntity(classtypeId);
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "="))
						.add(new DBRule("TREECODE", ctype.getTreecode(), "like-")).toList());
		List<String> ids = new ArrayList<>();
		for (ClassType type : types) {
			ids.add(type.getId());
		}
		return ids;
	}

	@Override
	@Transactional
	public List<ClassType> getSubTypes(String rootTypeid) {
		ClassType ctype = classtypeDaoImpl.getEntity(rootTypeid);
		List<ClassType> types = classtypeDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "="))
						.add(new DBRule("TREECODE", ctype.getTreecode(), "like-")).toList());
		return types;
	}

}
