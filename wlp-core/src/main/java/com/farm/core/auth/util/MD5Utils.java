package com.farm.core.auth.util;

public class MD5Utils {

	public static String encodeMd5(String password) {
		if (isMd5code(password)) {
			return password;
		} else {
			return new MD5().getMD5ofStr(password);
		}
	}

	public static boolean isMd5code(String password) {
		if (password.trim().length() == 32)
			return true;
		else
			return false;
	}
}
