package com.wcp.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

 
@Entity(name = "Qanswer")
@Table(name = "wcp_qanswer")
public class Qanswer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "PRAISEYES", length = 10, nullable = false)
	private Integer praiseyes;
	@Column(name = "PRAISENO", length = 10, nullable = false)
	private Integer praiseno;
	@Column(name = "ANSWERINGNUM", length = 10, nullable = false)
	private Integer answeringnum;
	@Column(name = "ADOPTTIME", length = 32)
	private String adopttime;
	@Column(name = "DESCRIPTION", length = 65535, nullable = false)
	private String description;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "QUESTIONID", length = 32, nullable = false)
	private String questionid;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "ANONYMOUS", length = 1, nullable = false)
	private String anonymous;

	public Integer getPraiseyes() {
		return this.praiseyes;
	}

	public void setPraiseyes(Integer praiseyes) {
		this.praiseyes = praiseyes;
	}

	public Integer getPraiseno() {
		return this.praiseno;
	}

	public void setPraiseno(Integer praiseno) {
		this.praiseno = praiseno;
	}

	public Integer getAnsweringnum() {
		return this.answeringnum;
	}

	public void setAnsweringnum(Integer answeringnum) {
		this.answeringnum = answeringnum;
	}

	public String getAdopttime() {
		return this.adopttime;
	}

	public void setAdopttime(String adopttime) {
		this.adopttime = adopttime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
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

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	
}