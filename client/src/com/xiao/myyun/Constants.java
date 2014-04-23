package com.xiao.myyun; 

public class Constants {

	public static String UserName="xiao";
	
	public static String BASEURL = "http://172.16.34.90:8080/"; //远程url
	
	//message
	public static String REMOTE_ADDINFO_URL = BASEURL + "Cloud/addInfo";// 备份短信
	public static String REMOTE_GETINFO_URL=BASEURL+"Cloud/getInfo";  //还原短信
	//contact
	public static String REMOTE_ADDCONTACT_URL = BASEURL + "Cloud/addContact";//备份联系人 
	public static String REMOTE_GETCONTACT_URL=BASEURL+"Cloud/getContact"; //还原联系人
	//file
	public static String REMOTE_GETFILEJSON_URL=BASEURL+"Cloud/getFileJson";
	public static String REMOTE_ADDFILE_URL=BASEURL+"Cloud/uploadFile";
	public static String REMOTE_Download_URL=BASEURL+"Cloud/downloadFile";
	public static String SavePath="/MyDownload/";  
	public static String REMOTE_DownloadPath=BASEURL+"Cloud/downloadfile/"; 
	//type
	public static String Type1="message";
	public static String Type2="contact";
	public static String Type3="file";

}
