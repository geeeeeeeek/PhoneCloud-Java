package com.xiao.myyun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xiao.bean.FormFile;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.util.Log;

public class Utils {
	
	public static boolean isAvailable(Activity activity) {
		boolean bool = false;
		try {
			ConnectivityManager connManager = (ConnectivityManager) activity
					.getSystemService("connectivity");

			NetworkInfo localNetworkInfo = connManager.getActiveNetworkInfo();
			if (localNetworkInfo == null)
				bool = false;
			else {
				if (localNetworkInfo.isAvailable()) {
					bool = true;
				} else {
					bool = false;
				}
			}
            
			State state = connManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState();
			if (State.CONNECTED == state) {
				Log.i("msg", "GPRS"); 
			}
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                     .getState();
			if (State.CONNECTED == state) {
				Log.i("msg", "WIFI"); 
			}

		} catch (Exception localException) {
		}
		return bool;
	}
	
	public static boolean getServiceStatus(Context ct,String className){
		ActivityManager manager=(ActivityManager)ct.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = manager.getRunningServices(100);
		for (int i = 0; i < mServiceList.size(); i++) {
			String classString=mServiceList.get(i).service.getClassName(); 
			if (classString.equals(className)) { 
				return true;
			}
		}
		return false;
	}
	
	public static boolean uploadFile(String path) {
		File file=new File(path);
        System.out.println("upload start: "+file.getName());
        try {
            String requestUrl =Constants.REMOTE_ADDFILE_URL;
            //请求普通信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", Constants.UserName);
            params.put("fileName", file.getName());
            //上传文件
            FormFile formfile = new FormFile(file.getName(), file, "file", "application/octet-stream");
            
            if(SocketHttpRequester.post(requestUrl, params, formfile)){ 
                System.out.println("upload success");
                return true;
            }else{
            	 System.out.println("upload fail"); 
            }
        } catch (Exception e) {
        	System.out.println("upload error");
            e.printStackTrace();
            return false;
        }
        return false;
    }
 

	public static boolean downloadFile(String fileName,String fileId) {
		String dirName = "";
		dirName = Environment.getExternalStorageDirectory()+Constants.SavePath;
        File dirFile=new File(dirName);
        if(!dirFile.exists()){
        	dirFile.mkdir();
        }
        File file=new File(dirName+fileName);
        
        try{ 
        	 if(!file.exists()){
            	file.createNewFile();
             }
        	 InputStream is=null;
             OutputStream os = new FileOutputStream(file);    
        	 Remote remote =new Remote();
        	 if(remote.connect()){
        		is= remote.getDownloadInputStream(Constants.UserName, fileName, fileId);
        	 }
        	 
             byte[] bs = new byte[1024];    
             int len;    
             while ((len = is.read(bs)) >0) {   
                 os.write(bs, 0, len);   
             }   
             os.close();  
             is.close();

             System.out.println("-------------downloaded successful" );  
             return true;
        }catch(Exception e){
        	e.printStackTrace();  
    		return false;
        } 

	}

}
