package com.xiao.bean;

public class SmsInfo {
	private String id;
	private String thread_id; 
	private String address;
	private String body;
	private long date;
	private int type;
	private int protocol;
	private String read;
	private String status;
	
 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	 
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getThread_id() {
		return thread_id;
	}
	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
 
}
