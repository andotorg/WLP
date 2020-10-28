package com.farm.parameter.dao;

import java.util.List;
import java.util.Map;

import com.farm.parameter.domain.AloneParameter;

public interface ParameterDaoInter {
	 
	public void deleteEntity(AloneParameter entity);

	 
	public AloneParameter getEntity(String id);

	public List<AloneParameter> getAllEntity();

	 
	public void insertEntity(AloneParameter entity);

	 
	public int getAllListNum();

	 
	public void editEntity(AloneParameter entity);

	 
	public List<Map<String, Object>> getListByDomainType(String domainType);

	 
	public List<AloneParameter> findListByKey(String paramKey);

	 
	public List<AloneParameter> findListByKeyAndExcludeParamId(String paramKey, String excludeParamId);

	 
	public AloneParameter getEntityByKey(String key);

	 
	public int getTableDateSize(String domain);
}
