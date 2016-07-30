package com.news;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import bean.NewsBean;
import db.ConnectionManager;

public class SinaNews {

	 private ConnectionManager manager = null;    //数据库连接类
	 private PreparedStatement pstmt = null;      //执行sql语句需要使用的对象
	 private List newsList = new ArrayList();     //用于存放字段信息的链表
	 private NewsBean bean = new NewsBean();      //和数据库字段对应的JavaBean
	 
	 public NewsBean setNews(String url) throws IOException{
		
		 bean = new NewsBean();
			String content="";
			String date = "";
			 String author = "";
			
			Document doc = Jsoup.connect(url).get();
		    String title = doc.head().select("title").text().toString();
		    System.out.println("新闻标题:"+title);
		    bean.setNewsTitle(title);  //得到标题
		    
		    if(url.contains("tech.sina.com")){
		     date = doc.select("[class=titer]").text().toString(); //新闻时间
		     content= doc.select("[class=content]").text().toString(); //新闻内容
		     author = doc.select("[class=source]").text().toString();  //新闻作者
		     
		    
		    }else if(url.contains("hb.sina.com")){
		    	date = doc.select("[class=source-time]").text().toString();
		    	content = doc.select("[class=article-body main-body]").text().toString();
		    	author = doc.select("[class=source-time]").text().toString();
		    }else if(url.contains("fj.sina.com")){
		    	date = doc.select("[class=source-time]").text().toString();
		    	content = doc.select("[class=article-box]").select("p").text().toString();
		    	author = doc.select("[class=source-time]").text().toString();
		    }else if(url.contains("shantou.house.sina.com")){
		    	date = doc.select("[class=origin]").select("span").get(0).text().toString();
		    	content = doc.select("[class=article-body]").text().toString();
		    	author = doc.select("[class=origin]").select("span").get(1).text().toString();
		    }else {
		    	author = doc.select("[class=article-title]").select("span").text().toString();
		    	content = doc.select("[class=main-body]").text().toString();
		    	date = doc.select("[class=article-title]").select("span").text().toString();
		    }
		    System.out.println("时间："+date);
		    bean.setNewsDate(date);    
		    		    
		    System.out.println("新闻内容："+content);
		    bean.setNewsContent(content);	    
		    
		    System.out.println("新闻作者："+author);
		    bean.setNewsAuthor(author);
		    bean.setNewsURL(url);
		    System.out.println("新闻地址："+url);
		    
		    return bean;
		}
	 
	 /**
		 * 写操作数据库的实现
	 * @throws SQLException 
		 */
		public boolean saveToDB(NewsBean bean) throws SQLException{
			boolean flag = true;
			String sql = "insert into news(newstitle,newsauthor,newscontent,newsurl,newsdate) values(?,?,?,?,?)";
			manager = new ConnectionManager();
			String titleLengh = bean.getNewsTitle();
			
			List<String> list = manager.getRs();
			if(list.contains(titleLengh)){
				return flag;
			}
			
			//对新闻标题长度的限制
			if(titleLengh.length()>60){
				return flag;
			}
			try{
				pstmt = manager.getConnection().prepareStatement(sql);
				pstmt.setString(1, bean.getNewsTitle());
	            pstmt.setString(2, bean.getNewsAuthor());
	            pstmt.setString(3, bean.getNewsContent());
	            pstmt.setString(4, bean.getNewsURL());
	            pstmt.setString(5, bean.getNewsDate());
	            flag = pstmt.execute();
			}catch (SQLException ex) {
				// TODO: handle exception
				  Logger.getLogger(QQNews.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally{
				 try 
		            {
		                pstmt.close();
		                manager.close();
		            } 
		            catch (SQLException ex) 
		            {
		                Logger.getLogger(QQNews.class.getName()).log(Level.SEVERE, null, ex);
		            }
			}
			return flag;
		}
	 public static void main(String[] args) throws IOException, SQLException{
		 String url = "http://henan.sina.com.cn/finance/cjnews/2016-02-19/0906174894.html";
		 SinaNews news = new SinaNews();
		 NewsBean bean = news.setNews(url);
		 news.saveToDB(bean);
	 }
}
