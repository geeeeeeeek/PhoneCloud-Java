package com.xiao.myyun;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.xiao.bean.FileInfo;

public class FileRestore extends Activity implements OnItemClickListener, OnClickListener{ 
	private Button backButton;
	private Button restoreButton;
	private ListView listView;
	private ArrayList<FileInfo> data;
	private FileRestoreAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_restore);
		backButton=(Button)findViewById(R.id.backbutton);
		restoreButton=(Button)findViewById(R.id.restore);
		listView=(ListView)findViewById(R.id.itemlist);
		backButton.setOnClickListener(this);
		restoreButton.setOnClickListener(this); 
		loadData(); //load remote data
	}
	
	private void loadData() { 
		Thread th=new Thread(){
			public void run(){
				Remote remote=new Remote();
				if(remote.connect()){
					String jsonData=remote.getJsonData(Constants.Type3,Constants.UserName);
					data=FileUtil.getFileInfoListFromJsonData(jsonData);
					if(data.size()>0){
						myHandler.sendEmptyMessage(1);
					}
				} 
			}
		};
		th.start(); 
	}
	
	Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			setListView();
		}
		
	};

	private void setListView() {
		 adapter=new FileRestoreAdapter(this,data,listView);
		 listView.setAdapter(adapter);
		 listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(adapter.isSelected.get(position)){
		  adapter.isSelected.put(position, false);
          adapter.fsInfoData.remove(position);
	    }else{
          adapter.isSelected.put(position, true);
          FileInfo fi=new FileInfo();
          fi.setFileId(data.get(position).getFileId());
          fi.setFileName(data.get(position).getFileName());
          fi.setFlag(data.get(position).getFlag());
          adapter.fsInfoData.add(fi);
	    }
          adapter.notifyDataSetChanged();
	      
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.backbutton:
			this.finish();
			break;
		case R.id.restore: 
			if(adapter.fsInfoData.size()<=0){
				break;
			}
			LocalUtils lu=new LocalUtils(this);
			lu.createDownloadTable((ArrayList<FileInfo>) adapter.fsInfoData);
			Intent in=new Intent(this,DownloadList.class);
			startActivity(in);
			finish();
			break;
		}
		
	}

}
