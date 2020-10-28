package com.farm.wcp.api.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.farm.wcp.api.WcpAppInter;

public class WcpAppClient {
	 
	public static WcpAppInter getServer(String rmiUrl)
			throws MalformedURLException, RemoteException, NotBoundException {
		WcpAppInter wcpApp = (WcpAppInter) Naming.lookup(rmiUrl);
		return wcpApp;
	}
}
