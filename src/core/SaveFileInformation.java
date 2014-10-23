package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**保存文件信息*/
public class SaveFileInformation {
	File file=new File("FileDate");
	BufferedWriter out;
	public SaveFileInformation() {
		if(!checkFileDateExist()){
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				System.out.println("Create File Error!");
//				e.printStackTrace();
			}
		}
		
	}
	/**将信息写到文件里*/
	void pushContent(String data){
		try {
			out=new BufferedWriter(new FileWriter(file));
			out.write(data);
			System.out.println("Writing file success.");
		} catch (IOException e) {
			System.out.println("Writing file error.");
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("File Closing Error.");
//			e.printStackTrace();
		}
		
	}
	public boolean checkFileDateExist(){
		return file.exists();
	}
}
