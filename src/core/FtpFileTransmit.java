package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpFileTransmit {
	private FTPClient ftp;
	/**  
     *   
     * @param path 上传到ftp服务器哪个路径下     
     * @param addr 地址  
     * @param port 端口号  
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
	protected boolean connect(String path,String addr,int port,String username,String password ){
		boolean result=false;
		ftp=new FTPClient();
		int reply;
		try {
			ftp.connect(addr, port);
			ftp.login(username, password);
	        reply = ftp.getReplyCode();    
	        if (!FTPReply.isPositiveCompletion(reply)) {      
	            ftp.disconnect();      
	            return result;      
	        }      
	        ftp.changeWorkingDirectory(path);      
	        result = true;      
	        return result;     
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**  
     *   
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    protected void upload(File file) {          
        	System.out.println("file");
            FileInputStream input;
			try {
				input = new FileInputStream(file);
				ftp.storeFile(file.getName(), input);      
				input.close();        
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}      
    }
    /**文件下载类
     * @param downloadPath 远程文件夹目录路径
     * @param fileName 要下载的文件名字
     * */
    protected void download(String downloadPath,String fileName){
    	try {
			ftp.changeWorkingDirectory(downloadPath);
			FTPFile[] files=ftp.listFiles();
			for(FTPFile file:files){
				if(file.getName().equals(fileName)){
					System.out.println("Downloading "+fileName);
					File localfile=new File(fileName);
					OutputStream out=new FileOutputStream(localfile);
					ftp.retrieveFile(fileName, out);
					out.close();
				}
			}
			
			
		} catch (IOException e) {
			System.out.println("Error in downloadfile from ftp");
//			e.printStackTrace();
		}
    }
}
