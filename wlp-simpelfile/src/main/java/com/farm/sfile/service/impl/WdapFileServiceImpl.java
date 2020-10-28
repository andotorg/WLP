package com.farm.sfile.service.impl;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
import com.farm.sfile.WdapFileServiceInter;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.exception.FileSizeException;
import com.farm.sfile.service.FileBaseServiceInter;
import com.farm.sfile.utils.FarmDocFiles;
import com.farm.sfile.utils.FileNameSortUtil;
import com.farm.sfile.utils.PdfToImgConvertor;

 
@Service
public class WdapFileServiceImpl implements WdapFileServiceInter {
	@Resource
	private FileBaseServiceInter fileBaseServiceImpl;

	private static final Logger log = Logger.getLogger(WdapFileServiceImpl.class);

	@Override
	public FileBase saveLocalFile(MultipartFile file, FileModel model, LoginUser currentUser, String fileProcesskey)
			throws FileSizeException, FileExNameException {
		// 校验文件长度
		if (!fileBaseServiceImpl.validateFileSize(file.getSize(), model)) {
			throw new FileSizeException("the file max size is " + model.getMaxsize());
		}
		// 校驗文件類型
		if (!fileBaseServiceImpl.validateFileExname(file.getOriginalFilename(), model)) {
			throw new FileExNameException("the file type is not exist " + StringUtils.join(model.getExnames(), ","));
		}
		CommonsMultipartFile cmFile = (CommonsMultipartFile) file;
		DiskFileItem item = (DiskFileItem) cmFile.getFileItem();
		{// 小于8k不生成到临时文件，临时解决办法。zhanghc20150919
			if (!item.getStoreLocation().exists()) {
				try {
					item.write(item.getStoreLocation());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		FileBase filebase = fileBaseServiceImpl.saveFile(item.getStoreLocation(), file.getOriginalFilename(),
				currentUser, fileProcesskey);
		return filebase;
	}

	@Override
	public FileBase saveLocalFile(byte[] data, FileModel fileModel, String filename, LoginUser currentUser)
			throws FileSizeException, FileExNameException {
		// 校验文件长度
		if (!fileBaseServiceImpl.validateFileSize(data.length, fileModel)) {
			throw new FileSizeException("the file max size is " + fileModel.getMaxsize());
		}
		// 校驗文件類型
		if (!fileBaseServiceImpl.validateFileExname(filename, fileModel)) {
			throw new FileExNameException(
					"the file type is not exist " + StringUtils.join(fileModel.getExnames(), ","));
		}
		FileBase filebase = fileBaseServiceImpl.saveFile(data, filename, currentUser);
		return filebase;
	}

	@Override
	public String getDownloadUrl(String fileid, FileModel model) {
		return fileBaseServiceImpl.getDownloadUrl(fileid, model);
	}

	@Override
	public String getExName(String filename) {
		return FarmDocFiles.getExName(filename);
	}

	@Override
	public PersistFile getFileIconFile(String fileid) {
		String exname = fileBaseServiceImpl.getFilebaseEntity(fileid).getExname();
		PersistFile appIcon = new PersistFile();
		appIcon.setName("icon." + exname);
		try {
			if (FileModel.getModelByFileExName(exname).equals(FileModel.IMG)) {
				// 图片就直接读取图片
				appIcon.setFile(new File(fileBaseServiceImpl.getFileRealPath(fileid)));
				appIcon.setName("icon." + exname);
			} else {
				// 不是图片就读取系统图标
				appIcon.setFile(FarmDocFiles.getSysIcon(exname.toUpperCase()));
				appIcon.setName("icon.png");
			}
		} catch (FileExNameException e) {
			throw new RuntimeException(e);
		}
		return appIcon;
	}

	@Override
	public void delFileByLogic(String fileid) {
		fileBaseServiceImpl.deleteFileByLogic(fileid, null);
	}

	@Override
	public String submitFile(String fileid) throws FileNotFoundException {
		return fileBaseServiceImpl.submitFile(fileid);
	}

	@Override
	public void freeFile(String fileid) {
		fileBaseServiceImpl.freeFile(fileid);
	}

	@Override
	public PersistFile getPersistFile(String fileid) {
		FileBase filebase = fileBaseServiceImpl.getFilebaseEntity(fileid);
		return getPersistFile(filebase);
	}

	@Override
	public PersistFile getPersistFile(FileBase filebase) {
		PersistFile appIcon = new PersistFile();
		if (filebase == null) {
			return null;
		}
		appIcon.setName(filebase.getTitle());
		appIcon.setFile(new File(fileBaseServiceImpl.getFileRealPath(filebase.getId())));
		appIcon.setSecret(filebase.getSecret());
		return appIcon;
	}

	@Override
	public String getFileRealPath(String fileid) {
		return fileBaseServiceImpl.getFileRealPath(fileid);
	}

	@Override
	public FileBase initFile(LoginUser user, String filename, long length, String appid, String sysname) {
		return fileBaseServiceImpl.initFileBase(user, filename, length, appid, sysname);
	}

	@Override
	public String getIconUrl(String fileid) {
		try {
			return fileBaseServiceImpl.getIconUrl(fileid,
					FileModel.getModelByFileExName(fileBaseServiceImpl.getFilebaseEntity(fileid).getExname()));
		} catch (FileExNameException e) {
			log.error(e);
			return e.getMessage();
		}
	}

	@Override
	public FileModel getFileModel(String fileid) throws FileExNameException {
		return FileModel.getModelByFileExName(fileBaseServiceImpl.getFilebaseEntity(fileid).getExname());
	}

	@Override
	public String getFileIdByAppId(String appid) {
		return fileBaseServiceImpl.getFileIdByAppId(appid);
	}

	@Override
	public String readFileToText(String fileid) {
		PersistFile pfile = getPersistFile(fileid);
		if (pfile != null && pfile.getFile().exists()) {
			String text = FarmDocFiles.readFileText(pfile.getFile());
			return text;
		} else {
			return null;
		}
	}

	@Override
	public void updateFilesize(String fileId, int length) {
		fileBaseServiceImpl.editFileSize(fileId, length);
	}

	@Override
	public void writeFileByText(String fileId, String text) {
		fileBaseServiceImpl.WriteTextFile(fileId, text);
	}

	@Override
	public FileBase getFileBase(String fileid) {
		return fileBaseServiceImpl.getFilebaseEntity(fileid);
	}

	@Override
	public PersistFile getDirImgFile(String fileid, int num) {
		PersistFile pfile = new PersistFile();
		FileBase bfile = fileBaseServiceImpl.getFilebaseEntity(fileid);
		File bFile = fileBaseServiceImpl.getFile(bfile);
		File imgsDir = PdfToImgConvertor.getImgDir(bFile);
		List<File> files = Arrays.asList(imgsDir.listFiles());
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return FileNameSortUtil.compareTo(o1.getName(), o2.getName());
			}
		});
		pfile.setFile(files.get(num));
		pfile.setName(files.get(num).getName());
		pfile.setSecret(bfile.getSecret());
		return pfile;
	}

}
