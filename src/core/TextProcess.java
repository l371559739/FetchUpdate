package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**对文本文件的内容处理*/
public class TextProcess {
	BufferedReader in;
	BufferedWriter out;
	public TextProcess(File file) {
		try {
			in=new BufferedReader(new FileReader(file));
			out=new BufferedWriter(new FileWriter(new File("hosts")));
			String temp=in.readLine();
			boolean logStart=false;
			while(temp!=null){
				if(logStart){
					if(temp.indexOf("#+MESSAGE_END")>=0){
						logStart=false;
					}
				}
				if(temp.indexOf("Fix & Bug :")>=0){
					logStart=true;
				}
				if(!logStart){
					if(temp.indexOf("tongji")<0){
						out.write(temp+"\n");
						if(temp.equals("#  imouto.host")){
							out.write("#  本文件由Find收集并做简单的修改，欢迎访问博客www.findspace.name\n\n#一下google+的连接是源作者的连接\n");
						}
					}					
				}
				temp=in.readLine();
				
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		new TextProcess(new File("imouto.host.txt"));
//	}
}
