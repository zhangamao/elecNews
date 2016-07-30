<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=GBK"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新闻生成页面</title>
    <meta http-equiv="content-Type" content="text/html;charset=GBK" >
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript">
  
 
  </script>
  </head>
  
  <body>
    <table align="center" cellpadding="0" cellspacing="0">
     <tr><td align="center"> <img src="image/nav.jpg" width="800px" /></td></tr>
     <tr>
     <td>
     爬取新闻成功，点击按钮得到新闻。
     <form id="form" name="form" action="generate.jsp">
     <input type="submit" value="获取新闻" />
     </form>
     
     </td>
     </tr>
    </table>
    
    
  </body>
</html>
