package core;

public class Main {
	
	private final String destUrl="https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z";
	
	FetchWebPage fetch=new FetchWebPage();
	public Main() {
		fetch.getFileTimeLabel(fetch.getPageContent(destUrl));
		
		
	}
}
