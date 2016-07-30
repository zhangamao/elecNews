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
	 private ConnectionManager manager = null;    //���ݿ�������
	 private PreparedStatement pstmt = null;      //ִ��sql�����Ҫʹ�õĶ���
	 private List newsList = new ArrayList();     //���ڴ���ֶ���Ϣ������
	 private NewsBean bean = new NewsBean();      //�����ݿ��ֶζ�Ӧ��JavaBean
	
	 public SouhuNews(){
		//initDB();
	}
	
	
	/**
	 * ������ݿ�ԭ�����ݣ������Զ���ŵ���ʼλ������Ϊ1
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
	 * �趨JavaBean�и��ֶε�ֵ
	 * @param newsTitle   ���ű������Ƣ�
     * @param newsauthor  ���α༭
     * @param newsContent ��������
     * @param newsDate    ��������
     * @param url         ��ҳ���ӵ�ַ
	 * @throws IOException 
	 */
	public NewsBean setNews(String url) throws IOException{
		bean = new NewsBean();
		Document doc = Jsoup.connect(url).get();
	    String title = doc.head().select("title").text().toString();
	    System.out.println("���ű��⣺"+title);
	    bean.setNewsTitle(title);  //�õ�����
	    String date = doc.select("[class=time]").text().toString(); //����ʱ��
	    System.out.println("ʱ�䣺"+date);
	    bean.setNewsDate(date);
	   
	    String content = doc.select("[class=text clear]").text().toString(); //��������
	    System.out.println("�������ݣ�"+content);
	    bean.setNewsContent(content);
	    
	    String author = "����";  //��������
	    System.out.println("�������ߣ�"+author);
	    bean.setNewsAuthor(author);
	    bean.setNewsURL(url);
	    System.out.println("���ŵ�ַ��"+url);
	    
	    return bean;
	}
	
	public static void main(String[] args) throws IOException, SQLException{
		String url = "http://roll.sohu.com/20160410/n443770982.shtml";
		SouhuNews news = new SouhuNews();
		NewsBean bean = news.setNews(url);
		
		news.saveToDB(bean);
	}
}
