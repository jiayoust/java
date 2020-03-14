package 正则;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileToString fts = new FileToString();
		String strContent;
		strContent = fts.readFile("url.txt");
		//System.out.println(strContent);
//		String str = "<a href=\"http://www.265g.com/\">265G游戏</a></span><span><a href=\"http://www.07073.com/\">07073游戏</a></span><span><a href=\"http://zt.ztgame.com/url/hao.html/\">征途</a>";
		String regex = "(http://(\\w+.)+\\w+).?\".{0,20}>(\\w*[^</a>]*)</a>"; 
//		String regex ="\"(http://(\\w+.)+com)/\\w+\">(\\w+)";
//		String regex ="http://([w-]+.)+[w-]+(/[w-./?%&=]*)?";
	    Pattern p = Pattern.compile(regex);  
	    Matcher m = p.matcher(strContent);  
	    //m.matches();
	    while(m.find())
	    {
	        System.out.println(m.group(3) + ": " +m.group(1));
//	        System.out.println(m.group(0));
	    }
	}

}

