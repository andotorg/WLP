package com.farm.wcp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.farm.wcp.api.exception.DocCreatErrorExcepiton;
import com.farm.wcp.domain.Results;

public interface WcpAppInter extends Remote {

	 
	public String uploadImgForUrl(String filename, byte[] file, String loginname, String password, String secret)
			throws RemoteException;

	 
	public String uploadImgForId(String filename, byte[] file, String loginname, String password, String secret)
			throws RemoteException;

	 
	public String creatHTMLKnow(String knowtitle, String knowtypeId, String text, String knowtag, String imgid,
			String loginname, String password, String secret) throws RemoteException, DocCreatErrorExcepiton;

	 
	public String creatHTMLQuestion(String title, String typeId, String text, String loginname, String password,
			String secret) throws RemoteException, DocCreatErrorExcepiton;

	 
	public void runLuceneIndex(String fileid, String docid, String text, String secret) throws RemoteException;

	 
	public Results getTypeDocs(String typeid, int pagesize, int currentpage, String loginname, String password,
			String secret) throws RemoteException;

	 
	public Results getAllTypes(String loginname, String password, String secret) throws RemoteException;

	 
	public Map<String, Object> getUserInfo(String loginname, String password, String secret) throws RemoteException;

	 
	public void FileConvertStartingEvent(String fileid, String docid, String secret) throws RemoteException;

	 
	public void FileConvertSuccessEvent(String fileid, String secret) throws RemoteException;

	 
	public void FileConvertErrorEvent(String fileid, String message, String secret) throws RemoteException;
}
