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
 * 分别解析不同网页的所有连接
 * 
 * @author M.Line
 *
 */
public class LinkParser {

	
	/**
	 * 通过分析，搜狗新闻搜索，可以自由选择新闻内容的网站来源，初步设定为搜索接口为搜狗新闻
	 * @param keyword
	 * @return
	 * @throws IOException 
	 */
	public static Set<String> extractLiks(String keyword) throws IOException{
		
		Set<String> links = new TreeSet<String>();	
		
		keyword = UnicodeUtil.toUtf8String(keyword.replace(" ", "+"));
		
//		ArrayList<String> sohuList = new ArrayList<String>();  //搜狐新闻
//		ArrayList<String> qqList = new ArrayList<String>();   //腾讯新闻
//		ArrayList<String> NTESList = new ArrayList<String>();  //网易新闻
//		ArrayList<String> ifengList = new ArrayList<String>();  //凤凰网新闻
//		ArrayList<String> sinaList = new ArrayList<String>();   //新浪新闻
		
		/*
		 * 搜索新闻可以分为：搜狐，腾讯，网易，凤凰，新浪
		 * 1.根据关键词得到不同网站的URL，然后在进行页面解析
		 */
	    String url = "";
	    Document doc = null;
	    Elements vrTitles;
	    Elements hrefs;
		
	    //1.搜狐新闻的前n条URL
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
	   //2.腾讯新闻的前n条URL
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
	    //3.网易新闻的前n条URL
	    url="http://news.sogou.com/news?query=site:163.com+"+(keyword)+"&manual=true&mode=2&sort=0&p=42230302";
	    doc = Jsoup.connect(url).get();
	    vrTitles = doc.select("[class=vrTitle]");
	    hrefs = vrTitles.select("a");
	    for(Element href: hrefs){
	    	String  link = href.attr("abs:href");
	    	links.add(link);
	    	//System.out.println(link);
	    	
	    }
	    //4.凤凰新闻的前n条URL
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
	    //5.新浪新闻的前n条URL
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
	 * 部分测试入口
	 * @param args
	 * @throws IOException
	 */
	
	/*
	 * public static void main(String[] args) throws IOException{
	 
		String keyword = "苹果 手机";
	
		Set<String> set = extractLiks(keyword);
		for(String str: set){
			System.out.println(str);
		}
		
	}*/
	
}
