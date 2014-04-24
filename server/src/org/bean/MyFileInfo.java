package org.bean;
/**
 * 文件信息，包括客户端Id、文件id、文件名、文件大小、是否是小文件、是否已上传到Hdfs、本地路径
 * @author xqs
 *
 */
public class MyFileInfo {

	String clientId; 
	String fileId;
	String fileName;
	String fileSize;
	String isSmall;
	String uploaded;
	String localPath;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
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
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getIsSmall() {
		return isSmall;
	}
	public void setIsSmall(String isSmall) {
		this.isSmall = isSmall;
	}
	public String getUploaded() {
		return uploaded;
	}
	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	
	
}
