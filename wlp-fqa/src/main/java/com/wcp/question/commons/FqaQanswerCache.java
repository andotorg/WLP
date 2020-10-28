package com.wcp.question.commons;

import java.util.LinkedList;
import java.util.Queue;

 
public class FqaQanswerCache {

	private Queue<String> queue = new LinkedList<>();
	private static final int maxNum = 10000;
	private static final FqaQanswerCache cache = new FqaQanswerCache();

	public static FqaQanswerCache getInstance() {
		return cache;
	}

	public synchronized boolean add(String userId, String qanswerid) {
		String key = userId + qanswerid;
		if (queue.contains(key)) {
			return false;
		}
		if (queue.size() >= maxNum) {
			queue.poll();
		}
		queue.add(key);
		return true;
	}
}
