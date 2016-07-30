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

import db.ConnectionManager;
import bean.NewsBean;

public class IFengNews {

	 private ConnectionManager manager = null;    //数据库连接类
	 private PreparedStatement pstmt = null;      //执行sql语句需要使用的对象
	 private List newsList = new ArrayList();     //用于存放字段信息的链表
	 private NewsBean bean = new NewsBean();      //和数据库字段对应的JavaBean
	 
	 public NewsBean setNews(String url){
		
		 bean = new NewsBean();
			String content="";
			String date = "";
			 String author = "";
			
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("------------网址不合法-------------");
			}
		    String title = doc.head().select("title").text().toString();
		    System.out.println("新闻标题:"+title);
		    bean.setNewsTitle(title);  //得到标题
		    
		   if(url.contains("news.ifeng.com")){
			   date = doc.select("[class=ss01]").text().toString();
			   content = doc.select("[class=js_selection_area]").text().toString();
			   author = doc.select("[class=ss03]").text().toString();
			   
		   }else if(url.contains("tech.ifeng.com")){
			   date = doc.select("[class=ss01]").text().toString();
			   content = doc.select("[class=js_selection_area]").text().toString();
			   author = doc.select("[class=ss03]").text().toString();
			   
		   }else if(url.contains("biz.ifeng.com")){
			   String str = doc.getElementById("artical_sth").select("p").text().toString();
			   date = str.substring(0, str.indexOf("来源"));
			   content = doc.getElementById("artical_real").text().toString();
			   author = str.substring(str.indexOf("来源"));
		   }else{
			   date = doc.select("[class=ss01]").text().toString();
			   content = doc.select("[class=js_selection_area]").text().toString();
			   author = doc.select("[class=ss03]").text().toString();
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
		String url = "http://biz.ifeng.com/business/tech/detail_2016_04/06/4798043_0.shtml";
		 IFengNews news = new IFengNews();
		 NewsBean bean = news.setNews(url);
		 news.saveToDB(bean);
	}
}
