package com.farm.parameter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.springframework.stereotype.Service;


@Service
public class ConstantVarService {
	private static Map<String, String> constant = new HashMap<String, String>();

	public static void registConstant(String key, String value) {
		constant.put(key,value);
	}

	public static List<Entry<String, String>> getEntrys() {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (Entry<String, String> node : constant.entrySet()) {
			list.add(node);
		}
		return list;
	}

	public static String getValue(String key) {
		return constant.get(key);
	}

}
