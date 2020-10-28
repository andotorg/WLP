package com.farm.sfile.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

 
public class FileCopyProcessCache {
	private static List<Map<String, Integer>> cache = new ArrayList<>();

	private static int curentCacheNum = 0;
	private static int catchSize = 5;

	 

	public static void setProcess(String key, Integer percentage) {
		if (cache.size() <= 0) {
			cache.add(new HashMap<String, Integer>());
			cache.add(new HashMap<String, Integer>());
		}
		if (cache.get(curentCacheNum).size() > catchSize) {
			if (curentCacheNum == 1) {
				curentCacheNum = 0;
			} else {
				curentCacheNum = 1;
			}
			cache.get(curentCacheNum).clear();
		}
		cache.get(curentCacheNum).put(key, percentage);
	}

	public static Integer getProcess(String key) {
		if (StringUtils.isBlank(key)) {
			return 100;
		}
		Integer process = cache.get(0).get(key);
		if (process == null) {
			process = cache.get(1).get(key);
		}
		return process;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1000; i++) {
			for (int n = 0; n < 10; n++) {
				Thread.sleep(100);
				String key = "key" + i;
				System.out.println("key=" + key);
				FileCopyProcessCache.setProcess(key, n);
				System.out.println("in cache：" + curentCacheNum + "cache size:" + cache.size() + ",cache0 size:"
						+ cache.get(0).size() + ",cache1 size:" + cache.get(1).size());
				System.out.println(FileCopyProcessCache.getProcess(key));
			}
		}
	}
}
