package com.farm.core.sql.query;

 
public class DBSort {
	private String sortTitleText;// 排序字段
	private String sortTypeText;// 排序类型
	private String isValidate;

	@SuppressWarnings("unused")
	private DBSort() {
	}

	 
	public DBSort(String title, String type) {
		DataQuerys.wipeVirus(type);
		DataQuerys.wipeVirus(title);
		sortTitleText = title;
		sortTypeText = type;
	}

	public DBSort(String title, String type, boolean isValidate) {
		if (isValidate) {
			DataQuerys.wipeVirus(type);
			DataQuerys.wipeVirus(title);
		}
		sortTitleText = title;
		sortTypeText = type;
	}

	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
	}

	 
	public String getSortTitleText() {
		if (sortTitleText == null
				|| sortTitleText.trim().toUpperCase().equals("NULL")) {
			return null;
		}
		return sortTitleText.replace("_", ".");
	}

	public String getSortTypeText() {
		return sortTypeText;
	}

	public void setSortTypeText(String sortTypeText) {
		this.sortTypeText = sortTypeText;
	}

	public String getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

}
