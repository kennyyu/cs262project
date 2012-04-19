<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("uid") == null)
		response.sendRedirect("./login.jsp");
	else if(session.getAttribute("utype").equals("admin"))
		response.sendRedirect("./admin.jsp");
%>
<!DOCTYPE html>
<html>
<head>
	<meta content="charset=UTF-8">
	
	<link href="./css/styles.css" type="text/css" rel="stylesheet" />
        <link href='http://fonts.googleapis.com/css?family=Sirin+Stencil' rel='stylesheet' type='text/css'>
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="./js/student.js"></script>
    <script type="text/javascript" src="./js/Sugared_DOM.js"></script>
	
	
	<title>Student Panel - Distributed Grading (CS 262 Final Project)</title>

</head>
<body>
	<div id="wrapper-main">
		<div id="main-header" class="header-text">
			<h1>Student Panel</h1>
		</div>
		<div id="main-nav">
			<ul class="tabs">
				<li class="active-tab">Manage Grading</li>
				<li>View Grades</li>
			</ul>
		</div>
		<div><a style="font-style:italic;float:right;font-size:11pt;" href="./logout.jsp">Log Out</a></div>
		<article id="main-content" style="clear:both;">
			<section class="active-tab" id="tab-manage-grading">
				<h2 class="tab-header">Manage Grading</h2>
				<div class="tab-content">
					<form>
						<div class="form-section">
							<div class="section-label">Submit Grade</div>
							<input type="text" name="student" placeholder="enter gradee id" />
							<input type="text" name="assignment" placeholder="enter assignment id" />
							<input type="hidden" name="uid" value="<%=session.getAttribute("uid") %>" />
							<input type="file" name="grade" placeholder="upload graded assignment" />
						</div>
						<div class="form-error-box"></div>
						<input name="submit" type="submit" value="submit grade" />
					</form>
					<hr />
					<div class="results-wrapper">
						<div class="results-header" class="header-text">Request Results</div>
						<div class="results-content">
						</div>
					</div>
				</div>
			</section>
			<section id="tab-view-grades">
				<h2 class="tab-header">View Grades</h2>
				<div class="tab-content">
					<form>
						<div class="form-section"><input type="text" name="assignment" placeholder="enter (optional) assignment id" /></div>
						<input type="submit" name="submit" value="get grades" />
						<input type="hidden" name="uid" value="<%=session.getAttribute("uid") %>" />
					</form>
					<hr />
					<div class="results-wrapper">
						<div class="results-header" class="header-text">Request Results</div>
						<div class="results-content">
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