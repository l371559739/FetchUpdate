package core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**@author find
 * 文件下载类
 * */
public class FileDownload {
	/**@param httpUrl 下载连接
	 * @param saveFile 本地保存目录
	 * */
	public boolean DownloadFile(String httpUrl,String saveFile){  
	       int bytesum = 0;  
	       int byteread = 0;  
	       URL url = null;  
	    try {  
	        url = new URL(httpUrl);  
	    } catch (MalformedURLException e1) {  
	    	System.out.println("URL　format error");
//	        e1.printStackTrace();  
	        return false;  
	    }  
	       try {  
	           URLConnection conn = url.openConnection();  
	           InputStream inStream = conn.getInputStream();  
	           FileOutputStream fs = new FileOutputStream(saveFile);  
	  
	           byte[] buffer = new byte[1204];  
	           while ((byteread = inStream.read(buffer)) != -1) {  
	               bytesum += byteread;  
	               fs.write(buffer, 0, byteread);  
	           }  
	           fs.close();
	           return true;  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	           return false;  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	           return false;  
	       }  
	   }  
	public static void main(String[] args) {
		FileDownload filedown;
		filedown = new FileDownload();
		filedown.DownloadFile(Main.HOSTSLIB[2], Main.SAVEFILES[2]);
		System.out.println("FileDownLoad OK");
	}
}
