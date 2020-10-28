package com.farm.parameter.dao;

import java.util.List;

import com.farm.parameter.domain.AloneDictionaryType;



public interface DictionaryTypeDaoInter  {
	 
	public List<AloneDictionaryType> getListByEntityId(String entityId);
	 
 	public void deleteEntity(AloneDictionaryType entity) ;
 	 
 	public void deleteEntityByTreecode(String entityId) ;
	 
	public AloneDictionaryType getEntity(String id) ;
	 
	public void insertEntity(AloneDictionaryType entity);
	 
	public int getAllListNum();
	 
	public void editEntity(AloneDictionaryType entity);
}
