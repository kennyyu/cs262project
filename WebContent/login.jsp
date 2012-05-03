<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(request.getMethod().equals("POST")) {
		String user = request.getParameter("username");
		if(user != null) {
			if(user.startsWith("student")) {
				String id = user.substring("student".length());
				session.setAttribute("utype", "student");
				if(id.isEmpty() || Integer.valueOf(id) == 0) {
					session.setAttribute("uid", "0");
				} else {
					session.setAttribute("uid", id);
				}
				response.sendRedirect("./student.jsp");
			} else if(user.equals("admin")) {
				session.setAttribute("uid", "0");
				session.setAttribute("utype", "admin");
				response.sendRedirect("./admin.jsp");
			}
		}
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
		<div><a style="font-style:italic;float:right;font-size:11pt;" href="./index.jsp">Return to Index</a></div>
		<div id="main-content">
			<form action="./login.jsp" method="post">
				<div class="form-section">
					<div class="form-field-wrapper">username: <input name="username" type="text" placeholder="enter userid <user_type[id]>" /></div>
				</div>
				<input name="submit" type="submit" value="log in" />
			</form>
		</div>
		<div id="main-footer" class="header-text">
			&copy; 2012 Jim Danz, Stefan Muller, Kenny Yu, Tony Ho, Willie Yao, Leora Pearson, Rob Bowden
		</div>
	</div>
</body>
</html>