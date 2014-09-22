package core;

import java.io.File;

public class Main {
	
	private final String DESTURL="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
	private final String DOWNLOADLINK="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z?dl=1";
	private final String SAVEFILEPATH="a.7z";
	private final String FTPPATH="/domains/findspace.name/public_html/adds";
	private final int PORT=21;
	private final String ADDR="";
	private final String USERNAME="";
	private final String PASSWORD="";
	FetchWebPage fetch;
	SaveFileInformation savefileinformation;
	DataCompare dataCompare;
	FileDownload filedown;
	
	UnZip unzip;
	String timeinformation;
	FtpFileTransmit ftp;
	TextProcess textProcess;
	public Main() {
		//获取网页的内容
		fetch=new FetchWebPage();
		savefileinformation=new SaveFileInformation();
		//查看获取到的网页上的时间戳
		timeinformation=fetch.getFileTimeLabel(fetch.getPageContent(DESTURL));
		dataCompare=new DataCompare();
		//比较获取到的时间戳和保存在本地的时间戳
//		if(!dataCompare.Compare(timeinformation)){
			System.out.println("--------updating----------");
			savefileinformation.pushContent(timeinformation);
			filedown=new FileDownload();
			filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
			unzip=new UnZip();
			unzip.extractile("a.7z");
			textProcess=new TextProcess(new File("imouto.host.txt"));
			ftp=new FtpFileTransmit();
			if(ftp.connect(FTPPATH, ADDR, PORT, USERNAME, PASSWORD)){
				try {
					ftp.upload(new File("hosts"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
//		}else{
//			System.out.println("File doesn't need updating");
//		}
		
		
	}
	public static void main(String[] args) {
		new Main();
	}
}
