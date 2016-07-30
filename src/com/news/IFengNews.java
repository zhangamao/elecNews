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

	 private ConnectionManager manager = null;    //���ݿ�������
	 private PreparedStatement pstmt = null;      //ִ��sql�����Ҫʹ�õĶ���
	 private List newsList = new ArrayList();     //���ڴ���ֶ���Ϣ������
	 private NewsBean bean = new NewsBean();      //�����ݿ��ֶζ�Ӧ��JavaBean
	 
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
				System.out.println("------------��ַ���Ϸ�-------------");
			}
		    String title = doc.head().select("title").text().toString();
		    System.out.println("���ű���:"+title);
		    bean.setNewsTitle(title);  //�õ�����
		    
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
			   date = str.substring(0, str.indexOf("��Դ"));
			   content = doc.getElementById("artical_real").text().toString();
			   author = str.substring(str.indexOf("��Դ"));
		   }else{
			   date = doc.select("[class=ss01]").text().toString();
			   content = doc.select("[class=js_selection_area]").text().toString();
			   author = doc.select("[class=ss03]").text().toString();
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
		String url = "http://biz.ifeng.com/business/tech/detail_2016_04/06/4798043_0.shtml";
		 IFengNews news = new IFengNews();
		 NewsBean bean = news.setNews(url);
		 news.saveToDB(bean);
	}
}
