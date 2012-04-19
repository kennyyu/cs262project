$(document).ready(function(){
	
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
	
	$("#tab-make-submission form").submit(function(){
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// ajax request object
		var request = {
				type:"post",
				data: {},
				success: function(data) {
					console.log(data);
					$("#tab-make-submission div.results-box").append(buildRequestResult(data));
				},
				error: function(e,jqXHR,ajaxSettings,exception){
					console.log(e.responseText);
					errorBox.append(buildFormError('query to server failed'));
				}
		};
		
		// retrieve and sanitize input values
		var student = parseInt($.trim(this.elements["student"].value));
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		var score = parseInt($.trim(this.elements["score"].value));
		var submission = this.elements["submission"].value;
		var uid = parseInt($.trim(this.elements["uid"].value));
		var type = this.elements["type"].value;
		
		if(type == "completed") {
			
			if(submission) {

				request.url = "./studentsubmitsubmission";
				request.data.uid = uid;
				request.data.submission = submission;
				$.ajax(request);
				
			} else {
				errorBox.prepend(buildFormError("Please, choose a file to upload.")
						.hide().fadeIn(2000));
			}
			
		} else if(type == "grade") {
			
			if(!isNaN(student) && !isNaN(assignment) && !isNaN(score)) {

				request.url = "./studentsubmitgrade";
				request.data.id = student;
				request.data.assignment = assignment;
				request.data.uid = uid;
				request.data.score = score;

				$.ajax(request);
				
			} else {
				var newError = buildFormError(
							'To request grades ' +
							'you need to enter a number for ' +
							'both the student ID and the ' +
							'assignment ID');
				errorBox.prepend(newError).hide().fadeIn(2000);
			}
			
		} else {
			var newError = buildFormError('invalid request');
			errorBox.prepend(newError).hide().fadeIn(2000);
		}
		
		return false;
		
	});
	
	$("#tab-view-grades form").submit(function(){
		
		// grab reference to error box just in case
		var errorBox = $(this).find("div.form-error-box");
		
		// ajax request object
		var request = {
				type:"post",
				data: {},
				success: function(data) {
					console.log(data);
					$("#tab-view-grades div.results-box").append(buildRequestResult(data));
				},
				error: function(e,jqXHR,ajaxSettings,exception){
					console.log(e.responseText);
					errorBox.append(buildFormError('query to server failed'));
				}
		};
		
		// check grades request
		request.url = "./studentgetgrades";
		
		// retrieve and sanitize input values
		var assignment = parseInt($.trim(this.elements["assignment"].value));
		var uid = parseInt($.trim(this.elements["uid"].value));
		
		if(!isNaN(uid)) {
			
			if(!isNaN(assignment)) {
				request.data.student = uid;
			}
			request.data.uid = uid;
			
			$.ajax(request);
			
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
			activeTabSection.hide("slow");
			activeTabSection.removeClass("active-tab");
			var tabToShow = $("section:has(h2:contains('"+tabToShowText+"'))");
			tabToShow.show("slow").addClass("active-tab");
			$("div#main-nav > ul > li.active-tab").removeClass("active-tab");
			thisTab.addClass("active-tab");
		}
	});
	
});