package com.farm.core.page;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

 
public class RequestMode implements java.io.Serializable {
	 
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(RequestMode.class);
	private Map<String, String> data;
	 
	private int operateType;
	 
	private String message = "";
	 
	private String ids;
	 
	private String currentKeyid;

	// ------------------------------------------------------------------------------

	 
	public String getCurrentKeyid() {
		return currentKeyid;
	}

	 
	public void putParameters(String key, String val) {
		if (this.data == null) {
			this.data = new HashMap<String, String>();
		}
		this.data.put(key, val);
	}

	 
	public String getParameters(String key) {
		return this.data.get(key);
	}

	 
	public void setCurrentKeyid(String currentKeyid) {
		this.currentKeyid = currentKeyid;
	}

	public RequestMode() {

	}

	public RequestMode(OperateType operate) {
		operateType = operate.value;
	}

	public RequestMode(OperateType operate,  String message) {
		operateType = operate.value;
		this.message = message;
	}

	 
	public static RequestMode initPageSet(RequestMode pageSet,
			OperateType operateType, Exception e) {
		if (pageSet == null) {
			pageSet = new RequestMode(OperateType.OTHER);
		}
		if (e != null) {
			pageSet.setMessage(e.getMessage());
			log.error(pageSet.getMessage());
		}
		if (operateType != null) {
			pageSet.setOperateType(operateType.value);
		}
		return pageSet;
	}



	 
	public void SetVar(OperateType operateType) {
		if (operateType != null)
			this.operateType = operateType.value;
	}

	 
	public void SetVar(OperateType operateType,
			String message) {
		if (operateType != null)
			this.operateType = operateType.value;
		if (message != null)
			this.message = message;
	}

	 
	public String getMessage() {
		return message;
	}

	 
	public void setMessage(String message) {
		this.message = message;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}


	 
	public String getIds() {
		return ids;
	}

	 
	public void setIds(String ids) {
		this.ids = ids;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
