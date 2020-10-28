package com.farm.web.easyui;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.core.auth.domain.WebMenu;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;

 
public class EasyUiTreeNode implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(EasyUiTreeNode.class);
	private String id;
	private String text;
	private String state = "open";
	private String iconCls;
	private String url;
	private String para;
	private boolean checked;
	private List<EasyUiTreeNode> children = new ArrayList<EasyUiTreeNode>();

	public EasyUiTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public EasyUiTreeNode(String id, String text, String state) {
		this.id = id;
		this.text = text;
		if (state != null) {
			this.state = state;
		}
	}

	public EasyUiTreeNode(String id, String text, String state, String iconCls) {
		this.id = id;
		this.text = text;
		if (state != null) {
			this.state = state;
		}
		if (iconCls != null && iconCls.trim().length() > 0) {
			this.iconCls = iconCls;
		}
	}

	public EasyUiTreeNode(String id, String text, String state, String iconCls,
			String url, String params) {
		this.id = id;
		this.text = text;
		if (state != null) {
			this.state = state;
		}
		if (url != null) {
			this.url = url;
		}
		if (params != null) {
			this.para = params;
		}
		if (iconCls != null && iconCls.trim().length() > 0) {
			this.iconCls = iconCls;
		}
	}

	public EasyUiTreeNode(String id, String text, String state, boolean checked) {
		this.id = id;
		this.text = text;
		if (state != null) {
			this.state = state;
		}
		this.checked = checked;
	}

	 
	public static List<EasyUiTreeNode> addNode_initState(
			List<EasyUiTreeNode> nodeList, String parentId, String id,
			String text, String state, String icon, String url, String params) {
		if (nodeList == null) {
			nodeList = new ArrayList<EasyUiTreeNode>();
		}
		EasyUiTreeNode oNode = findNode(nodeList, parentId);
		if (oNode == null) {
			nodeList
					.add(new EasyUiTreeNode(id, text, state, icon, url, params));
		} else {
			oNode.setState("closed");
		}
		return nodeList;
	}

	 
	public static List<EasyUiTreeNode> addNode_Standard(
			List<EasyUiTreeNode> nodeList, String parentId, String id,
			String text, String state) {
		if (nodeList == null) {
			nodeList = new ArrayList<EasyUiTreeNode>();
		}
		EasyUiTreeNode oNode = findNode(nodeList, parentId);
		if (oNode == null) {
			nodeList.add(new EasyUiTreeNode(id, text, state));
		} else {
			oNode.getChildren().add(new EasyUiTreeNode(id, text, state));
		}
		return nodeList;
	}

	public static List<EasyUiTreeNode> addNode_Standard(
			List<EasyUiTreeNode> nodeList, String parentId, String id,
			String text, String state, boolean idcheck) {
		if (nodeList == null) {
			nodeList = new ArrayList<EasyUiTreeNode>();
		}
		EasyUiTreeNode oNode = findNode(nodeList, parentId);
		if (oNode == null) {
			nodeList.add(new EasyUiTreeNode(id, text, state, idcheck));
		} else {
			oNode.getChildren().add(
					new EasyUiTreeNode(id, text, state, idcheck));
			oNode.setChecked(false);
		}
		return nodeList;
	}

	public static List<EasyUiTreeNode> addNode_Standard(
			List<EasyUiTreeNode> nodeList, String parentId, String id,
			String text, String state, boolean idcheck, String icon) {
		if (nodeList == null) {
			nodeList = new ArrayList<EasyUiTreeNode>();
		}
		EasyUiTreeNode oNode = findNode(nodeList, parentId);
		if (oNode == null) {
			EasyUiTreeNode node = new EasyUiTreeNode(id, text, state, idcheck);
			if (icon != null) {
				node.setIconCls(icon);
			}
			nodeList.add(node);
		} else {
			EasyUiTreeNode node = new EasyUiTreeNode(id, text, state, idcheck);
			if (icon != null) {
				node.setIconCls(icon);
			}
			oNode.getChildren().add(node);
			oNode.setChecked(false);
		}
		return nodeList;
	}

	 
	public static List<EasyUiTreeNode> formatAsyncAjaxTree(
			List<Map<String, Object>> result1,
			List<Map<String, Object>> result2, String parentIdIndex,
			String idIndex, String titleIndex, String iconIndex) {
		result1.addAll(result2);
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
		try {
			for (Map<String, Object> node : result1) {
				if (node.get(idIndex) == null) {
					continue;
				}
				treeNodes = EasyUiTreeNode.addNode_initState(treeNodes, node
						.get(parentIdIndex).toString(), node.get(idIndex)
						.toString(), node.get(titleIndex).toString(), null,
						node.get(iconIndex) != null ? node.get(iconIndex)
								.toString() : null, null, null);
			}
		} catch (Exception e) {
			log.error(e.getMessage());;
		}
		return treeNodes;
	}

	 
	public static List<EasyUiTreeNode> formatAsyncAjaxTreeForMenuTree(
			List<Map<String, Object>> result1,
			List<Map<String, Object>> result2, String parentIdIndex,
			String idIndex, String titleIndex, String iconIndex,
			String urlIndex, String paramsIndex,
			List<WebMenu> currentUserMenus) {
		result1.addAll(result2);
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
		try {
			for (Map<String, Object> node : result1) {
				boolean isShowMenu = false;
				for (WebMenu menu : currentUserMenus) {
					if (menu.getId().equals(node.get(idIndex))) {
						isShowMenu = true;
						break;
					}
				}
				if (!isShowMenu) {
					continue;
				}
				treeNodes = EasyUiTreeNode.addNode_initState(treeNodes, node
						.get(parentIdIndex).toString(), node.get(idIndex)
						.toString(), node.get(titleIndex).toString(), null,
						node.get(iconIndex) != null ? node.get(iconIndex)
								.toString() : null,
						node.get(urlIndex) != null ? node.get(urlIndex)
								.toString() : null,
						node.get(paramsIndex) != null ? node.get(paramsIndex)
								.toString() : null);
			}
		} catch (Exception e) {
			log.error(e.getMessage());;
		}
		return treeNodes;
	}

	 
	public static List<EasyUiTreeNode> formatAjaxTree(
			List<Map<String, Object>> result1, String parentIdIndex,
			String idIndex, String titleIndex) {
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
		try {
			for (Map<String, Object> node : result1) {
				if (node.get(idIndex) == null) {
					continue;
				}
				treeNodes = EasyUiTreeNode.addNode_Standard(treeNodes, node
						.get(parentIdIndex).toString(), node.get(idIndex)
						.toString(), node.get(titleIndex).toString(), null);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return treeNodes;
	}

	 
	public static List<EasyUiTreeNode> formatAjaxTree(
			List<Map<String, Object>> result1, String parentIdIndex,
			String idIndex, String titleIndex, String checkIndex,
			String iconIndex) {
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
		try {
			for (Map<String, Object> node : result1) {
				if (node.get(idIndex) == null) {
					continue;
				}
				boolean ischeck = false;
				if (node.get(checkIndex) == null) {
					ischeck = false;
				} else {
					ischeck = true;
				}
				treeNodes = EasyUiTreeNode.addNode_Standard(treeNodes, node
						.get(parentIdIndex).toString(), node.get(idIndex)
						.toString(), node.get(titleIndex).toString(), null,
						ischeck, node.get(iconIndex) == null ? null : node.get(
								iconIndex).toString());
			}
		} catch (Exception e) {
			log.error(e.getMessage());;
		}
		return treeNodes;
	}

	 
	public static EasyUiTreeNode findNode(List<EasyUiTreeNode> nodeList,
			String id) {
		for (EasyUiTreeNode node : nodeList) {
			if (node.id.equals(id)) {
				return node;
			} else {
				EasyUiTreeNode reNode = findNode(node.children, id);
				if (reNode != null) {
					return reNode;
				}
			}
		}
		return null;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<EasyUiTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyUiTreeNode> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	 
	public static DataResult queryTreeNodeTow(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title) {
		return queryTreeNodeTow(parentId, SortTitle, tableTitle, id_title,
				parentId_title, name_title, icon_title, null);
	}

	 
	public static DataResult queryTreeNodeTow(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title,
			String userWhere) {
		if (parentId == null || parentId.trim().length() <= 0) {
			parentId = "NONE";
		}
		DataResult nodeResult = null;
		DBSort sort = new DBSort("b." + SortTitle, "ASC");
		DataQuery query = DataQuery.getInstance("1", "b." + id_title + " as "
				+ id_title + ",b." + parentId_title + " as " + parentId_title
				+ ",b." + name_title + " as " + name_title + ",b." + icon_title
				+ " as " + icon_title, tableTitle + " a left join "
				+ tableTitle + " b on b." + parentId_title + "=a." + id_title);
		query.setPagesize(1000);
		query.addSort(sort);
		DataQuerys.wipeVirus(parentId);
		if (userWhere != null) {
			query.setSqlRule("and a." + parentId_title + "='" + parentId + "' "
					+ userWhere);
		} else {
			query
					.setSqlRule("and a." + parentId_title + "='" + parentId
							+ "' ");
		}
		try {
			nodeResult = query.search();
		} catch (SQLException e) {
			log.error(e.getMessage());;
		}
		return nodeResult;
	}

	 
	public static DataResult queryTreeNodeOne(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title) {
		return queryTreeNodeOne(parentId, SortTitle, tableTitle, id_title,
				parentId_title, name_title, icon_title, null, null);
	}

	 
	public static DataResult queryTreeNodeOne(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title,
			String userWhere) {
		return queryTreeNodeOne(parentId, SortTitle, tableTitle, id_title,
				parentId_title, name_title, icon_title, userWhere, null);
	}

	 
	public static DataResult queryTreeNodeOne(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title,
			String userWhere, String otherTitles) {
		if (parentId == null || parentId.trim().length() <= 0) {
			parentId = "NONE";
		}
		DataResult nodeResult = null;
		DBSort sort = new DBSort("a." + SortTitle, "ASC");
		DataQuery query = DataQuery.getInstance("1", (otherTitles == null ? ""
				: (otherTitles + ","))
				+ id_title
				+ ","
				+ parentId_title
				+ " as "
				+ parentId_title
				+ ","
				+ name_title
				+ " as "
				+ name_title
				+ ",a."
				+ icon_title
				+ " as " + icon_title, tableTitle + " a");
		query.setPagesize(1000);
		query.addSort(sort);
		DataQuerys.wipeVirus(parentId);
		if (userWhere != null) {
			query.setSqlRule("and " + parentId_title + "='" + parentId + "' "
					+ userWhere);
		} else {
			query.setSqlRule("and " + parentId_title + "='" + parentId + "'");
		}
		try {
			nodeResult = query.search();
		} catch (SQLException e) {
			log.error(e.getMessage());;
		}
		return nodeResult;
	}

	 
	public static List<EasyUiTreeNode> formatTreeNodes(
			List<Map<String, Object>> resultList, String parentIndex,
			String idIndex, String titleIndex, String iconIndex) {
		List<EasyUiTreeNode> treeNodes = new ArrayList<EasyUiTreeNode>();
		try {
			for (Map<String, Object> node : resultList) {
				if (node.get(idIndex) == null) {
					continue;
				}
				treeNodes = EasyUiTreeNode.addNode_initState(treeNodes, node
						.get(parentIndex).toString(), node.get(idIndex)
						.toString(), node.get(titleIndex).toString(), null,
						node.get(iconIndex) != null ? node.get(iconIndex)
								.toString() : null, null, null);
			}
		} catch (Exception e) {
			log.error(e.getMessage());;
		}
		return treeNodes;
	}

	 
	public static DataQuery queryTreeNodeOneAuth(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title,
			String userWhere, String otherTitles,boolean authFlag) {
		if (parentId == null || parentId.trim().length() <= 0) {
			parentId = "NONE";
		}
		DBSort sort = new DBSort("a." + SortTitle, "ASC");
		DataQuery query = DataQuery.getInstance("1", (otherTitles == null ? ""
				: (otherTitles + ","))
				+ id_title
				+ ","
				+ parentId_title
				+ " as "
				+ parentId_title
				+ ","
				+ name_title
				+ " as "
				+ name_title
				+ ",a."
				+ icon_title
				+ " as " + icon_title, tableTitle + " a");
		query.setPagesize(100);
		query.addSort(sort);
		DataQuerys.wipeVirus(parentId);
		if(authFlag)
		{
			query.setSqlRule("and a." + parentId_title + " is not null");
			query.setSqlRule("and " + parentId_title + "='" + parentId + "'");
		}
		else
		{
			if (userWhere != null) {
				query.setSqlRule("and " + parentId_title + "='" + parentId + "' "
						+ userWhere);
			} else {
				query.setSqlRule("and " + parentId_title + "='" + parentId + "'");
			}
		}
	
		return query;
	}


	

	 
	public static DataQuery queryTreeNodeTowAuth(String parentId,
			String SortTitle, String tableTitle, String id_title,
			String parentId_title, String name_title, String icon_title,
			String userWhere,boolean authFlag) {
		if (parentId == null || parentId.trim().length() <= 0) {
			parentId = "NONE";
		}
		DBSort sort = new DBSort("b." + SortTitle, "ASC");
		DataQuery query = DataQuery.getInstance("1", "b." + id_title + " as "
				+ id_title + ",b." + parentId_title + " as " + parentId_title
				+ ",b." + name_title + " as " + name_title + ",b." + icon_title
				+ " as " + icon_title, tableTitle + " a left join "
				+ tableTitle + " b on b." + parentId_title + "=a." + id_title);
		query.setPagesize(100);
		query.addSort(sort);
		DataQuerys.wipeVirus(parentId);
		if(authFlag)
		{
			query.setSqlRule("and a." + parentId_title + " is not null");
			query.setSqlRule("and a." + parentId_title + "='" + parentId
					+ "' ");
		}
		else
		{
			if (userWhere != null) {
				query.setSqlRule("and a." + parentId_title + "='" + parentId + "' "
						+ userWhere);
			} else {
				query.setSqlRule("and a." + parentId_title + "='" + parentId
								+ "' ");
			}
		}

		return query;
	}

}
