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
        bug.crawln(3, "http://www.olin.edu/");       
	}
}


