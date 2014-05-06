package org.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.bean.MyFileInfo;
import org.store.Constants;
import org.store.MyFile;

import com.opensymphony.xwork2.ActionSupport;

public class UploadFileAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 上传文件域
    private File file;
    // 上传文件类型 
    private String fileContentType;
    // 封装上传文件名
    private String fileFileName;
    // 接受依赖注入的属性
    private String savePath;

    private PrintWriter pw;
    @Override
    public String execute() {
    	MyFileInfo fi=new MyFileInfo();
		MyFile mf=new MyFile();
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpServletResponse response=ServletActionContext.getResponse();
        FileOutputStream fos = null;
        FileInputStream fis = null; 
        try {
            pw=response.getWriter();
            System.out.println("获取Android端传过来的文件信息：");
            System.out.println("用户名："+request.getParameter("username"));  
            System.out.println("文件名："+request.getParameter("fileName")); 
            System.out.println("文件存放目录: "+getSavePath());
            System.out.println("文件名称: "+fileFileName);
            System.out.println("文件大小: "+file.length());
            System.out.println("文件类型: "+fileContentType);

			fi.setClientId(request.getParameter("username"));
			fi.setFileName(request.getParameter("fileName"));
			fi.setFileSize(Long.toString(file.length()));
            if(file.length()>0){
    			String id=mf.getPublicId();
            	if(file.length()>=Constants.UploadSize){
            		String filePath=getUploadDir()+"/"+getFileFileName();
            		fos=new FileOutputStream(filePath);
            		fis=new FileInputStream(getFile());
            		byte[] buf=new byte[1024];
            		int len=0;
            		while((len=fis.read(buf))!=-1){
            			fos.write(buf,0,len);
            		}
            		fos.close();
            		if(mf.addBigFile(id,filePath)){
            			fi.setIsSmall("no");
            			fi.setUploaded("yes");
            			mf.storeToHbase(request.getParameter("username"),fi);
            		}
            	}else{ 
            		fos = new FileOutputStream(getSavePath() + "/" + id);
            		fis = new FileInputStream(getFile());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
        			fi.setIsSmall("yes");
        			fi.setUploaded("no");
        			mf.storeToHbase(request.getParameter("username"),fi);
                    mf.checkUploadPath(getSavePath());
            	}
            }
            System.out.println("文件上传成功");
    		pw.println("success\n"); 
    		pw.flush();
        } catch (Exception e) {
            System.out.println("文件上传失败"); 
    		pw.println("fail\n"); 
    		pw.flush();
            e.printStackTrace();
        } finally {
            close(fos, fis);
        }
        return null;
    }

    /**
     * 文件存放目录
     * 
     * @return
     */
    public String getSavePath() throws Exception{
        return ServletActionContext.getServletContext().getRealPath(savePath); 
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    
    public String getUploadDir(){
    	return ServletActionContext.getServletContext().getRealPath("/upload");
    }
  
	public File getFile() { 
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


    public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }



}
