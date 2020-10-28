package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.farm.wcp.ekca.OperateEvent.EVENT_TYPE;

 
public interface EkcaManagerInter extends Remote {
	 
	public void loginReady(String key, String loginName, String secret) throws RemoteException;

	 
	public int getUserPoint(String userid, String secret) throws RemoteException;

	 
	public String registUserCode(String userid, String secret) throws RemoteException;

	 
	public List<Map<String, Object>> getMostPointUsers(int pagesize, String secret) throws RemoteException;

	 
	public List<Map<String, Object>> getNewOperate(EVENT_TYPE event_type, int size, String userid, String secret)
			throws RemoteException;

}
