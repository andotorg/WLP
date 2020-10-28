package com.farm.core.page;

 
public enum OperateType {
	 
	SHOW(0),  
	ADD(1),  
	UPDATE(2),
	 
	DEL(4),
	 
	OTHER(3);
	public int value;

	private OperateType(int var) {
		value = var;
	}

	 
	public static OperateType getEnum(int type) {
		if (type == 0)
			return OperateType.SHOW;
		if (type == 1)
			return OperateType.ADD;
		if (type == 2)
			return OperateType.UPDATE;
		return null;
	}
}
