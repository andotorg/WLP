package com.farm.authority.service;

import java.sql.SQLException;
import java.util.List;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.Actiontree;
import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.easyui.EasyUiTreeNode;

 
public interface ActionServiceInter {
	 
	public Action insertActionEntity(Action entity, LoginUser user);

	 
	public Action editActionEntity(Action entity, LoginUser user);

	 
	public void deleteActionEntity(String id, LoginUser user);

	 
	public Action getActionEntity(String id);

	 
	public DataQuery createActionSimpleQuery(DataQuery query);

	 
	public Actiontree insertActiontreeEntity(Actiontree entity, LoginUser user, String authkey);

	 
	public Actiontree editActiontreeEntity(Actiontree entity, LoginUser user, String authkey);

	 
	public void deleteActiontreeEntity(String id, LoginUser user);

	 
	public Actiontree getActiontreeEntity(String id);

	 
	public DataQuery createActiontreeSimpleQuery(DataQuery query);

	 
	public List<EasyUiTreeNode> getSyncTree(String parentId, String domain);

	 
	public void moveActionTreeNode(String treeNodeId, String targetTreeNodeId);

	 
	public AuthKey getCacheAction(String key);

	 
	public List<WebMenu> getAllMenus();

	 
	public List<Action> getAllActions();

	 
	public DataResult searchAllMenu() throws SQLException;

	 
	public List<EasyUiTreeNode> searchAsynEasyUiTree(String id, String domain, List<WebMenu> usermenu);

}