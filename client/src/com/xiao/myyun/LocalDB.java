package com.xiao.myyun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; 

public class LocalDB {
	public static final String KEY_TABLE1 = "uploadlist";//上传表
	public static final String KEY_TABLE2="downloadlist";//下载表
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_FILENAME = "filename";
	public static final String KEY_FILEID="fileid";
	public static final String KEY_PATH= "path";
	public static final String KEY_UPLOADFLAG = "uploadflag";
	public static final String KEY_DOWNLOADFLAG="downloadflag";
	Context ctx;
	MyDBAdapter adapter;
	SQLiteDatabase db;
	
	class MyDBAdapter extends SQLiteOpenHelper {

		public MyDBAdapter(Context context) {
			super(context, "yun_db", null, 2);
			Log.i("msg", "数据库....yun_db");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql1 = "create table " + KEY_TABLE1 + " (" + KEY_ROWID
					+ " integer primary key autoincrement, " + KEY_FILENAME
					+ " text, " + KEY_PATH + " text," + KEY_UPLOADFLAG
					+ " text);"; 
			String sql2="create table "+KEY_TABLE2+" ("+KEY_ROWID
					+" integer primary key autoincrement,"+KEY_FILENAME
					+" text,"+KEY_FILEID+" text,"+KEY_DOWNLOADFLAG
					+" text);";
			db.execSQL(sql1); 
			db.execSQL(sql2);
			Log.i("msg", "新建表" + KEY_TABLE1 + " 成功"); 
			Log.i("msg", "新建表" + KEY_TABLE2 + " 成功");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
	
	public LocalDB(Context ctx) {
		this.ctx = ctx;
	}

	public LocalDB open() {
		adapter = new MyDBAdapter(ctx);
		db = adapter.getWritableDatabase();
		return this;
	}

	public void close() {
		adapter.close();
	}
	
	// 新建
	public long createTable1(String filename, String path, String uploadflag) {
		if (checkTable1(filename, path)) {
			return -1;
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FILENAME, filename);
		initialValues.put(KEY_PATH, path);
		initialValues.put(KEY_UPLOADFLAG, uploadflag);
		Log.i("msg", "条目插入成功.....");
		return db.insert(KEY_TABLE1, null, initialValues);
	}
	
	// 新建
		public long createTable2(String filename, String fileid, String downloadflag) {
			if (checkTable2(filename, fileid)) {
				return -1;
			}
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_FILENAME, filename);
			initialValues.put(KEY_FILEID,fileid);
			initialValues.put(KEY_DOWNLOADFLAG,downloadflag);
			Log.i("msg", "条目插入成功.....");
			return db.insert(KEY_TABLE2, null, initialValues);
		}
		
	public boolean checkTable1(String filename,String path) {
		Cursor c = db.query(false, KEY_TABLE1, new String[] { KEY_FILENAME,KEY_PATH },
				KEY_FILENAME + "=? and "+KEY_PATH+"=?", new String[] { filename,path }, null, null, null,
				null);
		Log.i("msg", "检查条目....." + c.getCount() + "个");
		if (c.getCount() > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	public boolean checkTable2(String filename,String fileid) {
		Cursor c = db.query(false, KEY_TABLE2, new String[] { KEY_FILENAME,KEY_FILEID },
				KEY_FILENAME + "=? and "+KEY_FILEID+"=?", new String[] { filename,fileid }, null, null, null,
				null);
		Log.i("msg", "检查条目....." + c.getCount() + "个");
		if (c.getCount() > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	public boolean checkTable1(String filename,String path,String flag) {
		Cursor c = db.query(false, KEY_TABLE1, new String[] { KEY_FILENAME,KEY_PATH },
				KEY_FILENAME + "=? and "+KEY_PATH+"=? and "+KEY_UPLOADFLAG+"=?", new String[] { filename,path,flag }, null, null, null,
				null);
		Log.i("msg", "检查条目....." + c.getCount() + "个");
		if (c.getCount() > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	public boolean checkTable2(String filename,String fileid,String flag) {
		Cursor c = db.query(false, KEY_TABLE2, new String[] { KEY_FILENAME,KEY_FILEID,KEY_DOWNLOADFLAG },
				KEY_FILENAME + "=? and "+KEY_FILEID+"=? and "+KEY_DOWNLOADFLAG+"=?", new String[] { filename,fileid,flag }, null, null, null,
				null);
		Log.i("msg", "检查条目....." + c.getCount() + "个");
		if (c.getCount() > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	public Cursor getTable1(){
		return db.query(true, KEY_TABLE1, new String[] { KEY_ROWID, KEY_FILENAME,
				KEY_PATH, KEY_UPLOADFLAG}, null, null,null,null,null,null); 
	}
	public Cursor getTable2(){
		return db.query(true, KEY_TABLE2, new String[] { KEY_ROWID, KEY_FILENAME,
				KEY_FILEID, KEY_DOWNLOADFLAG}, null, null,null,null,null,null); 
	}
	
	public boolean updateTable1(String filename,String path, String uploadflag){
		ContentValues args = new ContentValues();
		args.put(KEY_UPLOADFLAG, uploadflag);
		return db.update(KEY_TABLE1, args, KEY_FILENAME + "=? and "+KEY_PATH+"=?" , new String[]{filename,path}) > 0;
	}
	
	public boolean updateTable2(String filename,String fileid, String downloadflag){
		ContentValues args = new ContentValues();
		args.put(KEY_DOWNLOADFLAG, downloadflag);
		return db.update(KEY_TABLE2, args, KEY_FILENAME + "=? and "+KEY_FILEID+"=?" , new String[]{filename,fileid}) > 0;
	}
	
	public int deleteTable1(String filename,String path){ 
		return db.delete(KEY_TABLE1,KEY_FILENAME+"=? and "+KEY_PATH+"=?",new String[]{filename,path});
	}
	
	public int deleteTable2(String filename,String fileid){
		return db.delete(KEY_TABLE2,KEY_FILENAME+"=? and "+KEY_FILEID+"=?",new String[]{filename,fileid});
	}
}
