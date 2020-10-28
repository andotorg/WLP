package com.farm.parameter.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.farm.parameter.domain.AloneDictionaryEntity;
import com.farm.core.auth.domain.LoginUser;

public interface DictionaryEntityServiceInter {

	public void deleteEntity(String entity, LoginUser user);

	public AloneDictionaryEntity editEntity(AloneDictionaryEntity entity,
			LoginUser user);

	public AloneDictionaryEntity getEntity(String id);

	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity entity,
			LoginUser user);

	 
	public boolean validateIsRepeatKey(String key, String exId);

	 
	public void editComments(String id);

	 
	public List<AloneDictionaryEntity> getAllEntity();

	 
	public Map<String, String> getDictionary(String key);

	 
	public List<Entry<String, String>> getDictionaryList(String key);

	 
	public String getDicKey(String dicId);

}
