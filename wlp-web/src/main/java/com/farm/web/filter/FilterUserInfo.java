package com.farm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.password.PasswordProviderService;
import com.farm.core.AuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;
import com.farm.core.auth.util.Urls;
import com.farm.parameter.FarmParameterService;
import com.farm.web.constant.FarmConstant;

/**
 * 判断用户信息是否完善，如果不完善就跳转到用户信息修改页面
 * 
 * @author WangDong
 * @date November 17, 2017
 * 
 */
public class FilterUserInfo implements Filter {
	private static final Logger log = Logger.getLogger(FilterUserInfo.class);

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) arg0).getSession();
		// ---------判断用户是否需要补充信息，是否需要修改密码,准备参数开开始..---------------------------------------------------------------------------------
		LoginUser currentUser = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		String path = ((HttpServletRequest) arg0).getContextPath();
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":" + arg0.getServerPort() + path + "/";
		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestUrl = request.getRequestURL().toString();
		// 如果端口为80端口则，将该端口去掉，认为是不许要端口的
		String formatUrl = Urls.formatUrl(requestUrl,
				requestUrl.indexOf(":") < 8 ? basePath.replace(":80/", "/") : basePath);
		{// 不是后台请求直接运行访问()
			if (!isURL(formatUrl)) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		HttpServletResponse response = (HttpServletResponse) arg1;
		AuthorityService authServer = FarmAuthorityService.getInstance();
		{
			// 配置文件中是否启用判断用户信息完整性的逻辑
			if (currentUser == null) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{
			// 判断用户密码是否为默认密码，如果为默认密码表示没有修改过密码，需要修改密码
			try {
				if ((session.getAttribute("USERPASSWORD_COMPLETE" + currentUser.getId()) == null)
						&& FarmParameterService.getInstance().getParameter("config.sys.enforce.password.update")
								.equals("true")
						&& authServer.isLegality(currentUser.getLoginname(),
								PasswordProviderService.getInstanceProvider().getClientPassword(
										FarmParameterService.getInstance().getParameter("config.default.password")))) {
					// 用户未修改密码
					log.info("配置文件要求 ：用户必须修改密码");
					response.sendRedirect(basePath + "webuser/editCurrentUserPwd.do");
					return;
				}
				session.setAttribute("USERPASSWORD_COMPLETE" + currentUser.getId(), true);
			} catch (LoginUserNoExistException | LoginUserNoAuditException e) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		// --------------准备参数开结束----------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------------------
		{// 判断用户信息是否完善，如果不完善就跳转到用户信息修改页面
			if (!FarmParameterService.getInstance().getParameter("config.sys.perfect.userinfo.able").toUpperCase()
					.equals("TRUE")) {
				arg2.doFilter(arg0, arg1);
				return;
			}
			if (session.getAttribute("USERINFO_COMPLETE" + currentUser.getId()) == null) {
				// 判断用户信息是否完善，如果不完善就跳转到用户信息编辑页面
				log.info("配置文件要求 ：用户完善信息");
				response.sendRedirect(basePath + "webuser/edit.do");
				return;
			}
			// 完善就记录在session中，避免重复判断// 非受管的权限可以直接登录
			session.setAttribute("USERINFO_COMPLETE" + currentUser.getId(), true);
			arg2.doFilter(arg0, arg1);
			return;
		}
	}

	// -----------------------------------------------------------
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * 判断是否是调用controller的逻辑请求
	 * 
	 * @param urlStr
	 * @return
	 */
	private boolean isURL(String urlStr) {
		return Urls.isActionByUrl(urlStr, "do") || Urls.isActionByUrl(urlStr, "html");
	}
}