package org.store;
 
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration; 
import org.bean.SmsInfo; 
/**
 * 短信操作类，包括存储短信、获取短信json等。
 * @author xqs 
 *
 */
public class Sms {
	
	private Configuration conf = null; 
	public Sms() {
		conf = HBaseConfiguration.create();
		conf.set("hbase.master", Constants.HostName+":60000");
		conf.set("hbase.zookeeper.quorum", Constants.HostName);
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
	} 
	
	public void storeToHbase(String userName ,String jsonData) throws Exception{
		HBaseUtil hu=new HBaseUtil(); 
		hu.creatTable(Constants.TableName,Constants.Familys);
		List<SmsInfo> data=hu.parseSmsJsonData(jsonData);
		List<SmsInfo> data2=getSmsList(userName);
		boolean have;
		int size=data2.size();
		for(int i=0;i<data.size();i++)
		{
			SmsInfo smsInfo=data.get(i); 
			have=false;
			if(data2!=null){
				for(SmsInfo smsInfo2:data2){
					if(smsInfo.getAddress().equals(smsInfo2.getAddress())&&smsInfo.getBody().equals(smsInfo2.getBody())){
						have=true;
						break;
					}
				}
			} 
			if(!have){
				String id=hu.getValue(Constants.PublicKey,Constants.Family,Constants.PublicId);
				if(id==null){
					id="0";
				}
				id=Utils.zhuanhuan(Integer.parseInt(id)+1);
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"id", smsInfo.getId());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"thread_id", smsInfo.getThread_id());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"name", smsInfo.getName());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"address", smsInfo.getAddress());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"body", smsInfo.getBody());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"date", Long.toString(smsInfo.getDate()));
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"read", smsInfo.getRead());
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"type", Integer.toString(smsInfo.getType()));
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"protocol", Integer.toString(smsInfo.getProtocol()));
				hu.addRecord(userName+id,Constants.Family,Constants.ColumnPrefix1+"status", smsInfo.getStatus()); 
				hu.addRecord(Constants.PublicKey,Constants.Family,Constants.PublicId,id);
			} 
		}  
	}
	
	public String getSmsJsonData(String userName){
		HBaseUtil hu=new HBaseUtil(); 
		return hu.getRecords(userName,Constants.ColumnPrefix1);
	}

	public List<SmsInfo>  getSmsList(String userName){
		HBaseUtil hu=new HBaseUtil();
		return hu.parseSmsJsonData(getSmsJsonData(userName));
	}
	
}
