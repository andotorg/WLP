package com.farm.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 跨站攻击漏洞辅助程序，负责过滤js脚本
 * 
 * @author WangDong
 * @date Mar 14, 2017
 * 
 */
public class FilterXss implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestUrl = request.getRequestURL().toString();
		if (requestUrl.indexOf("/PubUpBase64File.") >= 0 || requestUrl.indexOf("/base64img.") >= 0) {
			// 如果是上传base64位的截图则不继续xss校验,後者是restfulApi的base64接口
			chain.doFilter(arg0, arg1);
			return;
		}
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) arg0), arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}