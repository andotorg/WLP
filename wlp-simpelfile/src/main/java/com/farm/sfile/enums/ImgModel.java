package com.farm.sfile.enums;

 
public enum ImgModel {
	MAX("max", "max", 1200), MED("med", "med", 960), MIN("min", "min", 330), ROTATE90("rotate90", "rotate",
			90), ROTATE0("rotate0", "rotate", 0);
	 
	private String urlIndex;
	 
	private String fileIndex;
	private int num;

	public String getUrlIndex() {
		return urlIndex;
	}

	public String getFileIndex() {
		return fileIndex;
	}

	public int getNum() {
		return num;
	}

	 
	ImgModel(String urlIndex, String fileIndex, int num) {
		this.urlIndex = urlIndex;
		this.num = num;
		this.fileIndex = fileIndex;
	}

	 
	public static ImgModel getEnum(String type) {
		for (ImgModel typenode : values()) {
			if (typenode.getUrlIndex().toUpperCase().equals(type.toUpperCase())) {
				return typenode;
			}
		}
		return null;
	}
}
