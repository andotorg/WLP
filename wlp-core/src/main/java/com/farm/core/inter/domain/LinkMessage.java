package com.farm.core.inter.domain;

 
public class LinkMessage {
	private String url;
	private String text;

	public LinkMessage(String url, String text) {
		this.url = url;
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
