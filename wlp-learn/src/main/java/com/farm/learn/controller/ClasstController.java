package com.farm.learn.controller;

import com.farm.learn.domain.Classt;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.service.impl.WdapFileServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

 
@RequestMapping("/classt")
@Controller
public class ClasstController extends WebUtils {
	private final static Logger log = Logger.getLogger(ClasstController.class);
	@Resource
	private ClasstServiceInter classTServiceImpl;
	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;

	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = classTServiceImpl.createClasstSimpleQuery(query).search();
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/classChooseQuery")
	@ResponseBody
	public Map<String, Object> classChooseQuery(DataQuery query, HttpServletRequest request) {
		try {
			query.addRule(new DBRule("PSTATE", "1", "="));
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = classTServiceImpl.createClasstSimpleQuery(query).search();
			result.runDictionary("1:发布,0:未发布", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/findImg")
	@ResponseBody
	public Map<String, Object> findImg(String id, HttpServletRequest request) {
		try {
			String imgid = classTServiceImpl.getClasstEntity(id).getImgid();
			return ViewMode.getInstance().putAttr("url", wdapFileServiceImpl.getDownloadUrl(imgid, FileModel.IMG))
					.putAttr("imgid", imgid).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Classt entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = classTServiceImpl.editClasstEntity(entity, null, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Classt entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = classTServiceImpl.insertClasstEntity(entity, null, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				classTServiceImpl.deleteClasstEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("learn/ClasstResult");
	}

	 
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", classTServiceImpl.getClasstEntity(ids))
						.returnModelAndView("learn/ClasstForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("learn/ClasstForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", classTServiceImpl.getClasstEntity(ids))
						.returnModelAndView("learn/ClasstForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("learn/ClasstForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("learn/ClasstForm");
		}
	}
}
