package com.farm.sfile.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farm.parameter.FarmParameterService;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.utils.DownloadUtils;
import com.farm.sfile.utils.HttpContentType;
import com.farm.web.WebUtils;

 
@RequestMapping("/download")
@Controller
public class FileDownloadController extends WebUtils {
	private static final Logger log = Logger.getLogger(FileDownloadController.class);

	@Resource
	private WdapFileServiceInter wdapFileServiceImpl;

	 
	@RequestMapping("/PubDirFile")
	public void loadDirFileByDown(String fileid, int num, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getDirImgFile(fileid, num);
		DownloadUtils.downloadFile(file.getFile(), file.getName(), response);
	}

	 
	@RequestMapping("/Pubfile")
	public void loadFileByDown(String id, String secret, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getPersistFile(id);
		if (!file.getSecret().trim().equals(secret)) {
			response.sendError(405, "安全码错误!secret：" + secret);
			return;
		}
		DownloadUtils.downloadFile(file.getFile(), file.getName(), response);
	}

	 
	@RequestMapping("/Pubimg")
	public void loadImgByDown(String id, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException, FileExNameException {
		FileBase fileb = wdapFileServiceImpl.getFileBase(id);
		FileModel model = FileModel.getModelByFileExName(fileb.getExname());
		if (!model.equals(FileModel.IMG)) {
			throw new RuntimeException("the file is not IMG!");
		}
		PersistFile file = wdapFileServiceImpl.getPersistFile(fileb);
		DownloadUtils.downloadFile(file.getFile(), file.getName(), response);
	}

	 
	@RequestMapping("/Pubphone")
	public void loadPhoneByDown(String id, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException, FileExNameException {
		FileBase fileb = wdapFileServiceImpl.getFileBase(id);
		if (fileb == null) {
			String imgpath = FarmParameterService.getInstance().getParameter("config.doc.none.photo.path");
			File defaultPhone = new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path")
					+ File.separator + imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
			DownloadUtils.downloadFile(defaultPhone, "默认头像", response);
		} else {
			PersistFile file = wdapFileServiceImpl.getPersistFile(fileb);
			DownloadUtils.downloadFile(file.getFile(), file.getName(), response);
		}
	}

	 
	@RequestMapping("/Pubload")
	public void loadFileByFile(String id, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		PersistFile file = wdapFileServiceImpl.getPersistFile(id);
		DownloadUtils.sendVideoFile(request, response, file.getFile(), file.getName(),
				new HttpContentType().getContentType(wdapFileServiceImpl.getExName(file.getName())));
	}

	// ---------------------------------------------------------------
}
