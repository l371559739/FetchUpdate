package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**对文本文件的内容处理*/
public class TextProcess {
	String UpdateTimeMark;
	BufferedReader in;
	BufferedWriter out;
// 下面的是为原来版本的hosts源准备的，后续将新建模型，脱离修改代码，只改传入的参数
//	public TextProcess(File file) {
//		try {
//			in=new BufferedReader(new FileReader(file));
//			out=new BufferedWriter(new FileWriter(new File("hosts")));
//			String temp=in.readLine();
//			boolean logStart=false;
//			boolean commemtsStart=false;
//			while(temp!=null){
//				if(logStart){
//					if(temp.indexOf("#+MESSAGE_END")>=0){
//						logStart=false;
//						commemtsStart=false;
//					}
//				}
//				
//				if(temp.indexOf("Fix & Bug :")>=0){
//					logStart=true;
//				}
//				if(!logStart){
//					if(temp.indexOf("tongji")<0){
//						if(!commemtsStart)out.write(temp+"\n");
//						if(temp.equals("#  imouto.host")){
//							out.write("# --- Welcome to www.findspace.name ----\n#\n");
////							out.write("# My Google+:https://plus.google.com/u/0/+HaoYueMing/posts#\n#\n#");
////							out.write("\n#   This hosts follows with imouto,\n#   but it is updated by a Find'spider.");
////							out.write("\n#   This hosts' author is imouto.Here is his google+ Homepage:");
////							out.write("\n#   https://plus.google.com/u/0/100484131192950935968/about\n");
////							out.write("\n#    here is the community page :https://plus.google.com/u/0/communities/111265655058678013030");
//							commemtsStart=true;
//						}
//						if(temp.indexOf("#+UPDATE_TIME")>=0){
//							UpdateTimeMark=temp;
//							
//						}
//					}					
//				}
//				temp=in.readLine();
//				
//			}
//			in.close();
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public TextProcess(File file) {
		try {
			in=new BufferedReader(new FileReader(file));
			out=new BufferedWriter(new FileWriter(new File("hosts")));
			String temp=in.readLine();
			boolean timeMark=false;
			while(temp!=null&&!timeMark){
				//找到时间标记
				if(!timeMark&&temp.indexOf("google hosts")>=0){
					out.write("#+BEGIN\n#+UPDATE_TIME "+UpdateTime(temp)+"\n#+MESSAGE\n#######################################################################\n#\n# --- Welcome to www.findspace.name ----\n# Connect Me:   SinaWeibo: @FindBlog\n#\n#######################################################################\n#+MESSAGE_END\n");
					timeMark=true;
				}
				temp=in.readLine();
			}
			while(temp!=null){
				if(temp.indexOf("#google hosts")>=0){
					out.write("\n\n#Google service \n");
					temp=in.readLine();
					break;
				}
				temp=in.readLine();
			}
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
	/**其他hosts的追加*/
	public void pushOtherHosts(String fileName){
		File otherHostsFile=new File(fileName);
		if(!otherHostsFile.exists())return;
		try {
			in=new BufferedReader(new FileReader(otherHostsFile));
			out=new BufferedWriter(new FileWriter(new File("hosts"),true));
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
	/**更新的时间*/
	private String UpdateTime(String timeStr){
		timeStr=timeStr.substring(timeStr.indexOf('t')+3);
		String date[]=timeStr.split(" ")[0].split("\\.");
		UpdateTimeMark=date[0]+"-"+date[1]+"-"+date[2]+" 00:00:00";
		return UpdateTimeMark;
		
	} 
	/**返回hosts更新的时间*/
	public String getUpdateTime(){return UpdateTimeMark;	}
	public static void main(String[] args) {
		new TextProcess(new File("test.txt"));
	}
}
