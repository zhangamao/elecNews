<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="servlet.*" %>
<%@ page import="textrank.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'generate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body>
     <%! String cont = new SummaryServlet().getSummary(); %>
     <table align="center" width="800px">
     
     <tr><td align="center"> <img src="image/nav.jpg" width="800px" /></td></tr>
     <tr><td><%! int index = cont.lastIndexOf("#"); %>
     <%=cont.substring(0, index) %>
     </td></tr>
     <tr>    
     <td align="left">
     <p>
     <font style="font-family:'Times New Roman';font-size-adjust: inherit; ">
        <%=cont.substring(index+1) %>
        </font>
        </p>
     </td>
     </tr>
   
    </table>
    
    
  </body>
</html>
