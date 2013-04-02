import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

/**
* @author Chloe Eghtebas 
* @author Brendan Ritter 
* @author Pati Martin Vicente
*
*/

public class Crawler {
    private Jedis jedis;
    private Document doc;

    //Uses URL to output a Document for using JSoup library
    public Document htmlOut(String url){
    	//Try and make files that are links also links
    	//if not, just stick with the links
        try {
            URL urlObject = new URL(url);
            url = urlObject.toString();
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        //Gets website's title
        //String title = doc.title();
        return doc;
    }

   

    //Identifies all the absolute URL links in a given HTML document.
    public LinkedList<String> linkIdentifier(Document doc){
        HashSet<String> uniqueLinks=new HashSet<String>();   
        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
          String absUrl = link.absUrl("href");
          	uniqueLinks.add(absUrl);
        }
        LinkedList<String> uniqueList= new LinkedList<String>(uniqueLinks);
        return uniqueList;
    }

    //Identifies all the text in the Document
    public String textIdentifier(Document doc){
        //Element head=doc.head();
        String allText= doc.text();
        return allText;
    }

    //Connects to Jedis Localhost, not used in our current code design
    public void hostConnect(String url){
        jedis= new Jedis("localhost");
        //System.out.println("Jedis connected to localhost");
        jedis.sadd("LinksDB", url);
        //System.out.println(jedis.spop("LinksDB"));
        //return jedis.scard("DB");
    }

    //Crawls one web page returning a LinkedList of links on the page and stores 
    // the URL's and text associated with the ULR's Document 
    public LinkedList<String> crawlOnce(LinkedList<String> absURL){
        jedis= new Jedis("localhost");
        LinkedList<String> links = new LinkedList<String>();
        while(absURL.size()>0){
            String urlQ= absURL.poll();
            jedis.sadd("DB", urlQ);
            //System.out.println(urlQ);
            Document newdoc = htmlOut(urlQ);
            links.addAll(linkIdentifier(newdoc));
            String tbody = textIdentifier(newdoc);
            jedis.sadd("DB", tbody);
        }       
        return links;
    }

    //crawls n times given n and a starting URL 
    public void crawln(int n, String a)
    {
        LinkedList<String> url= new LinkedList<String>();
        url.add(a);
        int i=1;
        while(i<n){
            System.out.println("Round" + i);
            url.addAll(crawlOnce(url));
            i++;
        }       
    }

}