$(document).ready(function(){
	
	// get list of assignments
	$.ajax({
		url: './publicgetassignments',
		type: 'post',
		dataType: 'json',
		success: function(data) {
			
			// data is array of assignments
			var assignments = data.assignments;
			
			// populate assignment select menu
			var select = $('select')[0];
			for(var i in assignments) {
				var assignment = assignments[i];
				select.add(new Option(assignment.description, assignment.id));
			}
			
		},
		error: function(e,jqXHR,ajaxSettings,exception){
			console.log(e.responseText);
		}
	});
	
	$('#tab-view-grades form').submit(function(){
		
		// get input (assignment ID)
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// check grades request
		if(!isNaN(assignment)) {
			
			$.ajax({
				url: './publicgetgrades',
				type: 'post',
				dataType: 'json',
				data: {assignment:assignment},
				success: function(data) {
					// populate table
					var table = $("table > tbody");
					table.empty();
					data.submissions.forEach(function(submission) {
						var grades = "";
						submission.grades.forEach(function(grade){
							grades += " <"+grade.score+">";
						});
						if(grades == "") grades = " no grades for submission";
						table.append(
							el('tr',[
			    				el('td',[""+submission.student]),
			    				el('td',[grades.substring(1)]),
			    			])
						);
					});
				},
				error: function(e,jqXHR,ajaxSettings,exception){console.log(e.responseText);}
			});
			
		} else {
			var newError = buildFormError(
						'To request grades ' +
						'you need to enter a number for ' +
						'both the student ID and the ' +
						'assignment ID');
			errorBox.append(newError).hide().fadeIn(2000);
		}
		
		return false;
		
	});
	
});