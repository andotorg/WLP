package com.farm.core;

import java.util.List;
import java.util.Set;

import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;

 
public interface AuthorityService {

	 
	public boolean isLegality(String loginName, String clientPassword)
			throws LoginUserNoExistException, LoginUserNoAuditException;

	 
	public LoginUser getUserByLoginName(String loginName);

	 
	public List<String> getUserPostKeys(String userId);

	 
	public String getUserOrgKey(String userId);

	 
	public LoginUser getUserById(String userId);

	 
	public Set<String> getUserAuthKeys(String userId);

	 
	public AuthKey getAuthKey(String key);

	 
	public List<WebMenu> getUserMenu(String userId);

	 
	public String loginHandle(String userId);

	 
	public LoginUser loadAndinitOutUser(String outuserid, String outuserid2, String name, String content);

}
