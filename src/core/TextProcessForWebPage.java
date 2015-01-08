package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessForWebPage {
	BufferedReader in;
	BufferedWriter out;
	boolean startContext=false;
	String line;
	String writeline;
	static final String STARTMARK="#base services"; 
	static final String ENDMARK="#google source end"; 
	public TextProcessForWebPage(String path) {
		Pattern regex=Pattern.compile("<span>(.*)</span><br />");
		Matcher regexMatcher;
		try {
			in=new BufferedReader(new FileReader(new File(path)));
			out=new BufferedWriter(new FileWriter(new File("hosts")));
			out.write("#+BEGIN\n#+UPDATE_TIME "+UpdateTimeCheck.DETAILTIME+"\n#+MESSAGE\n#######################################################################\n#\n# --- Welcome to www.findspace.name ----\n# Connect Me:   SinaWeibo: @FindBlog\n#\n#######################################################################\n#+MESSAGE_END\n");
			while((line=in.readLine())!=null){
				if(line.contains(STARTMARK))startContext=true;
				if(line.contains(ENDMARK))startContext=false;
				if(startContext){
					regexMatcher=regex.matcher(line);
					if(regexMatcher.find()){
						writeline=regexMatcher.group(1);
						writeline=writeline.replace("&nbsp;",""); 
					}
					out.write(writeline+"\n");
				}
			}
			out.write(ENDMARK+"\n");
			out.flush();
			out.close();
			in.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static void main(String[] args) {
////		new TextProcessForWebPage("test.txt");
//		new UpdateTimeCheck().getLocalTime();
//	}
}
