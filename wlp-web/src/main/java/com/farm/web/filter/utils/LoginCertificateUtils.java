package com.farm.web.filter.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.web.WebUtils;

/**
 * 判断是否有restful免密登录，登录请求如果有就执行登录(工具类)
 * 
 * @author macpl
 *
 */
public class LoginCertificateUtils {
	/**
	 * 缓存restful登录凭证
	 */
	private static final Map<String, Map<String, Object>> RESTFUL_LOGIN_CERTIFICATE = new HashMap<>();
	private static final Logger log = Logger.getLogger(LoginCertificateUtils.class);

	/**
	 * 判断登陆凭证并执行登陆
	 * 
	 * @param arg0
	 */
	public static void filterHandle(ServletRequest arg0) {
		HttpSession session = ((HttpServletRequest) arg0).getSession();
		{// 判断是否有restful免密登录，登录请求如果有就执行登录
			try {
				String certificate = arg0.getParameter("LOGIN_CERTIFICATE");
				log.debug("缓存restful登录凭证certificate:" + certificate);
				if (StringUtils.isNotBlank(certificate)) {
					Map<String, Object> certificateObj = RESTFUL_LOGIN_CERTIFICATE.get(certificate);
					if (certificateObj != null) {
						// 注册session
						Object loginName = certificateObj.get("loginname");
						if (loginName != null) {
							FarmAuthorityService.loginIntoSession(session,
									WebUtils.getCurrentIp((HttpServletRequest) arg0), loginName.toString(),
									"restful免密登录");
							clearCertificate();
						} else {
							log.warn("restful Login: loginName is error:" + certificate);
						}
					} else {
						log.warn("restful Login: certificate is error:" + certificate);
					}
				}
			} catch (Exception e) {
				log.warn("restful Login: certificate is error:" + e.getMessage());
			}
		}
	}

	/**
	 * 清理所有5分钟之前的凭证
	 */
	private static void clearCertificate() {
		log.info("RESTFUL_LOGIN_CERTIFICATE(" + RESTFUL_LOGIN_CERTIFICATE.size() + ")开始清理------");
		List<String> removeKes = new ArrayList<>();
		for (String uuidkey : RESTFUL_LOGIN_CERTIFICATE.keySet()) {
			Map<String, Object> certificate = RESTFUL_LOGIN_CERTIFICATE.get(uuidkey);
			Date certificateTime = (Date) certificate.get("time");
			Date currentTime = Calendar.getInstance().getTime();
			if ((currentTime.getTime() - certificateTime.getTime()) > 60 * 1000) {
				removeKes.add(uuidkey);
			}
		}
		for (String uuidkey : removeKes) {
			RESTFUL_LOGIN_CERTIFICATE.remove(uuidkey);
			log.info("清理------" + uuidkey + "过期，当前缓存区大小" + RESTFUL_LOGIN_CERTIFICATE.size());
		}
	}

	/**
	 * 注册并返回登录预登录凭证，后客户端可以通过在浏览器转发该凭证进行登录
	 * 
	 * @param loginname
	 * @return
	 */
	public static String registLoginCertificate(String loginname) {
		clearCertificate();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, Object> certificate = new HashMap<>();
		certificate.put("loginname", loginname);
		certificate.put("time", Calendar.getInstance().getTime());
		RESTFUL_LOGIN_CERTIFICATE.put(uuid, certificate);
		return uuid;
	}

	/**
	 * @param loginCertificate
	 * @return
	 */
	public static String getLoginNameByCertificate(String loginCertificate) {
		Map<String, Object> certificateObj = RESTFUL_LOGIN_CERTIFICATE.get(loginCertificate);
		if (certificateObj != null) {
			Object loginName = certificateObj.get("loginname");
			return loginName == null ? null : loginName.toString();
		} else {
			log.warn("restful Login: certificate is error:" + loginCertificate);
		}
		return null;
	}
}
