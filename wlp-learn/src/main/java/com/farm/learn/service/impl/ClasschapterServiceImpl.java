package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClasschapterDaoInter;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

 
@Service
public class ClasschapterServiceImpl implements ClasschapterServiceInter {
	@Resource
	private ClasschapterDaoInter classchapterDaoImpl;
	@Resource
	private ClasshourDaoInter classhourDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	private static final Logger log = Logger.getLogger(ClasschapterServiceImpl.class);

	@Override
	@Transactional
	public ClassChapter insertClasschapterEntity(ClassChapter entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		return classchapterDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ClassChapter editClasschapterEntity(ClassChapter entity, LoginUser user) {
		ClassChapter entity2 = classchapterDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNote(entity.getNote());
		entity2.setTitle(entity.getTitle());
		entity2.setSort(entity.getSort());
		// entity2.setClassid(entity.getClassid());
		// entity2.setPstate(entity.getPstate());
		classchapterDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasschapterEntity(String id, LoginUser user) {
		List<ClassHour> hours = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", id, "=")).toList());
		for (ClassHour hour : hours) {
			if (StringUtils.isNotBlank(hour.getFileid())) {
				wdapFileServiceImpl.freeFile(hour.getFileid());
			}
		}
		classhourDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", id, "=")).toList());
		classchapterDaoImpl.deleteEntity(classchapterDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public ClassChapter getClasschapterEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classchapterDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasschapterSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASSCHAPTER",
				"ID,CLASSID,NOTE,TITLE,SORT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<ClassChapter> getChapters(String classid) {
		List<ClassChapter> list = classchapterDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "=")).toList());
		Collections.sort(list, new Comparator<ClassChapter>() {
			@Override
			public int compare(ClassChapter o1, ClassChapter o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return list;
	}

}
