package com.farm.core.sql.query;

import java.util.ArrayList;
import java.util.List;

 
public class DBRuleList {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private List<DBRule> list = new ArrayList<DBRule>();
	private DBRuleList(){}
	
	public static DBRuleList getInstance(){
		return new DBRuleList();
	}

	public DBRuleList add(DBRule rule) {
		list.add(rule);
		return this;
	}

	public List<DBRule> toList() {
		return list;
	}
}
