package com.farm.core.inter.impl;

import java.util.List;
import org.apache.log4j.Logger;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.BusinessHandleInter;
import com.farm.core.inter.UserMessageHandleInter;
import com.farm.core.inter.domain.BusinessHandler;
import com.farm.core.inter.domain.Message;

 
public class SendMessageHandle extends BusinessHandleServer {
	 
	private static String serverId = "UserMessageHandleInterId";
	private static final Logger log = Logger.getLogger(SendMessageHandle.class);

	 
	public static SendMessageHandle getInstance() {
		SendMessageHandle obj = new SendMessageHandle();
		return obj;
	}

	 
	public void sendMessageHandlers(final Message message, final List<String> readUserIds, final String note,
			final LoginUser sendUser) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				if (message.getSenderBeanIds().size() == 0 || message.getSenderBeanIds().contains(impl.getId())) {
					// 如果消息绑定的发送方式为空，就可以使用所有方式发送，如果不为空就用限定方式发送
					UserMessageHandleInter hander = (UserMessageHandleInter) impl.getBeanImpl();
					for (String readUserId : readUserIds) {
						try {
							hander.sendMessageHandler(message, readUserId, note, sendUser, impl.getContext());
						} catch (Exception e) {
							log.warn("message send error!" + impl.getClass(), e);
						}
					}
				}
			}
		});
	}
}
