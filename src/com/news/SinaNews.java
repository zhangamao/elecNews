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

	 private ConnectionManager manager = null;    //���ݿ�������
	 private PreparedStatement pstmt = null;      //ִ��sql�����Ҫʹ�õĶ���
	 private List newsList = new ArrayList();     //���ڴ���ֶ���Ϣ������
	 private NewsBean bean = new NewsBean();      //�����ݿ��ֶζ�Ӧ��JavaBean
	 
	 public NewsBean setNews(String url) throws IOException{
		
		 bean = new NewsBean();
			String content="";
			String date = "";
			 String author = "";
			
			Document doc = Jsoup.connect(url).get();
		    String title = doc.head().select("title").text().toString();
		    System.out.println("���ű���:"+title);
		    bean.setNewsTitle(title);  //�õ�����
		    
		    if(url.contains("tech.sina.com")){
		     date = doc.select("[class=titer]").text().toString(); //����ʱ��
		     content= doc.select("[class=content]").text().toString(); //��������
		     author = doc.select("[class=source]").text().toString();  //��������
		     
		    
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
		    System.out.println("ʱ�䣺"+date);
		    bean.setNewsDate(date);    
		    		    
		    System.out.println("�������ݣ�"+content);
		    bean.setNewsContent(content);	    
		    
		    System.out.println("�������ߣ�"+author);
		    bean.setNewsAuthor(author);
		    bean.setNewsURL(url);
		    System.out.println("���ŵ�ַ��"+url);
		    
		    return bean;
		}
	 
	 /**
		 * д�������ݿ��ʵ��
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
			
			//�����ű��ⳤ�ȵ�����
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
