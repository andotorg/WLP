package com.farm.wcp.ekca;

import java.util.ArrayList;
import java.util.List;


 
public class PointEventHandle {
	 
	public enum POINT_HANDLE {
		 
		defaultAll("每次触发"),  
		operatorByDay("该操作每日触发一次"),  
		operatorByObj("相同操作人和所属操作对象只触发一次");
		private String title;
		

		public String getName() {
			return this.name();
		}

		private POINT_HANDLE(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	}

	private List<POINT_HANDLE> list = new ArrayList<>();

	public static PointEventHandle getInctance() {
		PointEventHandle obj = new PointEventHandle();
		obj.list.clear();
		return obj;
	}

	public PointEventHandle add(POINT_HANDLE event_handle) {
		list.add(event_handle);
		return this;
	}

	public List<POINT_HANDLE> asList() {
		return list;
	}

}
