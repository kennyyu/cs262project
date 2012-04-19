$(document).ready(function() {

	// enabled/disable appropriate input fields depending
	// upon selection
	$("select").change(function(){
		if(this.options[0].selected)
			$("form input[type='text']").attr("disabled","disabled");
		else
			$("form input[type='text']").removeAttr("disabled");
	});
	
	// enable submit button only when necessary fields have been filled
	$("form").change(function(){
		
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
			$("input[type='submit']").removeAttr("disabled");
		else
			$("input[type='submit']").attr("disabled","disabled");
			
	});
	
	// handle form submission
	// validate inputs and then send AJAX request
	$("form").submit(function(){
		
		// retrieve and sanitize input values
		var student = parseInt($.trim(this.elements["student"].value));
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		
		// ajax request object
		var request = {
				type:"post",
				data: {},
				success: function(data) {
					console.log(data);
					//$("#results-content").append($("<div class='request-result'>").append(data));
				},
				error: function(e,jqXHR,ajaxSettings,exception){console.log(e.responseText);}
		};
		
		// grab reference to error box just in case
		var errorBox = $("div#form-error-box");
		
		// check grades request
		if(this.elements["type"].value == "grades") {
			request.url = "./admingetgrades";
			if(!isNaN(student) && !isNaN(assignment)) {
				request.data.student = student;
				request.data.assignment = assignment;
				$.ajax(data);
			} else {
				var newError = el('div.form-error',[
							'To request grades ' +
							'you need to enter a number for ' +
							'both the student ID and the ' +
							'assignment ID'
						]);
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
				var newError = el('div.form-error',[
						'To request submissions(s) ' +
						'you need to enter a number for ' +
						'the student ID and/or the ' +
						'assignment ID',
						el('button.form-error-close-button',
								{title:'click to close'},[
								el('img',{
									src:'./images/icons_mono_32x32/stop32.png',
									height: 16,
									width: 16
								})
								
						])
					]);
				errorBox.append(newError).hide().fadeIn(2000);
			}
		}
		
		return false;
	});
	
});