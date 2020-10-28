package com.farm.parameter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


 
public class ZipUtils {
	private static final Logger log = Logger.getLogger(ZipUtils.class);
	 
	private File targetFile;

	private ZipUtils() {
	}

	private ZipUtils(File target) {
		targetFile = target;
		if (targetFile.exists())
			targetFile.delete();
	}

	 
	private void zipFiles(File srcfile) {

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(targetFile));

			if (srcfile.isFile()) {
				zipFile(srcfile, out, "");
			} else {
				File[] list = srcfile.listFiles();
				for (int i = 0; i < list.length; i++) {
					compress(list[i], out, "");
				}
			}
			log.info("文件压缩成功！");
		} catch (Exception e) {
			log.error("文件压缩失败："+e.getMessage());
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error("文件压缩失败："+e.getMessage());
			}
		}
	}

	 
	private void compress(File file, ZipOutputStream out, String basedir) {
		 
		if (file.isDirectory()) {
			this.zipDirectory(file, out, basedir);
		} else {
			this.zipFile(file, out, basedir);
		}
	}

	 
	private void zipFile(File srcfile, ZipOutputStream out, String basedir) {
		if (!srcfile.exists())
			return;

		byte[] buf = new byte[1024];
		FileInputStream in = null;

		try {
			int len;
			in = new FileInputStream(srcfile);
			out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			log.error("文件压缩失败："+e.getMessage());
		} finally {
			try {
				if (out != null)
					out.closeEntry();
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error("文件压缩失败："+e.getMessage());
			}
		}
	}

	 
	private void zipDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			 
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	 
	public static File exportZipFile(File ofiledir, File tfile) {
		new ZipUtils(tfile).zipFiles(ofiledir);
		return tfile;
	}

}