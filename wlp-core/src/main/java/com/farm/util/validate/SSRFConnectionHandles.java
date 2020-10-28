package com.farm.util.validate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
 
public class SSRFConnectionHandles {

	private static final Logger log = Logger.getLogger(SSRFConnectionHandles.class);

	public static void validateUrl(String url) {
		// 处理和校验URL
		if (StringUtils.isNotBlank(url)) {
			int num = url.indexOf("://");
			if (num > 0) {
				String protocol = url.substring(0, num);
				if (!protocol.trim().toUpperCase().equals("HTTP") && !protocol.trim().toUpperCase().equals("HTTPS")) {
					throw new RuntimeException("can't do the protocol:" + protocol);
				}
			}
		}
	}

	public static Exception exceptionHandle(Exception e) {
		// 处理错误消息
		log.error(e);
		return new Exception("请求不可达,具体原因请参照日志记录!");
		// return e;
	}
}
