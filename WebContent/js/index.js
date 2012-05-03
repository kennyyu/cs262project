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
		
		$.ajax({
			url: './publicgetgrades',
			type: 'post',
			success: function(data) {
				console.log(data);
				// populate table
				var table = $('table')[0];
				var row = table.insertRow(-1);
				var cell = row.insertCell(0);
				var textNode = document.createTextNode(assignment.description);
				cell.appendChild(textNode);
				cell = row.insertCell(1);
				textNode = document.createTextNode(assignment.id);
				cell.appendChild(textNode);
			},
			error: function(e,jqXHR,ajaxSettings,exception){console.log(e.responseText);}
		});
		
		return false;
		
	});
	
});