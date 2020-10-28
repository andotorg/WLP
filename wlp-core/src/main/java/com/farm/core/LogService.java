package com.farm.core;

 
public interface LogService {

	 
	public void log(String info, String loginUserId, String level,
			String methodName, String className, String ip);

}
