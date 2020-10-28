package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.learn.dao.ClasschapterDaoInter;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.utils.PdfToImgConvertor;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

 
@Service
public class ClasshourServiceImpl implements ClasshourServiceInter {
	@Resource
	private ClasshourDaoInter classhourDaoImpl;
	@Resource
	private ClasschapterDaoInter classchapterDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	private static final Logger log = Logger.getLogger(ClasshourServiceImpl.class);

	@Override
	@Transactional
	public ClassHour insertClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		ClassChapter chapter = classchapterDaoImpl.getEntity(entity.getChapterid());
		entity.setClassid(chapter.getClassid());
		if (entity.getFileid() != null) {
			wdapFileServiceImpl.submitFile(entity.getFileid());
			FileBase fbase = wdapFileServiceImpl.getFileBase(entity.getFileid());
			if (fbase.getExname().toUpperCase().equals("PDF")) {
				PdfToImgConvertor.waitingConvert(wdapFileServiceImpl.getPersistFile(entity.getFileid()).getFile());
			}
		}
		return classhourDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ClassHour editClasshourEntity(ClassHour entity, LoginUser user) throws FileNotFoundException {
		ClassHour entity2 = classhourDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setNote(entity.getNote());
		// entity2.setChapterid(entity.getChapterid());
		// entity2.setClassid(entity.getClassid());
		entity2.setTitle(entity.getTitle());
		entity2.setSort(entity.getSort());
		// entity2.setAuthorid(entity.getAuthorid());
		// entity2.setPcontent(entity.getPcontent());
		// entity2.setPstate(entity.getPstate());
		// entity2.setId(entity.getId());
		if (entity.getFileid() != null) {
			if (StringUtils.isNotBlank(entity2.getFileid())) {
				wdapFileServiceImpl.freeFile(entity2.getFileid());
			}
			entity2.setFileid(entity.getFileid());
			wdapFileServiceImpl.submitFile(entity.getFileid());
			FileBase fbase = wdapFileServiceImpl.getFileBase(entity.getFileid());
			if (fbase.getExname().toUpperCase().equals("PDF")) {
				PdfToImgConvertor.waitingConvert(wdapFileServiceImpl.getPersistFile(entity.getFileid()).getFile());
			}
		}
		classhourDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasshourEntity(String id, LoginUser user) {
		ClassHour hour = classhourDaoImpl.getEntity(id);
		wdapFileServiceImpl.freeFile(hour.getFileid());
		classhourDaoImpl.deleteEntity(hour);
	}

	@Override
	@Transactional
	public ClassHour getClasshourEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classhourDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasshourSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASSHOUR",
				"ID,NOTE,CHAPTERID,TITLE,SORT,FILEID,AUTHORID,PCONTENT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<ClassHour> getHoursByClass(String classid) {
		List<ClassHour> list = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", classid, "=")).toList());
		Collections.sort(list, new Comparator<ClassHour>() {
			@Override
			public int compare(ClassHour o1, ClassHour o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		return list;
	}

}
