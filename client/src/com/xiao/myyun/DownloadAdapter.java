package com.xiao.myyun;

import java.util.ArrayList; 
import com.xiao.bean.FileInfo; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private ArrayList<FileInfo> data;  
	public DownloadAdapter(Context context, final ArrayList<FileInfo> data,ListView listView ) {
		this.mInflater = LayoutInflater.from(context);
		this.data = data; 
	}
	 

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView( final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.download_row, null);
			holder.filename = (TextView) convertView.findViewById(R.id.filename);
			holder.status=(TextView)convertView.findViewById(R.id.status);  
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.filename.setText(data.get(position).getFileName()); 
		if(data.get(position).getFlag().equals("yes")){ 
			holder.status.setText("下载完成");
		}else{
			holder.status.setText("正在下载...");
		}
		         
		return convertView;
	}

	public final class ViewHolder {
		public TextView filename;
		public TextView status;
	}
	ViewHolder holder = null;


}
