package com.xiao.myyun;

import java.util.ArrayList; 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class UploadList  extends Activity{
    private ListView listView;
	private UploadAdapter uploadAdapter;
	private ArrayList<FSInfo> data=null; 
	private LocalUtils lu; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploads); 
		lu=new LocalUtils(this); 
		listView=(ListView)findViewById(R.id.uploadlist);  
		setListView();
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDeleteDialog(position,data);
				return false;
			}
			
		}); 
		UploadThread ut=new UploadThread(this);
		ut.upload(data,myHandler);
	}
	
	@SuppressLint("HandlerLeak")
	public Handler myHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) { 
			super.handleMessage(msg);
			switch(msg.what){
			case 1:
				setListView();
			}
		}
		
	};
	private void setListView() {
		data=lu.getUploadList();
		uploadAdapter=new UploadAdapter(this,data,listView);
		listView.setAdapter(uploadAdapter); 
	}

	protected void showDeleteDialog(final int position,final ArrayList<FSInfo> data) {
		new AlertDialog.Builder(this).setTitle("提示")
		.setMessage("是否删除？")
		.setPositiveButton("删除", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) { 
				 if(lu.deleteUploadedTable(data.get(position).getName(),data.get(position).getPath())){
					 Toast.makeText(UploadList.this,"删除成功",Toast.LENGTH_LONG).show();
					 setListView();//refresh
				 }
			}
		}).setNegativeButton("取消", null).show();
		
	}

}
