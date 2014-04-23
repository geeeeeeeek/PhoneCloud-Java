package com.xiao.bean;

public class FileInfo {

	private String fileId;
	private String fileName;
	private String flag;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "FileInfo [fileId=" + fileId + ", fileName=" + fileName
				+ ", flag=" + flag + "]";
	}
	
}
