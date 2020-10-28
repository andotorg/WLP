package com.farm.sfile;

import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.farm.core.auth.domain.LoginUser;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.domain.ex.PersistFile;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.sfile.exception.FileSizeException;

 
public interface WdapFileServiceInter {

	 
	public FileBase saveLocalFile(MultipartFile file, FileModel model, LoginUser currentUser, String fileProcesskey)
			throws FileSizeException, FileExNameException;

	 
	public FileBase saveLocalFile(byte[] data, FileModel fileModel, String filename, LoginUser currentUser)
			throws FileSizeException, FileExNameException;

	 
	public String getDownloadUrl(String fileid, FileModel model);

	 
	public String getExName(String filename);

	 
	public PersistFile getFileIconFile(String fileid);

	 
	public void delFileByLogic(String fileid);

	 
	public String submitFile(String fileid) throws FileNotFoundException;

	 
	public void freeFile(String fileid);

	 
	public PersistFile getPersistFile(String fileid);
	public PersistFile getPersistFile(FileBase file);

	 
	public String getFileRealPath(String fileid);

	 
	public FileBase initFile(LoginUser user, String filename, long length, String appid, String sysname);

	 
	public String getIconUrl(String fileid);

	 
	public FileModel getFileModel(String fileid) throws FileExNameException;


	 
	public String getFileIdByAppId(String appid);

	 
	public String readFileToText(String fileid);

	 
	public void updateFilesize(String fileId, int length);

	 
	public void writeFileByText(String fileId, String text);

	 
	public FileBase getFileBase(String fileid);

	public PersistFile getDirImgFile(String fileid, int num);

}
