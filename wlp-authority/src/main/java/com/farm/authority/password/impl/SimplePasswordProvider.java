package com.farm.authority.password.impl;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.farm.authority.password.PasswordProviderInter;
import com.farm.core.auth.util.MD5Utils;

 
public class SimplePasswordProvider implements PasswordProviderInter {
	private final static Logger log = Logger.getLogger(SimplePasswordProvider.class);

	@Override
	public String getDBPasswordByPlaint(String loginname, String plaintextPassword) {
		return encodeLoginPasswordOnMd5(plaintextPassword, loginname);
	}

	@Override
	public String getDBPasswordByClient(String loginname, String clientPassword) {
		if (!isBase64(clientPassword)) {
			throw new RuntimeException("the password is not client encode!");
		}
		clientPassword = decodeJsPassword(clientPassword);
		return encodeLoginPasswordOnMd5(clientPassword, loginname);
	}

	 
	private String encodeLoginPasswordOnMd5(String password, String loginName) {
		return MD5Utils.encodeMd5(password + loginName);
	}

	 
	public String decodeJsPassword(String password) {
		Base64 base64 = new Base64();
		try {
			password = new String(base64.decode(password), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.warn("前台密码解码错误", e);
		}
		return password;
	}

	 
	public String encodeJsPassword(String password) {
		Base64 base64 = new Base64();
		try {
			byte[] textByte = password.getBytes("UTF-8");
			password = base64.encodeToString(textByte);
		} catch (UnsupportedEncodingException e) {
			log.warn("模拟前台密码编码错误", e);
		}
		return password;
	}

	@Override
	public String getClientPassword(String plaintextPassword) {
		return encodeJsPassword(plaintextPassword);
	}

	private static boolean isBase64(String str) {
		String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		return Pattern.matches(base64Pattern, str);
	}

}
