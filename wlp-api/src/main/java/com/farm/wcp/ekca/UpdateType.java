package com.farm.wcp.ekca;

import java.io.Serializable;

 
public class UpdateType implements Serializable {
	private String val;
	private String userid;
	private String note;
	 
	private static final long serialVersionUID = -4241189109674676843L;


	public UpdateType(String val, String userid) {
		this.val = val;
		this.userid = userid;
	}

	public UpdateType(String val, String userid, String note) {
		this.val = val;
		this.userid = userid;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	 
	public static UpdateType getNew(String userid) {
		return new UpdateType("1", userid);
	}

	 
	public static UpdateType getUpdate(String userid) {
		return new UpdateType("2", userid);
	}

	public static UpdateType getUpdate(String userid, String note) {
		return new UpdateType("2", userid, note);
	}

	 
	public static UpdateType getDel(String userid) {
		return new UpdateType("3", userid);
	}

	public String getVal() {
		return val;
	}

	public String getUserid() {
		return userid;
	}

	public String getTitle() {
		if ("1".equals(val)) {
			return "创建";
		}
		if ("2".equals(val)) {
			return "更新";
		}
		if ("3".equals(val)) {
			return "删除";
		}
		return val;
	}

}
