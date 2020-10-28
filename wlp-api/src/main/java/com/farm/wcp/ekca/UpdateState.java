package com.farm.wcp.ekca;

import java.io.Serializable;

 
public class UpdateState implements Serializable {
	private String val;
	 
	private static final long serialVersionUID = -4241189109674676843L;

	public UpdateState(String val) {
		this.val = val;
	}

	 
	public static UpdateState getDisabled() {
		return new UpdateState("0");
	}

	 
	public static UpdateState getAble() {
		return new UpdateState("1");
	}

	 
	public static UpdateState getAuditing() {
		return new UpdateState("3");
	}

	 
	public static UpdateState getDelete() {
		return new UpdateState("2");
	}

	 
	public static UpdateState getComplete() {
		return new UpdateState("4");
	}

	public String getVal() {
		return val;
	}

}
