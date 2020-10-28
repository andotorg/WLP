package com.farm.web.tag.utils;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
/**
 * 加载page上下文中的数据
 * 
 * @author macpl
 *
 */
public class PageTagCache {
	private static final Logger log = Logger.getLogger(PageTagCache.class);
	public static Object get(String key, PageContext pageContext, PageTagCacheDoFunc runner) {
		//
		Object obj = pageContext.getAttribute(key, PageContext.REQUEST_SCOPE);
		if (obj == null) {
			obj = runner.run();
			pageContext.setAttribute(key, obj, PageContext.REQUEST_SCOPE);
		}else{
			log.debug("加载PageContext缓存数据...");
		}
		return obj;
	}

}
