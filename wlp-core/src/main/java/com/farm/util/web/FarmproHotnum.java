package com.farm.util.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.farm.core.time.TimeTool;

 
public class FarmproHotnum {
	static final Logger log = Logger.getLogger(FarmproHotnum.class);

	 
	public static int getHotnum(String date12, Integer hotNum,
			Integer visitNum, int hotWeight) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		int minuteMinus = 0;
		try {
			minuteMinus = TimeTool.countMinuteMinus(sdf.parse(date12.substring(
					0, 12)), new Date());
		} catch (ParseException e) {
			log.error(e + e.getMessage());
		}
		// 本期得分 = 上一期得分 × exp(-(冷却系数) × 间隔的小时数) + 本期本期票数
		return Integer.valueOf(String.valueOf(Math.round(hotNum
				* Math.exp(-((float) hotWeight / 100) * minuteMinus / 60))))
				+ visitNum * 10;
	}

}
