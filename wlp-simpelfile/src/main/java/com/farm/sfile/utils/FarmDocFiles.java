package com.farm.sfile.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;
import com.farm.sfile.enums.ImgModel;

public class FarmDocFiles {
	private static final Logger log = Logger.getLogger(FarmDocFiles.class);
	private static final Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|\\\\.]");

	 
	public static String filenameFilter(String str) {
		return str == null ? null : FilePattern.matcher(str).replaceAll("");
	}

	 
	public static File getSysIcon(String exname) {
		String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String filePath = "/text/img/fileicon/" + exname + ".png";
		filePath = FarmDocFiles.formatPathSeparator(filePath);
		File file = new File(webPath + filePath);
		if (!file.exists()) {
			file = new File(webPath + "/text/img/fileicon/Default.png");
		}
		return file;
	}

	 
	public static String generateDir() {
		// 把临时文件拷贝到上传目录下
		String dirPath = File.separator + TimeTool.getTimeDate12().substring(0, 4) + File.separator
				+ TimeTool.getTimeDate12().substring(4, 6) + File.separator + TimeTool.getTimeDate12().substring(6, 8)
				+ File.separator + TimeTool.getTimeDate12().substring(8, 10) + File.separator;
		return dirPath;
	}

	 
	public static File renameFile(File file, String filename) {
		String c = file.getParent();
		File mm = new File(c + File.separator + filename);
		if (!file.renameTo(mm)) {
			throw new RuntimeException("file rename fail!");
		}
		return mm;
	}

	 
	private static String getDirPath(String parameterKey) {
		String path = FarmParameterService.getInstance().getParameter(parameterKey);
		try {
			if (path.startsWith("WEBROOT")) {
				path = path.replace("WEBROOT",
						FarmParameterService.getInstance().getParameter("farm.constant.webroot.path"));
			}
			String separator = File.separator;
			if (separator.equals("\\")) {
				separator = "\\\\";
			}
			path = path.replaceAll("\\\\", "/").replaceAll("/", separator);
		} catch (Exception e) {
			log.warn(path + ":路径地址有误！");
			path = FarmParameterService.getInstance().getParameter(parameterKey);
		}
		return path;
	}

	 
	public static String getExName(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "none";
		}
	}

	 
	public static String getFileIdFromImgUrl(String urlStr) {
		String download_url = FarmParameterService.getInstance().getParameter("config.doc.download.url");// 在html中找到附件文件
		String img_url = FarmParameterService.getInstance().getParameter("config.doc.img.url");
		String fileid = null;
		if (urlStr.indexOf(img_url) >= 0) {
			if (urlStr.lastIndexOf("&") > 0) {
				String splits = urlStr.substring(urlStr.indexOf(img_url) + img_url.length(), urlStr.lastIndexOf("&"));
				fileid = splits;
			} else {
				String splits = urlStr.substring(urlStr.indexOf(img_url) + img_url.length());
				fileid = splits;
			}
		}
		// 为了适配旧的图片url
		if (urlStr.indexOf(download_url) >= 0) {
			if (urlStr.lastIndexOf("&") > 0) {
				String splits = urlStr.substring(urlStr.indexOf(download_url) + download_url.length(),
						urlStr.lastIndexOf("&"));
				fileid = splits;
			} else {
				String splits = urlStr.substring(urlStr.indexOf(download_url) + download_url.length());
				fileid = splits;
			}
		}
		return fileid;
	}

	 
	public static List<String> getFilesIdFromHtml(String html) {
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<>();
		if (html == null) {
			return list;
		}
		Document doc = Jsoup.parse(html);
		// 寻找图片
		for (Element node : doc.getElementsByTag("img")) {
			String urlStr = node.attr("src");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		// 寻找多媒体
		for (Element node : doc.getElementsByTag("embed")) {
			String urlStr = node.attr("src");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		// 寻找附件
		for (Element node : doc.getElementsByTag("a")) {
			String urlStr = node.attr("href");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		list.addAll(set);
		return list;
	}

	 
	public static void copyFile(File file, String newPath, String newFileName, String fileProcesskey) {
		int byteread = 0;
		File oldfile = file;
		if (oldfile.exists()) { 
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(file);
				if (newFileName == null) {
					newFileName = file.getName();
				}
				fs = new FileOutputStream(newPath + newFileName);
				byte[] buffer = new byte[1444];
				int readed = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					if (StringUtils.isNotBlank(fileProcesskey)) {
						readed = readed + buffer.length;
						int percentage = (int) (1.0 * readed / file.length() * 100);
						FileCopyProcessCache.setProcess(fileProcesskey, percentage);
					}
					fs.write(buffer, 0, byteread);
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			} finally {
				try {
					inStream.close();
					fs.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void copyFile(File file, File newfile, String fileProcesskey) {
		int byteread = 0;
		File oldfile = file;
		if (oldfile.exists()) { 
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(file);
				fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[1444];
				int readed = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					if (StringUtils.isNotBlank(fileProcesskey)) {
						readed = readed + buffer.length;
						int percentage = (int) (1.0 * readed / file.length() * 100);
						FileCopyProcessCache.setProcess(fileProcesskey, percentage);
					}
					fs.write(buffer, 0, byteread);
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			} finally {
				try {
					inStream.close();
					fs.close();
				} catch (IOException e) {
				}
			}
		}
	}

	 
	public static Long saveFile(InputStream inStream, String filename, String newPath) {
		int byteread = 0;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(newPath + filename);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e) {
			}
		}
		File file = new File(newPath + filename);
		return file.length();
	}

	 
	public static File getFormatImg(File file, ImgModel type, String prefix) {
		String plusName = "." + type.getFileIndex() + "." + prefix;
		return new File(file.getPath() + plusName);
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	private static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); 
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); 
		} catch (Exception e) {
			log.error(e + e.getMessage(), e);
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	private static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static long saveFile(byte[] fileData, String filename, String newPath) {
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(newPath + filename);
			fs.write(fileData);
			fs.flush();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
			}
		}
		File file = new File(newPath + filename);
		return file.length();
	}

	 
	public static String formatPathSeparator(String filePath) {
		filePath = filePath.replaceAll("/", File.separator.equals("/") ? "/" : "\\\\");
		return filePath;
	}

	 
	public static String readFileText(File file) {
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), EncodeUtils.getEncode(file.getPath()));
			reader = new BufferedReader(isr);
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sbf.append(tempStr + "\n");
			}
			reader.close();
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					isr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sbf.toString();
	}

	 
	public static void deleteFilesByDir(File imgsDir) {
		File[] files = imgsDir.listFiles();
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				f.delete();
			}
		}
	}
}
