$(document).ready(function() {
	
	$("#shop-name").focusout(function() {
		$.ajax({
			url : "/search/json?entity=place&query=" + $("#shop-name").val(),
			dataType : "json",
			success : function(data) {
				$("#checkbox-container").html("");
				if(data.length > 0){
					$("#checkbox-container").html("<h4>Select places you wanna be in</h4>");
					$("#checkbox-container").show();
					$.map(data, function(item) {
						$('#checkbox-container').append(
							"<div class='checkbox col-md-12'>" +
							"<label>" +
							"<input type='checkbox' name='places' " +
							"value='" + item.name + "'>" + item.name +
							"</label>" +
							"</div>");
					});
				}
			}
		});
	});
	
});