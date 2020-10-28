package com.wcp.question.controller;

import com.wcp.question.domain.Qanswerplus;
import com.wcp.question.service.QanswerplusServiceInter;
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

 
@RequestMapping("/qanswerplus")
@Controller
public class QanswerplusController extends WebUtils{
  private final static Logger log = Logger.getLogger(QanswerplusController.class);
  @Resource
  QanswerplusServiceInter qanswerPlusServiceImpl;

  public QanswerplusServiceInter getQanswerplusServiceImpl() {
  return qanswerPlusServiceImpl;
  }

  public void setQanswerplusServiceImpl(QanswerplusServiceInter qanswerPlusServiceImpl) {
  this.qanswerPlusServiceImpl = qanswerPlusServiceImpl;
  }




         
  @RequestMapping("/query")
  @ResponseBody
  public Map<String, Object> queryall(DataQuery query,
      HttpServletRequest request) {
        
    try {
      query = EasyUiUtils.formatGridQuery(request, query);
      DataResult result = qanswerPlusServiceImpl
          .createQanswerplusSimpleQuery(query).search();
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
  public Map<String, Object> editSubmit(Qanswerplus entity, HttpSession session) {
        
    try {
      entity = qanswerPlusServiceImpl.editQanswerplusEntity(entity,
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
  public Map<String, Object> addSubmit(Qanswerplus entity, HttpSession session) {
        
    try {
      entity = qanswerPlusServiceImpl.insertQanswerplusEntity(entity,
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
        qanswerPlusServiceImpl.deleteQanswerplusEntity(id,
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
        .returnModelAndView("question/QanswerplusResult");
  }
         
  @RequestMapping("/form")
  public ModelAndView view(RequestMode pageset, String ids) {
    try {
      switch (pageset.getOperateType()) {
      case (0):{//查看
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", qanswerPlusServiceImpl.getQanswerplusEntity(ids))
            .returnModelAndView("question/QanswerplusForm");
      }
      case (1): {// 新增
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .returnModelAndView("question/QanswerplusForm");
      }
      case (2):{//修改
        return ViewMode.getInstance().putAttr("pageset", pageset)
            .putAttr("entity", qanswerPlusServiceImpl.getQanswerplusEntity(ids))
            .returnModelAndView("question/QanswerplusForm");
      }
      default:
        break;
      }
      return ViewMode.getInstance().returnModelAndView("question/QanswerplusForm");
    } catch (Exception e) {
      return ViewMode.getInstance().setError(e.getMessage(),e)
          .returnModelAndView("question/QanswerplusForm");
    }
  }
}
