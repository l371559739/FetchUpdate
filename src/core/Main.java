package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sun.jmx.snmp.Timestamp;

public class Main {

	/**要追加文件的文件名*/
	private final String OTHERHOSTS="other.hosts";
	/**上传文件的ftp路径地址*/
	private final String FTPPATH = "/wwwroot/adds";
	/**ftp端口*/
	private final int PORT = 21;
	/**配置文件的路径,此处需要自己新建，建在src下面即可*/
	private final String CONFIGPATH="ftpconfig";
	private String ADDR;
	/**ftp的用户名*/
	private String USERNAME;
	/**ftp的密码*/
	private String PASSWORD;
	/**邮箱用户名*/
	private String userName;
	/**邮箱密码*/
	private String userKey;
	/**要发送的目的邮箱*/
	private String destMailAdress;
	FileDownload filedown;
	FtpFileTransmit ftp;
	TextProcessForWebPage textProcess;
	UpdateTimeCheck updatetimecheck;
	/**hosts源地址<br>0:google<br>1:facebook<br>2:关键字*/
	public static final String[]HOSTSLIB={"http://www.360kb.com/kb/2_122.html","http://www.360kb.com/kb/2_139.html","https://raw.githubusercontent.com/vokins/simpleu/master/hosts"};
	public static final String[]SAVEFILES={"googlecookie","facebookcookie","keywordscookied"};
	public Main() {
		readConfig();
		//下载文档
		filedown = new FileDownload();
		for(int i=0;i<HOSTSLIB.length;i++){
			filedown.DownloadFile(HOSTSLIB[i], SAVEFILES[i]);
		}
		System.out.println("FileDownLoad OK");
		//对下载的文档进行处理
		updatetimecheck=new UpdateTimeCheck();
		if(updatetimecheck.needUpdate()){
			System.out.println("--------updating----------");
			textProcess=new TextProcessForWebPage();
			textProcess.ProcessText(Main.SAVEFILES[0], 0);
//			textProcess.ProcessVidGithub(Main.SAVEFILES[2]);
			textProcess.ProcessText(Main.SAVEFILES[1], 1);
			textProcess.ProcessViaKeywords(Main.SAVEFILES[2]);
//			PushOtherHosts.pushOtherHosts(OTHERHOSTS);
			System.out.println("Change local hosts successfully.");
			ftp = new FtpFileTransmit();
			if (ftp.connect(FTPPATH, ADDR, PORT, USERNAME, PASSWORD)) {
				System.out.println("Ftp connect successful.");
				try {
					ftp.upload(new File("hosts"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//如果有更新，且更新成功了，发送邮件
			new SendMail(UpdateTimeCheck.DETAILTIME,userName,userKey,destMailAdress);
			System.out.println("-----Update Successfully------");
		}
	}
	//读取配置文件
	public void readConfig(){
		try {
			BufferedReader in=new BufferedReader(new FileReader(new File(CONFIGPATH)));
			ADDR=in.readLine();
			USERNAME=in.readLine();
			PASSWORD=in.readLine();
			userName=in.readLine();
			userKey=in.readLine();
			destMailAdress=in.readLine();
//			System.out.println("read"+ADDR+USERNAME+PASSWORD+userName+userKey+destMailAdress);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Main();
	}
}
