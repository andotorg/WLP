package com.farm.core.auth.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

 
public class Urls {

	 
	@SuppressWarnings("unused")
	private boolean checkpopedom(Map<String, Object> actionMap, Map<String, Map<String, String>> useraction,
			String url) {
		// 查看是否是受控权限
		if (actionMap == null) {
			return false;
		}
		if (actionMap.get(url) != null) {
			// 是受检权限,看用户是否拥有该权限
			if (useraction == null) {
				return false;
			}
			if (useraction.get(url) != null) {
				return true;
			} else {
				return false;
			}

		} else {
			// 不是受检权限
			return true;
		}
	}

	// struts2的请求

	 
	public static String formatUrl(String requestUrl, String basePath) {
		// 去掉basepath
		requestUrl = requestUrl.replace(basePath, "");
		// 截去url参数
		int num = requestUrl.indexOf("?");
		if (num > 0) {
			requestUrl = requestUrl.substring(0, num);
		}
		if (requestUrl.trim().indexOf("http") == 0) {
			int lastSplitIndex = requestUrl.lastIndexOf("/");
			int lastSplitTowIndex = requestUrl.substring(0, lastSplitIndex).lastIndexOf("/");
			// 截取到倒数第二个斜杠
			if (lastSplitTowIndex >= 0) {
				requestUrl = requestUrl.substring(lastSplitTowIndex + 1);
			} else {
				requestUrl = requestUrl.substring(lastSplitIndex + 1);
			}
		}
		// 截去url前缀
		// int num2 = requestUrl.replace("\\", "/").lastIndexOf("/");
		// requestUrl = requestUrl.substring(num2);
		return requestUrl;
	}

	 
	public static boolean isActionByUrl(String _Url, String _postfix) {
		if (_Url.indexOf(".") < 0) {
			return false;
		}
		_Url = _Url.trim();
		String postfix = _Url.substring(_Url.lastIndexOf(".") + 1);
		if (postfix.toUpperCase().equals(_postfix.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	 
	public static boolean isActionByUrlCaseSensitive(String _Url, String _postfix) {
		if (_Url.indexOf(".") < 0) {
			return false;
		}
		_Url = _Url.trim();
		String postfix = _Url.substring(_Url.lastIndexOf(".") + 1);
		if (postfix.equals(_postfix)) {
			return true;
		}
		return false;
	}

	 
	public static String getActionKey(String formatUrl) {
		int num = formatUrl.indexOf("?");
		if (num > 0) {
			formatUrl = formatUrl.substring(0, num);
		}
		String name = formatUrl.substring(0, formatUrl.indexOf("."));
		return name;
	}

	public static String getActionSubKey(String actionkey) {
		// 截去url前缀
		int num2 = actionkey.replace("\\", "/").lastIndexOf("/");
		if (num2 >= 0) {
			actionkey = actionkey.substring(num2);
		}
		return actionkey;
	}

	 
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	 
	public static String getUrlParameters(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
		StringBuffer urlp = new StringBuffer();
		for (Entry<String, String[]> node : entrySet) {
			if (node.getValue() != null & node.getValue().length > 0) {
				if (urlp.length() > 0) {
					urlp.append("&");
					urlp.append(node.getKey() + "=" + node.getValue()[0]);
				} else {
					urlp.append(node.getKey() + "=" + node.getValue()[0]);
				}
			}
		}
		return urlp.toString();
	}

	 
	public boolean isHavePop(String URL_index, Map<String, String> userUrl) {
		return false;

	}

	 
	public static String getGroupKey(String key) {
		String groupkey = null;
		try {
			int splitIndex = key.replaceAll("\\\\", "/").indexOf("/");
			if (splitIndex > 0) {
				groupkey = key.substring(0, splitIndex).toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			groupkey = null;
		}
		if (StringUtils.isBlank(groupkey)) {
			groupkey = null;
		}
		return groupkey;
	}
}