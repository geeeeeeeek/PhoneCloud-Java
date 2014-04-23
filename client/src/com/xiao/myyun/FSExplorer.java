package com.xiao.myyun;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FSExplorer extends Activity implements OnItemClickListener, OnClickListener {
	private static final String TAG = "FSExplorer";
	private Button backfse;
	private Button uploadButton;
	private MyAdapter myAdapter;
	private ArrayList<FSInfo> uploadList;

	ListView itemlist = null;
	String path = "/sdcard";
	String nowPath="";
	List<Map<String, Object>> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.files_brouse);
		backfse=(Button)findViewById(R.id.backbutton4);
		uploadButton=(Button)findViewById(R.id.uploadbutton);
		backfse.setOnClickListener(this);
		uploadButton.setOnClickListener(this);
		setTitle("文件浏览器");
		uploadList=new ArrayList<FSInfo>();
		itemlist = (ListView) findViewById(R.id.itemlist);
		refreshListItems(path);
	}

	private void refreshListItems(String path) {
		setTitle("文件浏览器 > "+path);
		list = buildListForSimpleAdapter(path);
		myAdapter=new MyAdapter(this,list,itemlist); 
		itemlist.setAdapter(myAdapter);
		itemlist.setOnItemClickListener(this);
		itemlist.setSelection(0);
	}

	private List<Map<String, Object>> buildListForSimpleAdapter(String path) {
		nowPath=path;
		File[] files = new File(path).listFiles();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(files.length);
		// 
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", "/");
		root.put("img", R.drawable.file_root);
		root.put("path", "sd卡根目录");
		root.put("flag", "no");
		list.add(root);
		//
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("name", "..");
		pmap.put("img", R.drawable.file_paranet);
		pmap.put("path", "上一级目录");
		pmap.put("flag", "no");
		list.add(pmap);
		//
		for (File file : files){
			Map<String, Object> map = new HashMap<String, Object>();
			if(file.isDirectory()){
				map.put("img", R.drawable.directory);
				map.put("flag", "no");
				map.put("name", file.getName());
				map.put("path", file.getPath());
				list.add(map);  
			}
		}
		for (File file : files){
			Map<String, Object> map = new HashMap<String, Object>();
			if(!file.isDirectory()){
				map.put("img", R.drawable.file_doc);
				map.put("flag", "yes");
				map.put("name", file.getName());
				map.put("path", file.getPath());
				list.add(map); 
				String fPath=file.getPath(); 
			} 
		}
		return list;
	}
	
	// 返回上级目录,判断path
	private void goToParent() {
		File file = new File(path);
		File str_pa = file.getParentFile();
		if (str_pa.getAbsolutePath().equals("/")) {
			Toast.makeText(this, "这是根目录", Toast.LENGTH_SHORT).show();
			refreshListItems(path);
		} else {
			path = str_pa.getAbsolutePath();
			refreshListItems(path);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Log.i(TAG, "item clicked! [" + position + "]");
		if (position == 0) {
			path = "/sdcard";
			refreshListItems(path);
		}else if(position == 1){
			goToParent();
		} else {
			path = (String) list.get(position).get("path");
			File file = new File(path);
			if (file.isDirectory())
				refreshListItems(path);
			else
				{
				if(myAdapter.isSelected.get(position)){
					  myAdapter.isSelected.put(position, false);
                      myAdapter.fsInfoData.remove(position);
				    }else{
				      myAdapter.isSelected.put(position, true);
				      myAdapter.fsInfoData.put(position,list.get(position).get("name").toString());
				    }
				      myAdapter.notifyDataSetChanged();
			     }
			}
	}
 

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.backbutton4:
			finish();
			break;
		case R.id.uploadbutton: 
			for(int i=0;i<list.size();i++){
				if(myAdapter.fsInfoData.get(i)!=null){  
					FSInfo fs=new FSInfo();
					fs.setName(myAdapter.fsInfoData.get(i).toString());
					fs.setPath(nowPath);
					fs.setFlag("no");
					uploadList.add(fs);
				}
			}
			if(uploadList.size()<=0){
				break;
			}
			LocalUtils lu=new LocalUtils(this);
			lu.createUploadTable(uploadList);
			Intent in=new Intent(this,UploadList.class);
			startActivity(in);
			finish();
			break;
		} 
	}

 

}
