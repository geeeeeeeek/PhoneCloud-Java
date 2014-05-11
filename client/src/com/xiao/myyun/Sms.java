package com.xiao.myyun;

import java.util.List;
import com.xiao.bean.SmsInfo;
import android.content.Context;

public class Sms {

	public Context ct;
	public Sms(Context ct){
		this.ct=ct;
	}
	
	public void beifen(){
		Thread th=new Thread(){
			public void run(){
				String smsJsonData=SmsUtil.getSmsJsonData(ct);
				if(smsJsonData.equals(""))
					return; 
				Remote remote=new Remote();
				if(remote.connect()){
					String result=remote.sendJsonData(Constants.Type1, Constants.UserName, smsJsonData);
					if(result.equals("ok")){
						System.out.println("beifen success!");
					}
				}
			}
		};
		th.start();
	}
	
	public void huanyuan(){
		Thread th=new Thread(){
			public void run(){
				Thread th=new Thread(){
					public void run(){ 
						Remote remote=new Remote();
						if(remote.connect()){
							String result=remote.getJsonData(Constants.Type1, Constants.UserName); 
							List<SmsInfo> smsData=SmsUtil.getSmsInfoListFromJsonData(result);
							SmsUtil.insertSms(ct,smsData);
						}
					}
				};
				th.start();
			}
		};
		th.start();
	}
	
	
}
