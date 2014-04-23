package com.xiao.myyun;

import java.util.ArrayList;

import com.xiao.bean.FileInfo;

import android.content.Context;
import android.database.Cursor;

public class LocalUtils {

   private Context context;
   private LocalDB localDb;
   public LocalUtils(Context context){
	   this.context=context;
	   localDb =new LocalDB(this.context);
   }
   
   public void createUploadTable(ArrayList<FSInfo> data){
	   localDb.open();
	   for(FSInfo fs:data){ 
		   localDb.createTable1(fs.getName(), fs.getPath(), fs.getFlag());
	   }
	   localDb.close();
   }
   
   public void createDownloadTable(ArrayList<FileInfo> data){
	   localDb.open();
	   for(FileInfo fi:data){ 
		   localDb.createTable2(fi.getFileName(),fi.getFileId(),fi.getFlag());
	   }
	   localDb.close();
   }
   
   public void updateUploadTable(String filename,String path, String uploadflag){
	   localDb.open();
	   localDb.updateTable1(filename,path,uploadflag);
	   localDb.close();
   }
   
   public void updateDownloadTable(String filename,String fileid, String downloadflag){
	   localDb.open();
	   localDb.updateTable2(filename,fileid,downloadflag);
	   localDb.close();
   }
   
   public boolean checkUploadedTable(String filename,String path){
	   localDb.open();
	   boolean bool=localDb.checkTable1(filename,path,"yes");
	   localDb.close();
	   return bool;
   }
   
   public boolean checkDownloadedTable(String filename,String fileid){
	   localDb.open();
	   boolean bool=localDb.checkTable2(filename,fileid,"yes");
	   localDb.close();
	   return bool;
   }
   
   public boolean deleteUploadedTable(String filename,String path){
	   localDb.open();
	   int n=localDb.deleteTable1(filename, path);
	   localDb.close();
	   if(n>0){
		   return true;
	   }else{
		   return false;
	   }
   }
   
   public boolean deleteDownloadTable(String filename,String fileid){
	   localDb.open();
	   int n=localDb.deleteTable2(filename, fileid);
	   localDb.close();
	   if(n>0){
		   return true;
	   }else{
		   return false;
	   }
   }
   
   public ArrayList<FSInfo> getUploadList(){
	   ArrayList<FSInfo> data=new ArrayList<FSInfo>();
	   FSInfo fs=new FSInfo();
	   localDb.open();
	   Cursor c=localDb.getTable1();
	   if(c.moveToFirst()){
	    	 do{
	    		 fs=new FSInfo();
	    		 fs.setName((c.getString(c.getColumnIndex("filename"))));
	    		 fs.setPath(c.getString(c.getColumnIndex("path")));
	    		 fs.setFlag(c.getString(c.getColumnIndex("uploadflag")));
	    		data.add(fs);
	    	 }while(c.moveToNext());
	    }
	    c.close();
	    localDb.close();
	    return data;
   }
   
   public ArrayList<FileInfo> getDownloadList(){
	   ArrayList<FileInfo> data=new ArrayList<FileInfo>();
	   FileInfo fi=new FileInfo();
	   localDb.open();
	   Cursor c=localDb.getTable2();
	   if(c.moveToFirst()){
	    	 do{
	    		 fi=new FileInfo();
	    		 fi.setFileName(c.getString(c.getColumnIndex("filename")));
	    		 fi.setFileId(c.getString(c.getColumnIndex("fileid")));
	    		 fi.setFlag(c.getString(c.getColumnIndex("downloadflag")));
	    		data.add(fi);
	    	 }while(c.moveToNext());
	    }
	    c.close();
	    localDb.close();
	    return data;
   }
   
   
   public void close(){
	   localDb.close();
   }
}
