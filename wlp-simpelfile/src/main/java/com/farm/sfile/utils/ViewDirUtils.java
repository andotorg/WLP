package com.farm.sfile.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.farm.parameter.FarmParameterService;
import com.farm.sfile.domain.ex.DirResouce;
import com.farm.sfile.exception.FileNotExistException;
import com.farm.web.WebUtils;

 
public class ViewDirUtils {

	 
	public static String getPath(File realfile) {
		String path = realfile.getParentFile().getPath();
		path = path + File.separator + realfile.getName().replaceAll("\\.", "_") + "_view";
		return path;
	}

	private static List<DirResouce> getAllDir() {
		String val = FarmParameterService.getInstance().getParameter("config.doc.dir");
		// KEY1:PATH，KEY2:PATH
		boolean isMainPath = true;
		List<DirResouce> allDir = new ArrayList<>();
		for (String node : WebUtils.parseIds(val)) {
			// KEY1:PATH
			DirResouce dr = new DirResouce();
			dr.setKey(node.substring(0, node.indexOf(":")));
			dr.setPath(node.substring(node.indexOf(":") + 1));
			if (!new File(dr.getPath()).exists()) {
				new File(dr.getPath()).mkdirs();
			}
			if (!new File(dr.getPath()).exists()) {
				throw new FileNotExistException(dr.getPath());
			}
			if (isMainPath) {
				dr.setWriteAble(isMainPath);
				isMainPath = false;
			} else {
				dr.setWriteAble(isMainPath);
			}
			allDir.add(dr);
		}
		return allDir;
	}

	public static DirResouce getDir(String key) {
		for (DirResouce dir : getAllDir()) {
			if (dir.getKey().equals(key)) {
				return dir;
			}
		}
		throw new RuntimeException("无指定文件夹资源（" + key + "），请检查参数config.doc.dir");
	}

	public static DirResouce getWriteAbleDir() {
		for (DirResouce dir : getAllDir()) {
			if (dir.isWriteAble()) {
				return dir;
			}
		}
		throw new RuntimeException("无可用文件夹资源，请检查参数config.doc.dir");
	}
}
