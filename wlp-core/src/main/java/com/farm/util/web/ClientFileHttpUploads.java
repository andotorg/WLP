package com.farm.util.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.farm.wcp.api.util.HttpUtils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ClientFileHttpUploads {

	private String uploadUrl;
	private String progressUrl;
	private String existRemoteFileUrl;
	private File file;
	private ProgressHandle handles;
	private Map<String, String> baseParas;
	private Map<String, String> uploadParas;
	private Map<String, String> getProgressParas;
	private Map<String, String> existRemoteFileParas;
	private String processkey;

	public static ClientFileHttpUploads getInstance(String url, Map<String, String> param) {
		ClientFileHttpUploads obj = new ClientFileHttpUploads();
		obj.uploadUrl = url;
		obj.baseParas = param;
		HashMap<String, String> newmap = new HashMap<>();
		newmap.putAll(param);
		obj.uploadParas = newmap;
		return obj;
	}

	 
	public void setRemoteProgressUrl(String url) {
		progressUrl = url;
		processkey = UUID.randomUUID().toString().replaceAll("-", "");
		HashMap<String, String> newmap = new HashMap<>();
		newmap.putAll(baseParas);
		getProgressParas = newmap;
	}

	 
	public void setRemoteFileExistUrl(String url) {
		existRemoteFileUrl = url;
		HashMap<String, String> newmap = new HashMap<>();
		newmap.putAll(baseParas);
		existRemoteFileParas = newmap;
	}

	public ClientFileHttpUploads setProgressHandle(ProgressHandle handles) {
		this.handles = handles;
		return this;
	}

	 
	public JSONObject doUpload(File upFile, String filename) throws IOException {
		return doUpload(upFile, filename, null);
	}

	 
	public JSONObject doUpload(File upFile, String filename, String syncid) throws IOException {
		try {
			// 先判斷文件是否已經上传
			if (StringUtils.isNotBlank(existRemoteFileUrl) && StringUtils.isNotBlank(syncid)) {
				existRemoteFileParas.put("syncid", syncid);
				JSONObject existFileresultobj = HttpUtils.httpPost(existRemoteFileUrl, existRemoteFileParas);
				if (existFileresultobj.getInt("STATE") != 0) {
					throw new RuntimeException(existFileresultobj.getString("MESSAGE"));
				}
				if (existFileresultobj.getBoolean("ISREPEAT")) {
					return existFileresultobj;
				}
			}
			file = upFile;
			// 启动远程执行函数
			startGetProgressTheard();
			uploadParas.put("processkey", processkey);
			uploadParas.put("filename", filename);
			if (StringUtils.isNotBlank(syncid)) {
				uploadParas.put("syncid", syncid);
			}
			String result = post(uploadUrl, uploadParas, file, handles);
			JSONObject obj = new JSONObject(result);
			if (obj.getInt("STATE") == 0) {
				return obj;
			} else {
				throw new RuntimeException(obj.getString("MESSAGE"));
			}
		} finally {
			// 关闭远程执行函数
			start = 2;
		}
	}

	// 0未开始上传，1文件传输完成，2远程处理完成（全部完成）
	private int start = 0;

	private void startGetProgressTheard() {
		new Thread() {
			public void run() {
				while (start < 2) {
					try {// 文件未全部上传完毕
						if (start > 0) {
							// 文件以及上传，等待远程处理
							HashMap<String, String> newMap = new HashMap<>();
							newMap.putAll(getProgressParas);
							newMap.put("processkey", processkey);
							JSONObject json = HttpUtils.httpPost(progressUrl, newMap);
							if (handles != null) {
								String state = json.get("STATE").toString();
								if (state.equals("0")) {
									handles.handle(50 + json.getInt("PROCESS") / 2,
											file.length() * json.getInt("PROCESS") / 100, file.length(), "REMOTE");
								}
							}
						}
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public String post(String url, Map<String, String> map, final File file, final ProgressHandle hanle)
			throws IOException {
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
				.readTimeout(20 * 60 * 1000, TimeUnit.MILLISECONDS).writeTimeout(20 * 60 * 1000, TimeUnit.MILLISECONDS)
				.build();
		MultipartBody.Builder builder = new MultipartBody.Builder();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
			}
		}
		RequestBody fileBody = new RequestBody() {
			@Override
			public long contentLength() throws IOException {
				return file.length();
			}

			@Override
			public MediaType contentType() {
				String TYPE = "application/octet-stream";
				return MediaType.parse(TYPE);
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				int byteread = 0;
				File oldfile = file;
				if (oldfile.exists()) { 
					InputStream inStream = null;
					try {
						inStream = new FileInputStream(file);
						byte[] buffer = new byte[9999];
						int readed = 0;
						int lastpressent = 0;
						while ((byteread = inStream.read(buffer)) != -1) {
							readed = readed + buffer.length;
							int percentage = (int) (1.0 * readed / file.length() * 100);
							if (percentage > 99) {
								start = 1;
							}
							if (hanle != null) {
								int sendInt = percentage;
								if (StringUtils.isNotBlank(progressUrl)) {
									sendInt = sendInt / 2;
								}
								if (lastpressent != sendInt) {
									hanle.handle(sendInt, readed, file.length(), "UPLOAD");
								}
								lastpressent = sendInt;
							}
							sink.write(buffer, 0, byteread);
						}
					} finally {
						inStream.close();
					}
				}
			}
		};
		RequestBody requestBody = builder.setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), fileBody)
				.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	 
	public interface ProgressHandle {
		public void handle(int percent, long allsize, long completesize, String state);
	}

}
