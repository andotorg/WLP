package com.farm.sfile.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMd5EncodeUtils {

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		File file = new File("D:\\test\\smallFile250m.rar");
		// File file = new File("E:/拉鲁斯法汉双解词典(12新)/制作文件库/其他/SJ00040936
		// 拉鲁斯法汉双解词典(内文排版).zip");
		String md5 = calcMD5(file);
		long endTime = System.currentTimeMillis();
		System.out.println("MD5:" + md5 + "\n 耗时:" + ((endTime - beginTime) / 1000) + "s");
	}

	 
	public static String calcMD5(File file) {
		InputStream stream = null;
		try {
			stream = Files.newInputStream(file.toPath(), StandardOpenOption.READ);
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] buf = new byte[8192];
			int len;
			while ((len = stream.read(buf)) > 0) {
				digest.update(buf, 0, len);
			}
			return toHexString(digest.digest());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String toHexString(byte[] data) {
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

}
