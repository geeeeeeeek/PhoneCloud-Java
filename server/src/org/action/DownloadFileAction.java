package org.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.store.MyFile;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadFileAction extends ActionSupport { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=ServletActionContext.getRequest();
    HttpServletResponse response=(HttpServletResponse) ServletActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
    String path=ServletActionContext.getServletContext().getRealPath("/")+"downloadfile\\"+request.getParameter("fileid");
    
	 public InputStream getTargetFile() throws Exception {  
		      
	        File f = new File(path); 
	        if (f.exists()) { 
	            return new FileInputStream(f); 
	        } else { 
	            return null; 
	        } 
	} 
	 
	 public long getFileSize() { 
	        File f = new File(path); 
	        return f.length(); 
	} 
	 
	public String getFileName(){
		return "abc";
	}

    @Override
    public String execute() { 
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpServletResponse response=(HttpServletResponse) ServletActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);

        try {
            System.out.println("------------------------------------------");
            System.out.println("请求文件信息：");
            System.out.println("用户名："+request.getParameter("username"));  
            System.out.println("文件名："+request.getParameter("filename"));   
            System.out.println("文件id："+request.getParameter("fileid"));   
            MyFile mf=new MyFile();
            boolean bool=mf.getFile(request.getParameter("username"), request.getParameter("fileid"), request.getParameter("filename"));
            if(bool){
            	return SUCCESS;
            }
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return null;
    }

 



}
