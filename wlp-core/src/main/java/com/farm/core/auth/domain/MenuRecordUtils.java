package com.farm.core.auth.domain;

import java.util.Set;

 
public class MenuRecordUtils {
	 
	public static boolean contains(WebMenu menu, Set<MenuRecord> records) {
		for (MenuRecord record : records) {
			if ((menu.getId() != null && record.getId() != null && menu.getId().equals(record.getId()))
					|| menu.getUrl() != null && record.getKey() != null && menu.getUrl().equals(record.getKey())) {
				return true;
			}
		}
		return false;
	}

	 
	public static boolean contains(String uri, Set<MenuRecord> records) {
		for (MenuRecord record : records) {
			if (uri != null && record.getKey() != null && uri.equals(record.getKey())) {
				return true;
			}
		}
		return false;
	}

}
