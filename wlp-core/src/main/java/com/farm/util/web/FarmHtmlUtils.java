package com.farm.util.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class FarmHtmlUtils {

	 
	public static String HtmlRemoveTag(String html) {
		if (html == null)
			return null;
		String htmlStr = html; 
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
			// }
			String regEx_html = "<[^>]+>"; 

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); 

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); 

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); 

			textStr = htmlStr;

		} catch (Exception e) {
			// System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr.replaceAll("\\s+", " ");// 返回文本字符串
	}

	 
	public static Map<String, String> parseStyleToMap(String style) {
		Map<String, String> css = new HashMap<>();
		for (String node : style.split(";")) {
			String[] nodeArray = node.split(":");
			if (nodeArray.length == 2) {
				css.put(nodeArray[0].trim().toLowerCase(), nodeArray[1].trim());
			}
		}
		return css;
	}

	 
	public static String joinStyleMap(Map<String, String> styles) {
		String style = null;
		for (Entry<String, String> node : styles.entrySet()) {
			if (StringUtils.isNotBlank(node.getKey()) && StringUtils.isNotBlank(node.getValue())) {
				if (style == null) {
					style = node.getKey() + ":" + node.getValue();
				} else {
					style = style + node.getKey() + ":" + node.getValue();
				}
			}
		}
		return style;
	}
}
