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
				<li class="active-tab">View Grades</li>
				<li>Make Submission</li>
			</ul>
		</div>
		<div><a style="font-style:italic;float:right;font-size:11pt;" href="./logout.jsp">Log Out</a></div>
		<article id="main-content" style="clear:both;">
			<section class="active-tab" id="tab-view-grades">
				<h2 class="tab-header">View Grades</h2>
				<div class="tab-content">
					<div class="form-section">
						<table id="view-grades-table">
							<caption class="results-header">Grades for All Assignments</caption>
							<colgroup title="index" span="1" />
							<colgroup title="grades" />		
							<thead>
								<tr>
									<th scope="col">Assignment</th>
									<th scope="colgroup">Grades &lt;grade,grader&gt;</th>
								</tr>
							</thead>
							<tbody>
							</tbody>			
						</table>
					</div>
				</div>
			</section>
			<section id="tab-make-submission">
				<h2 class="tab-header">Make Submission</h2>
				<div class="tab-content">
					<form>
						<div class="form-section first-section">
							<div class="section-label">What do you want to submit?</div>
							<select name="type">
								<option selected="selected" value="">select one</option>
								<option value="completed">Completed Assignment</option>
								<option value="graded">Graded Assignment</option>
							</select>
						</div>
						<div class="form-section second-section">
							<div class="section-label">Please, specify...</div>
							<input disabled="disabled" type="text" name="student" placeholder="enter gradee id" />
							<select disabled="disabled" name="assignment">
								<option value="-1">select one</option>
							</select>
							<input disabled="disabled" type="text" name="score" placeholder="enter score (grade)" />
             				<input disabled="disabled" type="text" name="comments" placeholder="enter comments" />
							<input disabled="disabled" type="file" name="submission" placeholder="upload assignment" />
							<input disabled="disabled" type="hidden" name="uid" value="<%=session.getAttribute("uid") %>" />
						</div>
						<div class="form-error-box"></div>
						<input name="submit" type="submit" value="submit" />
					</form>
				</div>
			</section>
		</article>
		<div id="main-footer" class="header-text">
			&copy; 2012 Jim Danz, Stefan Muller, Kenny Yu, Tony Ho, Willie Yao, Leora Pearson, Rob Bowden
		</div>
	</div>
</body>
</html>