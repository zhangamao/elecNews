package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  ���ݿ������࣬��java�����ܹ��������ݿ�
 *  @author Bob Hu
 * 
 */
public class ConnectionManager 
{
    private Connection con = null;  
    private boolean autoCommit = true;
    private PreparedStatement pstmt;
    private ResultSet rs;
    /**
     * ���캯��
     */
    public ConnectionManager() 
    {
       
    }

    /**
     *  �������ݿ�����Ӳ���������ȡ���ݿ����ӵĶ���
     */
    public Connection getConnection()
    {
         try 
         {
        	 //���������ƣ�sql server2005ʹ��
        	String dirveName = "com.mysql.jdbc.Driver";
 			String user = "sa";
 			String pwd = "123456";

 			String url = "jdbc:mysql://localhost:3306/news?useUnicode=true&characterEncoding=UTF-8";

        	//ʹ�����ϵĲ������г�ʼ��
            Class.forName(dirveName).newInstance();
            con = DriverManager.getConnection(url, user, pwd);
            con.setAutoCommit(autoCommit);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) 
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  con;
    }
    
    public List<String> getRs() throws SQLException{
    	List<String> titleList = new ArrayList<String>();
    	String sql = "select * from news";
    	pstmt = getConnection().prepareStatement(sql);
    	rs = pstmt.executeQuery();
    	while(rs.next()){
    		String title = rs.getString("newstitle");
    		titleList.add(title);
    	}
    	rs.close();
    	pstmt.close();
    	con.close();
    	return titleList;
    }
    
    /**
     * �ر����ݿ������
     */
    public void close() 
    {
        if (con != null) 
        {
            try 
            {
                con.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
            finally 
            {
                con = null;
            }
        }
    }
    
    //����ʹ��
    public static void main(String[] args) 
    {
       ConnectionManager conn = new ConnectionManager();
       Connection c = conn.getConnection();
       System.out.println(c.toString());
    }
}
