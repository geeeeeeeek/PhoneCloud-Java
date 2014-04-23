package com.xiao.myyun; 
import java.util.List; 
import android.content.Context; 
import com.xiao.bean.ContactInfo;

public class Contact {
	private Context ct;

	public Contact(Context ct) {
		this.ct = ct;
	}
    
	public void tongbu(){
		Thread th=new Thread(){
			public void run(){
				 try {
					 String contactJsonData=ContactUtil.getContactJsonData(ct);
						
						Remote remote=new Remote();
						if(remote.connect()){
							String result=remote.sendJsonData(Constants.Type2, Constants.UserName, contactJsonData);
							System.out.println(result);
							List<ContactInfo> data=ContactUtil.getContactListFromJsonData(result);
							ContactUtil.insertContact(ct, data);
							
						}
				} catch (Exception e) { 
					e.printStackTrace();
				} 
			}
		};
		th.start();
	}
}
