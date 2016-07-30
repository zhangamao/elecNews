package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchBean {

	private Connection con = null;
	ResultSet rs = null;
	Statement stmt_query;
	Statement stmt_update;

	/**
	 * ���캯����������ݿ�����ӹ���
	 * 
	 * @throws ClassNotFoundException
	 */
	public SearchBean() {
		try {
			String dirveName = "com.mysql.jdbc.Driver";
			String urser = "sa";
			String pwd = "123456";

			String url = "jdbc:mysql://localhost:3306/news?useUnicode=true&characterEncoding=UTF-8";

			Class.forName(dirveName);
			con = DriverManager.getConnection(url);
			stmt_query = con.createStatement();
			stmt_update = con.createStatement();
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("���ݿ�����ʧ��");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("faq():" + e.getMessage()+"��������ʧ��");
		}

	}
	
	/**
	 * �Ͽ������ݿ������
	 */
	public void close(){
		if(con!=null){
			try{
				con.close();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				con = null;
			}
		}
		
	}
	 /**
     * ����ָ����sql��䷵�ز�ѯ������ݼ� 
     * @param sql ��ָ����sql���
     * @return
     */
	public ResultSet executeQuery(String sql){
		rs = null;
		try{
			rs = stmt_query.executeQuery(sql);
		}catch(SQLException ex){
			System.out.println("aq.executeQuery()");
		}
		return rs;
	}
	
	 /**
     * �ַ�����ת������Ҫ�����������
     * @param str
     * @return
     */
	public String strtochn(String str)
	{
		   byte[] byte1=str.getBytes();
		   String tmp="";
		   try
		   {
		   	    tmp= new String(byte1,"8859_1");
		   }
		   catch(Exception e)
		   {
		   	   System.err.println("strToChn�쳣" + e.getMessage() );   
		   }
		   return tmp;
	}
	
	/**
	 * ִ��sql���
	 * @param sql
	 * @return
	 */
	public boolean executeUpdate( String sql )
	{
		   try
		   {
		   	   stmt_update.executeUpdate( sql );
		   	   return true;
		   }
		   catch( SQLException e )
		   {
		       System.err.println( " aq.excuteUpdate: " + e.getMessage() );	
		   }
		   return false;
	}
}
