package com.farm.wcp.util;

import javax.servlet.http.HttpSession;

import com.farm.core.auth.exception.CheckCodeErrorException;
import com.farm.parameter.FarmParameterService;
import com.farm.web.constant.FarmConstant;

 
public class CheckCodeUtil {

	 
	public static boolean isCheckCodeAble(String checkcode, HttpSession session) throws CheckCodeErrorException {
		Object isNoCheckCode = session.getAttribute("NO_CHECKCODE_BIND_NEW_USER");
		if (isNoCheckCode != null && (boolean) isNoCheckCode) {
			session.removeAttribute("NO_CHECKCODE_BIND_NEW_USER");
			return true;
		}
		// 是否启用登录验证码
		if (FarmParameterService.getInstance().getParameter("config.sys.verifycode.able").equals("true")) {
			Integer leNum = (Integer) session.getAttribute(FarmConstant.SESSION_LOGINERROR_NUM);
			if (leNum == null) {
				// 从配置文件加载允许无验证码登录的错误次数
				String numStr = FarmParameterService.getInstance().getParameter("config.sys.verifycode.checknum");
				leNum = Integer.valueOf(numStr);
				session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, leNum);
			}
			if(checkcode==null){
				checkcode="NONE";
			}
			if (leNum <= 0) {
				boolean ischeck = checkcode.toUpperCase().equals(session.getAttribute("verCode"));
				session.removeAttribute("verCode");
				if (!ischeck) {
					throw new CheckCodeErrorException("验证码错误");
				}
			} else {
				// 免验证码登录次数不为0，不启用验证码
				return true;
			}
		}
		// 配置文件未启用验证码
		return true;
	}

	 
	public static void addLoginError(HttpSession session) {
		Integer leNum = (Integer) session.getAttribute(FarmConstant.SESSION_LOGINERROR_NUM);
		if (leNum == null) {
			// 从配置文件加载允许无验证码登录的错误次数
			leNum = Integer.valueOf(FarmParameterService.getInstance().getParameter("config.sys.verifycode.checknum"));
			session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, leNum <= 0 ? 0 : (leNum - 1));
		} else {
			session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, leNum <= 0 ? 0 : (leNum - 1));
		}
	}

	 
	public static void addLoginSuccess(HttpSession session) {
		// 从配置文件加载允许无验证码登录的错误次数
		int leNum = Integer.valueOf(FarmParameterService.getInstance().getParameter("config.sys.verifycode.checknum"));
		session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, leNum);
	}

	 
	public static void setEnforceCheckCode(HttpSession session) {
		session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, 0);
	}
	
	 
	public static void loadCheckAbleNum(HttpSession session) {
		if(session.getAttribute(FarmConstant.SESSION_LOGINERROR_NUM)==null){
			int leNum = Integer.valueOf(FarmParameterService.getInstance().getParameter("config.sys.verifycode.checknum"));
			session.setAttribute(FarmConstant.SESSION_LOGINERROR_NUM, leNum);
		}
	}
	 
	public static void invalidOnce(HttpSession session) {
		session.setAttribute("NO_CHECKCODE_BIND_NEW_USER", true);
	}
}
