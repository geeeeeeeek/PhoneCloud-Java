package com.xiao.myyun;

import java.util.ArrayList; 
import org.json.JSONArray;
import org.json.JSONObject; 
import com.xiao.bean.FileInfo; 

public class FileUtil {

	public static ArrayList<FileInfo> getFileInfoListFromJsonData(String jsonData) {

		try {
			JSONArray arr = new JSONArray(jsonData);
			ArrayList<FileInfo> list = new ArrayList<FileInfo>();
			FileInfo fi;
			for (int i = 0; i < arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				fi = new FileInfo();
				fi.setFileName(temp.getString("fileName"));
				fi.setFileId(temp.getString("fileId")); 
				fi.setFlag("no");
				list.add(fi);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
