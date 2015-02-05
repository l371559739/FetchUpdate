package core;

import java.io.File;

import com.sun.jmx.snmp.Timestamp;

public class Main {

	/**要追加文件的文件名*/
	private final String OTHERHOSTS="other.hosts";
	/**上传文件的ftp路径地址*/
	private final String FTPPATH = "/wwwroot/adds";
	/**ftp端口*/
	private final int PORT = 21;
	
	

	
	
	private final String ADDR = "";
	/**ftp的用户名*/
	private final String USERNAME = "";
	/**ftp的密码*/
	private final String PASSWORD = "";
	/**邮箱用户名*/
	private final String userName="";
	/**邮箱密码*/
	private final String userKey="";
	/**要发送的目的邮箱*/
	private final String destMailAdress="@163.com";
	FileDownload filedown;
	FtpFileTransmit ftp;
	TextProcessForWebPage textProcess;
	UpdateTimeCheck updatetimecheck;
	/**hosts源地址<br>0:google<br>1:facebook<br>2:关键字*/
	public static final String[]HOSTSLIB={"http://www.360kb.com/kb/2_122.html","http://www.360kb.com/kb/2_139.html","https://raw.githubusercontent.com/vokins/simpleu/master/hosts"};
	public static final String[]SAVEFILES={"googlecookie","facebookcookie","keywordscookied"};
	public Main() {
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

	public static void main(String[] args) {
		new Main();
	}
}
