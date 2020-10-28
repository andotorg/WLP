package com.farm.core.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

 
public class AppConfig {
	private static final String BUNDLE_NAME = "config/config"; 
	private static final Logger log = Logger.getLogger(AppConfig.class);
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private AppConfig() {
	}

	 
	public static String getString(String key) {
		try {
			String messager = RESOURCE_BUNDLE.getString(key);
			return messager;
		} catch (MissingResourceException e) {
			String messager = "不能在配置文件" + BUNDLE_NAME + "中发现参数：" + '!' + key + '!';
			log.error(messager);
			throw new RuntimeException(messager);
		}
	}

	 
	public static boolean getBoolean(String string) {
		try {
			String messager = RESOURCE_BUNDLE.getString(string);
			return messager.trim().toUpperCase().equals("TRUE");
		} catch (MissingResourceException e) {
			log.warn(e.getMessage(), e);
			return false;
		}
	}
}
