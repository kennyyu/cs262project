<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("uid") == null)
		response.sendRedirect("./login.jsp");
	else if(session.getAttribute("utype").equals("student"))
		response.sendRedirect("./student.jsp");
%>
<!DOCTYPE html>
<html>
<head>
	<meta content="charset=UTF-8">
	
	<link href="./css/styles.css" type="text/css" rel="stylesheet" />
        <link href='http://fonts.googleapis.com/css?family=Sirin+Stencil' rel='stylesheet' type='text/css'>
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="./js/admin.js"></script>
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
				<li class="active-tab">Review Student Work</li>
				<li>Manage Assignments</li>
			</ul>
		</div>
		<div><a style="font-style:italic;float:right;font-size:11pt;" href="./logout.jsp">Log Out</a></div>
		<article id="main-content" style="clear:both;">
			<section class="active-tab" id="tab-review-student-work">
				<h2 class="tab-header">Review Student Work</h2>
				<div class="tab-content">
					<div class="form-section">
						<div class="section-label">Choose Assignment</div>
						<form id="review-student-work-form">
							<select name="assignment">
								<option value="-1" selected="selected">select assignment</option>
							</select>
							<input name="submit" type="submit" value="go" />
						</form>
					</div>
					<div class="form-section">
						<table>
							<caption class="results-header">Grades for Assignment <span id="assignment-span"></span></caption>
							<colgroup title="index" span="1" />
							<colgroup title="grades" />		
							<thead>
								<tr>
									<th scope="col">Student</th>
									<th scope="colgroup">Grades</th>
								</tr>
							</thead>
							<tbody>
							</tbody>			
						</table>
					</div>
				</div>
			</section>
			<section id="tab-manage-assignments">
				<h2 class="tab-header">Manage Assignments</h2>
				<div class="tab-content">
					<div class="form-section">
						<div class="section-label">Add Assignment</div>
						<form id="add-assignment-form">
							<input type="text" name="description" maxlength="256" placeholder="type assignment name" />
							<div class="form-error-box"></div>
							<input name="submit" type="submit" value="add assignment" />
						</form>
					</div>
					<div class="form-section">
						<div class="section-label">Assign Graders</div>
						<form id="assign-graders-form">
							<select name="assignment">
								<option value="-1" selected="selected">select assignment</option>
							</select>
							<div class="form-error-box"></div>
							<input name="submit" type="submit" value="get grader assignments" />
						</form>
						<div class="section-label">Send Assignments</div>
						<form id="send-assignments-form">
							<div class="form-error-box"></div>
							<input name="submit" type="submit" value="confirm and send assignments" />
						</form>
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