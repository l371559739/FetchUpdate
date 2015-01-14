package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TextProcessForWebPage {
	BufferedReader in;
	BufferedWriter out;
	boolean startContext=false;
	String line;
	String writeline;
	static final String STARTMARKS[]={"#base services","#facebook hosts"}; 
	static final String ENDMARKS[]={"#google source end","#facebook hosts 2015 end"}; 
	static final String KEYWORDS[]={"wiki","twi","dropbox"};
	static final String BAN="127.0.0.1";
	public TextProcessForWebPage() {
		try {
			out=new BufferedWriter(new FileWriter(new File("hosts")));
			out.write("#+BEGIN\n#+UPDATE_TIME "+UpdateTimeCheck.DETAILTIME+"\n#+MESSAGE\n#######################################################################\n#\n# --- Welcome to www.findspace.name ----\n# Connect Me:   SinaWeibo: @FindBlog\n#\n#######################################################################\n#+MESSAGE_END\n\n\n#Google service\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**@param 	path:输入文件的路径<br>
	 *			index:<br>0 ：google <br>1：facebook 
	 * */
	public void ProcessText(String path,int index) {
		Pattern regex=Pattern.compile(".*(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*");
		Matcher regexMatcher;
		try {
			in=new BufferedReader(new FileReader(new File(path)));
			out=new BufferedWriter(new FileWriter(new File("hosts"),true));
			while((line=in.readLine())!=null){
				if(line.contains(STARTMARKS[index])){
					startContext=true;
				}
				if(line.contains(ENDMARKS[index]))startContext=false;
				if(startContext){
					regexMatcher=regex.matcher(line);
					if(regexMatcher.find()){
						writeline=regexMatcher.group();
						if(writeline!=null){
							writeline=writeline.replace("	", "");
							writeline=writeline.replace("&nbsp;"," "); 
							writeline=writeline.replace("<span>", "");
							writeline=writeline.replace("</span>", "");
							writeline=writeline.replace("<br />", " ");
							if(writeline.indexOf('>')>=0){
								writeline=writeline.substring(writeline.indexOf('>')+1);
							}
						}
						out.write(writeline+"\n");
					}
				}
			}
			out.write(ENDMARKS[index]+"\n\n\n");
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
	public void ProcessViaKeywords(String path){
		try {
			in=new BufferedReader(new FileReader(new File(path)));
			out=new BufferedWriter(new FileWriter(new File("hosts"),true));
			out.write("\n#keywords hosts\n\n");
			while((line=in.readLine())!=null){
				for(int i=0;i<KEYWORDS.length;i++)if(line.contains(KEYWORDS[i])&&!line.contains(BAN))out.write(line+"\n");
			}
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		TextProcessForWebPage tt=new TextProcessForWebPage();
//		tt.ProcessText(Main.SAVEFILES[0],0);
//		tt.ProcessText(Main.SAVEFILES[1],1);
//		tt.ProcessViaKeywords(Main.SAVEFILES[2]);
////		new UpdateTimeCheck().getLocalTime();
//	}
}
