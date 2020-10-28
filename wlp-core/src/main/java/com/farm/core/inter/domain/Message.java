package com.farm.core.inter.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.farm.core.message.MessageTypeFactory;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.util.web.FarmHtmlUtils;

 
public class Message {

	 
	private String id;
	 
	private MessageFormatString title;
	 
	private MessageFormatString text;
	 
	private List<LinkMessage> links;

	 
	private MessageType type;

	 
	public Set<String> getSenderBeanIds() {
		Set<String> senderBeanIds = type.getSenderBeanIds();
		if (senderBeanIds == null || senderBeanIds.size() <= 0) {
			senderBeanIds = new HashSet<>();
			senderBeanIds.add("WCPMSG");
			return senderBeanIds;
		}
		return senderBeanIds;
	}

	 
	public Message(TYPE_KEY key) {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		type = MessageTypeFactory.getInstance().getType(key.name());
	}

	// ----------------------------------

	 
	public String getTypeKey() {
		return type.getKey().name();
	}

	public String getId() {
		return id;
	}

	 
	public List<String> getSenders() {
		return type.getSenders();
	}

	 
	public String getTitle() {
		String returnTitle = title.toString();
		if (returnTitle.length() > 64) {
			returnTitle = FarmHtmlUtils.HtmlRemoveTag(returnTitle);
			if (returnTitle.length() > 64) {
				returnTitle = returnTitle.substring(0, 60) + "...";
			}
		}
		return returnTitle;
	}

	 
	public boolean isSendAble() {
		return type.isAble();
	}

	 
	public MessageFormatString initTitle() {
		this.title = new MessageFormatString(type.getTitleModel());
		return this.title;
	}

	public String getText() {
		return text.toString();
	}

	 
	public MessageFormatString initText() {
		this.text = new MessageFormatString(type.getContentModel());
		return this.text;
	}

	public List<LinkMessage> getLinks() {
		if (links == null) {
			links = new ArrayList<>();
		}
		return links;
	}

	public void setLinks(List<LinkMessage> links) {
		this.links = links;
	}

	public void addLink(LinkMessage link) {
		if (links == null) {
			links = new ArrayList<>();
		}
		links.add(link);
	}

	public String getLinkHtml() {
		String text = "";
		if (links != null) {
			for (LinkMessage link : links) {
				text = text + "<a class='wcp_doc_link' href='" + link.getUrl()
						+ "'><span class='glyphicon glyphicon-link'></span>&nbsp;" + link.getText() + "</a>&nbsp;";
			}
			text = "<br/><b>[" + text + "]</b>";
		}
		return text;
	}
}
