<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	contentType="text/html; charset=GBK"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="Content-type" content="text/html;charset=GBK">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
    
     function check(){
    	
    	
    	var name = document.getElementById("newsfield").value;
    	
    	if(name == "" || name.equals(" ")||name==null){
    		alert("输入一个关键字吧！");
    	}
        
		
     }
    </script>

	</head>
	

	<body >
		<div id="Layer1"
			style="position: absolute; width: 100%; height: 100%; z-index: -1">
			<img src="image/bg.jpg" height="100%" width="100%" />
		</div>
		<div style="width: 100%;padding-top: 300px" align="center" >
			<form action="newsServlet" name="form" method="post" id="form" >
			   <input id="newsfield" name="newsfield" size="80" type="text" style="height: 30; font-family: cursive; font-size: 15; "/>
			   
			  <button onclick="check();" style="border: thick;border-color: blue;height: 30;" type="submit"> 爬 取 新 闻 </button>
			</form>
		</div>
	</body>
</html>
