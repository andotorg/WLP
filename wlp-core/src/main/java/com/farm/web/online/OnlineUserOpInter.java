package com.farm.web.online;

import java.util.HashMap;
import java.util.Map;

import com.farm.core.sql.result.DataResult;

 
public interface OnlineUserOpInter {
	 
	static final Map<String, Map<String, Object>> onlineUserTable = new HashMap<String, Map<String, Object>>();
	 
	static final String key_TIME = "TIME";
	 
	static final String key_IP = "IP";
	 
	static final String key_LNAME = "LNAME";
	 
	static final String key_USEROBJ = "USEROBJ";
	 
	static final String key_LOGINTIME = "LOGINTIME";
	
	 
	static final String key_STARTTIME = "STARTTIME";
	
	 
	static final String key_VISITTIME = "VISITTIME";
	 
	static final long onlineVilaMinute = 20;
	 
	static final long usersMaxSize = 1000;

	 
	public void userVisitHandle();

	 
	public DataResult findOnlineUser();

}
