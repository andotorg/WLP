package com.wcp.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "QanswerComment")
@Table(name = "wcp_qanswer_comment")
public class Qanswercomment implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "DESCRIPTION", length = 65535, nullable = false)
        private String description;
        @Column(name = "ANSWERID", length = 32, nullable = false)
        private String answerid;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;

        public String  getDescription() {
          return this.description;
        }
        public void setDescription(String description) {
          this.description = description;
        }
        public String  getAnswerid() {
          return this.answerid;
        }
        public void setAnswerid(String answerid) {
          this.answerid = answerid;
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