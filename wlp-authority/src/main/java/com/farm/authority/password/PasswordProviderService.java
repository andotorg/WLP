package com.farm.authority.password;

import com.farm.authority.password.impl.SafePasswordProvider;
import com.farm.authority.password.impl.SimplePasswordProvider;
import com.farm.parameter.FarmParameterService;

 
public class PasswordProviderService {
	public static PasswordProviderInter getInstanceProvider() {
		// SIMPLE:简单类型,SAFE:安全类型
		String type = FarmParameterService.getInstance().getParameter("config.password.provider.type");
		if (type.equals("SAFE")) {
			return new SafePasswordProvider();
		}
		return new SimplePasswordProvider();
	}

	 
	public static PasswordProviderInter getInstanceProvider(String type) {
		// SIMPLE:简单类型,SAFE:安全类型
		if (type.equals("SAFE")) {
			return new SafePasswordProvider();
		}
		return new SimplePasswordProvider();
	}
}
