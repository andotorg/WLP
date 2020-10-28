package com.farm.core.auth.domain;

public interface AuthKey {
	 
	public boolean isLogin();

	 
	public boolean isCheck();

	 
	public boolean isUseAble();

	 
	public String getTitle();

	 
	public String getKey();

	 
	public boolean isGroupKey();
}
