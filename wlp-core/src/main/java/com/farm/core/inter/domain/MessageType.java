package com.farm.core.inter.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.farm.core.message.MessageTypeFactory.TYPE_KEY;

 
public class MessageType {
	 
	private TYPE_KEY key;// 类型key
	private String typeName;// 类型名称
	private String titleModel;// 标题模板
	private String contentModel;// 内容模板
	private String titleDescribe;// 参数说明
	private String contentDescribe;// 内容模板參數説明
	private String senderDescribe;// 接收人描述
	private boolean isAble;
	private List<String> senders = new ArrayList<>();// 消息抄送人
	private Set<String> senderBeanIds = new HashSet<>();// 消息發送方式beanId

	public void setSenderBeanIds(Set<String> senderBeanIds) {
		this.senderBeanIds = senderBeanIds;
	}

	@SuppressWarnings("unused")
	private MessageType() {
		// 私有化無參構造函數
	}

	public MessageType(String typeName, TYPE_KEY key, String titleModel, String contentModel, String titleDescribe,
			String contentDescribe, String senderDescribe) {
		this.key = key;
		this.typeName = typeName;
		this.titleModel = titleModel;
		this.contentModel = contentModel;
		this.titleDescribe = titleDescribe;
		this.contentDescribe = contentDescribe;
		this.senderDescribe = senderDescribe;
	}

	public Set<String> getSenderBeanIds() {
		return senderBeanIds;
	}

	// -------------------------------

	 
	public TYPE_KEY getKey() {
		return key;
	}

	 
	public String getTypeName() {
		return typeName;
	}

	 
	public String getTitleModel() {
		return titleModel;
	}

	 
	public String getContentModel() {
		return contentModel;
	}

	 
	public String getTitleDescribe() {
		return titleDescribe;
	}

	 
	public String getContentDescribe() {
		return contentDescribe;
	}

	 
	public boolean isAble() {
		return isAble;
	}

	public void SetAble(boolean able) {
		isAble = able;
	}

	public String getSenderDescribe() {
		return senderDescribe;
	}

	public void setTitleModel(String titleModel) {
		this.titleModel = titleModel;
	}

	public void setContentModel(String contentModel) {
		this.contentModel = contentModel;
	}

	public List<String> getSenders() {
		return senders;
	}

	public void setSenders(List<String> senders) {
		this.senders = senders;
	}

}
