package com.wcp.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

 
@Entity(name = "Question")
@Table(name = "wcp_question")
public class Question implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "ANSWERINGNUM", length = 10, nullable = false)
	private Integer answeringnum;
	@Column(name = "VISITNUM", length = 10, nullable = false)
	private Integer visitnum;
	@Column(name = "PRAISENO", length = 10, nullable = false)
	private Integer praiseno;
	@Column(name = "PRAISEYES", length = 10, nullable = false)
	private Integer praiseyes;
	@Column(name = "ATTENTIONS", length = 10, nullable = false)
	private Integer attentions;
	@Column(name = "ANSWERS", length = 10, nullable = false)
	private Integer answers;
	@Column(name = "ANONYMOUS", length = 2, nullable = false)
	private String anonymous;
	@Column(name = "ENDTIME", length = 16)
	private String endtime;
	@Column(name = "PUBTIME", length = 16, nullable = false)
	private String pubtime;
	@Column(name = "DESCRIPTION", length = 65535, nullable = false)
	private String description;
	@Column(name = "TITLE", length = 64, nullable = false)
	private String title;
	@Column(name = "TYPEID", length = 32)
	private String typeid;
	@Column(name = "PRICE", length = 10, nullable = false)
	private Integer price;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "TAGS", length = 256)
	private String tags;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;
	@Column(name = "QANSWERID", length = 32)
	private String qanswerid;
	@Column(name = "IMGID", length = 32)
	private String imgid;
	@Column(name = "SHORTDESC", length = 256, nullable = false)
	private String shortdesc;
	@Column(name = "HOTNUM", length = 10, nullable = false)
	private Integer hotnum;
	@Column(name = "LASTVTIME", length = 16, nullable = false)
	private String lastvtime;
	

	public String getLastvtime() {
		return lastvtime;
	}

	public void setLastvtime(String lastvtime) {
		this.lastvtime = lastvtime;
	}

	public Integer getAnsweringnum() {
		return this.answeringnum;
	}

	public void setAnsweringnum(Integer answeringnum) {
		this.answeringnum = answeringnum;
	}

	public Integer getVisitnum() {
		return this.visitnum;
	}

	public void setVisitnum(Integer visitnum) {
		this.visitnum = visitnum;
	}

	public Integer getPraiseno() {
		return this.praiseno;
	}

	public void setPraiseno(Integer praiseno) {
		this.praiseno = praiseno;
	}

	public Integer getPraiseyes() {
		return this.praiseyes;
	}

	public void setPraiseyes(Integer praiseyes) {
		this.praiseyes = praiseyes;
	}
	
	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public Integer getAttentions() {
		return this.attentions;
	}

	public void setAttentions(Integer attentions) {
		this.attentions = attentions;
	}

	public Integer getAnswers() {
		return this.answers;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}

	public String getAnonymous() {
		return this.anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getPubtime() {
		return this.pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

	public String getQanswerid() {
		return qanswerid;
	}

	public void setQanswerid(String qanswerid) {
		this.qanswerid = qanswerid;
	}

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public Integer getHotnum() {
		return hotnum;
	}

	public void setHotnum(Integer hotnum) {
		this.hotnum = hotnum;
	}
}