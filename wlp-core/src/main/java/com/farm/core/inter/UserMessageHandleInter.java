package com.farm.core.inter;

import java.util.Map;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.Message;

 
public interface UserMessageHandleInter {
	 
	public void sendMessageHandler(Message message, String readUserId, String note, LoginUser sendUser,
			Map<String, String> context);

}
