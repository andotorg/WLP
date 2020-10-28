package com.farm.util.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

 
public class WebVisitBuff {
	private static Map<String, WebVisitBuff> BUFF_S = new HashMap<String, WebVisitBuff>();
	private Set<String> pool = new HashSet<String>();
	private int max = 0;

	 
	public static WebVisitBuff getInstance(String domain, int maxNum) {
		if (BUFF_S.get(domain) == null) {
			WebVisitBuff ob = new WebVisitBuff();
			BUFF_S.put(domain, ob);
		}
		BUFF_S.get(domain).max = maxNum;
		return BUFF_S.get(domain);
	}

	 
	public boolean canVisite(String key) {
		if (pool.size() > max) {
			pool.clear();
		}
		if (pool.contains(key)) {
			return false;
		} else {
			pool.add(key);
			return true;
		}
	}
}
