package com.farm.category.service.impl;

import com.farm.category.domain.TagType;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.category.dao.TagtypeDaoInter;
import com.farm.category.service.TagtypeServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：标签类别服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class TagtypeServiceImpl implements TagtypeServiceInter {
	@Resource
	private TagtypeDaoInter tagtypeDaoImpl;

	private static final Logger log = Logger.getLogger(TagtypeServiceImpl.class);

	@Override
	@Transactional
	public TagType insertTagtypeEntity(TagType entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		return tagtypeDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public TagType editTagtypeEntity(TagType entity, LoginUser user) {
		TagType entity2 = tagtypeDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setName(entity.getName());
		entity2.setSort(entity.getSort());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		tagtypeDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteTagtypeEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		tagtypeDaoImpl.deleteEntity(tagtypeDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public TagType getTagtypeEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return tagtypeDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createTagtypeSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_C_TAGTYPE",
				"ID,NAME,SORT,PCONTENT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

}
