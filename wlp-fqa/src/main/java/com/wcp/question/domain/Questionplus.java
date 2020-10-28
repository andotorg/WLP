package com.wcp.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "QuestionPlus")
@Table(name = "wcp_question_plus")
public class Questionplus implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "CTIME", length = 14, nullable = false)
        private String ctime;
        @Column(name = "DESCRIPTION", length = 65535, nullable = false)
        private String description;
        @Column(name = "QUESTIONID", length = 32, nullable = false)
        private String questionid;

        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String getDescription() {
          return this.description;
        }
        public void setDescription(String description) {
          this.description = description;
        }
        public String  getQuestionid() {
          return this.questionid;
        }
        public void setQuestionid(String questionid) {
          this.questionid = questionid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}