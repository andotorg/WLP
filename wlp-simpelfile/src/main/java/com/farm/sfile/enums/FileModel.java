package com.farm.sfile.enums;

import java.util.List;

import com.farm.sfile.exception.FileExNameException;
import com.farm.parameter.FarmParameterService;

 
public enum FileModel {
	IMG, MEDIA, PHOTO, FILE,
	// 媒體或圖片
	IMMED,
	// 课件文件
	HOUR;
	 
	public long getMaxsize() {
		if (this.equals(IMG)) {
			return FarmParameterService.getInstance().getParameterLong("config.doc.img.upload.length.max");
		}
		if (this.equals(IMMED)) {
			return FarmParameterService.getInstance().getParameterLong("config.doc.upload.length.max");
		}
		if (this.equals(HOUR)) {
			return FarmParameterService.getInstance().getParameterLong("config.doc.hour.upload.length.max");
		}
		return FarmParameterService.getInstance().getParameterLong("config.doc.upload.length.max");
	}

	public List<String> getExnames() {
		if (this.equals(IMG) || this.equals(PHOTO)) {
			return FarmParameterService.getInstance().getParameterStringList("config.doc.img.upload.types");
		}
		if (this.equals(MEDIA)) {
			return FarmParameterService.getInstance().getParameterStringList("config.doc.media.upload.types");
		}
		if (this.equals(IMMED)) {
			List<String> all = FarmParameterService.getInstance().getParameterStringList("config.doc.img.upload.types");
			all.addAll(FarmParameterService.getInstance().getParameterStringList("config.doc.media.upload.types"));
			return all;
		}
		if (this.equals(HOUR)) {
			return FarmParameterService.getInstance().getParameterStringList("config.doc.hour.upload.types");
		}
		return FarmParameterService.getInstance().getParameterStringList("config.doc.upload.types");
	}

	public static FileModel getModel(String type) {
		if (IMG.name().equals(type.toUpperCase())) {
			return IMG;
		}
		if (MEDIA.name().equals(type.toUpperCase())) {
			return MEDIA;
		}
		if (IMMED.name().equals(type.toUpperCase())) {
			return IMMED;
		}
		if (HOUR.name().equals(type.toUpperCase())) {
			return HOUR;
		}
		return FILE;
	}

	 
	public static FileModel getModelByFileExName(String exName) throws FileExNameException {
		for (FileModel model : values()) {
			if (model.getExnames().contains(exName.toUpperCase())
					|| model.getExnames().contains(exName.toLowerCase())) {
				return model;
			}
		}
		throw new FileExNameException("the file type is not regist:" + exName);
	}
}
