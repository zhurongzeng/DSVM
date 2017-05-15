package com.dashuf.dsvm.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HttpClientUtils {
	
	public JSONObject sendHttpPost(String url, String parms) {
		System.out.println("进入sendHttpPost");
		if (null == url || "".equals(url)) {
			return null;
		}

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringBuffer json = new StringBuffer();
		JSONObject response = null;
		String line = null;
		try {
			post.setEntity(new StringEntity(parms, "UTF-8"));
			CloseableHttpResponse res = client.execute(post);
			HttpEntity entity = res.getEntity();
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
			String content = java.net.URLDecoder.decode(json.toString(), "UTF-8");
			response = new JSONObject(content);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("sendHttpPost异常");
		}
		return response;
	}
}
