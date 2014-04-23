package com.xiao.myyun;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class Remote {
	public Remote() {
		
	}

	public boolean connect() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.BASEURL);
		int code = 0;
		try {

			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			HttpResponse response = httpclient.execute(httpPost);

			code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				Log.i("msg", "connect success...code:" + code);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("msg", "connect failed...code:" + code);
			return false;
		}
		Log.i("msg", "connect failed...code:" + code);
		return false;
	}

	public String sendJsonData(String type, String userName, String jsonData) {
		HttpClient httpclient = new DefaultHttpClient();
		String remote = "";
		if (type.equals(Constants.Type1)) {
			remote = Constants.REMOTE_ADDINFO_URL;
		} else if (type.equals(Constants.Type2)) {
			remote = Constants.REMOTE_ADDCONTACT_URL;
		}
		HttpPost httppost = new HttpPost(remote);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			// Your DATA
			nameValuePairs.add(new BasicNameValuePair("type", type));
			nameValuePairs.add(new BasicNameValuePair("username", userName));
			nameValuePairs.add(new BasicNameValuePair("jsondata", jsonData));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			return EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getJsonData(String type, String userName) {
		HttpClient httpclient = new DefaultHttpClient();
        String remotePath="";
        if(type==Constants.Type1){
        	remotePath=Constants.REMOTE_GETINFO_URL;
        }else if(type==Constants.Type3){ 
        	remotePath=Constants.REMOTE_GETFILEJSON_URL;
        }
		HttpPost httppost = new HttpPost(remotePath);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			// Your DATA
			nameValuePairs.add(new BasicNameValuePair("type", type));
			nameValuePairs.add(new BasicNameValuePair("username", userName));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			return EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public InputStream getDownloadInputStream(String userName, String fileName,
			String fileId) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(Constants.REMOTE_Download_URL);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			// Your DATA
			nameValuePairs.add(new BasicNameValuePair("username", userName));
			nameValuePairs.add(new BasicNameValuePair("filename", fileName));
			nameValuePairs.add(new BasicNameValuePair("fileid", fileId));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));

			HttpResponse response = httpclient.execute(httppost);
			Header[] headers = response.getAllHeaders();
			long size = 0;// 文件大小 
			for (Header h : headers) {
				 if ("Content-Length".equals(h.getName())) {
					size = Long.valueOf(h.getValue());
				}
			}
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {  
			      return null;
	        }  
               return response.getEntity().getContent();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
