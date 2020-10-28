package com.farm.sfile.utils;

public class FileNameSortUtil {
	private static int getNum(String sb) {
		if (sb == null) {
			return 0;
		}
		if ("".equals(sb)) { 
			return 0;
		}
		try{
			return Integer.valueOf(sb);
		}catch(Exception e){
			e.printStackTrace();
			return Integer.valueOf(99);
		}
	}

	private static boolean isNum(char c) {
		boolean b = false;
		if (c >= '0' && c <= '9') {
			b = true;
		}
		return b;
	}
	 
	public static int compareTo(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int lim = Math.min(len1, len2);
		char v1[] = s1.toCharArray();
		char v2[] = s2.toCharArray();
		char int1[] = null;
		char int2[] = null;

		int k = 0;
		// 外层循环用于遍历两个字符串
		while (k < lim) {
			char c1 = v1[k];
			char c2 = v2[k];
			// 如果遍历到两个字符串相同位置都是数字, 那么就要去把这个数字后面连续的数字都挖出来
			if (isNum(c1) && isNum(c2)) {
				int1 = new char[len1 - k];
				int2 = new char[len2 - k];
				int count1 = 0;
				int count2 = 0;
				int n1, n2;

				// 此循环用于挖出 s1 数字, 依次放到预先的int1[] 数组中
				for (int i = k; i < len1; i++) {
					if (isNum(v1[i])) {
						int1[count1] = v1[i];
					} else {
						break;
					}
					count1++;
				} 
				for (int i = k; i < len2; i++) {
					if (isNum(v2[i])) {
						int2[count2] = v2[i];
					} else {
						break;
					}
					count2++;
				}
				n1 = getNum(String.valueOf(int1).trim());
				n2 = getNum(String.valueOf(int2).trim());

				// 需要判断n1 和 n2 是否相等, 如果相等, 则先把 k 移位, 然后continue
				if (n1 == n2) {
					k += int1.length;
					continue;
				} 
				return n1 - n2;
			} 
			if (c1 != c2) {
				return c1 - c2;
			}
			k++;
		}
		return len1 - len2;
	}
}
