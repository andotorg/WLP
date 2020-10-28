package com.farm.learn.controller;

import com.farm.learn.domain.ClassClassType;
import com.farm.learn.service.ClassclasstypeServiceInter;
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
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

 
@RequestMapping("/classclasstype")
@Controller
public class ClassclasstypeController extends WebUtils{
  private final static Logger log = Logger.getLogger(ClassclasstypeController.class);
  @Resource
  private ClassclasstypeServiceInter classClassTypeServiceImpl;


         
  @RequestMapping("/query")
  @ResponseBody
  public Map<String, Object> queryall(DataQuery query,
      HttpServletRequest request) {
        
    try {
      query = EasyUiUtils.formatGridQuery(request, query);
      DataResult result = classClassTypeServiceImpl
          .createClassclasstypeSimpleQuery(query).search();
      return ViewMode.getInstance()
          .putAttrs(EasyUiUtils.formatGridData(result))
          .returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage(),e)
          .returnObjMode();
    }
  }


   
  @RequestMapping("/edit")
  @ResponseBody
  public Map<String, Object> editSubmit(ClassClassType entity, HttpSession session) {
        
    try {
      entity = classClassTypeServiceImpl.editClassclasstypeEntity(entity,
          getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.UPDATE)
          .putAttr("entity", entity).returnObjMode();
      
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.UPDATE)
          .setError(e.getMessage(),e).returnObjMode();
    }
  }

         
  @RequestMapping("/add")
  @ResponseBody
  public Map<String, Object> addSubmit(ClassClassType entity, HttpSession session) {
        
    try {
      entity = classClassTypeServiceImpl.insertClassclasstypeEntity(entity,
          getCurrentUser(session));
      return ViewMode.getInstance().setOperate(OperateType.ADD)
                                        .putAttr("entity", entity).returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setOperate(OperateType.ADD)
                                       .setError(e.getMessage(),e).returnObjMode();
    }
  }

   
  @RequestMapping("/del")
  @ResponseBody
  public Map<String, Object> delSubmit(String ids, HttpSession session) {
    try {
      for (String id : parseIds(ids)) {
        classClassTypeServiceImpl.deleteClassclasstypeEntity(id,
            getCurrentUser(session));
      }
      return ViewMode.getInstance().returnObjMode();
    } catch (Exception e) {
      log.error(e.getMessage());
      return ViewMode.getInstance().setError(e.getMessage(),e)
          .returnObjMode();
    }
  }

  @RequestMapping("/list")
  public ModelAndView index(HttpSession session) {
    return ViewMode.getInstance()
        .returnModelAndView("learn/ClassclasstypeResult");
  }
         
  @RequestMapping("/form")
  public ModelAndView view(RequestMode pageset, String ids) {
    try {
      switch (pageset.getOperateType()) {
      case (0):{//查看
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", classClassTypeServiceImpl.getClassclasstypeEntity(ids))
            .returnModelAndView("learn/ClassclasstypeForm");
      }
      case (1): {// 新增
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .returnModelAndView("learn/ClassclasstypeForm");
      }
      case (2):{//修改
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", classClassTypeServiceImpl.getClassclasstypeEntity(ids))
            .returnModelAndView("learn/ClassclasstypeForm");
      }
      default:
        break;
      }
      return ViewMode.getInstance().returnModelAndView("learn/ClassclasstypeForm");
    } catch (Exception e) {
      return ViewMode.getInstance().setError(e + e.getMessage(),e)
          .returnModelAndView("learn/ClassclasstypeForm");
    }
  }
}
