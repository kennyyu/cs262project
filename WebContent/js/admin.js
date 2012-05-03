$(document).ready(function() {
	
	function addAssignment(desc, id) {

		var option = new Option(desc,id);
		$("form#review-student-work-form select")[0].add(option);
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
					
				},
				error: function(e,jqXHR,ajaxSettings,exception){
					var newError = buildFormError(
								'Query to server failed.'
							);
					errorBox.empty().append(newError);
					console.log(e.responseText);
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
	
	$("form#assign-graders-form").submit(function() {
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		 // get inputs
		var id = parseInt($.trim(this.elements["assignment"].value));
		var description = $.trim(this.elements["assignment"].text);
		
		// validate inputs
		if(!isNaN(id)) {
			
			// send request
			$.ajax({
				url: './adminshardassignment',
				type: 'post',
				dataType: 'json',
				data: {assignmentID:id,description:description},
				success: function(data) {
					
					console.log(data);
					
					// show assignments
					
				},
				error: function(e,jqXHR,ajaxSettings,exception){
					var newError = buildFormError(
								'Query to server failed.'
							);
					errorBox.empty().append(newError);
					console.log(e.responseText);
				}
			});
			
		} else {
			var newError = buildFormError(
						'Select an assignment.'
					);
			errorBox.append(newError).hide().fadeIn(1500);
		}
		
		return false;
		
	});
	
	$("form#send-assignments-form").submit(function() {
		
		$.ajax({
			url: './adminsendshard',
			type: 'post',
			dataType: 'text',
			data:{id:33},
			success: function(data) {
				console.log(data);
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
	$("#tab-review-student-work form").submit(function(){
		
		// retrieve and sanitize input values
		var student = parseInt($.trim(this.elements["student"].value));
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// ajax request object
		var request = {
				type:"post",
				data: {},
				success: function(data) {
					console.log(data);
					$("#tab-review-student-work div.results-box").append(buildRequestResult(data));
				},
				error: function(e,jqXHR,ajaxSettings,exception){
					console.log(e.responseText);
					errorBox.append(buildFormError('query to server failed'));
				}
		};
		
		// check grades request
		if(this.elements["type"].value == "grades") {
			request.url = "./admingetgrades";
			if(!isNaN(student) && !isNaN(assignment)) {
				request.data.student = student;
				request.data.assignment = assignment;
				$.ajax(request);
			} else {
				var newError = buildFormError(
							'To request grades ' +
							'you need to enter a number for ' +
							'both the student ID and the ' +
							'assignment ID');
				errorBox.append(newError).hide().fadeIn(2000);
			}
		} // check submissions request
		else if (this.elements["type"].value == "submissions") {
			request.url = "./admingetsubmissions";
			var isValidRequest = false;
			if(!isNaN(student)) {
				request.data.student = student;
				isValidRequest = true;
			}
			if(!isNaN(assignment)) {
				request.data.assignment = assignment;
				isValidRequest = true;
			}
			if(isValidRequest)
				$.ajax(request);
			else {
				var newError = buildFormError(
						'To request submissions(s) ' +
						'you need to enter a number for ' +
						'the student ID and/or the ' +
						'assignment ID'
					);
				errorBox.append(newError).hide().fadeIn(2000);
			}
		}
		
		return false;
	});
	
	/*
	 * result stuff
	 */
	function buildRequestResult(result) {
		var newResult = el('div.request-result',[result]);
		return newResult;
	}
	$("div.results-box").live("button",'click',function(){
		$(this).parent().remove();
	});
	
	
	/*
	 * navigation
	 */
	$("div#main-nav > ul > li").click(function() {
		var thisTab = $(this);
		if(!thisTab.hasClass("active-tab")) {
			var tabToShowText = $(thisTab).text();
			var activeTabSection = $("section.active-tab");
			activeTabSection.hide("slow");
			activeTabSection.removeClass("active-tab");
			var tabToShow = $("section:has(h2:contains('"+tabToShowText+"'))");
			tabToShow.show("slow").addClass("active-tab");
			$("div#main-nav > ul > li.active-tab").removeClass("active-tab");
			thisTab.addClass("active-tab");
		}
	});
	
});