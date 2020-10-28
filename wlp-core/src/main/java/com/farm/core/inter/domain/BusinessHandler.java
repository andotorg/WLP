package com.farm.core.inter.domain;

import java.util.Map;

 
public class BusinessHandler {
	 
	private Object beanImpl;
	 
	private Map<String, String> context;

	 
	private String id;
	
	private String title;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Object getBeanImpl() {
		return beanImpl;
	}

	public void setBeanImpl(Object beanImpl) {
		this.beanImpl = beanImpl;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

}
