package com.xiao.myyun;

import java.util.ArrayList;

import com.xiao.bean.FileInfo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class DownLoadThread {
	 private Context ct;
	 private Thread th;
	 
	 public DownLoadThread(Context ct){
			this.ct=ct;
	 }
	 
	 public void download(final ArrayList<FileInfo> data,final Handler myHandler){
		 th=new Thread(){
				public void run(){  
					LocalUtils lu=new LocalUtils(ct);
					for(FileInfo fi:data){ 
						if(lu.checkDownloadedTable(fi.getFileName(),fi.getFileId()))
							continue;
						 
						if(Utils.downloadFile(fi.getFileName(),fi.getFileId())){ 
							lu.updateDownloadTable(fi.getFileName(),fi.getFileId(),"yes");
							Message msg=new Message();
							msg.what=1;
							myHandler.sendMessage(msg);
						} 
					}  
				}
			};
			th.start();
	 }
}
