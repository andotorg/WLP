package com.farm.sfile.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.farm.core.page.ViewMode;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.exception.FileSizeException;
import com.farm.sfile.utils.FileCopyProcessCache;
import com.farm.web.WebUtils;

 
@RequestMapping("/upload")
@Controller
public class FileUploadController extends WebUtils {
	private static final Logger log = Logger.getLogger(FileUploadController.class);

	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;

	@RequestMapping(value = "/PubUploadProcess.do")
	@ResponseBody
	public Map<String, Object> PubUploadProcess(String processkey, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		Integer process = 0;
		try {
			String filekey = session.getId() + processkey;
			process = FileCopyProcessCache.getProcess(filekey);
		} catch (Exception e) {
			view.setError(e.getMessage(), e);
		}
		return view.putAttr("process", process).returnObjMode();
	}

	 
	@RequestMapping(value = "/media.do")
	@ResponseBody
	public Map<String, Object> PubFPuploaMedia(@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		Map<String, Object> data = upload(file, null, FileModel.IMMED.name(), request, session);
		return ViewMode.getInstance().putAttr("error", data.get("STATE")).putAttr("url", data.get("url"))
				.putAttr("message", data.get("MESSAGE")).putAttr("id", data.get("id"))
				.putAttr("fileName", data.get("fileName")).returnObjMode();
	}

	 
	@RequestMapping(value = "/general.do")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			String processkey, String type, HttpServletRequest request, HttpSession session) {
		FileBase filebase = null;
		String url = null;
		ViewMode view = ViewMode.getInstance();
		try {
			FileModel fileModel = getFileMOdel(type, file.getOriginalFilename());
			String filekey = session.getId() + processkey;
			filebase = wdapFileServiceImpl.saveLocalFile(file, fileModel, getCurrentUser(session), filekey);
			url = wdapFileServiceImpl.getDownloadUrl(filebase.getId(), fileModel);
			String filename = getUrlEncodeFileName(filebase.getTitle());
			view.putAttr("fileName", URLDecoder.decode(filename, "utf-8"));
			view.putAttr("id", filebase.getId());
			view.putAttr("url", url);
		} catch (FileSizeException e) {
			view.setError("文件大小错误:" + e.getMessage(), e);
		} catch (FileExNameException e) {
			view.setError("文件类型错误:" + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			view.setError(e.getMessage(), e);
		} catch (Exception e) {
			view.setError(e.getMessage(), e);
		}
		return view.returnObjMode();
	}

	 
	@RequestMapping("/base64img")
	@ResponseBody
	public Map<String, Object> base64up(String base64, String filename, HttpSession session) {
		FileBase filebase = null;
		String url = null;
		ViewMode view = ViewMode.getInstance();
		try {
			base64 = base64.trim();
			if (base64.indexOf(",") > 0) {
				// data:image/png;base64,
				if (base64.indexOf("data:image/") == 0) {
					filename = filename.substring(0, filename.indexOf(".")) + "."
							+ base64.substring(base64.indexOf("/") + 1, base64.indexOf(";"));
				}
				base64 = base64.substring(base64.indexOf(",") + 1);
			}
			FileModel fileModel = getFileMOdel(null, filename);
			byte[] data = Base64.decodeBase64(base64);
			filebase = wdapFileServiceImpl.saveLocalFile(data, fileModel, filename, getCurrentUser(session));
			url = wdapFileServiceImpl.getDownloadUrl(filebase.getId(), fileModel);
			filename = getUrlEncodeFileName(filebase.getTitle());
			view.putAttr("fileName", filename);
			view.putAttr("id", filebase.getId());
			view.putAttr("url", url);
		} catch (FileSizeException e) {
			view.setError("文件大小错误:" + e.getMessage(), e);
		} catch (FileExNameException e) {
			view.setError("文件类型错误:" + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			view.setError(e.getMessage(), e);
		}
		return view.returnObjMode();
	}

	// ---------------------------------------------------------------------------------------
	 
	private String getUrlEncodeFileName(String fileName) throws UnsupportedEncodingException {
		try {
			return URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
		} catch (Exception e) {
			return URLEncoder.encode(fileName, "utf-8");
		}
	}

	 
	private FileModel getFileMOdel(String fileModelName, String fileName) throws FileExNameException {
		if (StringUtils.isBlank(fileModelName)) {
			return FileModel.getModelByFileExName(wdapFileServiceImpl.getExName(fileName));
		} else {
			return FileModel.getModel(fileModelName);
		}
	}

}
