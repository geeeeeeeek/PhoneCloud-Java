package org.store;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException; 
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
/**
 * 工具类
 * @author xqs
 * 
 */
public class Utils {

	public static String zhuanhuan(int i) {
		String str = String.format("%010d", i);//such as 123 => 0000000123
		return str; 
	}
	
	/**
	 * 将文件转换为字节数组
	 * @param path
	 * @return
	 */
	public static byte[] file2byte(String path){
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }
	    catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	}
	
	/**
	 * 将字节数组写入文件
	 * @param data
	 * @param path
	 */
	public static void byte2File(byte[] data,String path){
	    if(data.length<3||path.equals("")) return;
	    try{
	    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	    imageOutput.write(data, 0, data.length);
	    imageOutput.close(); 
	    } catch(Exception ex) {
	      System.out.println("Exception: " + ex);
	      ex.printStackTrace();
	    }
	  }



}
