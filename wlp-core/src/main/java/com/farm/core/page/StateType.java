package com.farm.core.page;

 
public enum StateType {
	 
	SUCCESS(0),  
	ERROR(1);
	public int value;

	private StateType(int var) {
		value = var;
	}
}
