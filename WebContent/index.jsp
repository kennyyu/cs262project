<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("uid") != null) {
		if(session.getAttribute("utype").equals("admin"))
			response.sendRedirect("./admin.jsp");
		else if(session.getAttribute("utype").equals("student"))
			response.sendRedirect("./student.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta content="charset=UTF-8">
	
	<link href="./css/styles.css" type="text/css" rel="stylesheet" />
        <link href='http://fonts.googleapis.com/css?family=Sirin+Stencil' rel='stylesheet' type='text/css'>
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="./js/site.js"></script>
    <script type="text/javascript" src="./js/Sugared_DOM.js"></script>
	
	
	<title>Administration - Distributed Grading (CS 262 Final Project)</title>

</head>
<body>
	<div id="wrapper-main">
		<div id="main-header" class="header-text">
			<h1>Admin Panel</h1>
		</div>
		<div id="main-nav">
			<ul class="tabs">
				<li class="active-tab">View Grades</li>
			</ul>
		</div>
		<div><a style="font-style:italic;float:right;font-size:11pt;" href="./login.jsp">Log In</a></div>
		<article id="main-content" style="clear:both;">
			<section class="active-tab" id="tab-view-grades">
				<h2 class="tab-header">View Grades</h2>
				<div class="tab-content">
					<form>
						<div class="form-section first-section">
							<div class="section-label">What do you want?</div>
							<select name="type">
								<option selected="selected" value="">choose one</option>
								<option>grades</option>
								<option>submissions</option>
							</select>
						</div>
						<div class="form-section second-section">
							<div class="section-label">For what/whom?</div>
							<input disabled="disabled" maxlength="32" name="student" type="text" placeholder="type student id here" />
							<input disabled="disabled" maxlength="32" name="assignment" type="text" placeholder="type assignment id here" />
						</div>
						<div id="form-error-box"></div>
						<input disabled="disabled" name="submit" type="submit" value="get grades" />
					</form>
					<hr />
					<div id="results-wrapper">
						<div id="results-header" class="header-text">Request Results</div>
						<div id="results-content">
						</div>
					</div>
				</div>
			</section>
		</article>
		<div id="main-footer" class="header-text">
			&copy; 2012 Jim Danz, Stefan Muller, Kenny Yu, Tony Ho, Willie Yao, Leora Pearson, Rob Bowden
		</div>
	</div>
</body>
</html>