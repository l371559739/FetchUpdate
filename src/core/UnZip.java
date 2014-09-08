package core;

public class UnZip {

	
	void extractile(){
		SevenZFile sevenZFile = new SevenZFile(new File("test-documents.7z"));
	    SevenZArchiveEntry entry = sevenZFile.getNextEntry();
	    while(entry!=null){
	        System.out.println(entry.getName());
	        FileOutputStream out = new FileOutputStream(entry.getName());
	        byte[] content = new byte[(int) entry.getSize()];
	        sevenZFile.read(content, 0, content.length);
	        out.write(content);
	        out.close();
	        entry = sevenZFile.getNextEntry();
	    }
	    sevenZFile.close();
	}
}
