import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public final class WebServerRun {
	public static void main(String []args){
		
		Crawler bug= new Crawler();
		
		Document newdoc= null;
		newdoc= bug.htmlOut("http://www.facebook.com/");
		
		//System.out.println(newdoc);
		//String title = newdoc.title();
		//System.out.println(newdoc.toString());
		LinkedList<String> url= bug.linkIdentifier(newdoc);
		
		ArrayList<String> al= new ArrayList<String>();
		url.toArray();
		for(int i=0;i<url.size();i++)
		{
			al.add(url.get(i));
			System.out.println(al.get(i));
		}
		
		
		bug.crawlOnce(url);
		//bug.textIdentifier(newdoc);
		
		//String html = "<html><head><title>First parse</title></head>"
			//	  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		//Document doc = Jsoup.parse(html);
		
		
		//System.out.println(title);
	}
}
