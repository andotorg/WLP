package com.farm.authority.password;

 
public interface PasswordProviderInter {

	 
	public String getDBPasswordByPlaint(String loginname, String plaintextPassword);

	 
	public String getDBPasswordByClient(String loginname, String clientPassword);

	 
	public String getClientPassword(String plaintextPassword);
}
