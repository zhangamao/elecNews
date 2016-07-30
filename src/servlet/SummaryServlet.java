package servlet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import textrank.TextRankKeyword;
import textrank.TextRankSummary;



import db.ConnectionManager;

public class SummaryServlet extends Thread{

	private ConnectionManager manager = new ConnectionManager();
	private PreparedStatement pstm = null;
	private Connection conn = null;
	private ResultSet rs = null;
	private TextRankKeyword keyword;
	private TextRankSummary summary;
	
	    public String getSummary(){
		
		conn = manager.getConnection();
		String sql = "select * from news";
		String title = null;
		StringBuffer content = new StringBuffer("");
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				title += rs.getString("newstitle");
				content.append( rs.getString("newscontent"));
			}
			
			rs.close();
			pstm.close();
			conn.close();
			
			keyword = new TextRankKeyword();
			title = keyword.getKeyword("", title);	
			
			List<String> cont = summary.getTopSentenceList(content.toString(), 30);
			
			content = new StringBuffer(title+"\n");
			for(String str: cont){
				content.append(str);
			}
			
			return content.toString();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main(String[] args){
		
		
		SummaryServlet summary = new SummaryServlet();
		System.out.println(summary.getSummary());
	}
	
}
