package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchWebPage {
	/**
	 * 获取网页内容
	 * @param
	 * weburl 输入的网址
	 * */
	public String getPageContent(String weburl){
		URL url;
		StringBuffer sb=new StringBuffer();
		String temp;
		try {
			url=new URL(weburl);
			BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream(),"utf8"));
			while((temp=in.readLine())!=null)sb.append(temp);
			in.close();
			
		} catch (MalformedURLException e) {
			System.out.println("Your ulr format is wrong.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**这个是自定义的方法，根据需要，自己写
	 * 获得class的内容,如果与已经存储的不同，则表示更新了。。不再进行时间判断。。*/
	public String getFileTimeLabel(String str){
		List<String>list=new ArrayList<String>();
		String mark="<div class="+'"'+"meta"+'"'+">.*?</div>";
		Pattern pattern=Pattern.compile(mark);
		Matcher matcher=pattern.matcher(str);
		String content="";
		while(matcher.find()){
			list.add(matcher.group());
			
		}
		for(int i=0;i<list.size();i++){
			content+=list.get(i);
		}
		/**第一次&符号的位置*/
		int andPosition=content.indexOf('&');
		/**第一个右尖括号的位置*/
		int rightBracket=content.indexOf('>');
		return content.substring(rightBracket+1, andPosition);
	}
	//test
	public static void main(String[] args) {
		FetchWebPage aa=new FetchWebPage();
		System.out.println(aa.getFileTimeLabel(aa.getPageContent("https://www.dropbox.com/sh/lw0ljk3sllmimpz/AAC-n6LmtWbdlKQRbdEa0QUoa/imouto.host.7z")));
	}
}
