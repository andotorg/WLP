package com.farm.parameter.service;

import java.util.List;
import java.util.Map;

import com.farm.parameter.domain.AloneParameter;
import com.farm.parameter.exception.KeyExistException;
import com.farm.core.auth.domain.LoginUser;

public interface ParameterServiceInter {
	public final String CURRENCY_YES = "0";
	public final String CURRENCY_USER = "1";
	public final String TYPE_GENERAL = "0";
	public final String TYPE_DECORDE = "1";
	public final String TYPE_MD5 = "2";

	 
	public void setValue(String key, String pValue, LoginUser aloneUser);

	 
	public void setUserValue(String key, String pValue, LoginUser aloneUser);

	 
	public List<Map<String, Object>> getAllParameters();

	 
	public List<Map<String, Object>> getUserParameters(String userid);

	 
	public String getValue(String key, String userId);

	public String getValue(String key);

	// -----------------------------------------------------------------------------

	 
	public AloneParameter insertEntity(AloneParameter entity,  LoginUser aloneUser)
			throws KeyExistException;

	 
	public void deleteEntity(String parameterId, LoginUser user);

	 
	public AloneParameter editEntity(AloneParameter entity, LoginUser aloneUser);

	 
	public boolean refreshCache();

	 
	public List<Map<String, Object>> getTransParamList(String domainType);

	 
	public boolean isRepeatKey(String paramKey, String excludeParamId);

	 
	public AloneParameter findEntityByKey(String paramKey);

	 
	public AloneParameter getEntity(String parameterId);

	 
	public Map<String, Object> getCacheInfo();

	 
	public void flashAllCache();

	 
	public void loadXmlParasToDatabase(LoginUser aloneUser );

	 
	public int getTableDateSize(String tablename);

}
