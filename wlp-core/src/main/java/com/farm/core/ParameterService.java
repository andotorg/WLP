package com.farm.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

 
public interface ParameterService {

	 
	public String getParameter(String key);

	 
	public int getParameterInt(String key);

	public Float getParameterFloat(String key);

	 
	public boolean getParameterBoolean(String key);

	 
	public String getParameter(String key, String userId);

	 
	public List<Entry<String, String>> getDictionaryList(String index);

	 
	public Map<String, String> getDictionary(String key);

	 
	public List<String> getParameterStringList(String key);
	
	 
	public long getParameterLong(String key);

}
