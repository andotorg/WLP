package com.farm.core.sql.result;

import java.util.Map;

 
public interface ResultsHandle {
	 
	public void handle(Map<String, Object> row);
}
