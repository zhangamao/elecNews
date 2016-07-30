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

public class SouhuNews {
	 private ConnectionManager manager = null;    //数据库连接类
	 private PreparedStatement pstmt = null;      //执行sql语句需要使用的对象
	 private List newsList = new ArrayList();     //用于存放字段信息的链表
	 private NewsBean bean = new NewsBean();      //和数据库字段对应的JavaBean
	
	 public SouhuNews(){
		//initDB();
	}
	
	
	/**
	 * 清空数据库原有内容，并将自动编号的起始位置设置为1
	 */
	public void initDB(){
		
		String sql = "truncate table news";
		manager = new ConnectionManager();
		try{
			pstmt  = manager.getConnection().prepareStatement(sql);
			pstmt.execute();
			
		}catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally{
			try{
				pstmt.close();
				manager.close();
			}catch (SQLException ex) {
				// TODO: handle exception
				Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
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
			  Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally{
			 try 
	            {
	                pstmt.close();
	                manager.close();
	            } 
	            catch (SQLException ex) 
	            {
	                Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE, null, ex);
	            }
		}
		return flag;
	}
	/**
	 * 设定JavaBean中各字段的值
	 * @param newsTitle   新闻标题名称
     * @param newsauthor  责任编辑
     * @param newsContent 新闻内容
     * @param newsDate    发表日期
     * @param url         网页连接地址
	 * @throws IOException 
	 */
	public NewsBean setNews(String url) throws IOException{
		bean = new NewsBean();
		Document doc = Jsoup.connect(url).get();
	    String title = doc.head().select("title").text().toString();
	    System.out.println("新闻标题："+title);
	    bean.setNewsTitle(title);  //得到标题
	    String date = doc.select("[class=time]").text().toString(); //新闻时间
	    System.out.println("时间："+date);
	    bean.setNewsDate(date);
	   
	    String content = doc.select("[class=text clear]").text().toString(); //新闻内容
	    System.out.println("新闻内容："+content);
	    bean.setNewsContent(content);
	    
	    String author = "作者";  //新闻作者
	    System.out.println("新闻作者："+author);
	    bean.setNewsAuthor(author);
	    bean.setNewsURL(url);
	    System.out.println("新闻地址："+url);
	    
	    return bean;
	}
	
	public static void main(String[] args) throws IOException, SQLException{
		String url = "http://roll.sohu.com/20160410/n443770982.shtml";
		SouhuNews news = new SouhuNews();
		NewsBean bean = news.setNews(url);
		
		news.saveToDB(bean);
	}
}
