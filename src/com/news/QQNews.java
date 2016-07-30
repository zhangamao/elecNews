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

public class QQNews {

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
		    System.out.println("���ű��⣺"+title);
		    bean.setNewsTitle(title);  //�õ�����
		    
		    if(url.contains("cd.qq.com")){
		     date = doc.select("[class=article-time]").text().toString(); //����ʱ��
		     content= doc.select("[class=bd]").text().toString(); //��������
		     author = content.substring(content.indexOf("���α༭"),content.indexOf("��������"));  //��������
		     
		    
		    }else if(url.contains("tech.qq.com")){
		    	date = doc.select("[class=pubTime]").text().toString();
		    	content = doc.select("[class=bd]").text().toString();
		    	author = doc.select("[class=auth]").text().toString();
		    }else if(url.contains("hb.qq.com")){
		    	date = doc.select("[class=article-time]").text().toString();
		    	content = doc.select("[class=bd]").text().toString();
		    	author = doc.select("[class=color-a-3]").text().toString();
		    }else if(url.contains("cq.qq.com")){
		    	date = doc.select("[class=article-time]").text().toString();
		    	content = doc.select("[class=bd]").text().toString();
		    	author = doc.select("[class=color-a-1]").text().toString();
		    }else if(url.contains("henan.qq.com")){
		    	date = doc.select("[class=article-time]").text().toString();
		    	content = doc.select("[class=bd]").text().toString();
		    	author = doc.select("[class=color-a-1]").text().toString();
		    }else{
		    	date = doc.select("[class=article-time]").text().toString();
		    	content = doc.select("[class=bd]").text().toString();
		    	author = doc.select("[class=color-a-1]").text().toString();
		    }
		    
		    System.out.println("ʱ�䣺"+date);
		    bean.setNewsDate(date);   	    
		    
		   
		    System.out.println("�������ݣ�"+content);
		    bean.setNewsContent(content);
		    
		    
		    System.out.println("�������ߣ�"+author);
		    bean.setNewsAuthor(author);
		    bean.setNewsURL(url);
		    System.out.println("���ŵ�ַ����"+url);
		    
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
			String url = "http://henan.qq.com/a/20160205/020226.htm";
			QQNews news = new QQNews();
			NewsBean bean = news.setNews(url);
			news.saveToDB(bean);
		}
}
