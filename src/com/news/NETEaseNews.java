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

public class NETEaseNews {

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
		    
		    if(url.contains("tech.163.com")){
		     date = doc.select("[class=post_time_source]").text().toString(); //新闻时间
		     content= doc.select("[class=post_text]").text().toString(); //新闻内容
		     author = doc.select("[class=post_time_source]").text().toString();  //新闻作者
		    
		    
		    }else if(url.contains("play.163.com")){
		    	date = doc.select("[class=time]").text().toString();
		    	content = doc.select("[class=end-text clearfix]").text().toString();
		    	author = "SEED";
		    }
		    else if(url.contains("digi.163.com")){
		    	
		    		String str = doc.select("[class=post_time_source]").text().toString();
		    		date = str.substring(0, str.indexOf("来源"));
		    		content = doc.select("[class=post_text]").text().toString();		
		    		author=str.substring(str.indexOf("来源"));
		    }else if(url.contains("mobile.163.com")){
		    	String str = doc.select("[class=post_time_source]").text().toString();
		    	date = str.substring(0, str.indexOf("来源"));
		    	author = author=str.substring(str.indexOf("来源"));
		    	content = doc.select("[class=post_text]").text().toString();
		    }else if(url.contains("hebei.news.163")){
		    	String str = doc.select("[class=ep-time-soure cDGray]").text().toString();
		    	date = str.substring(0, str.indexOf("来源"));
		    	author = str.substring(str.indexOf("来源"));
		    	content = doc.getElementById("endText").text().toString();
		    	
		    }else{
		    	date = doc.select("[class=ep-time-soure cDGray]").text().toString();
		    	content = doc.getElementById("endText").text().toString();
		    	author = doc.select("[class=ep-time-soure cDGray]").text().toString();
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
//	public static void main(String[] args) throws IOException, SQLException{
//		String url = "http://tech.163.com/16/0402/12/BJL9DM27000915BD.html";
//		 NETEaseNews news = new NETEaseNews();
//		 NewsBean bean = news.setNews(url);
//		 news.saveToDB(bean);
//	}
}
