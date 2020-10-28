package com.farm.wcp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.learn.domain.ex.HourView;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.web.WebUtils;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 课时创建
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/hourweb")
@Controller
public class HourWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(HourWebController.class);
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

	/**
	 * 继续学习
	 * 
	 * @return
	 */
	@RequestMapping("/PubContinue")
	public ModelAndView view(String classid, String hourid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			HourView hourview = null;
			if (StringUtils.isBlank(hourid)) {
				hourview = classTServiceImpl.getUserCurrentHour(classid, getCurrentUser(session));
			} else {
				hourview = classTServiceImpl.getUserHour(hourid, getCurrentUser(session));
			}
			return view.putAttr("hourview", hourview).returnModelAndView("web-simple/hour/hour-view");
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

}
