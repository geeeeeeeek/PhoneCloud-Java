package com.xiao.myyun; 

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class UploadThread{
    private Context ct;
    private Thread th;
	public UploadThread(Context ct){
		this.ct=ct;
	}
	public void upload(final ArrayList<FSInfo> data,final Handler myHandler){
		th=new Thread(){
			public void run(){  
				LocalUtils lu=new LocalUtils(ct);
				for(FSInfo fs:data){ 
					if(lu.checkUploadedTable(fs.getName(),fs.getPath()))
						continue;
					if(Utils.uploadFile(fs.getPath()+"/"+fs.getName())){
						lu.updateUploadTable(fs.getName(), fs.getPath(), "yes"); 
						Message msg=new Message();
						msg.what=1;
						myHandler.sendMessage(msg);
					}
				}
				if(this.isAlive()){ 
				}
				
			}
		};
		th.start();
	}
 
}
