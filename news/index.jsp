<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/backend/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>新闻爬取</title>
		<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size:12px;
	color:#666666;
	background:#fff;
	text-align:center;

}

* {
	margin:0;
	padding:0;
}

a {
	color:#1E7ACE;
	text-decoration:none;	
}

a:hover {
	color:#000;
	text-decoration:underline;
}
h3 {
	font-size:14px;
	font-weight:bold;
}

pre,p {
	color:#1E7ACE;
	margin:4px;
}
input, select,textarea {
	padding:1px;
	margin:2px;
	font-size:12px;
}
.buttom{
	padding:1px 10px;
	font-size:12px;
	border:1px #1E7ACE solid;
	background:#D0F0FF;
}
#formwrapper {
	width:95%;
	margin:15px auto;
	padding:20px;
	text-align:left;
}

fieldset {
	padding:10px;
	margin-top:5px;
	border:1px solid #1E7ACE;
	background:#fff;
}

fieldset legend {
	color:#1E7ACE;
	font-weight:bold;
	background:#fff;
}

fieldset label {
	float:left;
	width:120px;
	text-align:right;
	padding:4px;
	margin:1px;
}

fieldset div {
	clear:left;
	margin-bottom:2px;
}

.enter{ text-align:center;}
.clear {
	clear:both;
}

-->
</style>	
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
			<img src="../image/bg.jpg" height="100%" width="100%" />
		</div>
		<div style="width: 100%;padding-top: 300px" align="center" >
			<form action="../newsServlet" name="form" method="post" id="form" >
			   <input id="newsfield" name="newsfield" size="80" type="text" style="height: 30; font-family: cursive; font-size: 15; "/>
			   
			  <button onclick="check();" style="border: thick;border-color: blue;height: 30; width: 80px" type="submit"> 爬 取 新 闻 </button>
			</form>
		</div>
	</body>
</html>
