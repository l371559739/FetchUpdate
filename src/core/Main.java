package core;

import java.io.File;

import com.sun.jmx.snmp.Timestamp;

public class Main {
	//更新了一个新的hosts地址
	static final String DOWNLOADLINK = "http://www.360kb.com/kb/2_122.html";
	/**抓取到以后在本地存储的文件名*/
	static final String SAVEFILEPATH = "test.txt";
	/**要追加文件的文件名*/
	private final String OTHERHOSTS="other.hosts";
	/**上传文件的ftp路径地址*/
	private final String FTPPATH = "/domains//public_html/adds";
	/**ftp端口*/
	private final int PORT = 21;
	/**ftp的ip地址*/
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
	FetchWebPage fetch;
	SaveFileInformation savefileinformation;
	FileDownload filedown;

	FtpFileTransmit ftp;
//	TextProcess textProcess;
	TextProcessForWebPage textProcess;
	UpdateTimeCheck updatetimecheck;
	public Main() {
		//下载文档
		filedown = new FileDownload();
		filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
		System.out.println("FileDownLoad OK");
		//对下载的文档进行处理
		updatetimecheck=new UpdateTimeCheck();
		if(updatetimecheck.needUpdate()){
			System.out.println("--------updating----------");
			textProcess=new TextProcessForWebPage(SAVEFILEPATH);
			PushOtherHosts.pushOtherHosts(OTHERHOSTS);
			System.out.println("Change local hosts successfully.");
			ftp = new FtpFileTransmit();
			if (ftp.connect(FTPPATH, ADDR, PORT, USERNAME, PASSWORD)) {
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
