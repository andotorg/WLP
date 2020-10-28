package com.farm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.farm.core.ParameterService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.MenuRecordUtils;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.exception.UserNoLoginException;
import com.farm.core.page.ViewMode;
import com.farm.core.time.TimeTool;
import com.farm.util.spring.BeanFactory;
import com.farm.web.constant.FarmConstant;

public class WebUtils {

	 
	private static Map<String, String> LOGIN_AUTH_LIST = new HashMap<String, String>();

	 
	public static String getTokenUsername(String token) {
		String loginname = LOGIN_AUTH_LIST.get(token);
		if (loginname != null)
			LOGIN_AUTH_LIST.remove(token);
		return loginname;
	}

	 
	public static String creatLAToken(String loginname) {
		if (loginname == null || loginname.isEmpty()) {
			return null;
		}
		if (LOGIN_AUTH_LIST.size() > 200) {
			LOGIN_AUTH_LIST.clear();
		}
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		LOGIN_AUTH_LIST.put(token, loginname);
		return token;
	}

	 
	protected Object BEAN(String beanIndex) {
		return BeanFactory.getBean(beanIndex);
	}

	@SuppressWarnings("unchecked")
	public List<WebMenu> getCurrentUserMenus(HttpSession session) {
		List<WebMenu> menuList = (List<WebMenu>) session.getAttribute(FarmConstant.SESSION_USERMENU);
		List<WebMenu> rightMenus = new ArrayList<>();
		for (WebMenu menu : menuList) {
			rightMenus.add(menu);
		}
		return rightMenus;
	}

	 
	public static LoginUser getCurrentUser(HttpSession session) {
		LoginUser user = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		return user;
	}

	 
	public LoginUser getCurrentUserOrThrowException(HttpSession session) throws UserNoLoginException {
		LoginUser user = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		if (user == null) {
			throw new UserNoLoginException();
		}
		return user;
	}

	public String getGoUrl(HttpSession session, ParameterService parameterService) {
		if (SsoUtils.isNeedBackSsoUrl(session)) {
			// 如果是单点登陆过来的就返回子系统
			return SsoUtils.getSsobackUrl();
		}
		String goUrl = null;
		if (goUrl == null) {
			// 要去哪里
			goUrl = (String) session.getAttribute(parameterService.getParameter("farm.constant.session.key.go.url"));
			session.removeAttribute(parameterService.getParameter("farm.constant.session.key.go.url"));
		}
		if (goUrl == null) {
			// 来自哪里
			goUrl = (String) session.getAttribute(parameterService.getParameter("farm.constant.session.key.from.url"));
		}
		// if (goUrl != null && (goUrl.indexOf("pageset.pageType") >= 0 ||
		// goUrl.indexOf("OPERATE") >= 0
		// || goUrl.indexOf("STATE") >= 0)) {
		// 
		// goUrl = null;
		// }
		if (goUrl != null && goUrl.indexOf("login/webPage") > 0) {
			// 如果返回的是登录页面，则去默认首页
			goUrl = null;
		}
		if (goUrl == null) {
			// 默认页面
			goUrl = "/" + getDefaultIndexPage(parameterService);
		}
		return goUrl;
	}

	public static String getDefaultIndexPage(ParameterService parameterService) {
		String Path = parameterService.getParameter("config.index.defaultpage");
		return Path;
	}

	public LoginUser getCurrentUserByDebug(HttpSession session) {
		LoginUser user = new LoginUser() {
			@Override
			public String getName() {
				return "测试";
			}

			@Override
			public String getLoginname() {
				return "debug";
			}

			@Override
			public String getId() {
				return "debug";
			}

			@Override
			public String getType() {
				return "NONE";
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		};
		return user;
	}

	 
	@SuppressWarnings("unused")
	public LoginUser setCurrentUser(LoginUser user, HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_USEROBJ, user);
		String photoid = null;
		if (photoid != null && photoid.trim().length() > 0) {
			// if (session == null) {
			// getSession().put(AloneConstant.SESSION_USERPHOTO,
			// EkpFileFaceImpl.getInstance().getFileUrl(photoid));
			// } else {
			// session.setAttribute(AloneConstant.SESSION_USERPHOTO,
			// EkpFileFaceImpl.getInstance().getFileUrl(photoid));
			// }
		}
		return user;
	}

	 
	public void clearCurrentUser(HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_USEROBJ, null);
	}

	 
	public void setCurrentUserAction(Set<String> userAction, HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_USERACTION, userAction);
	}

	 
	public void setLoginTime(HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_LOGINTIME, TimeTool.getTimeDate14());
	}

	 
	public String getLoginTime(HttpSession session) {
		return (String) session.getAttribute(FarmConstant.SESSION_LOGINTIME);
	}

	 
	public void setCurrentUserMenu(List<WebMenu> userMenu, HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_USERMENU, userMenu);
	}

	 
	public HttpSession getSession(HttpSession httpSession) {
		return httpSession;
	}

	 
	public static String getCurrentIp(HttpServletRequest httpRequest) {
		String ip = httpRequest.getHeader("X-Real-IP");
		if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = httpRequest.getHeader("X-Forwarded-For");
		if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return httpRequest.getRemoteAddr();
		}
	}

	 
	public void setCookie(String cookieName, String value, HttpServletResponse httpResponse) {
		Cookie cookie = new Cookie(cookieName, value);
		int expireday = 60 * 60 * 24 * 30; 
		cookie.setMaxAge(expireday);
		httpResponse.addCookie(cookie);
	}

	 
	public void delCookie(String cookieName, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		if (cookieName == null || cookieName.equals("")) {
			return;
		}
		Cookie[] cookies = httpRequest.getCookies();
		int length = 0;

		if (cookies != null && cookies.length > 0) {
			length = cookies.length;
			for (int i = 0; i < length; i++) {
				String cname = cookies[i].getName();
				if (cname != null && cname.equals(cookieName)) {
					String cValue = cookies[i].getValue();
					setCookie(cname, cValue, httpResponse);
				} else {
					continue;
				}
			}
		}
	}

	public String getCookieValue(String cookieName, HttpServletRequest httpRequest) {
		if (cookieName == null || cookieName.equals("")) {
			return null;
		}
		Cookie[] cookies = httpRequest.getCookies();
		int length = 0;

		if (cookies != null && cookies.length > 0) {
			length = cookies.length;
			for (int i = 0; i < length; i++) {
				String cname = cookies[i].getName();
				if (cname != null && cname.equals(cookieName)) {
					String cValue = cookies[i].getValue();
					return cValue;
				} else {
					continue;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	 
	public static List<String> parseIds(String ids) {
		if (ids == null) {
			return new ArrayList<String>();
		}
		ids = ids.replace("，", ",");
		String[] markdot = ids.split(",");
		List<String> list_ = new ArrayList<String>();
		for (int i = 0; i < markdot.length; i++) {
			String temp = markdot[i];
			if (temp != null && !temp.equals("") && !temp.equals(" "))
				list_.add(temp);
		}
		return list_;
	}
}
