$(document).ready(function() {

	$("#input-shop").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "/search/json?type=shop&query=" + $("#input-shop").val(),
				dataType: "json",
				success: function (data) {
					response( $.map( data, function(item) {
			            return {
			            	label: item.name,
				            value: item.name
			            }
			        }));
				}
			});
		},
		minLength: 1,
		open: function() {
			$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close: function() {
			$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});
	
	$("#input-product").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "/search/json?type=product&query=" + $("#input-product").val(),
				dataType: "json",
				success: function (data) {
					response( $.map( data, function(item) {
			            return {
				            label: item.name,
				            value: item.name
			            }
			        }));
				}
			});
		},
		minLength: 1,
		open: function() {
			$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close: function() {
			$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});

});
