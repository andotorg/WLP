package com.farm.core.inter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.core.inter.BusinessHandleInter;
import com.farm.core.inter.domain.BusinessHandler;

 
public class BusinessHandleServer {
	private static final Logger log = Logger.getLogger(BusinessHandleServer.class);
	static final Map<String, List<BusinessHandler>> BEANS = new HashMap<>();
	 
	private static boolean IS_SYCN = false;

	 
	public static void addBeans(String serverId, List<BusinessHandler> handler) {
		BEANS.put(serverId, handler);
	}

	 
	public static Map<String, String> getBeanInfos(String serverId) {
		List<BusinessHandler> beans = BEANS.get(serverId);
		Map<String, String> infos = new HashMap<String, String>();
		for (BusinessHandler bean : beans) {
			if (StringUtils.isNotBlank(bean.getId())) {
				infos.put(bean.getId(), bean.getTitle());
			}
		}
		return infos;
	}

	 
	public static void setSync(boolean isSync) {
		IS_SYCN = isSync;
	}

	 
	void runAll(String serverId, final BusinessHandleInter handle) {
		if (IS_SYCN) {
			// 立即執行
			final List<BusinessHandler> handlers = BEANS.get(serverId);
			for (BusinessHandler handler : handlers) {
				handle.execute(handler);
			}
		} else {
			// 異步執行
			try {
				final List<BusinessHandler> handlers = BEANS.get(serverId);
				if (handlers != null) {
					Thread t = new Thread(new Runnable() {
						public void run() {
							log.debug("[启动线程]执行业务回调任务，数量" + handlers.size());
							for (BusinessHandler handler : handlers) {
								try {
									handle.execute(handler);
								} catch (Exception e) {
									log.error("业务回调任务失败:", e);
								}
							}
						}
					});
					t.start();
				}
			} catch (Exception e) {
				log.error("业务回调任务:" + serverId + ":" + e.getMessage() + e.getClass());
			}
		}

	}
}
