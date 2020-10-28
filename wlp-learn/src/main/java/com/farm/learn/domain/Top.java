package com.farm.learn.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
 
@Entity(name = "Top")
@Table(name = "wlp_l_top")
public class Top implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "MODEL", length = 1, nullable = false)
        private String model;
        @Column(name = "VISITNUM", length = 10, nullable = false)
        private Integer visitnum;
        @Column(name = "IMGID", length = 32, nullable = false)
        private String imgid;
        @Column(name = "URL", length = 512, nullable = false)
        private String url;
        @Column(name = "TITLE", length = 256, nullable = false)
        private String title;
        @Column(name = "TYPE", length = 1, nullable = false)
        private String type;
        @Column(name = "SORT", length = 10, nullable = false)
        private Integer sort;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Transient
        private String imgurl;
        

        public String getImgurl() {
			return imgurl;
		}
		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}
		public String  getModel() {
          return this.model;
        }
        public void setModel(String model) {
          this.model = model;
        }
        public Integer  getVisitnum() {
          return this.visitnum;
        }
        public void setVisitnum(Integer visitnum) {
          this.visitnum = visitnum;
        }
        public String  getImgid() {
          return this.imgid;
        }
        public void setImgid(String imgid) {
          this.imgid = imgid;
        }
        public String  getUrl() {
          return this.url;
        }
        public void setUrl(String url) {
          this.url = url;
        }
        public String  getTitle() {
          return this.title;
        }
        public void setTitle(String title) {
          this.title = title;
        }
        public String  getType() {
          return this.type;
        }
        public void setType(String type) {
          this.type = type;
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