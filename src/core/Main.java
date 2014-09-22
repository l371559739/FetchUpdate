package core;

public class Main {
	
	private final String DESTURL="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
	private final String DOWNLOADLINK="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z?dl=1";
	private final String SAVEFILEPATH="a.7z";
	FetchWebPage fetch;
	SaveFileInformation savefileinformation;
	DataCompare dataCompare;
	FileDownload filedown;
	
	UnZip unzip;
	String timeinformation;
	public Main() {
		fetch=new FetchWebPage();
		savefileinformation=new SaveFileInformation();
		timeinformation=fetch.getFileTimeLabel(fetch.getPageContent(DESTURL));
		dataCompare=new DataCompare();
//		if(!dataCompare.Compare(timeinformation)){
			System.out.println("--------updating----------");
			savefileinformation.pushContent(timeinformation);
			filedown=new FileDownload();
			filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
			unzip=new UnZip();
			unzip.extractile("a.7z");
//		}else{
//			System.out.println("File doesn't need updating");
//		}
		
		
	}
	public static void main(String[] args) {
		new Main();
	}
}
