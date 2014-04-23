package com.xiao.myyun; 
import android.os.Parcel;
import android.os.Parcelable;

public class FSInfo implements Parcelable{

	public String name;
	public String path; 
	public String flag;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(path);
		dest.writeString(flag);
	}
	
	public static final Parcelable.Creator<FSInfo> CREATOR = new Parcelable.Creator<FSInfo>() {

		@Override
		public FSInfo createFromParcel(Parcel source) {
			FSInfo fs=new FSInfo();
			fs.name=source.readString();
			fs.path=source.readString();
			fs.flag=source.readString();
			return fs;
		}

		@Override
		public FSInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		} 
		
	};
	
}
