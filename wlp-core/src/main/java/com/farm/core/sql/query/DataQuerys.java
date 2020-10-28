package com.farm.core.sql.query;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

 
public class DataQuerys {
	static final Logger log = Logger.getLogger(DataQuerys.class);

	 
	public static void wipeVirus(String var) {
		if (var != null) {
			// 防止sql注入的字符转义
			var = StringEscapeUtils.escapeSql(var);
			if (sql_inj(var)) {
				throw new RuntimeException(var + "违反SQL注入风险约束！");
			}
		}
	}

	private static boolean sql_inj(String str) {
		String inj_str = "'| and | exec | insert | select | delete | update | count |*|%| chr | mid | master | truncate | char | declare |;| or |+|,";
		// 这里的东西还可以自己添加
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			String charstr = inj_stra[i];
			if (str.indexOf(charstr) >= 0) {
				log.error(str + "违反SQL注入风险约束:" + inj_stra[i]);
				System.out.println(str + "违反SQL注入风险约束:" + inj_stra[i]);
				return true;
			}
		}
		return false;
	}

	public static void wipeVirusNoDot(String var) {
		// 防止sql注入的字符转义
		var = StringEscapeUtils.escapeSql(var);
		if (sql_injNoDot(var)) {
			throw new RuntimeException(var + "违反SQL注入风险约束！");
		}
	}

	private static boolean sql_injNoDot(String str) {
		String inj_str = "'| and | exec | insert | select | delete | update | count |*|%| chr | mid | master | truncate | char | declare |;| or |+";
		// 这里的东西还可以自己添加
		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				log.error(str + "违反SQL注入风险约束:" + inj_stra[i]);
				return true;
			}
		}
		return false;
	}

	 
	public static String getWhereInSubVals(List<String> ids) {
		String where = null;
		for (String id : ids) {
			if (where == null) {
				where = "'" + id + "'";
			} else {
				where = where + ",'" + id + "'";
			}
		}
		return where;
	}
}
