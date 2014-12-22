package core;

import java.io.File;

import com.sun.jmx.snmp.Timestamp;

public class Main {
	//更新了一个新的hosts地址
	private final String DOWNLOADLINK = "https://raw.githubusercontent.com/Elegantid/Hosts/master/hosts";
	/**抓取到以后在本地存储的文件名*/
	private final String SAVEFILEPATH = "test.txt";
	/**要追加文件的文件名*/
	private final String OTHERHOSTS="other.hosts";
	/**上传文件的ftp路径地址*/
	private final String FTPPATH = "";
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
	private final String destMailAdress="findxiaoxun@163.com";
	FetchWebPage fetch;
	SaveFileInformation savefileinformation;
	DataCompare dataCompare;
	FileDownload filedown;

	String timeinformation;
	FtpFileTransmit ftp;
	TextProcess textProcess;
	public Main() {
		//下载文档
		filedown = new FileDownload();
		filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
		System.out.println("FileDownLoad OK");
		//对下载的文档进行处理
		textProcess = new TextProcess(new File(SAVEFILEPATH));
		textProcess.pushOtherHosts(OTHERHOSTS);
		//获取在文档处理过程中保存的更新信息
		timeinformation = textProcess.getUpdateTime();
		//把获取的更新信息保存到本地
		savefileinformation = new SaveFileInformation();
//		System.out.println(timeinformation);
		dataCompare = new DataCompare();
		// 比较获取到的时间戳和保存在本地的时间戳
		if (!dataCompare.Compare(timeinformation)) {
			savefileinformation.pushContent(timeinformation);
			System.out.println("--------updating----------");
			ftp = new FtpFileTransmit();
			if (ftp.connect(FTPPATH, ADDR, PORT, USERNAME, PASSWORD)) {
				try {
					ftp.upload(new File("hosts"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//如果有更新，且更新成功了，发送邮件
			new SendMail(timeinformation,userName,userKey,destMailAdress);
			System.out.println("-----Update Successfully------");
		} else {
			System.out.println("File doesn't need updating");
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
