package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.farm.wcp.ekca.UpdateState;
import com.farm.wcp.ekca.UpdateType;
import com.farm.wcp.ekca.OperateEvent;

 
public interface EkcaAppInter extends Remote {
	 
	public void updateUser(String oid, String name, String type, String loginname, String imgid,
			UpdateState updateState, UpdateType updateType, String secret) throws RemoteException;

	 
	public void updateUserOrgid(String oid, String orgid, UpdateType updateType, String secret) throws RemoteException;

	 
	public void updateUserPostid(String oid, List<String> postids, UpdateType updateType, String secret)
			throws RemoteException;

	 
	public void updateOrg(String oid, String treecode, String name, String parentid, int sort, UpdateState updateState,
			UpdateType updateType, String secret) throws RemoteException;

	 
	public void updatePost(String oid, String name, String orgid, UpdateState updateState, UpdateType updateType,
			String secret) throws RemoteException;

	 
	public void updateQuestion(String oid, String title, String typeid, String anonymous, UpdateState updateState,
			UpdateType updateType, String secret) throws RemoteException;

	 
	public void updateType(String oid, String name, int sort, String type, String parentid, String treecode,
			String knowshow, String fqashow, UpdateState updateState, UpdateType updateType, String secret)
			throws RemoteException;

	 
	public void updateKnow(String oid, String title, String domtype, String version, String typeid,
			UpdateState updateState, UpdateType updateType, String secret) throws RemoteException;

	 
	public void recordOperate(OperateEvent operateEvent, String userid, String userip, String knowId, String QuestId,
			String versionid, String oid, String pid, String secret) throws RemoteException;
}
