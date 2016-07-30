<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/backend/common/taglib.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="servlet.*"%>
<%@ page import="textrank.*"%>
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

		<title>新闻生成页面</title>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #666666;
	background: #fff;
	text-align: center;
}

* {
	margin: 0;
	padding: 0;
}

a {
	color: #1E7ACE;
	text-decoration: none;
}

a:hover {
	color: #000;
	text-decoration: underline;
}

h3 {
	font-size: 14px;
	font-weight: bold;
}

pre,p {
	color: #1E7ACE;
	margin: 4px;
}

input,select,textarea {
	padding: 1px;
	margin: 2px;
	font-size: 12px;
}

.buttom {
	padding: 1px 10px;
	font-size: 12px;
	border: 1px #1E7ACE solid;
	background: #D0F0FF;
}

#formwrapper {
	width: 95%;
	margin: 15px auto;
	padding: 20px;
	text-align: left;
}

fieldset {
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #1E7ACE;
	background: #fff;
}

fieldset legend {
	color: #1E7ACE;
	font-weight: bold;
	background: #fff;
}

fieldset label {
	float: left;
	width: 120px;
	text-align: right;
	padding: 4px;
	margin: 1px;
}

fieldset div {
	clear: left;
	margin-bottom: 2px;
}

.enter {
	text-align: center;
}

.clear {
	clear: both;
}
-->
</style>
	</head>

	<body>

		<div id="formwrapper">
			<fieldset>
				<%!String cont = new SummaryServlet().getSummary();%>
				<div>
					<legend>
						新闻标题关键词
					</legend>

					<%!int index = cont.lastIndexOf("#");%>

					<input type="text" name="title" id="title"
						value="<%=cont.substring(0, index)%>" />
				</div>

				<div>
					<label for="source">
						新闻来源
					</label>
					<input type="text" value="" />
				</div>
				<div>
					<label for="content">
						新闻内容
					</label>

					<textarea rows="20" cols="100" name="content" id="content" style="font-family: 'Times New Roman'; font-size-adjust: inherit;">
     						
							<%=cont.substring(index + 1)%>
    						
     </textarea>



				</div>

			</fieldset>
		</div>




	</body>
</html>
