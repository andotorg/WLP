package com.farm.web.easyui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

 
public class EasyUiUtils {
	 
	private static String EDITOR_TYPE_TEXT = "text";
	 
	private static String EDITOR_TYPE_SELECT = "numberbox";
	 
	@SuppressWarnings("unused")
	private static String EDITOR_TYPE_CHECKBOX = "{ \"type\":\"checkbox\", \"options\":{\"on\":true,\"off\":false} }";
	 
	@SuppressWarnings("unused")
	private static String EDITOR_TYPE_NUMBERBOX = "numberbox";

	public static Map<String, Object> formatGridData(DataResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", result.getTotalSize());
		map.put("rows", result.getResultList());
		return map;
	}

	 
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> formatPropertygridData(
			List<Map<String, Object>> list, String nameIndex,
			String valueIndex, String GroupIndex, String editorType,
			String ruleIndex, String idIndex) {
		// [{"name":"Name","value":"Bill Smith","group":"ID Settings","editor":"text"}]
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Map<String, Object> easyMap = new HashMap<String, Object>();
			easyMap.put("name", map.get(nameIndex).toString());
			easyMap.put("value", map.get(valueIndex).toString());
			easyMap.put("group", map.get(GroupIndex).toString());
			easyMap.put("id", map.get(idIndex).toString());
			// 1是文本2是枚举
			Object editor = EDITOR_TYPE_TEXT;
			if ("2".equals(map.get(editorType).toString())) {
				editor = EDITOR_TYPE_SELECT;
			}
			if ("3".equals(map.get(editorType).toString())) {
				if(map.get(ruleIndex)!=null){
					editor = getComboboxOptions(map.get(ruleIndex).toString());
				}
			}
			if ("4".equals(map.get(editorType).toString())) {
				Map<String, Object> type = new HashMap<String, Object>();
				type.put("type", "checkbox");
				Map<String, Object> type2 = new HashMap<String, Object>();
				type2.put("on", true);
				type2.put("off", false);
				type.put("options", type2);
				editor = type;
				// { "type":"checkbox", "options":{"on":true,"off":false} }
			}
			easyMap.put("editor", editor);
			returnList.add(easyMap);
		}

		return returnList;
	}

	 
	private static Map<String, Object> getComboboxOptions(String Rules) {
		// "editor":{
		// "type":"combobox",
		// "options":{
		// "data":[{"value":1,"text":"一"},{"value":2,"text":"二"}],
		// "panelHeight":"auto"
		// }
		Map<String, Object> map1 = new HashMap<String, Object>();
		// ---------------------------------------
		String[] ruleArray = Rules.split(",");
		List<Map<String, Object>> ruleList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ruleArray.length; i++) {
			Map<String, Object> nodeMap = new HashMap<String, Object>();
			nodeMap.put("value", ruleArray[i]);
			nodeMap.put("text", ruleArray[i]);
			ruleList.add(nodeMap);
		}
		Map<String, Object> mapOptions = new HashMap<String, Object>();
		mapOptions.put("data", ruleList);
		mapOptions.put("panelHeight", "auto");
		map1.put("type", "combobox");
		map1.put("options", mapOptions);
		return map1;
	}

	 
	public static List<EasyUiTreeNode> formatAjaxTree(
			List<Map<String, Object>> result1, String parentIdIndex,
			String idIndex, String titleIndex, String checkIndex) {
		return EasyUiTreeNode.formatAjaxTree(result1, parentIdIndex, idIndex,
				titleIndex, checkIndex, null);
	}

	 
	public static List<EasyUiTreeNode> formatAjaxTree(
			List<Map<String, Object>> result1, String parentIdIndex,
			String idIndex, String titleIndex) {
		return EasyUiTreeNode.formatAjaxTree(result1, parentIdIndex, idIndex,
				titleIndex);
	}

	 
	public static List<EasyUiTreeNode> formatAsyncAjaxTree(
			List<Map<String, Object>> result1,
			List<Map<String, Object>> result2, String parentIdIndex,
			String idIndex, String titleIndex, String imgIndex) {
		return EasyUiTreeNode.formatAsyncAjaxTree(result1, result2,
				parentIdIndex, idIndex, titleIndex, imgIndex);
	}

	 
	public static DataQuery formatGridQuery(HttpServletRequest request,
			DataQuery query) {
		// page=1&rows=10&sort=TITLE&order=asc
		if (query == null) {
			query = new DataQuery();
		}
		if (request.getParameter("page") != null) {
			query.setCurrentPage(request.getParameter("page").toString());
		}
		if (request.getParameter("rows") != null) {
			query.setPagesize(Integer.valueOf(request.getParameter("rows")));
		}
		if (request.getParameter("sort") != null) {
			query.addSort(new DBSort(request.getParameter("sort"), request
					.getParameter("order")));
		}
		return query;
	}
}
