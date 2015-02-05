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
	static final String TIMEMARK="google hosts 2015.";
	static final String TIMEMARKLOCAL="#+UPDATE_TIME";
	Pattern timeregex=Pattern.compile(".*(\\d{4,4}.\\d{1,2}.\\d{1,2}).*");
	Matcher regexMatcher;
	String updatetime;
	static String remotetime;
	static String DETAILTIME;
	String localtime;
	BufferedReader in;
	public UpdateTimeCheck() {
		try {
			in=new BufferedReader(new FileReader(new File(Main.SAVEFILES[0])));
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
			System.out.println("Remotetime:  "+remotetime);
			
//			while((line=in.readLine())!=null){
//				if(line.contains(";version")){
//					remotetime=line.substring(line.indexOf('=')+1);
//					DETAILTIME=remotetime.substring(0,4)+"-"+remotetime.substring(4,6)+"-"+remotetime.substring(6,8)+" "+remotetime.substring(8,10)+":"+remotetime.substring(10,12)+":00";
//					remotetime=DETAILTIME.substring(0,DETAILTIME.indexOf(' '));
//					System.out.println(remotetime+"  "+DETAILTIME);
//				}
//			}
			in.close();
			System.out.println("LocalTime: "+getLocalTime());
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
