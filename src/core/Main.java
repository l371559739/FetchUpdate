package core;

import java.io.File;

import com.sun.jmx.snmp.Timestamp;

public class Main {

	private final String DESTURL = "https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
	// private final String DOWNLOADLINK =
	// "https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z?dl=1";
	private final String DOWNLOADLINK = "https://raw.githubusercontent.com/zxdrive/imouto.host/master/imouto.host.txt";

	private final String SAVEFILEPATH = "test.txt";
	private final String FTPPATH = "/domains/findspace.name/public_html/adds";
	private final int PORT = 21;
	private final String ADDR = "";
	private final String USERNAME = "";
	private final String PASSWORD = "";
	private final static int TIMESLEEP = 3600 * 1000;
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
		//获取在文档处理过程中保存的更新信息
		timeinformation = textProcess.getUpdateTime();
		//把获取的更新信息保存到本地
		savefileinformation = new SaveFileInformation();
		System.out.println(timeinformation);
		dataCompare = new DataCompare();
		// 比较获取到的时间戳和保存在本地的时间戳
//		if (!dataCompare.Compare(timeinformation)) {
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
			new SendMail(timeinformation);
			System.out.println("-----Update Successfully------");
//		} else {
//			System.out.println("File doesn't need updating");
//		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
