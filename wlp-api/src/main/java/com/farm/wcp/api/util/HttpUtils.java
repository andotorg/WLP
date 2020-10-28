package com.farm.wcp.api.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtils {

	 
	public static void printApiDoc(Map<String, String> para) {
		for (Entry<String, String> node : para.entrySet()) {
			// <tr>
			// <th scope="row">id</th>
			// <td>主键</td>
			// <td class="demo">可以为空</td>
			// </tr>
			System.out.println("<tr>");
			System.out.println("<th scope='row'>" + node.getKey() + "</th>");
			System.out.println("<td></td>");
			System.out.println("<td class='demo'>可以为空|必填</td>");
			System.out.println("</tr>");
		}
	}

	public static JSONObject httpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10 * 1000).setConnectTimeout(10 * 1000)
				.build();
		httpGet.setConfig(requestConfig);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				resultStr = resultStr.replace("callback(", "").replace(")", "").replace(";", "");
				JSONObject result = new JSONObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
		} finally {
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					System.out.println("httpPost:" + e + e.getMessage());
				}
			}
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					System.out.println("httpPost:" + e + e.getMessage());
				}
			}
		}

		return null;
	}

	public static JSONObject httpPost(String url, Map<String, String> data) {
		long startTime = new Date().getTime();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10 * 1000).setConnectTimeout(10 * 1000)
				.build();
		httpPost.setConfig(requestConfig);
		// httpPost.addHeader("Content-Type", "application/json");
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : data.keySet()) {
				params.add(new BasicNameValuePair(key, data.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
			response = httpClient.execute(httpPost, new BasicHttpContext());
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				if (response.getStatusLine().getStatusCode() == 404) {
					String showAddr = url.indexOf("?") > 0 ? url.substring(0, url.indexOf("?")) : url;
					throw new RuntimeException("远程服务状态：" + response.getStatusLine().getStatusCode() + "，地址:" + showAddr);
				}
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = new JSONObject(resultStr);
				return result;
			}
		} catch (IOException e) {
			System.out.println("httpPost-runtime:" + (new Date().getTime() - startTime));
			System.out
					.println("ERROR:" + e.getMessage() + " request url=" + url + ", exception, msg=" + e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				System.out.println("httpPost:" + e + e.getMessage());
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					System.out.println("httpPost:" + e + e.getMessage());
				}
			}
			// System.out.println("httpPost-runtime:" + (new Date().getTime() -
			// startTime));
		}
		return null;
	}

	public static JSONObject httpGet(String url, Map<String, String> para) {
		String paras = null;
		for (Entry<String, String> node : para.entrySet()) {
			String val = null;
			try {
				val = URLEncoder.encode(node.getValue(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				val = node.getValue();
			}
			if (paras == null) {
				paras = node.getKey() + "=" + val;
			} else {
				paras = paras + "&" + node.getKey() + "=" + val;
			}
		}
		if (paras != null) {
			url = url + "?" + paras;
		}
		System.out.println("doget:" + url);
		return httpGet(url);
	}

	 
	public static JSONObject doPostJson(String url, JSONObject param) {
		HttpPost httpPost = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			if (param != null) {
				StringEntity se = new StringEntity(param.toString(), "utf-8");
				httpPost.setEntity(se); 
				httpPost.setHeader("Content-Type", "application/json");
			}

			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");
				JSONObject result = new JSONObject(resultStr);
				return result;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	 
	public static boolean isHasIpOrPort(String url) {
		{
			String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
			Pattern pattern = Pattern.compile(ip);
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				return true;
			}
		}
		{
			String port = ":([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
			Pattern pattern = Pattern.compile(port);
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
}
