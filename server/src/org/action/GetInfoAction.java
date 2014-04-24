package org.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.store.Sms;

import com.opensymphony.xwork2.ActionSupport;

public class GetInfoAction extends ActionSupport implements
ServletRequestAware, ServletResponseAware, ServletContextAware{
	private  ServletContext context; 
	private HttpServletRequest request;
	private HttpServletResponse response;
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		this.context=arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}

	@Override
	public String execute() throws Exception {
		request.setCharacterEncoding("utf-8");
		String userName = request.getParameter("username"); 
		Sms sms=new Sms(); 
	 	String jsonData=sms.getSmsJsonData(userName);   
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=response.getWriter();
		pw.write(jsonData); 
		return null;
	}
}
