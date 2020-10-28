package com.farm.parameter.dao;

import java.util.List;

import com.farm.parameter.domain.AloneDictionaryEntity;



public interface DictionaryEntityDaoInter  {
	 
 	public void deleteEntity(AloneDictionaryEntity  entity) ;
	 
	public AloneDictionaryEntity  getEntity(String id) ;
	 
	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity  entity);
	 
	public int getAllListNum();
	 
	public void editEntity(AloneDictionaryEntity  entity);
	
	 
	public List<AloneDictionaryEntity> findEntityByKey(String key);
	
	 
	public List<AloneDictionaryEntity> findEntityByKey(String key, String exId);
	 
	public List<AloneDictionaryEntity> getAllEntity();
}
