$(document).ready(function() {
	
	function addAssignment(desc, id) {

		var option = new Option(desc,id);
		$("form#review-student-work-form select")[0].add(option);
		option = new Option(desc,id);
		$("form#assign-graders-form select")[0].add(option);
		
	}
	
	// get list of assignments
	$.ajax({
		url: './publicgetassignments',
		type: 'post',
		dataType: 'json',
		success: function(data) {
			
			// data is array of assignments
			var assignments = data.assignments;
			
			// populate assignment select menu
			for(var i in assignments) {
				var assignment = assignments[i];
				addAssignment(assignment.description, assignment.id);
			}
			
		},
		error: function(e,jqXHR,ajaxSettings,exception){
			console.log(e.responseText);
		}
	});

	
	/**
	 * manage assignment stuff
	 */
	
	// add assignment form
	$("form#add-assignment-form").submit(function(){
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// get inputs
		var description = $.trim(this.elements["description"].value);
		
		
		// validate inputs
		if(description != "") {
			
			// send request
			$.ajax({
				url: './adminaddassignment',
				type: 'post',
				dataType: 'text',
				data: {description:description},
				success: function(data) {
					
					// add assignment
					// data is assignment ID
					addAssignment(description, data);
					
					// notify user
					var newError = buildFormError(
							'Succesfully added assignment '+description+'.'
						);
					errorBox.empty().append(newError);
					
					// select newly-added assignment in next form
					var select = $("form#assign-graders-form select")[0];
					select.options[select.options.length - 1].selected = true;
					$("form#assign-graders-form select").trigger('change');
					
				},
				error: function(e){
					var errMessage = $(e.responseText).children("b:contains('message')").next().text();
					errorBox.prepend(buildFormError('query to server failed:'+
							errMessage
					));
				}
			});
			
		} else {
			var newError = buildFormError(
						'To add an assignment ' +
						'you need to enter a title'
					);
			errorBox.empty().append(newError);
		}
		
		return false;	// prevent page refresh
		
	});
	
	// disabled submit buttons until proper fields have been filled
	$("form#assign-graders-form select").change(function() {
		$("form#review-assignments-form input[name='assignment']").val(this.value);
		if(this.value == -1) {
			$(this.form.elements["submit"]).attr('disabled','disabled');
			$("form#review-assignments-form input[type='submit']").attr('disabled','disabled');
		} else {
			$("form#review-assignments-form input[type='submit']").removeAttr('disabled');
			$(this.form.elements["submit"]).removeAttr('disabled');
		}
	});
	
	// show returned shard to user
	function viewShard(shard) {
		
		// select shard in send assignments form
		$("form#send-assignments-form input[name='shard']").val(shard.id);
		// clear rows of shard review table
		var table = $("div#review-assignments-table-wrapper > table > tbody");
		table.empty();
		if(shard && shard.shard) {
			shard.shard.forEach(function(assignment) {
				var gradees = "";
				assignment.gradees.forEach(function(gradee){
					gradees += " "+gradee;
				});
				table.append(
					el('tr',[
	    				el('td',[""+assignment.grader]),
	    				el('td',[gradees.substring(1)]),
	    			])
				);
			});
		}
		
		$("form#send-assignments-form input[name='submit']").removeAttr('disabled');
		
	}
	
	$("form#assign-graders-form").submit(function() {
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		 // get inputs
		var id = parseInt($.trim(this.elements["assignment"].value));
		
		// validate inputs
		if(!isNaN(id) && id >= 0) {
			
			// send request
			$.ajax({
				url: './adminshardassignment',
				type: 'post',
				dataType: 'json',
				data: {assignmentID:id},
				success: viewShard,
				error: function(e){
					$("div#review-assignments-table-wrapper > table > tbody").empty();
					var errMessage = $(e.responseText).children("b:contains('message')").next().text();
					errorBox.prepend(buildFormError('query to server failed:'+
							errMessage
					));
				}
			});
			
		} else {
			var newError = buildFormError(
						'Please, select an assignment.'
					);
			errorBox.empty().append(newError).hide().fadeIn(1500);
		}
		
		return false;
		
	});
	
	$("form#review-assignments-form").submit(function(){
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		 // get inputs
		var id = parseInt($.trim(this.elements["assignment"].value));
		
		// validate inputs
		if(!isNaN(id) && id >= 0) {
			
			// send request
			$.ajax({
				url: './adminreviewshard',
				type: 'post',
				dataType: 'json',
				data: {assignmentID:id},
				success: viewShard,
				error: function(e){
					$("div#review-assignments-table-wrapper > table > tbody").empty();
					var errMessage = $(e.responseText).children("b:contains('message')").next().text();
					errorBox.prepend(buildFormError('query to server failed:'+
							errMessage
					));
				}
			});
			
		} else {
			var newError = buildFormError(
						'Please, select an assignment.'
					);
			errorBox.append(newError).hide().fadeIn(1500);
		}
		
		return false;	// prevent page refresh
		
	});
	
	$("form#send-assignments-form").submit(function() {
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		 // get inputs
		var id = parseInt($.trim(this.elements["shard"].value));
		
		var form = this;
		
		$.ajax({
			url: './adminsendshard',
			type: 'post',
			dataType: 'text',
			data:{shardID:id},
			success: function(data) {
				var newError = buildFormError(
						'Assignments sent.'
					);
				errorBox.empty().append(newError);
				$(form.elements["submit"]).attr('disabled','disabled');
			}
		});
		
		return false;
		
	});
	
	/*
	 * Form stuff
	 */
	function buildFormError(message) {
		var newError = el('div.form-error',[message,
	        				el('button.form-error-close-button',
	        						{title:'click to close'},[
	        						el('img',{
	        							src:'./images/icons_mono_32x32/stop32.png',
	        							height: 16,
	        							width: 16
	        						})
	        						
	        				])
	        			]);
		return newError;
			
	}
	$("div.form-error-box").delegate("button",'click',function(){
		$(this).parent().hide().remove();
		return false;
	});
	
	/**
	 * Review Student Work stuff
	 */
	
	// handle form submission
	// validate inputs and then send AJAX request
	$("form#review-student-work-form").submit(function(){
		
		// get input (assignment ID)
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// check grades request
		if(!isNaN(assignment)) {
			
			$.ajax({
				url: './admingetgrades',
				type: 'post',
				dataType: 'json',
				data: {assignment:assignment},
				success: function(data) {
					// populate table
					var table = $("table#review-student-work-table > tbody");
					table.empty();
					data.submissions.forEach(function(submission) {
						var grades = "";
						submission.grades.forEach(function(grade){
							grades += " <"+grade.score+","+grade.grader+">";
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
				error: function(e){
					var errMessage = $(e.responseText).children("b:contains('message')").next().text();
					errorBox.prepend(buildFormError('query to server failed:'+
							errMessage
					));
				}
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
	
	
	/*
	 * navigation
	 */
	$("div#main-nav > ul > li").click(function() {
		var thisTab = $(this);
		if(!thisTab.hasClass("active-tab")) {
			var tabToShowText = $(thisTab).text();
			var activeTabSection = $("section.active-tab");
			activeTabSection.slideUp("slow");
			activeTabSection.removeClass("active-tab");
			var tabToShow = $("section:has(h2:contains('"+tabToShowText+"'))");
			tabToShow.slideDown("slow").addClass("active-tab");
			$("div#main-nav > ul > li.active-tab").removeClass("active-tab");
			thisTab.addClass("active-tab");
		}
	});
	
});