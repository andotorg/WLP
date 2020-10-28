package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "ClassT")
@Table(name = "wlp_l_class")
public class Classt implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "INTRODUCTIONID", length = 32)
        private String introductionid;
        @Column(name = "NAME", length = 128, nullable = false)
        private String name;
        @Column(name = "DIFFICULTY", length = 1, nullable = false)
        private String difficulty;
        @Column(name = "SHORTINTRO", length = 512, nullable = false)
        private String shortintro;
        @Column(name = "OUTAUTHOR", length = 64, nullable = false)
        private String outauthor;
        @Column(name = "ALTIME", length = 10, nullable = false)
        private Integer altime;
        @Column(name = "EVALUATION", length = 10, nullable = false)
        private Integer evaluation;
        @Column(name = "LEARNEDNUM", length = 10, nullable = false)
        private Integer learnednum;
        @Column(name = "IMGID", length = 32, nullable = false)
        private String imgid;
        @Column(name = "MINIMGID", length = 32)
        private String minimgid;
        @Column(name = "BOOKEDNUM", length = 10, nullable = false)
        private Integer bookednum;
        @Column(name = "HOTSCORE", length = 10, nullable = false)
        private Integer hotscore;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Column(name = "EUSERNAME", length = 64, nullable = false)
        private String eusername;
        @Column(name = "EUSER", length = 32, nullable = false)
        private String euser;
        @Column(name = "ETIME", length = 16, nullable = false)
        private String etime;

        public String  getIntroductionid() {
          return this.introductionid;
        }
        public void setIntroductionid(String introductionid) {
          this.introductionid = introductionid;
        }
        public String  getName() {
          return this.name;
        }
        public void setName(String name) {
          this.name = name;
        }
        public String  getDifficulty() {
          return this.difficulty;
        }
        public void setDifficulty(String difficulty) {
          this.difficulty = difficulty;
        }
        public String  getShortintro() {
          return this.shortintro;
        }
        public void setShortintro(String shortintro) {
          this.shortintro = shortintro;
        }
        public String  getOutauthor() {
          return this.outauthor;
        }
        public void setOutauthor(String outauthor) {
          this.outauthor = outauthor;
        }
        public Integer  getAltime() {
          return this.altime;
        }
        public void setAltime(Integer altime) {
          this.altime = altime;
        }
        public Integer  getEvaluation() {
          return this.evaluation;
        }
        public void setEvaluation(Integer evaluation) {
          this.evaluation = evaluation;
        }
        public Integer  getLearnednum() {
          return this.learnednum;
        }
        public void setLearnednum(Integer learnednum) {
          this.learnednum = learnednum;
        }
        public String  getImgid() {
          return this.imgid;
        }
        public void setImgid(String imgid) {
          this.imgid = imgid;
        }
        public String  getMinimgid() {
          return this.minimgid;
        }
        public void setMinimgid(String minimgid) {
          this.minimgid = minimgid;
        }
        public Integer  getBookednum() {
          return this.bookednum;
        }
        public void setBookednum(Integer bookednum) {
          this.bookednum = bookednum;
        }
        public Integer  getHotscore() {
          return this.hotscore;
        }
        public void setHotscore(Integer hotscore) {
          this.hotscore = hotscore;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getEusername() {
          return this.eusername;
        }
        public void setEusername(String eusername) {
          this.eusername = eusername;
        }
        public String  getEuser() {
          return this.euser;
        }
        public void setEuser(String euser) {
          this.euser = euser;
        }
        public String  getEtime() {
          return this.etime;
        }
        public void setEtime(String etime) {
          this.etime = etime;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}