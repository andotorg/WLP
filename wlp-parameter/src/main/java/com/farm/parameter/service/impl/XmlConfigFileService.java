package com.farm.parameter.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.farm.parameter.FarmParameterService;
import com.farm.parameter.controller.ParameterController;

@Service
public class XmlConfigFileService {
	private static Map<String, String> constantValue = new HashMap<String, String>();
	private static Map<String, String> constantGroup = new HashMap<String, String>();
	private static Map<String, String> constantNote = new HashMap<String, String>();
	private static final Logger log = Logger.getLogger(ParameterController.class);

	 
	public static List<String> readCheckXmlConfig(String fileName) {
		URL url = XmlConfigFileService.class.getClassLoader().getResource(fileName);
		File file = new File(url.getFile());
		List<String> list = new ArrayList<String>();
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			Elements eles = document.getElementsByTag("parameter");
			for (Element node : eles) {
				list.add(node.text());
			}
		} catch (IOException e) {
			log.error(e + e.getMessage(), e);
		}
		return list;
	}

	 
	public static boolean registConstant(String fileName) {
		log.info("注册XML配置文件" + fileName);
		URL url = XmlConfigFileService.class.getClassLoader().getResource(fileName);
		File file = new File(url.getFile());
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			Elements eles = document.getElementsByTag("parameter");
			for (Element node : eles) {
				String key = node.attr("name");
				String value = node.getElementsByTag("val").text();
				if (node.parent().tagName().toUpperCase().equals("GROUP")) {
					String groupName = node.parent().attr("describe");
					constantGroup.put(key, groupName);
				}
				Elements noteElentent=node.getElementsByTag("describe");
				if(noteElentent!=null){
					constantNote.put(key, noteElentent.text());
				}
				constantValue.put(key, value);
			}
		} catch (IOException e) {
			log.error(e + e.getMessage(), e);
		}
		return true;
	}

	 
	public static void registVesionParas(Map<String, String> vParas) {
		for (Entry<String, String> node : vParas.entrySet()) {
			constantValue.put(node.getKey(), node.getValue());
		}
	}

	public static List<Entry<String, String>> getEntrys() {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (Entry<String, String> node : constantValue.entrySet()) {
			list.add(node);
		}
		return list;
	}

	public static String getValue(String key) {
		return constantValue.get(key);
	}

	public static String getGroupName(String key) {
		return constantGroup.get(key);
	}
	public static String getNote(String key) {
		return constantNote.get(key);
	}
	 
	public static void loadXmlToApplication(ServletContext context) {
		for (Entry<String, String> node : constantValue.entrySet()) {
			context.setAttribute(node.getKey().replaceAll("\\.", "_"),
					FarmParameterService.getInstance().getParameter(node.getKey()));
		}
	}



}
