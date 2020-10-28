package com.farm.core.time;

import org.apache.log4j.Logger;


 
public class TimeStopWatch {
	private long startTime;
	private long endTime;
	static final Logger log = Logger.getLogger(TimeStopWatch.class);
	 
	public static TimeStopWatch start() {
		TimeStopWatch tr = new TimeStopWatch();
		tr.startTime = System.currentTimeMillis();
		return tr;
	}

	 
	public TimeStopWatch endPrintInfo(String showflag) {
		endTime = System.currentTimeMillis(); 
		log.info(showflag+"-程序运行时间：" + (endTime - startTime) + "ms");
		return this;
	}

	 
	public long getEndTimes() {
		endTime = System.currentTimeMillis(); 
		return endTime - startTime;
	}
}
