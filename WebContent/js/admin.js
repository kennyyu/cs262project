$(document).ready(function() {
	
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
	// enabled/disable appropriate input fields depending
	// upon selection
	$("#tab-review-student-work select").change(function(){
		if(this.selectedIndex == 0) {
			$(this.form.elements["student"]).attr("disabled","disabled");
			$(this.form.elements["assignment"]).attr("disabled","disabled");
		} else {
			$(this.form.elements["student"]).removeAttr("disabled");
			$(this.form.elements["assignment"]).removeAttr("disabled");
		}
	});
	
	// enable submit button only when necessary fields have been filled
	/*$("#tab-review-student-work form").change(function(){
		
		var fields_to_fill = $("input[type='text']:enabled");
		var fields_to_fill_count = fields_to_fill.size();
		var fields_filled_count =  fields_to_fill.filter(function(index){
			return !/^\s*$/.test(this.value);
		}).size();
		if(fields_to_fill_count > 0 && (
				(this.elements["type"].value == "submissions" &&
						fields_filled_count >= 1) ||
				(this.elements["type"].value == "grades" &&
						fields_filled_count == 2)))
			$(this.elements["submit"]).removeAttr("disabled");
		else
			$(this.elements["submit"]).attr("disabled","disabled");
			
	});*/
	
	// handle form submission
	// validate inputs and then send AJAX request
	$("#tab-review-student-work form").submit(function(){
		
		// retrieve and sanitize input values
		var student = parseLong($.trim(this.elements["student"].value));
		var assignment = parseLong($.trim(this.elements["assignment"].value));
		
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
	
	/**
	 * manage assignment stuff
	 */
	// enabled/disable appropriate input fields depending
	// upon selection
	$("#tab-manage-assignments select").change(function(){
		var selectedOption = this.options[this.selectedIndex].value;
		var form = $(this.form)[0];
		if(selectedOption == "add") {
			$(form.elements["assignmentID"]).attr("disabled","disabled");
			$(form.elements["assignmentDescription"]).removeAttr("disabled");
		} else if (selectedOption == "shard") {
			$(form.elements["assignmentID"]).removeAttr("disabled");
			$(form.elements["assignmentContent"]).removeAttr("disabled","disabled");
		} else {
			$("#tab-manage-assignments form input").attr("disabled","disabled");
		}
	});
	
	// enable submit button only when necessary fields have been filled
	/*$("#tab-manage-assignments form").change(function(){
		
		var fields_to_fill = $("input[type='text']:enabled");
		var fields_to_fill_count = fields_to_fill.size();
		var fields_filled_count =  fields_to_fill.filter(function(index){
			return !/^\s*$/.test(this.value);
		}).size();
		if(fields_to_fill_count > 0 && (
				(this.elements["type"].value == "add" &&
						fields_filled_count >= 1) ||
				(this.elements["type"].value == "shard" &&
						fields_filled_count >= 1)))
			$(this.elements["submit"]).removeAttr("disabled");
		else
			$(this.elements["submit"]).attr("disabled","disabled");
			
	});*/
	
	// handle form submission
	// validate inputs and then send AJAX request
	$("#tab-manage-assignments form").submit(function(){
		
		var assignment;
		
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
		if(this.elements["type"].value == "add") {
			request.url = "./adminaddassignment";
			assignment = $.trim(this.elements["assignmentDescription"].value);
			if(assignment != "") {
				request.data.assignment = assignment;
				$.ajax(request);
			} else {
				var newError = buildFormError(
							'To add an assignment ' +
							'you need to enter a title'
						);
				errorBox.append(newError).hide().fadeIn(2000);
			}
		} // check submissions request
		else if (this.elements["type"].value == "shard") {
			request.url = "./adminshardassignment";
			assignment = parseLong($.trim(this.elements["assignmentID"].value));
			if(!isNaN(assignment)) {
				request.data.assignmentID = assignment;
				$.ajax(request);
			} else {
				var newError = buildFormError(
							'To shard an assignment ' +
							'you need to specify an assignment ' +
							'by entering its ID'
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