package org.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.store.MyFile;
import org.store.Sms;

import com.opensymphony.xwork2.ActionSupport;

public class GetFileJsonAction extends ActionSupport {
	 
	@Override
	public String execute() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
        HttpServletResponse response=(HttpServletResponse) ServletActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
        String userName=request.getParameter("username");
        MyFile mf=new MyFile(); 
	 	String jsonData=mf.getFileJsonData(userName);   
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=response.getWriter();
		pw.write(jsonData); 
		return null;
	} 
	
}