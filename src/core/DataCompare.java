package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**比较获取的网页的数据和文件中保存的数据对比，如果不同（即便有错误），
 * 则认为是更新的，重新写文件信息到文件，并下载*/
public class DataCompare {
	File file=new File("FileDate");
	BufferedReader in;
	public boolean Compare(String compareData) {
		try {
			in=new BufferedReader(new FileReader(file));
			//根据数据不同，来写循环，这里只有一行数据
			String temp=in.readLine();
			if(temp!=null)
				return temp.equals(compareData);
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("File not exist");
			return false;
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File read error");
			return false;
//			e.printStackTrace();
		}
	}
}
