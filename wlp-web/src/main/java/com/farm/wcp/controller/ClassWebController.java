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

import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.ViewMode;
import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.learn.domain.Classt;
import com.farm.learn.domain.ex.ClassView;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.learn.service.UserPopServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 课程创建
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/classweb")
@Controller
public class ClassWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(ClassWebController.class);
	@Resource
	private ClasstypeServiceInter classTypeServiceImpl;
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private ClasschapterServiceInter classChapterServiceImpl;
	@Resource
	private ClasshourServiceInter classHourServiceImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;
	@Resource
	private TopServiceInter topServiceImpl;
	@Resource
	private UserPopServiceInter userPopServiceImpl;

	/**
	 * 课程查看
	 * 
	 * @return
	 */
	@RequestMapping("/Pubview")
	public ModelAndView view(String classid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			ClassView classview = classTServiceImpl.getClassView(classid, getCurrentUser(session));
			view.putAttr("classview", classview);
			if (StringUtils.isBlank(classview.getIntroText())
					|| StringUtils.isNotBlank(type) && type.equals("chapter")) {
				return view.returnModelAndView("web-simple/classform/class-view-chapter");
			} else {
				return view.returnModelAndView("web-simple/classform/class-view-home");
			}
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 推荐阅读（置顶）查看
	 * 
	 * @return
	 */
	@RequestMapping("/PubTopview")
	public ModelAndView PubTopview(String topid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			String url = topServiceImpl.getTopEntity(topid).getUrl();
			if (url.indexOf("://") > 0) {
				return view.returnRedirectUrl(url);
			}
			if (url.startsWith("/")) {
				return view.returnRedirectUrl(url);
			}
			return view.returnRedirectUrl("/" + url);
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/***
	 * 创建课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView create(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		return view.returnModelAndView("web-simple/classform/class-form-base");
	}

	/**
	 * 提交课程base数据
	 * 
	 * @return
	 */
	@RequestMapping("/savebase")
	public ModelAndView addSubmit(Classt entity, String classtypeid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {

			if (StringUtils.isNotBlank(entity.getId())) {
				if (!userPopServiceImpl.isEditClassByTypeid(classtypeid, getCurrentUser(session))) {
					throw new RuntimeException("当前用户无此权限!");
				}
				entity = classTServiceImpl.editClasstEntity(entity, classtypeid, getCurrentUser(session));
			} else {
				if (!userPopServiceImpl.isEditClassByTypeid(classtypeid, getCurrentUser(session))) {
					throw new RuntimeException("当前用户无此权限!");
				}
				entity = classTServiceImpl.insertClasstEntity(entity, classtypeid, getCurrentUser(session));
			}
			return view.returnRedirectUrl("/classweb/mng.do?classid=" + entity.getId());
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 提交课程章节
	 * 
	 * @return
	 */
	@RequestMapping("/savechapter")
	@ResponseBody
	public Map<String, Object> editSubmit(ClassChapter entity, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(entity.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			if (StringUtils.isNotBlank(entity.getId())) {
				entity = classChapterServiceImpl.editClasschapterEntity(entity, getCurrentUser(session));
			} else {
				entity = classChapterServiceImpl.insertClasschapterEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交课程课时
	 * 
	 * @return
	 */
	@RequestMapping("/savehour")
	@ResponseBody
	public Map<String, Object> savehour(ClassHour entity, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(entity.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			if (StringUtils.isNotBlank(entity.getId())) {
				entity = classHourServiceImpl.editClasshourEntity(entity, getCurrentUser(session));
			} else {
				entity = classHourServiceImpl.insertClasshourEntity(entity, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载课程章节数据
	 * 
	 * @return
	 */
	@RequestMapping("/loadchapter")
	@ResponseBody
	public Map<String, Object> loadchapter(String chapterid, HttpSession session) {
		try {
			ClassChapter chapter = classChapterServiceImpl.getClasschapterEntity(chapterid);
			return ViewMode.getInstance().putAttr("entity", chapter).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载课程课时数据
	 * 
	 * @return
	 */
	@RequestMapping("/loadhour")
	@ResponseBody
	public Map<String, Object> loadhour(String hourid, HttpSession session) {
		try {
			ClassHour hour = classHourServiceImpl.getClasshourEntity(hourid);
			PersistFile persist = wdapFileServiceImpl.getPersistFile(hour.getFileid());
			return ViewMode.getInstance().putAttr("entity", hour).putAttr("persist", persist).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除章节数据
	 * 
	 * @return
	 */
	@RequestMapping("/delchapter")
	@ResponseBody
	public Map<String, Object> delchapter(String chapterid, HttpSession session) {
		try {
			ClassChapter chapter = classChapterServiceImpl.getClasschapterEntity(chapterid);
			if (!userPopServiceImpl.isEditClass(chapter.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classChapterServiceImpl.deleteClasschapterEntity(chapterid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除课时数据
	 * 
	 * @return
	 */
	@RequestMapping("/delhour")
	@ResponseBody
	public Map<String, Object> delhour(String hourid, HttpSession session) {
		try {
			ClassHour hour = classHourServiceImpl.getClasshourEntity(hourid);
			if (!userPopServiceImpl.isEditClass(hour.getClassid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classHourServiceImpl.deleteClasshourEntity(hourid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 保存课程介绍
	 * 
	 * @param id
	 * @param text
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveintroduction")
	public ModelAndView saveintroduction(String id, String text, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		Classt entity = null;
		try {
			if (!userPopServiceImpl.isEditClass(id, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			entity = classTServiceImpl.editIntroduction(id, text, getCurrentUser(session));
			return view.returnRedirectUrl("/classweb/mng.do?type=introduction&classid=" + entity.getId());
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 刪除课程
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public ModelAndView del(String classid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.deleteClasstEntity(classid, getCurrentUser(session));
			return view.returnRedirectUrl("/userspace/tempclass.do");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 发布课程
	 * 
	 * @param classid
	 * @param session
	 * @return
	 */
	@RequestMapping("/public")
	public ModelAndView publicClass(String classid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.publicClass(classid, getCurrentUser(session));
			return view.returnRedirectUrl("/userspace/tempclass.do");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 取消发布课程
	 * 
	 * @param classid
	 * @param session
	 * @return
	 */
	@RequestMapping("/temp")
	@ResponseBody
	public Map<String, Object> tempClass(String classid, HttpSession session) {
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			classTServiceImpl.tempClass(classid, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 课程編輯
	 * 
	 * @return
	 */
	@RequestMapping("/mng")
	public ModelAndView mng(String classid, String type, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			if (!userPopServiceImpl.isEditClass(classid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无此权限!");
			}
			ClassView classview = classTServiceImpl.getClassView(classid, getCurrentUser(session));
			view.putAttr("classview", classview);
			if (type == null || type.equals("base")) {
				return view.returnModelAndView("web-simple/classform/class-form-base");
			}
			if (type.equals("introduction")) {
				return view.returnModelAndView("web-simple/classform/class-form-introduction");
			}
			if (type.equals("chapter")) {
				List<ClassChapter> chapters = classChapterServiceImpl.getChapters(classid);
				List<ClassHour> hours = classHourServiceImpl.getHoursByClass(classid);
				return view.putAttr("chapters", chapters).putAttr("hours", hours)
						.returnModelAndView("web-simple/classform/class-form-chapter");
			}
			return view.returnModelAndView("web-simple/classform/class-form-base");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}
}
