package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

 
@Entity(name = "ClassHour")
@Table(name = "wlp_l_classhour")
public class ClassHour implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "NOTE", length = 2048)
	private String note;
	@Column(name = "CHAPTERID", length = 32, nullable = false)
	private String chapterid;
	@Column(name = "TITLE", length = 256, nullable = false)
	private String title;
	@Column(name = "SORT", length = 10, nullable = false)
	private Integer sort;
	@Column(name = "FILEID", length = 32)
	private String fileid;
	@Column(name = "AUTHORID", length = 32)
	private String authorid;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
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
	@Column(name = "CLASSID", length = 32, nullable = false)
	private String classid;

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getChapterid() {
		return this.chapterid;
	}

	public void setChapterid(String chapterid) {
		this.chapterid = chapterid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFileid() {
		return this.fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getAuthorid() {
		return this.authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
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

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

}