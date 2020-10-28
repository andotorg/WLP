package com.farm.sfile.service.impl;

import com.farm.sfile.domain.FileBase;
import com.farm.sfile.enums.FileModel;
import com.farm.sfile.exception.FileExNameException;
import com.farm.core.time.TimeTool;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.farm.sfile.dao.FileBaseDaoInter;
import com.farm.sfile.service.FileBaseServiceInter;
import com.farm.sfile.utils.FarmDocFiles;
import com.farm.sfile.utils.FileDirDeleteUtils;
import com.farm.sfile.utils.FileMd5EncodeUtils;
import com.farm.sfile.utils.ViewDirUtils;
import com.farm.util.web.FarmFormatUnits;
import com.farm.util.web.FarmHtmlUtils;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

 
@Service
public class FileBaseServiceImpl implements FileBaseServiceInter {
	@Resource
	private FileBaseDaoInter filebaseDaoImpl;
	private static final Logger log = Logger.getLogger(FileBaseServiceImpl.class);

	@Override
	@Transactional
	public FileBase insertFilebaseEntity(FileBase entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		if (entity.getPstate() == null) {
			entity.setPstate("0");
		}
		if (entity.getFilesize() == null) {
			entity.setFilesize(0);
		}
		String id = UUID.randomUUID().toString().replaceAll("-", "").replaceAll(" ", "");
		entity.setSecret(id);
		entity = filebaseDaoImpl.insertEntity(entity);
		return entity;
	}

	@Override
	@Transactional
	public FileBase editFilebaseEntity(FileBase entity, LoginUser user) {
		FileBase entity2 = filebaseDaoImpl.getEntity(entity.getId());
		entity2.setResourcekey(entity.getResourcekey());
		entity2.setSysname(entity.getSysname());
		entity2.setPstate(entity.getPstate());
		entity2.setExname(entity.getExname());
		entity2.setRelativepath(entity.getRelativepath());
		entity2.setFilename(entity.getFilename());
		entity2.setTitle(entity.getTitle());
		entity2.setPcontent(entity.getPcontent());
		entity2.setId(entity.getId());
		filebaseDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteFilebaseEntity(String id, LoginUser user) {
		FileBase filebase = filebaseDaoImpl.getEntity(id);
		if (filebase.getPstate().equals("2") || filebase.getPstate().equals("1")) {
			throw new RuntimeException("the file can't delete!state is " + filebase.getPstate());
		}
		// 删除中间表
		// filebaseDaoImpl.delRelationsViewTables(id);
		// 刪除文件本表
		filebaseDaoImpl.deleteEntity(filebase);
		{
			// 物理删除文件
			File file = getFile(filebase);
			if (file.exists()) {
				if (!file.delete()) {
					throw new RuntimeException("the file not delete !" + file.getPath());
				}
			}
			File viewDir = new File(ViewDirUtils.getPath(file));
			FileDirDeleteUtils.delFolder(viewDir.getPath());
		}
	}

	@Override
	@Transactional
	public FileBase getFilebaseEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return filebaseDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createFilebaseSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WLP_F_FILE",
				"ID,RESOURCEKEY,SYSNAME,PSTATE,EXNAME,RELATIVEPATH,SECRET,FILENAME,TITLE,FILESIZE,PCONTENT,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		dbQuery.setDefaultSort(new DBSort("CTIME", "desc"));
		return dbQuery;
	}

	@Override
	@Transactional
	public void deleteFileByLogic(String fileid, LoginUser currentUser) {
		FileBase entity = filebaseDaoImpl.getEntity(fileid);
		entity.setPstate("9");
		filebaseDaoImpl.editEntity(entity);
	}

	@Override
	public boolean validateFileSize(long filesize, FileModel model) {
		if (filesize > Long.valueOf(model.getMaxsize())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validateFileExname(String originalFilename, FileModel model) {
		String exname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		if (!model.getExnames().contains(exname.toUpperCase()) && !model.getExnames().contains(exname.toLowerCase())) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public FileBase saveFile(File file, String filename, LoginUser currentUser, String fileProcesskey) {
		FileBase filebase = initFileBase(currentUser, filename, file.length(), null, "本地");
		FarmDocFiles.copyFile(file, getDirRealPath(filebase.getId()), filebase.getFilename(), fileProcesskey);
		return filebase;
	}

	@Override
	@Transactional
	public FileBase saveFile(byte[] data, String filename, LoginUser currentUser) {
		FileBase filebase = initFileBase(currentUser, filename, data.length, null, "本地");
		FarmDocFiles.saveFile(data, filebase.getFilename(), getDirRealPath(filebase.getId()));
		return filebase;
	}

	@Override
	@Transactional
	public FileBase initFileBase(LoginUser currentUser, String filename, long filesize, String appid, String sysName) {
		if (StringUtils.isNotBlank(appid)) {
			String fileid = getFileIdByAppId(appid);
			if (fileid != null) {
				return getFilebaseEntity(fileid);
			}
		}
		FileBase entity = new FileBase();
		String exName = FarmDocFiles.getExName(filename);
		entity.setFilename(UUID.randomUUID().toString().replaceAll("-", "") + "." + exName + ".file");
		entity.setTitle(filename);
		if (StringUtils.isNotBlank(appid)) {
			entity.setAppid(appid);
		}
		entity.setSysname(sysName);
		entity.setExname(exName);
		entity.setFilesize(new Long(filesize).intValue());
		entity.setRelativepath(FarmDocFiles.generateDir());
		entity.setResourcekey(ViewDirUtils.getWriteAbleDir().getKey());
		return insertFilebaseEntity(entity, currentUser);
	}

	@Override
	@Transactional
	public File getFile(FileBase filebase) {
		return new File(getDirRealPath(filebase) + filebase.getFilename());
	}

	@Override
	@Transactional
	public String getDirRealPath(FileBase filebase) {
		String path = ViewDirUtils.getDir(filebase.getResourcekey()).getPath() + filebase.getRelativepath();
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	@Override
	@Transactional
	public String getDirRealPath(String fileid) {
		FileBase file = filebaseDaoImpl.getEntity(fileid);
		return getDirRealPath(file);
	}

	@Override
	@Transactional
	public String getFileRealPath(String fileid) {
		return getDirRealPath(fileid) + getFilebaseEntity(fileid).getFilename();
	}

	@Override
	@Transactional
	public String getDownloadUrl(String fileid, FileModel model) {
		FileBase filebase = getFilebaseEntity(fileid);
		if (filebase == null) {
			return "download/Pubfile.do?id=fileNotExist";
		}
		return "download/Pubfile.do?id=" + fileid + "&secret=" + filebase.getSecret();
	}

	@Override
	public String getIconUrl(String fileid, FileModel model) {
		return "download/Pubicon.do?id=" + fileid;
	}

	@Override
	@Transactional
	public String submitFile(String fileid) throws FileNotFoundException {
		FileBase entity = filebaseDaoImpl.getEntity(fileid);
		File file = new File(getFileRealPath(fileid));
		String secret = entity.getSecret();
		if (!file.exists()) {
			throw new FileNotFoundException("the file is not exist:" + file.getPath());
		}
		// if (file.length() < 200 * 1024 * 1024) {
		// try {
		// log.info("file size:" + file.length() + "run md5 encodeing.....");
		// entity.setSecret(FileMd5EncodeUtils.calcMD5(file));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		entity.setPstate("1");
		filebaseDaoImpl.editEntity(entity);
		return secret + ":" + entity.getSecret();
	}

	@Override
	@Transactional
	public void freeFile(String fileid) {
		FileBase entity = filebaseDaoImpl.getEntity(fileid);
		if (entity != null) {
			entity.setPstate("0");
			filebaseDaoImpl.editEntity(entity);
		}
	}

	@Override
	@Transactional
	public String getFileIdByAppId(String appid) {
		if (StringUtils.isBlank(appid)) {
			return null;
		}
		List<FileBase> files = filebaseDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("APPID", appid, "=")).add(new DBRule("PSTATE", "9", "!=")).toList());
		if (files.size() > 0) {
			return files.get(0).getId();
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void editFileSize(String fileId, int length) {
		FileBase entity = filebaseDaoImpl.getEntity(fileId);
		entity.setFilesize(length);
		filebaseDaoImpl.editEntity(entity);
	}

	@Override
	@Transactional
	public void WriteTextFile(String fileId, String text) {
		if (StringUtils.isBlank(fileId) || StringUtils.isBlank(text)) {
			return;
		}
		File file = getFile(getFilebaseEntity(fileId));
		FileWriterWithEncoding writer = null;
		try {
			writer = new FileWriterWithEncoding(file, "utf-8");
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
