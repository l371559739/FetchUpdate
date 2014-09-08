package core;

public class Main {
	
	private final String DESTURL="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
	private final String DOWNLOADLINK="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z?dl=1";
	private final String SAVEFILEPATH="a.7z";
	FetchWebPage fetch=new FetchWebPage();
	SaveFileInformation savefileinformation;
	DataCompare dataCompare;
	FileDownload filedown;
	
	String timeinformation;
	public Main() {
		savefileinformation=new SaveFileInformation();
		timeinformation=fetch.getFileTimeLabel(fetch.getPageContent(DESTURL));
		dataCompare=new DataCompare();
		if(!dataCompare.Compare(timeinformation)){
			savefileinformation.pushContent(timeinformation);
			filedown=new FileDownload();
			filedown.DownloadFile(DOWNLOADLINK, SAVEFILEPATH);
			
		}else{
			System.out.println("File doesn't need updating");
		}
		
		
	}
	public static void main(String[] args) {
		new Main();
	}
}
