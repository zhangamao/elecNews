package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import bean.UnicodeUtil;

/**
 * �ֱ������ͬ��ҳ����������
 * 
 * @author M.Line
 *
 */
public class LinkParser {

	
	/**
	 * ͨ���������ѹ�������������������ѡ���������ݵ���վ��Դ�������趨Ϊ�����ӿ�Ϊ�ѹ�����
	 * @param keyword
	 * @return
	 * @throws IOException 
	 */
	public static Set<String> extractLiks(String keyword) throws IOException{
		
		Set<String> links = new TreeSet<String>();	
		
		keyword = UnicodeUtil.toUtf8String(keyword.replace(" ", "+"));
		
//		ArrayList<String> sohuList = new ArrayList<String>();  //�Ѻ�����
//		ArrayList<String> qqList = new ArrayList<String>();   //��Ѷ����
//		ArrayList<String> NTESList = new ArrayList<String>();  //��������
//		ArrayList<String> ifengList = new ArrayList<String>();  //���������
//		ArrayList<String> sinaList = new ArrayList<String>();   //��������
		
		/*
		 * �������ſ��Է�Ϊ���Ѻ�����Ѷ�����ף���ˣ�����
		 * 1.���ݹؼ��ʵõ���ͬ��վ��URL��Ȼ���ڽ���ҳ�����
		 */
	    String url = "";
	    Document doc = null;
	    Elements vrTitles;
	    Elements hrefs;
		
	    //1.�Ѻ����ŵ�ǰn��URL
	    url="http://news.sogou.com/news?query=site:sohu.com+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String link = href.attr("abs:href");
	    	links.add(link);
	    	//sohuList.add(link);
	    	//System.out.println(link);
	    }
	   //2.��Ѷ���ŵ�ǰn��URL
	    url="http://news.sogou.com/news?query=site:qq.com+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String link = href.attr("abs:href");
	    	links.add(link);
	    	//qqList.add(link);
	    	//System.out.println(link);
	    }
	    //3.�������ŵ�ǰn��URL
	    url="http://news.sogou.com/news?query=site:163.com+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String  link = href.attr("abs:href");
	    	links.add(link);
	    	//System.out.println(link);
	    	
	    }
	    //4.������ŵ�ǰn��URL
	    url="http://news.sogou.com/news?query=site:ifeng.com+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String link = href.attr("abs:href");
	    	links.add(link);
	    	//ifengList.add(link);
	    	//System.out.println(link);
	    }
	    //5.�������ŵ�ǰn��URL
	    url="http://news.sogou.com/news?query=site:sina.com.cn+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String link = href.attr("abs:href");
	    	links.add(link);
	    	//sinaList.add(link);
	    	//System.out.println(link);
	    }
	    
//	    links.add(sohuList);
//	    links.add(qqList);
//	    links.add(ifengList);
//	    links.add(NTESList);
//	    links.add(sinaList);
	    
		return links;
	}
	
	/**
	 * ���ֲ������
	 * @param args
	 * @throws IOException
	 */
	
	/*
	 * public static void main(String[] args) throws IOException{
	 
		String keyword = "ƻ�� �ֻ�";
	
		Set<String> set = extractLiks(keyword);
		for(String str: set){
			System.out.println(str);
		}
		
	}*/
	
}
