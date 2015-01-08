package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//暂时先按天更新
public class UpdateTimeCheck {
	static final String TIMEMARK="<strong>google hosts";
	static final String TIMEMARKLOCAL="#+UPDATE_TIME";
	Pattern timeregex=Pattern.compile("<strong>.*</strong><strong>(.*)&nbsp;</strong>.*");
	Matcher regexMatcher;
	String updatetime;
	static String remotetime;
	static String DETAILTIME;
	String localtime;
	BufferedReader in;
	public UpdateTimeCheck() {
		try {
			in=new BufferedReader(new FileReader(new File(Main.SAVEFILEPATH)));
			String line;
			while((line=in.readLine())!=null){
				if(line.contains(TIMEMARK)){
//					System.out.println(line);
					regexMatcher=timeregex.matcher(line);
					if(regexMatcher.find()){
						updatetime=regexMatcher.group(1);
						String timeinfo[]=updatetime.split("\\.");
						remotetime=timeinfo[0]+"-"+timeinfo[1]+"-"+timeinfo[2];
						setDetailTime();
					}
				}
			}
			in.close();
			getLocalTime();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getRemoteTime(){
		return remotetime;
	}
	public String getLocalTime(){
		try {
			in=new BufferedReader(new FileReader(new File("hosts")));
			String line;
			while((line=in.readLine())!=null){
				if(line.contains(TIMEMARKLOCAL)){
					localtime=line.substring(TIMEMARKLOCAL.length()+1);
					localtime=localtime.substring(0,localtime.indexOf(' '));
				}
			}
			return localtime;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public boolean needUpdate(){
		return !(remotetime.equals(localtime));
	}
	private void setDetailTime(){
		Calendar c=Calendar.getInstance();
		DETAILTIME=remotetime+" "+(c.get(Calendar.HOUR)+12)+":"+c.get(Calendar.MINUTE)+":00";
	}
}
