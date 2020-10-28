package com.wcp.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "QuestionDetail")
@Table(name = "wcp_question_detail")
public class Questiondetail implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "VTYPE", length = 2, nullable = false)
        private String vtype;
        @Column(name = "USERIP", length = 128, nullable = false)
        private String userip;
        @Column(name = "QUESTIONID", length = 32, nullable = false)
        private String questionid;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CUSER", length = 32)
        private String cuser;
        @Column(name = "CUSERNAME", length = 64)
        private String cusername;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;

        public String  getVtype() {
          return this.vtype;
        }
        public void setVtype(String vtype) {
          this.vtype = vtype;
        }
        public String  getUserip() {
          return this.userip;
        }
        public void setUserip(String userip) {
          this.userip = userip;
        }
        public String  getQuestionid() {
          return this.questionid;
        }
        public void setQuestionid(String questionid) {
          this.questionid = questionid;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
        }
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}