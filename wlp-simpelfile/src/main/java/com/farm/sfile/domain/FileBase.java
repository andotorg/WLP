package com.farm.sfile.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

 
@Entity(name = "FileBase")
@Table(name = "wlp_f_file")
public class FileBase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "RESOURCEKEY", length = 32, nullable = false)
	private String resourcekey;
	@Column(name = "SYSNAME", length = 128, nullable = false)
	private String sysname;
	@Column(name = "PSTATE", length = 1, nullable = false)
	private String pstate;
	@Column(name = "EXNAME", length = 32, nullable = false)
	private String exname;
	@Column(name = "RELATIVEPATH", length = 1024, nullable = false)
	private String relativepath;
	@Column(name = "SECRET", length = 32, nullable = false)
	private String secret;
	@Column(name = "FILENAME", length = 128, nullable = false)
	private String filename;
	@Column(name = "TITLE", length = 256, nullable = false)
	private String title;
	@Column(name = "FILESIZE", length = 10, nullable = false)
	private Integer filesize;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "EUSER", length = 32, nullable = false)
	private String euser;
	@Column(name = "EUSERNAME", length = 64, nullable = false)
	private String eusername;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "APPID", length = 128)
	private String appid;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getResourcekey() {
		return resourcekey;
	}

	public void setResourcekey(String resourcekey) {
		this.resourcekey = resourcekey;
	}

	public String getSysname() {
		return this.sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getExname() {
		return this.exname;
	}

	public void setExname(String exname) {
		this.exname = exname;
	}

	public String getRelativepath() {
		return this.relativepath;
	}

	public void setRelativepath(String relativepath) {
		this.relativepath = relativepath;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getEuser() {
		return this.euser;
	}

	public void setEuser(String euser) {
		this.euser = euser;
	}

	public String getEusername() {
		return this.eusername;
	}

	public void setEusername(String eusername) {
		this.eusername = eusername;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getEtime() {
		return this.etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}