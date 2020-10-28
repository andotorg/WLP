package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "ClassTag")
@Table(name = "wlp_l_classtag")
public class ClassTag implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "CLASSID", length = 32, nullable = false)
        private String classid;
        @Column(name = "TAGID", length = 32, nullable = false)
        private String tagid;

        public String  getClassid() {
          return this.classid;
        }
        public void setClassid(String classid) {
          this.classid = classid;
        }
        public String  getTagid() {
          return this.tagid;
        }
        public void setTagid(String tagid) {
          this.tagid = tagid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}