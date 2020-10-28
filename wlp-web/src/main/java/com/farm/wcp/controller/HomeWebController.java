package com.farm.wcp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.category.domain.ClassType;
import com.farm.category.service.ClasstypeServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.learn.domain.Top;
import com.farm.learn.service.ClasschapterServiceInter;
import com.farm.learn.service.ClasshourServiceInter;
import com.farm.learn.service.ClasstServiceInter;
import com.farm.learn.service.TopServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.wcp.util.ThemesUtil;
import com.farm.wcp.util.ZxingTowDCode;
import com.farm.web.WebUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.wcp.question.service.QuestionServiceInter;

/**
 * 文件
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/home")
@Controller
public class HomeWebController extends WebUtils {
	@Resource
	private QuestionServiceInter questionServiceImpl;
	private static final Logger log = Logger.getLogger(HomeWebController.class);
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

	/***
	 * 首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/Pubindex")
	public ModelAndView index(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		try {
			List<ClassType> types = classTypeServiceImpl.getAllTypes();
			int homeMaxTypeNum = FarmParameterService.getInstance().getParameterInt("config.category.hometype.num");
			// 最新课程
			DataResult newClasses = classTServiceImpl.getNewClases(getCurrentUser(session));
			List<Top> tops = topServiceImpl.allTops(getCurrentUser(session));
			return view.putAttr("types", types).putAttr("tops", tops).putAttr("newClasses", newClasses)
					.putAttr("homeMaxTypeNum", homeMaxTypeNum)
					.returnModelAndView(ThemesUtil.getThemePage("home-indexPage", request));
		} catch (Exception e) {
			return view.setError(e.getMessage(), e).returnModelAndView("web-simple/simple-500");
		}
	}

	/**
	 * 展示二维码
	 */
	@RequestMapping("/PubQRCode")
	public void QRCode(HttpServletRequest request, HttpServletResponse response) {
		OutputStream outp = null;
		try {
			// 站点主页 String text = request.getScheme() + "://" +
			// request.getServerName() + ":" + request.getServerPort() +
			// request.getContextPath() +
			// "/";
			// 来访页面 request.getHeader("Referer");
			String text = request.getHeader("Referer");
			int width = 150;
			int height = 150;
			// 二维码的图片格式
			String format = "gif";
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			// 关于文件下载时采用文件流输出的方式处理：
			response.setContentType("application/x-download");
			// String filedownload = "想办法找到要提供下载的文件的物理路径＋文件名";
			String filedisplay = "给用户提供的下载文件名";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			outp = response.getOutputStream();
			ZxingTowDCode.writeToStream(bitMatrix, format, outp);

		} catch (Exception e) {
			log.error(e + e.getMessage(), e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
				} catch (IOException e) {
					log.error(e + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 授權信息頁面
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public ModelAndView info(HttpSession session, HttpServletRequest request) {
		return ViewMode.getInstance().returnModelAndView("/licence/index");
	}

	/**
	 * 抓走用户session中的一条数据(抓取并从session中删除) home/carrySession
	 * 
	 * @return
	 */
	@RequestMapping("/carrySession")
	@ResponseBody
	public Map<String, Object> carrySession(String attrName, HttpSession session) {
		ViewMode page = ViewMode.getInstance();
		Object val = session.getAttribute(attrName);
		if (val != null) {
			session.removeAttribute(attrName);
			page.putAttr("val", val);
		}
		return page.returnObjMode();
	}
}
