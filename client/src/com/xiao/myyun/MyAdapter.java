package com.xiao.myyun;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.BaseAdapter;
import android.widget.CheckBox; 
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView; 
public class MyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Map<String, Object>> data; 
	public HashMap<Integer,Boolean> isSelected; 
    public HashMap<Integer,String> fsInfoData;
	public MyAdapter(Context context, final List<Map<String, Object>> data,ListView listView ) {
		this.mInflater = LayoutInflater.from(context);
		this.data = data;
		isSelected = new HashMap<Integer,Boolean>();
		for(int i = 0; i<data.size(); i++){
		    isSelected.put(i, false);
		}  
		fsInfoData=new HashMap<Integer,String>();
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
			convertView = mInflater.inflate(R.layout.file_row, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.img=(ImageView)convertView.findViewById(R.id.img);
			holder.ckb = (CheckBox) convertView.findViewById(R.id.ckb); 

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 
		holder.name.setText(data.get(position).get("name").toString());  
		if(data.get(position).get("flag").toString().equals("yes")){ 
			holder.ckb.setVisibility(View.VISIBLE);
			holder.ckb.setChecked(isSelected.get(position));
		}else{
			holder.ckb.setVisibility(View.INVISIBLE);
		} 
		 
		
		holder.ckb.setOnClickListener(new OnClickListener() {
		    @Override 
		    public void onClick(View v) {
		    if(isSelected.get(position)){
		    isSelected.put(position, false);
		    fsInfoData.remove(position); 
		    }else{
		    isSelected.put(position, true);
		    fsInfoData.put(position, data.get(position).get("name").toString());
		    }
		    notifyDataSetChanged();
		    }
		    });
		
		holder.img.setImageResource(Integer.parseInt(data.get(position).get("img").toString()));
	           
		return convertView;
	}

	public final class ViewHolder {
		public TextView name;
		public ImageView img;
		public CheckBox ckb; 
	}
	ViewHolder holder = null;


}