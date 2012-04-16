<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta content="charset=UTF-8">
	
	<link href="./css/styles.css" type="text/css" rel="stylesheet" />
	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="./js/site.js"></script>
	
	
	<title>Administration - Distributed Grading (CS 262 Final Project)</title>

</head>
<body>
	<div id="wrapper-main">
		<div id="header">
			<h1>Admin Panel</h1>
		</div>
		<div id="content">
			<div id="wrapper-content">
				<form>
					<div class="form-section">
						<div class="section-label">What do you want?</div>
						<select name="what">
							<option selected="selected" value="">choose one</option>
							<option>grades</option>
							<option>submissions</option>
						</select>
					</div>
					<div class="form-section">
						<div class="section-label">For what/whom?</div>
						<input class="submissions-param" disabled="disabled" maxlength="32" name="student" type="text" placeholder="type student id here" />
						<input class="submissions-param" disabled="disabled" maxlength="32" name="assignment" type="text" placeholder="type assignment id here" />
						<input class="grades-param" disabled="disabled" maxlength="32" name="submission" type="text" placeholder="type submission id here" />
					</div>
					<div class="form-section">
						<div class="section-label">&nbsp;</div>
						<input disabled="disabled" type="submit" value="request data" />
					</div>
				</form>
				<hr />
				<div id="results">
					<div id="results-header">Request Results</div>
					<div id="results-content">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>