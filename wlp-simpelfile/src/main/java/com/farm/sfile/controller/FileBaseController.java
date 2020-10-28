package com.farm.sfile.controller;

import com.farm.sfile.domain.FileBase;
import com.farm.sfile.service.FileBaseServiceInter;

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

 
@RequestMapping("/filebase")
@Controller
public class FileBaseController extends WebUtils {
	private final static Logger log = Logger.getLogger(FileBaseController.class);
	@Resource
	private FileBaseServiceInter fileBaseServiceImpl;

	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (query.getRule("PSTATE") == null) {
				query.addRule(new DBRule("PSTATE", "9", "!="));
			}
			DataResult result = fileBaseServiceImpl.createFilebaseSimpleQuery(query).search();
			result.runDictionary("0:临时,1:持久,2:永久,9:删除", "PSTATE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(FileBase entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = fileBaseServiceImpl.editFilebaseEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(FileBase entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = fileBaseServiceImpl.insertFilebaseEntity(entity, getCurrentUser(session));
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
				fileBaseServiceImpl.deleteFilebaseEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	
	 
	@RequestMapping("/logicdel")
	@ResponseBody
	public Map<String, Object> logicdel(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				fileBaseServiceImpl.deleteFileByLogic(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	
	
	
	

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("file/FileBaseResult");
	}

	 
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			ViewMode view = ViewMode.getInstance();
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return view.putAttr("pageset", pageset).putAttr("realpath", fileBaseServiceImpl.getFileRealPath(ids))
						.putAttr("entity", fileBaseServiceImpl.getFilebaseEntity(ids))
						.returnModelAndView("file/FileBaseForm");
			}
			case (1): {// 新增
				return view.putAttr("pageset", pageset).returnModelAndView("file/FileBaseForm");
			}
			case (2): {// 修改
				return view.putAttr("pageset", pageset).putAttr("entity", fileBaseServiceImpl.getFilebaseEntity(ids))
						.returnModelAndView("file/FileBaseForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("file/FileBaseForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("file/FileBaseForm");
		}
	}

	 
	@RequestMapping("/viewText")
	public ModelAndView viewText(String id) {
		try {
			ViewMode view = ViewMode.getInstance();
			return view.returnModelAndView("file/FileTextView");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("file/FileTextView");
		}
	}
}
