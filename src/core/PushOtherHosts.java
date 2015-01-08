package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PushOtherHosts {

	/**其他hosts的追加
	 * @return */
	public static  void pushOtherHosts(String fileName){
		File otherHostsFile=new File(fileName);
		if(!otherHostsFile.exists())return;
		try {
			BufferedReader in=new BufferedReader(new FileReader(otherHostsFile));
			BufferedWriter out=new BufferedWriter(new FileWriter(new File("hosts"),true));
			String temp=in.readLine();
			while(temp!=null){
				out.write(temp+"\n");
				temp=in.readLine();
			}
			out.flush();
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
