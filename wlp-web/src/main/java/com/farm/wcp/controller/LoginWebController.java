package com.farm.wcp.controller;

import java.net.URLDecoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Outuser;
import com.farm.authority.domain.User;
import com.farm.authority.password.PasswordProviderService;
import com.farm.authority.service.OutuserServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.exception.CheckCodeErrorException;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;
import com.farm.core.page.ViewMode;
import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.CheckCodeUtil;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.SsoUtils;
import com.farm.web.WebUtils;
import com.farm.web.filter.utils.LoginCertificateUtils;
import com.farm.web.tag.DefaultIndexPageTaget;

@RequestMapping("/login")
@Controller
public class LoginWebController extends WebUtils {
	private final static Logger log = Logger.getLogger(LoginWebController.class);
	@Resource
	private OutuserServiceInter outUserServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;

	/**
	 * 进入WCP站内登录页面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubLogin")
	public ModelAndView pubLogin(HttpSession session, HttpServletRequest request) {
		ViewMode view = ViewMode.getInstance();
		CheckCodeUtil.loadCheckAbleNum(session);
		ViewMode mode = ViewMode.getInstance();
		LoginUser user = getCurrentUser(session);
		if (user != null) {
			mode.putAttr("user", user);
		}
		return view.returnModelAndView(ThemesUtil.getThemePage("home-login", request));
	}

	/**
	 * 进入默认登录页面（可在配置文件wcpWebConfig.xml中config.login.default.url配置默认页面为default:
	 * login/PubLogin）
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/webPage")
	public ModelAndView login(HttpSession session, HttpServletRequest request) {
		// 将登录前的页面地址存入session中，为了登录后回到该页面中
		String url = request.getHeader("Referer");
		session.setAttribute(FarmParameterService.getInstance().getParameter("farm.constant.session.key.from.url"),
				url);
		try {
			String defultLoginUrl = FarmParameterService.getInstance().getParameter("config.login.default.url");
			if (!defultLoginUrl.toUpperCase().equals("DEFAULT") && (defultLoginUrl.indexOf(".html") > 0
					|| defultLoginUrl.indexOf(".do") > 0 || defultLoginUrl.indexOf(".jsp") > 0)) {
				return ViewMode.getInstance().returnRedirectUrl(defultLoginUrl);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return ViewMode.getInstance().returnRedirectUrl("/login/PubLogin.html");
	}

	/**
	 * 绑定老用户
	 * 
	 * @param checkcode
	 *            验证码
	 * @param name
	 *            老用户登录名/手机号/邮箱
	 * @param password
	 *            老用户密码
	 * @param outuserid
	 *            外部账户
	 * @param active
	 *            页面中激活的标签也[old|new]
	 * @param outusername
	 *            外部人员姓名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindOldUser")
	public ModelAndView bindOldUser(String checkcode, String name, String password, String outuserid, String active,
			String outusername, HttpServletRequest request, HttpSession session) {
		try {
			// 强制启用登录验证码
			CheckCodeUtil.setEnforceCheckCode(session);
			if (!CheckCodeUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			String loginName = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// 登录成功
				// 绑定老用户
				LoginUser user = FarmAuthorityService.getInstance().getUserByLoginName(loginName);
				Outuser outuser = outUserServiceImpl.getOutuserByAccountId(outuserid);
				outuser.setUserid(user.getId());
				outUserServiceImpl.editOutuserEntity(outuser);
				// 注册session
				FarmAuthorityService.loginIntoSession(session, getCurrentIp(request), name, "外部账户绑定时登录");
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// 登录失败
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).putAttr("active", active)
						.putAttr("outuserid", outuserid).putAttr("outusername", outusername).setError("用户密码错误", null)
						.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
			}
		} catch (LoginUserNoExistException e) {
			log.info("当前用户不存在");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("active", active)
					.putAttr("outuserid", outuserid).putAttr("outusername", outusername).setError("当前用户不存在", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("active", active)
					.putAttr("outuserid", outuserid).putAttr("outusername", outusername).setError("验证码错误", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "当前用户已注册，正等待管理员审核!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 绑定新用户
	 * 
	 * @param checkcode
	 *            验证码
	 * @param outuserid
	 *            外部账户
	 * @param loginname
	 *            登陆名
	 * @param outusername
	 *            外部人员姓名
	 * @param orgid
	 *            组织机构id
	 * @param photourl
	 *            用户头像
	 * @param active
	 *            页面中激活的标签也[old|new]
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindNewUser")
	public ModelAndView bindNewUser(String checkcode, String outuserid, String loginname, String outusername,
			String orgid, String photourl, String active, HttpServletRequest request, HttpSession session) {
		if (!FarmParameterService.getInstance().getParameter("config.show.out.regist.able").toLowerCase()
				.equals("true")) {
			return ViewMode.getInstance().setError("用户注册功能已经被禁用，请通过管理员进行创建新用户！", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		User user = null;
		// url解码loginname（因为有可能是从浏览器重定向过来的）
		outusername = urlDecode(outusername, loginname);
		try {
			// 强制启用登录验证码
			CheckCodeUtil.setEnforceCheckCode(session);
			if (!CheckCodeUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过!");
			}
			Outuser outuser = outUserServiceImpl.getOutuserByAccountId(outuserid);
			if (outUserServiceImpl.getLocalUserByAccountId(outuserid) != null) {
				throw new RuntimeException("已经绑定用户,请重新登陆!");
			}
			if (photourl != null && !photourl.isEmpty()) {
				try {
					photourl = photourl.replaceAll("&amp;", "&");
					String fileid = null;
					user = userServiceImpl.insertUserEntity(outusername, loginname,
							PasswordProviderService.getInstanceProvider().getClientPassword(TimeTool.getTimeDate12()),
							fileid);
				} catch (Exception e) {
					log.error(e.getMessage());
					user = userServiceImpl.insertUserEntity(outusername, loginname,
							PasswordProviderService.getInstanceProvider().getClientPassword(TimeTool.getTimeDate12()));
				}
			} else {
				user = userServiceImpl.insertUserEntity(outusername, loginname,
						PasswordProviderService.getInstanceProvider().getClientPassword(TimeTool.getTimeDate12()));
			}
			// 为用户设置组织机构
			userServiceImpl.setUserOrganization(user.getId(), orgid, getCurrentUser(session));
			// 登录成功
			outuser.setUserid(user.getId());
			outUserServiceImpl.editOutuserEntity(outuser);
			// 注册session
			Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
					user.getLoginname(), "外部账户创建用户时登录");
			sendFirstMessageToUser(loginParas,
					FarmParameterService.getInstance().getParameter("config.sys.firstBind.message"));
			String goUrl = getGoUrl(session, FarmParameterService.getInstance());
			return ViewMode.getInstance().returnRedirectUrl(goUrl);
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().setError("验证码错误", null).putAttr("outuserid", outuserid)
					.putAttr("orgid", orgid).putAttr("loginname", loginname).putAttr("photourl", photourl)
					.putAttr("active", active).putAttr("outusername", outusername)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).putAttr("outuserid", outuserid)
					.putAttr("orgid", orgid).putAttr("loginname", loginname).putAttr("photourl", photourl)
					.putAttr("outusername", outusername).putAttr("active", active)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		}
	}

	/**
	 * 处理后台过来的同步请求（经过浏览器跳转）：处理验证码屏蔽，处理中文字符编码
	 * 
	 * @param session
	 * @param outusername
	 * @param loginname
	 * @return
	 */
	private String urlDecode(String outusername, String loginname) {
		// 是否使用验证码(在后台自动绑定时使用：如ldap自动绑定用户,因爲不能 从前台获取验证码，所以通过session参数屏蔽)
		try {
			outusername = URLDecoder.decode(outusername, "utf-8");
			outusername = URLDecoder.decode(outusername, "utf-8");
		} catch (Exception e) {
			outusername = loginname;
			log.warn(e);
		}
		return outusername;
	}

	/**
	 * 验证验证码并且获取用户登录名
	 * 
	 * @param checkcode
	 *            验证码
	 * @param name
	 *            登录名/手机号/邮箱
	 * @param session
	 * @return
	 * @throws CheckCodeErrorException
	 */
	private String findUserLoginName(String name, HttpSession session) {
		if (name == null) {
			// 制作验证
			return null;
		}
		return name;
	}

	/**
	 * 处理登录状态结果
	 * 
	 * @param state
	 */
	private void loginStateHandle(boolean state, HttpSession session) {
		if (state) {
			CheckCodeUtil.addLoginSuccess(session);
		} else {
			CheckCodeUtil.addLoginError(session);
		}
	}

	/**
	 * 提交登录请求
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/websubmit")
	public ModelAndView webLoginCommit(String checkcode, String name, String password, HttpServletRequest request,
			HttpSession session) {
		try {
			// 是否启用登录验证码
			if (!CheckCodeUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			name = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// 登录成功
				// 注册session
				Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
						name, "登录页");
				sendFirstMessageToUser(loginParas,
						FarmParameterService.getInstance().getParameter("config.sys.firstlogin.message"));
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				// 本地登陆
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// 登录失败
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).setError("用户密码错误", null).returnModelAndView(ThemesUtil.getThemePage("home-login", request));
			}
		} catch (LoginUserNoExistException e) {
			log.info("当前用户不存在");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).setError("当前用户不存在", null).returnModelAndView(ThemesUtil.getThemePage("home-login", request));
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).setError("验证码错误", null).returnModelAndView(ThemesUtil.getThemePage("home-login", request));
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "当前用户已经注册成功，正等待管理员审核!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 外部用户登錄
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/outlogin")
	public ModelAndView outlogin(String certificate, String islocal, String active, HttpServletRequest request,
			HttpSession session) {
		try {
			String ckey = LoginCertificateUtils.getLoginNameByCertificate(certificate);
			if (ckey == null) {
				throw new RuntimeException("certificate无效!");
			}
			if (StringUtils.isBlank(active)) {
				active = "old";
			}
			if (islocal != null) {
				if (islocal.toLowerCase().equals("true")) {
					// 本地用户登陆
					String loginname = ckey;
					Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session,
							getCurrentIp(request), loginname, "登录页");
					sendFirstMessageToUser(loginParas,
							FarmParameterService.getInstance().getParameter("config.sys.firstlogin.message"));
					String goUrl = "/" + getDefaultIndexPage(FarmParameterService.getInstance());
					loginStateHandle(true, session);
					// 本地登陆
					return ViewMode.getInstance().returnRedirectUrl(goUrl);
				} else {
					// 外部用户注册
					String outuserid = ckey;
					Outuser outuser = outUserServiceImpl.getOutuserEntity(outuserid);
					if (outuser == null) {
						throw new RuntimeException("未找到外部用户" + outuserid);
					}
					return ViewMode.getInstance().putAttr("outuserid", outuser.getAccountid())
							.putAttr("outusername", outuser.getAccountname())
							.putAttr("loginname", outuser.getAccountid()).putAttr("orgid", null)
							.putAttr("systitle", "CAS").putAttr("active", active)
							.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
				}
			} else {
				throw new RuntimeException("入參islocal不能爲空!");
			}
		} catch (Exception e) {
			return ViewMode.getInstance().putAttr("MESSAGE", e.getMessage())
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 发送第一个系统消息给用户
	 * 
	 * @param loginParas
	 *            loginIntoSession方法的返回值
	 * @param message
	 *            发送的消息内容
	 */
	private void sendFirstMessageToUser(Map<String, String> loginParas, String message) {
		if (loginParas.get("lastLoginTime") == null || loginParas.get("lastLoginTime").isEmpty()) {
//			String firstLoginTipmessage = null;
//			try {
//				firstLoginTipmessage = message;
//			} catch (Exception e) {
//				firstLoginTipmessage = "欢迎登录系统！";
//			}
//			Message messageMaster = new Message(TYPE_KEY.user_login);
//			messageMaster.initTitle();
//			messageMaster.initText().setString(firstLoginTipmessage);
//			usermessageServiceImpl.sendMessage(messageMaster, loginParas.get("UserId"));
		}
	}

	@RequestMapping("/webout")
	public ModelAndView weblogOut(String name, HttpSession session) {
		try {
			clearCurrentUser(session);
			if (SsoUtils.isNeedBackSsoUrl(session)) {
				return ViewMode.getInstance().returnRedirectUrl(SsoUtils.getSsobackUrl());
			}
			String logoutPlusUrl = FarmParameterService.getInstance().getParameter("config.logout.plus.url");
			if (!logoutPlusUrl.toUpperCase().equals("NONE")) {
				return ViewMode.getInstance().returnRedirectUrl(logoutPlusUrl);
			}
			return ViewMode.getInstance().returnRedirectUrl("/" + DefaultIndexPageTaget.getDefaultIndexPage());
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@RequestMapping("/out")
	public ModelAndView logOut(String name, HttpSession session) {
		clearCurrentUser(session);
		return ViewMode.getInstance().returnRedirectUrl("/login/webPage.html");
	}

	/**
	 * 获得当前用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubCurrent")
	@ResponseBody
	public Map<String, Object> current(HttpSession session) {
		log.debug("检查当前用户信息");
		LoginUser user = getCurrentUser(session);
		boolean islogined = (user != null);
		return ViewMode.getInstance().putAttr("islogin", islogined).putAttr("curentUser", user).returnObjMode();
	}
}
