package org.store;

public class Constants {
	//主机名
	public static String HostName="myhadoop";
	//表名
	public static String TableName="phone"; 
	//列族名
	public static String Family="info"; 
	
	public static String Familys[]={Family};
	 
	//----------------前缀名字--------------------
	public static String ColumnPrefix1="msg";
	public static String ColumnPrefix2="contact";
	public static String ColumnPrefix3="file";
	public static String ColumnPrefix4="diary"; 
	
	//---------------用到的列名-------------------
	public static String MessageCount="messagecount";
	public static String Count="count";
	public static String PublicKey="public";
	public static String PublicId="publicId";
	public static String PublicSmallFile="publicSmallFile";
	public static String PublicSmallFileSize="publicSmallFileSize";
	public static String SmallFileSize="smallFileSize";
	public static String PublicRowKeys="publicRowKeys";
	public static String MapFilePath="mapFilePath";
	public static String PublicMapFilePath="publicMapFilePath";
	public static String PublicMapFilePathNumber="publicMapFilePathNumber";
	 
	//----------------路径--------------------------
	public static String HdfsDir="/uploadFiles/";
	public static String MapFileDir="/mapFile/";
	public static String WebRootDir="C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps";
	public static String LocalUploadDir=WebRootDir+"/Cloud/upload/";
	public static String LocalDownloadDir=WebRootDir+"/Cloud/downloadfile/";
	
	//小文件上传阈值，10M=10485760字节
	public static long UploadSize=10485760;

}
