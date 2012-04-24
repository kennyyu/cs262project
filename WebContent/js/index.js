$(document).ready(function(){
	
	$('#tab-view-grades form').submit(function(){
		
		$.ajax({
			url: './publicgetgrades',
			type: 'post',
			success: function(data) {
				console.log(data);
				//$("#results-content").append($("<div class='request-result'>").append(data));
			},
			error: function(e,jqXHR,ajaxSettings,exception){console.log(e.responseText);}
		});
		
		return false;
		
	});
	
});