package com.farm.learn.service.impl;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassClassType;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.domain.ex.HourView;
import com.farm.core.time.TimeTool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.learn.dao.ClasschapterDaoInter;
import com.farm.learn.dao.ClassclasstypeDaoInter;
import com.farm.learn.dao.ClasshourDaoInter;
import com.farm.learn.dao.ClasstDaoInter;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.utils.FarmDocFiles;
import com.farm.sfile.utils.PdfToImgConvertor;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.farm.category.domain.ClassType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：课程服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ClasstServiceImpl implements ClasstServiceInter {
	@Resource
	private ClasstDaoInter classtDaoImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private ClasschapterDaoInter classchapterDaoImpl;
	@Resource
	private ClasshourDaoInter classhourDaoImpl;
	@Resource
	private ClasschapterServiceInter classChapterServiceImpl;
	@Resource
	private ClasshourServiceInter classHourServiceImpl;
	@Resource
	private ClassclasstypeDaoInter classclasstypeDaoImpl;
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	private static final Logger log = Logger.getLogger(ClasstServiceImpl.class);

	@Override
	@Transactional
	public Classt insertClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException {
		if (StringUtils.isBlank(classtypeid)) {
			throw new RuntimeException("the classTypeid is not exist!");
		}
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("0");
		entity.setBookednum(0);
		entity.setLearnednum(0);
		entity.setEvaluation(0);
		entity.setHotscore(0);
		wdapFileServiceImpl.submitFile(entity.getImgid());
		entity = classtDaoImpl.insertEntity(entity);

		ClassClassType ctype = new ClassClassType();
		ctype.setClassid(entity.getId());
		ctype.setClasstypeid(classtypeid);
		classclasstypeDaoImpl.insertEntity(ctype);

		return entity;
	}

	@Override
	@Transactional
	public Classt editClasstEntity(Classt entity, String classtypeid, LoginUser user) throws FileNotFoundException {
		Classt entity2 = classtDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		if (entity.getIntroductionid() != null) {
			entity2.setIntroductionid(entity.getIntroductionid());
		}
		if (entity.getName() != null) {
			entity2.setName(entity.getName());
		}

		if (entity.getDifficulty() != null) {
			entity2.setDifficulty(entity.getDifficulty());
		}
		if (entity.getShortintro() != null) {
			entity2.setShortintro(entity.getShortintro());
		}
		if (entity.getOutauthor() != null) {
			entity2.setOutauthor(entity.getOutauthor());
		}
		if (entity.getAltime() != null) {
			entity2.setAltime(entity.getAltime());
		}
		if (entity.getEvaluation() != null) {
			entity2.setEvaluation(entity.getEvaluation());
		}
		if (entity.getLearnednum() != null) {
			entity2.setLearnednum(entity.getLearnednum());
		}
		if (entity.getImgid() != null) {
			wdapFileServiceImpl.freeFile(entity2.getImgid());
			entity2.setImgid(entity.getImgid());
			wdapFileServiceImpl.submitFile(entity.getImgid());
		}
		if (entity.getMinimgid() != null) {
			entity2.setMinimgid(entity.getMinimgid());
		}
		if (entity.getBookednum() != null) {
			entity2.setBookednum(entity.getBookednum());
		}
		if (entity.getHotscore() != null) {
			entity2.setHotscore(entity.getHotscore());
		}
		// entity2.setPcontent(entity.getPcontent());
		classtDaoImpl.editEntity(entity2);
		{
			classclasstypeDaoImpl
					.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", entity.getId(), "=")).toList());
			ClassClassType ctype = new ClassClassType();
			ctype.setClassid(entity.getId());
			ctype.setClasstypeid(classtypeid);
			classclasstypeDaoImpl.insertEntity(ctype);
		}
		return entity2;
	}

	@Override
	@Transactional
	public void deleteClasstEntity(String id, LoginUser user) {
		List<ClassHour> hours = classhourDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		for (ClassHour hour : hours) {
			if (StringUtils.isNotBlank(hour.getFileid())) {
				wdapFileServiceImpl.freeFile(hour.getFileid());
			}
		}
		classhourDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		classchapterDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		Classt classt = classtDaoImpl.getEntity(id);
		if (StringUtils.isNotBlank(classt.getImgid())) {
			wdapFileServiceImpl.freeFile(classt.getImgid());
		}
		if (StringUtils.isNotBlank(classt.getMinimgid())) {
			wdapFileServiceImpl.freeFile(classt.getMinimgid());
		}
		classclasstypeDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CLASSID", id, "=")).toList());
		classtDaoImpl.deleteEntity(classt);
	}

	@Override
	@Transactional
	public Classt getClasstEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return classtDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createClasstSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS",
				"ID,INTRODUCTIONID,NAME,DIFFICULTY,SHORTINTRO,OUTAUTHOR,ALTIME,EVALUATION,LEARNEDNUM,IMGID,MINIMGID,BOOKEDNUM,HOTSCORE,PSTATE,PCONTENT,CUSERNAME,CUSER,CTIME,EUSERNAME,EUSER,ETIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public DataResult getTempClases(DataQuery query, LoginUser currentUser) throws SQLException {
		// query.addRule(new DBRule("CUSER", currentUser.getId(), "="));
		query.addRule(new DBRule("PSTATE", "0", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS",
				"ID,INTRODUCTIONID,NAME,DIFFICULTY,SHORTINTRO,OUTAUTHOR,ALTIME,EVALUATION,LEARNEDNUM,IMGID,MINIMGID,BOOKEDNUM,HOTSCORE,PSTATE,PCONTENT,CUSERNAME,CUSER,CTIME,EUSERNAME,EUSER,ETIME");
		DataResult result = dbQuery.search();
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
				List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId((String) row.get("ID"));
				if (classtypes.size() > 0) {
					ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
					List<ClassType> types = classTypeServiceImpl.getAllPathType(type);
					row.put("TYPES", types);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public DataResult getPublicClases(DataQuery query, LoginUser currentUser) throws SQLException {
		// query.addRule(new DBRule("CUSER", currentUser.getId(), "="));
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on a.id=b.CLASSID",
				"a.ID AS ID, INTRODUCTIONID, NAME, DIFFICULTY, SHORTINTRO, OUTAUTHOR, ALTIME, EVALUATION, LEARNEDNUM, IMGID, MINIMGID, BOOKEDNUM, HOTSCORE, b.CLASSTYPEID as CLASSTYPEID");
		DataResult result = dbQuery.search();
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
				List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId((String) row.get("ID"));
				if (classtypes.size() > 0) {
					ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
					List<ClassType> types = classTypeServiceImpl.getAllPathType(type);
					row.put("TYPES", types);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public ClassView getClassView(String classid, LoginUser currentUser) {
		ClassView classv = new ClassView();
		classv.setClasst(classtDaoImpl.getEntity(classid));
		String url = wdapFileServiceImpl.getDownloadUrl(classv.getClasst().getImgid(), FileModel.IMG);
		classv.setImgurl(url);
		if (StringUtils.isNotBlank(classv.getClasst().getIntroductionid())) {
			classv.setIntroText(wdapFileServiceImpl.readFileToText(classv.getClasst().getIntroductionid()));
		}
		List<ClassChapter> chapters = classChapterServiceImpl.getChapters(classid);
		List<ClassHour> hours = classHourServiceImpl.getHoursByClass(classid);
		classv.setChapters(chapters);
		classv.setHours(hours);
		List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId(classid);
		if (classtypes.size() > 0) {
			ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
			classv.setType(type);
			classv.setTypes(classTypeServiceImpl.getAllPathType(type));
		}
		return classv;
	}

	@Override
	@Transactional
	public Classt editIntroduction(String classid, String text, LoginUser currentUser) throws FileNotFoundException {
		Classt classt = classtDaoImpl.getEntity(classid);
		// 判斷是已經創建簡介
		FileBase filebase = null;
		if (StringUtils.isBlank(classt.getIntroductionid())
				|| wdapFileServiceImpl.getFileBase(classt.getIntroductionid()) == null) {
			// false：創建簡介
			filebase = wdapFileServiceImpl.initFile(currentUser, classt.getName() + "简介.html", text.length(), classt.getId(),
					"课程简介");
			classt.setIntroductionid(filebase.getId());
			classtDaoImpl.editEntity(classt);
		} else {
			filebase = wdapFileServiceImpl.getFileBase(classt.getIntroductionid());
		}
		{// 提交或取消附件
			String oldText = wdapFileServiceImpl.readFileToText(filebase.getId());
			List<String> oldFiles = FarmDocFiles.getFilesIdFromHtml(oldText);
			for (String fileid : oldFiles) {
				wdapFileServiceImpl.freeFile(fileid);
			}
			List<String> newFiles = FarmDocFiles.getFilesIdFromHtml(text);
			for (String fileid : newFiles) {
				wdapFileServiceImpl.submitFile(fileid);
			}
		}
		// 保存簡介
		wdapFileServiceImpl.writeFileByText(filebase.getId(), text);
		wdapFileServiceImpl.submitFile(filebase.getId());
		return classt;
	}

	@Override
	@Transactional
	public void publicClass(String classid, LoginUser currentUser) {
		Classt Classt = classtDaoImpl.getEntity(classid);
		Classt.setPstate("1");
		Classt.setEtime(TimeTool.getTimeDate14());
		classtDaoImpl.editEntity(Classt);
	}

	@Override
	@Transactional
	public void tempClass(String classid, LoginUser currentUser) {
		Classt Classt = classtDaoImpl.getEntity(classid);
		Classt.setPstate("0");
		classtDaoImpl.editEntity(Classt);
	}

	@Override
	@Transactional
	public DataResult getNewClases(LoginUser currentUser) throws SQLException {
		DataQuery query = DataQuery.getInstance();
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		query.setPagesize(12);
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on a.id=b.CLASSID",
				"a.ID AS ID, INTRODUCTIONID,a.ETIME as ETIME, NAME, DIFFICULTY, SHORTINTRO, OUTAUTHOR, ALTIME, EVALUATION, LEARNEDNUM, IMGID, MINIMGID, BOOKEDNUM, HOTSCORE, b.CLASSTYPEID as CLASSTYPEID");
		DataResult result = dbQuery.search();
		result.runformatTime("ETIME", "yyyy年MM月dd日");
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
				List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId((String) row.get("ID"));
				if (classtypes.size() > 0) {
					ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
					List<ClassType> types = classTypeServiceImpl.getAllPathType(type);
					row.put("TYPES", types);
					row.put("TYPE", type);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public DataResult getClases(DataQuery query, LoginUser currentUser) throws SQLException {
		query.addRule(new DBRule("PSTATE", "1", "="));
		query.addSort(new DBSort("ETIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query, "WLP_L_CLASS a left join WLP_L_CLASSCLASSTYPE b on a.id=b.CLASSID",
				"a.ID AS ID, INTRODUCTIONID, NAME, DIFFICULTY, SHORTINTRO, OUTAUTHOR, ALTIME, EVALUATION, LEARNEDNUM, IMGID, MINIMGID, BOOKEDNUM, HOTSCORE, b.CLASSTYPEID as CLASSTYPEID");
		DataResult result = dbQuery.search();
		result.runDictionary("1:入门,2:初级,3:中级,4:高级", "DIFFICULTY");
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (row.get("IMGID") != null) {
					String url = wdapFileServiceImpl.getDownloadUrl((String) row.get("IMGID"), FileModel.IMG);
					row.put("IMGURL", url);
				}
				List<ClassClassType> classtypes = classclasstypeDaoImpl.getEntityByClassId((String) row.get("ID"));
				if (classtypes.size() > 0) {
					ClassType type = classTypeServiceImpl.getClasstypeEntity(classtypes.get(0).getClasstypeid());
					List<ClassType> types = classTypeServiceImpl.getAllPathType(type);
					row.put("TYPES", types);
				}
			}
		});
		return result;
	}

	@Override
	@Transactional
	public HourView getUserCurrentHour(String classid, LoginUser currentUser) throws FileExNameException {
		return loadPdfImgsInfo(getHours(classid, null, currentUser).get(0));
	}

	@Override
	@Transactional
	public HourView getUserHour(String hourid, LoginUser currentUser) throws FileExNameException {
		ClassHour hour = classhourDaoImpl.getEntity(hourid);
		List<HourView> hourviews = getHours(hour.getClassid(), hour.getId(), currentUser);
		for (HourView view : hourviews) {
			if (view.getHour().getId().equals(hourid)) {
				return loadPdfImgsInfo(view);
			}
		}
		return loadPdfImgsInfo(getHours(hour.getClassid(), null, currentUser).get(0));
	}

	private HourView loadPdfImgsInfo(HourView hourView) {
		FileBase filebase = wdapFileServiceImpl.getFileBase(hourView.getHour().getFileid());
		if (filebase.getExname().toUpperCase().equals("PDF")) {
			File imgsDir = PdfToImgConvertor
					.getImgDir(wdapFileServiceImpl.getPersistFile(hourView.getHour().getFileid()).getFile());
			if (imgsDir.exists()) {
				List<File> files = Arrays.asList(imgsDir.listFiles());
				hourView.setPinum(files.size());
			}
		}
		return hourView;
	}

	private List<HourView> getHours(String classid, String hourid, LoginUser currentUser) throws FileExNameException {
		ClassView classview = getClassView(classid, currentUser);
		List<ClassHour> hours = classview.getHours();
		if (hours.size() <= 0) {
			throw new RuntimeException(" hour is not exist by the class!");
		}
		List<HourView> hourviews = new ArrayList<>();
		Map<String, ClassChapter> chapterMap = new HashMap<String, ClassChapter>();
		for (ClassChapter chapter : classview.getChapters()) {
			chapterMap.put(chapter.getId(), chapter);
		}
		for (ClassHour hour : hours) {
			HourView view = new HourView();
			view.setHour(hour);
			view.setClassview(classview);
			view.setViewurl(wdapFileServiceImpl.getDownloadUrl(hour.getFileid(),
					wdapFileServiceImpl.getFileModel(hour.getFileid())));
			view.setFilebase(wdapFileServiceImpl.getFileBase(hour.getFileid()));
			ClassChapter cchapter = chapterMap.get(hour.getChapterid());
			if (cchapter != null) {
				// 有章節的課時數據完整才添加到結果中
				view.setChapter(cchapter);
				view.setHours(hourviews);
				hourviews.add(view);
			}
		}
		Collections.sort(hourviews, new Comparator<HourView>() {
			@Override
			public int compare(HourView o1, HourView o2) {
				if (o1.getChapter().getSort() == o2.getChapter().getSort()) {
					return o1.getHour().getSort() - o2.getHour().getSort();
				} else {
					return o1.getChapter().getSort() - o2.getChapter().getSort();
				}
			}
		});
		return hourviews;
	}

}
