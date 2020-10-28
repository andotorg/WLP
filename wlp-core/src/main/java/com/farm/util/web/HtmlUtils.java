package com.farm.util.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.farm.core.auth.domain.LoginUser;
import com.farm.util.validate.SSRFConnectionHandles;
import com.farm.util.web.FarmHtmlUtils;

public class HtmlUtils {

	 
	public static String htmlEncode(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}




	 
	public static String HtmlRemoveTag(String html) {
		return FarmHtmlUtils.HtmlRemoveTag(html);// 返回文本字符串
	}


	 
	public static boolean isHaveKnowMenu(String text) {
		Document document = Jsoup.parseBodyFragment(text);
		Elements eles = document.getElementsByTag("H1");
		eles.addAll(document.getElementsByTag("H2"));
		eles.addAll(document.getElementsByTag("H3"));
		eles.addAll(document.getElementsByTag("H4"));
		return eles.size() > 0;
	}

}
