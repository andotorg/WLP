package com.farm.wcp.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.FileDownloadUtils;
import com.farm.wcp.util.VerifyCodeUtils;
import com.farm.web.WebUtils;

/**
 * 文件上传下载
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/webfile")
@Controller
public class FileAccessController extends WebUtils {
	private static final Logger log = Logger.getLogger(FileAccessController.class);

	/**
	 * 下载验证码
	 * 
	 * @return
	 */
	@RequestMapping("/Pubcheckcode")
	public void checkcode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		session = request.getSession(true);
		// 删除以前的
		session.removeAttribute("verCode");
		session.setAttribute("verCode", verifyCode.toUpperCase());
		// 生成图片
		int w = 100, h = 30;
		try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			log.error("下载验证码", e);
		}
	}
	/**
	 * 下载maxlogo
	 * 
	 * @return
	 */
	@RequestMapping("/PubHomelogo")
	public void maxlogo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String filePath = "/view/web-simple/atext/png/maxicon".replaceAll("/",
				File.separator.equals("/") ? "/" : "\\\\");
		File file = new File(webPath + filePath);
		downloadFile(file, "homelogo", response, request);
	}
	/**
	 * 下载logo
	 * 
	 * @return
	 */
	@RequestMapping("/Publogo")
	public void logo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String filePath = "/view/web-simple/atext/png/icon".replaceAll("/", File.separator.equals("/") ? "/" : "\\\\");
		File file = new File(webPath + filePath);
		downloadFile(file, "logo", response, request);
	}
	/**
	 * response下载文件
	 * 
	 * @param file
	 * @param filename
	 * @param response
	 * @param request
	 * @return true 的话表示第一次请求 false 表示后面多次请求
	 */
	private void downloadFile(File file, String filename, HttpServletResponse response, HttpServletRequest request) {
		if (file.length() > 104857600) {
			FileDownloadUtils.rangeStreamDownloadFile(request, response, file, filename, "application/octet-stream");
		} else {
			FileDownloadUtils.simpleDownloadFile(file, filename, "application/octet-stream", response);
		}
	}
}
