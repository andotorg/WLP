package com.farm.category.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：课程分类类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "ClassType")
@Table(name = "wlp_c_classtype")
public class ClassType implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "NAME", length = 32, nullable = false)
        private String name;
        @Column(name = "TREECODE", length = 256, nullable = false)
        private String treecode;
        @Column(name = "PARENTID", length = 32, nullable = false)
        private String parentid;
        @Column(name = "SORT", length = 10, nullable = false)
        private Integer sort;
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

        public String  getName() {
          return this.name;
        }
        public void setName(String name) {
          this.name = name;
        }
        public String  getTreecode() {
          return this.treecode;
        }
        public void setTreecode(String treecode) {
          this.treecode = treecode;
        }
        public String  getParentid() {
          return this.parentid;
        }
        public void setParentid(String parentid) {
          this.parentid = parentid;
        }
        public Integer  getSort() {
          return this.sort;
        }
        public void setSort(Integer sort) {
          this.sort = sort;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getEuser() {
          return this.euser;
        }
        public void setEuser(String euser) {
          this.euser = euser;
        }
        public String  getEusername() {
          return this.eusername;
        }
        public void setEusername(String eusername) {
          this.eusername = eusername;
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
        public String  getEtime() {
          return this.etime;
        }
        public void setEtime(String etime) {
          this.etime = etime;
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