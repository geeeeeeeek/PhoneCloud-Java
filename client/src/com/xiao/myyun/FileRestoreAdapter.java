package com.xiao.myyun;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView; 
import com.xiao.bean.FileInfo; 

public class FileRestoreAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private ArrayList<FileInfo> data; 
	public HashMap<Integer,Boolean> isSelected; 
    public List<FileInfo> fsInfoData;
	public FileRestoreAdapter(Context context, final ArrayList<FileInfo> data,ListView listView ) {
		this.mInflater = LayoutInflater.from(context);
		this.data = data;
		isSelected = new HashMap<Integer,Boolean>();
		for(int i = 0; i<data.size(); i++){
		    isSelected.put(i, false);
		}  
		fsInfoData=new ArrayList<FileInfo>();
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
			convertView = mInflater.inflate(R.layout.file_restore_row, null);
			holder.filename = (TextView) convertView.findViewById(R.id.filename); 
			holder.ckb = (CheckBox) convertView.findViewById(R.id.ckb); 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.filename.setText(data.get(position).getFileName());
		holder.ckb.setChecked(isSelected.get(position));
		holder.ckb.setOnClickListener(new OnClickListener() {
		    @Override 
		    public void onClick(View v) {
		    if(isSelected.get(position)){
			    isSelected.put(position, false);
			    fsInfoData.remove(position); 
		    }else{
			    isSelected.put(position, true);
			    FileInfo fi=new FileInfo();
			    fi.setFileId(data.get(position).getFileId());
			    fi.setFileName(data.get(position).getFileName());
			    fi.setFlag(data.get(position).getFlag());
			    fsInfoData.add(fi);
		    }
		    notifyDataSetChanged();
		    }
		    });
		         
		return convertView;
	}

	public final class ViewHolder {
		public TextView filename; 
		public CheckBox ckb; 
	}
	ViewHolder holder = null;


}
