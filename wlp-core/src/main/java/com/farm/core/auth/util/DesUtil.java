package com.farm.core.auth.util;


import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

 
public class DesUtil {

	 
	private static final String ALGORITHM = "DES";

	 
	private static final boolean IS_NET_KEY = false;

	 
	private static String PRIVATE_KEY;

	private static final String ENCODING = "UTF-8";

	 
	private Provider provider = null;
	private static DesUtil obj = null;

	public static DesUtil getInstance(String private_key) {
		if (obj == null) {
			obj = new DesUtil();
			PRIVATE_KEY = private_key;
		}
		return obj;
	}

	 
	private DesUtil() {
		this.provider = new BouncyCastleProvider();
	}

	 
	public DesUtil(Provider provider) {
		this.provider = provider;
	}

	 
	public byte[] encrypt(byte[] src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行加密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行加密");
		}
		try {
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM, this.provider);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	 
	public SecretKey generateKey() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(ALGORITHM, this.provider);

			// 修复SecureRandom在linux下使用相同的种子生成的随机数不同的问题
			// keyGenerator.init(new SecureRandom(privateKey.getBytes()));
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(PRIVATE_KEY.getBytes());
			keyGenerator.init(sr);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	 
	public byte[] encrypt(String src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行加密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行加密");
		}
		return encrypt(src.getBytes(), key);
	}

	 
	public String encryptString(String src, Key key) {
		return encryptString(src, key, null);
	}

	 
	public String encryptString(String src) {
		return encryptString(src, IS_NET_KEY);
	}

	 
	public String encryptString(String src, boolean isDotNet) {
		if (!isDotNet) {
			return encryptString(src, this.generateKey(), null);
		} else {
			return DesNetHelper.encryptString(src);
		}
	}

	 
	public String encryptString(String src, Key key, String encoding) {
		// 调用DES进行加密
		byte[] passbyties = encrypt(src, key);
		// 进行Base64编码
		String passText = Base64.encodeBase64String(passbyties);
		// 转码操作
		if (null != encoding && !"".equals(encoding)) {
			try {
				passText = new String(passText.getBytes(), encoding);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return passText;
	}

	 
	public byte[] decrypt(byte[] src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行解密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行解密");
		}
		try {
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM, this.provider);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 现在，获取数据并解密
			// 正式执行解密操作
			return cipher.doFinal(src);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	 
	public byte[] decrypt(String data, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行解密");
		}
		if (data == null) {
			throw new IllegalArgumentException("不能对空数据进行解密");
		}
		return decrypt(data.getBytes(), key);
	}

	 
	public String decryptString(String data, Key key, String encoding) {
		// 进行转码
		if (null != encoding && !"".equals(encoding)) {
			try {
				data = new String(data.getBytes(), encoding);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		// 进行Base64编码解密
		byte[] btdate = Base64.decodeBase64(data);
		// 进行DES解密
		byte[] textPass = decrypt(btdate, key);
		return new String(textPass);
	}

	 
	public String decryptString(String data, Key key) {
		return decryptString(data, key, null);
	}

	public String decryptString(String data) {
		if (!IS_NET_KEY) {
			return decryptString(data, this.generateKey());
		} else {
			return DesNetHelper.decryptString(data);
		}
	}

	 
	public static class DesNetHelper {

		 
		public static String toHexString(byte b[]) {
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				String plainText = Integer.toHexString(0xff & b[i]);
				if (plainText.length() < 2)
					plainText = "0" + plainText;
				hexString.append(plainText);
			}
			return hexString.toString();
		}

		 
		public static byte[] encrypt(String message, String key) throws Exception {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(message.getBytes(ENCODING));
		}

		 
		public static String decrypt(String message, String key) throws Exception {

			byte[] bytesrc = convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		}

		 
		public static byte[] convertHexString(String ss) {
			byte digest[] = new byte[ss.length() / 2];
			for (int i = 0; i < digest.length; i++) {
				String byteString = ss.substring(2 * i, 2 * i + 2);
				int byteValue = Integer.parseInt(byteString, 16);
				digest[i] = (byte) byteValue;
			}

			return digest;
		}

		 
		public static String encryptString(String data) {
			try {
				String jiami = java.net.URLEncoder.encode(data, ENCODING).toLowerCase();
				return toHexString(encrypt(jiami, PRIVATE_KEY)).toUpperCase();
			} catch (Exception ex) {
				throw new RuntimeException("加密[" + data + "]失败！");
			}
		}

		 
		public static String decryptString(String data) {
			try {
				return java.net.URLDecoder.decode(decrypt(data, PRIVATE_KEY), ENCODING);
			} catch (Exception ex) {
				throw new RuntimeException("解密[" + data + "]失败！");
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		DesUtil helper = DesUtil.getInstance("WCPDOC");
		System.out.println("加密后：" + helper.encryptString("sys222admin"));
		System.out.println("加密后：" + helper.encryptString("OBLIGATEKEY"));
		System.out.println("解密后：" +  helper.decryptString("0IY9ndJZ5Pbsv6dGK5hVQQ=="));

		 
	}
}
