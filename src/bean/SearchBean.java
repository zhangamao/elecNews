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
	 * 构造函数，完成数据库的连接工作
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
			System.out.println("数据库连接失败");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("faq():" + e.getMessage()+"驱动加载失败");
		}

	}
	
	/**
	 * 断开与数据库的连接
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
     * 根据指定的sql语句返回查询后的数据集 
     * @param sql 待指定的sql语句
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
     * 字符编码转换，主要解决乱码问题
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
		   	   System.err.println("strToChn异常" + e.getMessage() );   
		   }
		   return tmp;
	}
	
	/**
	 * 执行sql语句
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
