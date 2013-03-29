import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
/**
 * 
 */

/**
 * @author neghtebas and brendan ritter
 *
 */
public class Crawler {

	private Jedis jedis;
	//private Crawler bug= new Crawler();
	private Document doc;

	
	public Document htmlOut(String url){
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Gets website's title
		//String title = doc.title();
		return doc; 
	}
	
	//Identifies all the absurl links in a given html doc. 
	public LinkedList<String> linkIdentifier(Document doc){
		HashSet<String> uniqueLinks=new HashSet<String>();
		
		Elements links = doc.getElementsByTag("a");
		//System.out.println(links);
		for (Element link : links) {
		  String absUrl = link.absUrl("href");
			//System.out.println(link.text());
		  uniqueLinks.add(absUrl);
		}
		LinkedList<String> uniqueList= new LinkedList<String>(uniqueLinks);
		//uniqueList= uniqueLinks;
		return uniqueList;
	}
	public String textIdentifier(Document doc){
		//Element head=doc.head();
		//String allText=head.text();
		String allText= doc.text();
		//System.out.println(doc.text());
		return allText;
		
	}
	
	
	public Long hostConnect(String url){
		jedis= new Jedis("localhost"); 
		System.out.println("Jedis connected to localhost");
		jedis.sadd("DB", url);
		return jedis.scard("DB");
	}
	
	
	public LinkedList<String> crawlOnce(LinkedList<String> absURL){
		while(absURL.size()>0){
			//System.out.println(absURL.size());
			String urlQ= absURL.poll();
			//System.out.println("URL of WEbsite beign crawled " + urlQ);
			
			Document newdoc = htmlOut(urlQ);
			LinkedList<String> links= linkIdentifier(newdoc);
			//System.out.println("Links found on this page: \n" + links);
			String tbody= textIdentifier(newdoc);
			//System.out.println("Text on page: \n"+ tbody);
			//System.out.println(hostConnect(urlQ));		
		}
		return null;
	}
	
	public void emit(String curUrl,HashSet<String> allLinks,String allText){	
		HashMap<String, String[]> indexin= new HashMap<String, String[]>();
		//Somehow sent to indexer 
	}	
	
}
