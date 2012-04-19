<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(request.getParameter("type") == "student" &&
		request.getParameter("username") == "student") {
		session.setAttribute("uid", "1");
		session.setAttribute("utype", "student");
		response.sendRedirect("./index.jsp");
	} else if(request.getParameter("type") == "admin" &&
			request.getParameter("username") == "admin") {
		session.setAttribute("uid", "0");
		session.setAttribute("utype", "admin");
		response.sendRedirect("./index.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta content="charset=UTF-8">
	
	<link href="./css/styles.css" type="text/css" rel="stylesheet" />
        <link href='http://fonts.googleapis.com/css?family=Sirin+Stencil' rel='stylesheet' type='text/css'>
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="./js/login.js"></script>
    <script type="text/javascript" src="./js/Sugared_DOM.js"></script>
	
	
	<title>Log In - Distributed Grading (CS 262 Final Project)</title>

</head>
<body>
	<div id="wrapper-main">
		<div id="main-header" class="header-text">
			<h1>Log In</h1>
		</div>
		<div id="content">
			<form action="./login.jsp">
				<div class="form-section">
					<div class="form-field-wrapper centered">
						student <input type="radio" name="type" checked="checked" value="student" />
						admin <input type="radio" name="type" value="admin" />
					</div>
					<div class="form-field-wrapper">username: <input name="username" type="text" placeholder="enter userid" /></div>
					<div class="form-field-wrapper">password: <input name="password" type="password" placeholder="enter password" /></div>
					<input name="submit" type="submit" />
				</div>
			</form>
		</div>
		<div id="main-footer" class="header-text">
			&copy; 2012 Jim Danz, Stefan Muller, Kenny Yu, Tony Ho, Willie Yao, Leora Pearson, Rob Bowden
		</div>
	</div>
</body>
</html>