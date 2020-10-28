package com.farm.web;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class SsoUtils {
	// _NOCLEAR加上此后缀，再session清理时不会被清理掉
	private static String BACKURL_SESSION_KEY = "SSO_BACKURL_NOCLEAR";

	public static String getBackurlSessionKey() {
		return BACKURL_SESSION_KEY;
	}

	 
	public static boolean isNeedBackSsoUrl(HttpSession session) {
		if (StringUtils.isBlank((String) session.getAttribute(SsoUtils.getBackurlSessionKey()))) {
			return false;
		} else {
			return true;
		}
	}

	 
	public static String getSsobackUrl() {
		return "/sso/goback.do";
	}
}
