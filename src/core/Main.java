package core;

import java.io.File;

import com.sun.jmx.snmp.Timestamp;

public class Main {

	private final String DESTURL = "https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
//	private final String DOWNLOADLINK = "https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z?dl=1";
	private final String DOWNLOADLINK = "https://raw.githubusercontent.com/zxdrive/imouto.host/master/imouto.host.txt";
	
	private final String SAVEFILEPATH = "test.txt";
	private final String FTPPATH = "/domains/findspace.name/public_html/adds";
	private final int PORT = 21;
	//测试时，将下面三个填写你的信息即可
	//这里是ftp的ip地址
	private final String ADDR = "";
	//ftp用户名
	private final String USERNAME = "";
	//ftp密码
	private final String PASSWORD = "";
	private final static int TIMESLEEP=3600*1000;
	FetchWebPage fetch;
	SaveFileInformation savefileinformation;
	DataCompare dataCompare;
	FileDownload filedown;

	String timeinformation;
	FtpFileTransmit ftp;
	TextProcess textProcess;
	
	
	public Main() {
		// 获取网页的内容
//		fetch = new FetchWebPage();
////		 查看获取到的网页上的时间戳
//		timeinformation = fetch.getFileTimeLabel(fetch.getPageContent(DESTURL));
			filedown = new FileDownload();
			filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
			System.out.println("FileDownLoad OK");
//			unzip = new UnZip();
//			unzip.extractile("a.7z");
		textProcess = new TextProcess(new File(SAVEFILEPATH));
		timeinformation=textProcess.getUpdateTime();
		savefileinformation = new SaveFileInformation();
		System.out.println(timeinformation);
		dataCompare = new DataCompare();
//		 比较获取到的时间戳和保存在本地的时间戳
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
//
		} else {
			System.out.println("File doesn't need updating");
		}
//
	}

	public static void main(String[] args) {
				new Main();
	}
}
