package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.NewsBean;

import com.news.IFengNews;
import com.news.NETEaseNews;
import com.news.QQNews;
import com.news.SinaNews;
import com.news.SouhuNews;

import crawler.LinkParser;

import db.ConnectionManager;

public class NewsServlet extends HttpServlet {

	private ConnectionManager manager = null; // ���ݿ�������
	private PreparedStatement pstmt = null; // ִ��sql�����Ҫʹ�õĶ���
	private List newsList = new ArrayList(); // ���ڴ���ֶ���Ϣ������
	private NewsBean bean = new NewsBean(); // �����ݿ��ֶζ�Ӧ��JavaBean
	Thread thread = null;

	SouhuNews sohuNew; // �Ѻ�����
	QQNews qqNew; // ��Ѷ����
	IFengNews ifengNew; // ���������
	SinaNews sinaNew; // ��������
	NETEaseNews netEaseNew; // ��������

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		initDB();
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("newsfield");
		
        if(name.trim().equals("")||name.trim()==null){
        	System.out.println("��������Ϊ��");
        }
        else{
		System.out.println("��������Ϊ��" + name);

		LinkParser parser = new LinkParser();

		System.out.println("�������ݣ�" + name);
		
		response.setContentType("text/html;charset=UTF-8");

		
		Set<String> set = parser.extractLiks(name);
		StringBuffer sb = new StringBuffer("");
		for (String str : set) {
			sb.append(str + "\n");
			try {
				if (str.contains("sohu")) {
					System.out.println("-----" + str + "-----");
					sohuNew = new SouhuNews();
					bean = sohuNew.setNews(str);
					sohuNew.saveToDB(bean);
				} else if (str.contains("qq")) {
					System.out.println("-----" + str + "-----");
					qqNew = new QQNews();
					bean = qqNew.setNews(str);
					qqNew.saveToDB(bean);
				} else if (str.contains("sina")) {
					System.out.println("-----" + str + "-----");
					sinaNew = new SinaNews();
					bean = sinaNew.setNews(str);
					sinaNew.saveToDB(bean);

				} else if (str.contains("ifeng")) {
					System.out.println("-----" + str + "-----");
					ifengNew = new IFengNews();
					bean = ifengNew.setNews(str);
					ifengNew.saveToDB(bean);

				} else if (str.contains("163")) {
					System.out.println("-----" + str + "-----");
					netEaseNew = new NETEaseNews();
					bean = netEaseNew.setNews(str);
					netEaseNew.saveToDB(bean);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/backend/news/result.jsp");
		dispatcher.forward(request, response);
        }
	}

	public void initDB() {

		String sql = "truncate table news";
		manager = new ConnectionManager();
		try {
			pstmt = manager.getConnection().prepareStatement(sql);
			pstmt.execute();

		} catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE, null,
					ex);
		} finally {
			try {
				pstmt.close();
				manager.close();
			} catch (SQLException ex) {
				// TODO: handle exception
				Logger.getLogger(SouhuNews.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
