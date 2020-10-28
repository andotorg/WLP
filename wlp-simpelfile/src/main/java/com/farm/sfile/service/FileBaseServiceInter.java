package com.farm.sfile.service;

import com.farm.core.sql.query.DataQuery;
import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;

 
public interface FileBaseServiceInter {
	 
	public FileBase saveFile(File file, String filename, LoginUser currentUser, String fileProcesskey);

	public FileBase saveFile(byte[] data, String filename, LoginUser currentUser);

	 
	public FileBase insertFilebaseEntity(FileBase entity, LoginUser user);

	 
	public FileBase editFilebaseEntity(FileBase entity, LoginUser user);

	 
	public void deleteFilebaseEntity(String id, LoginUser user);

	 
	public FileBase getFilebaseEntity(String id);

	 
	public DataQuery createFilebaseSimpleQuery(DataQuery query);

	 
	public void deleteFileByLogic(String fileid, LoginUser currentUser);

	 
	public boolean validateFileSize(long filesize, FileModel model);

	 
	public boolean validateFileExname(String originalFilename, FileModel model);

	 
	public FileBase initFileBase(LoginUser currentUser, String filetitle, long filesize, String appid, String string);

	 
	public String getDirRealPath(String fileid);

	 
	public String getDirRealPath(FileBase filebase);

	 
	public String getFileRealPath(String fileid);

	 
	public File getFile(FileBase filebase);

	 
	public String getDownloadUrl(String fileid, FileModel model);

	 
	public String getIconUrl(String fileid, FileModel model);

	 
	public String submitFile(String fileid) throws FileNotFoundException;

	 
	public void freeFile(String fileid);

	 
	public String getFileIdByAppId(String appid);

	 
	public void editFileSize(String fileId, int length);

	 
	public void WriteTextFile(String fileId, String text);

}