package com.farm.authority.password.impl;

import com.farm.authority.password.PasswordProviderInter;
import com.farm.core.auth.util.MD5Utils;

 
public class SafePasswordProvider implements PasswordProviderInter {
	private static final String SALT = "MACPROW234CPAUTOGEN23657984ERATE5234WERTWERTDME234THODSTUB";
	private static final String CLIENT_SALT = "FARM";
	@Override
	public String getDBPasswordByPlaint(String loginname, String plaintextPassword) {
		if (MD5Utils.isMd5code(plaintextPassword)) {
			throw new RuntimeException("用戶密码不可以是HASH编码");
		}
		String clientPassword = getClientPassword(plaintextPassword);
		return getDBPasswordByClient(loginname, clientPassword);
	}

	@Override
	public String getDBPasswordByClient(String loginname, String clientPassword) {
		clientPassword = clientPassword.toUpperCase();
		if (!MD5Utils.isMd5code(clientPassword)) {
			throw new RuntimeException("用戶密码不是正确的HASH编码");
		}
		return MD5Utils.encodeMd5(clientPassword + loginname + SALT);
	}

	@Override
	public String getClientPassword(String plaintextPassword) {
		return MD5Utils.encodeMd5(plaintextPassword+CLIENT_SALT).toUpperCase();
	}
}
