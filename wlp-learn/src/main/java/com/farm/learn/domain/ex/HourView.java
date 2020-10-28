package com.farm.learn.domain.ex;

import java.util.List;

import com.farm.learn.domain.ClassChapter;
import com.farm.learn.domain.ClassHour;
import com.farm.sfile.domain.FileBase;

public class HourView {
	//
	private ClassHour hour;
	private ClassView classview;
	private String viewurl;
	private FileBase filebase;
	private List<HourView> hours;
	private ClassChapter chapter;
	// pdf转换的imgs数量
	private int pinum = 0;

	public int getPinum() {
		return pinum;
	}

	public void setPinum(int pinum) {
		this.pinum = pinum;
	}

	public ClassHour getHour() {
		return hour;
	}

	public void setHour(ClassHour hour) {
		this.hour = hour;
	}

	public ClassView getClassview() {
		return classview;
	}

	public void setClassview(ClassView classview) {
		this.classview = classview;
	}

	public String getViewurl() {
		return viewurl;
	}

	public void setViewurl(String viewurl) {
		this.viewurl = viewurl;
	}

	public FileBase getFilebase() {
		return filebase;
	}

	public void setFilebase(FileBase filebase) {
		this.filebase = filebase;
	}

	public List<HourView> getHours() {
		return hours;
	}

	public void setHours(List<HourView> hours) {
		this.hours = hours;
	}

	public ClassChapter getChapter() {
		return chapter;
	}

	public void setChapter(ClassChapter chapter) {
		this.chapter = chapter;
	}

}
