$(document).ready(function() {

	$("#input-shop").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "/search/json?entity=shop&query=" + $("#input-shop").val(),
				dataType: "json",
				success: function (data) {
					response( $.map( data, function(item) {
			            return {
				            label: "Shop: " + item.name,
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
	
	var price;
	$("#input-product").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "/search/json?entity=product&query=" + $("#input-product").val(),
				dataType: "json",
				success: function (data) {
					response( $.map( data, function(item) {
						price = item.price;
			            return {
				            label: "Product: " + item.name,
				            value: item.name
			            }
			        }));
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			$("#input-price").val(price);
		},
		open: function() {
			$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close: function() {
			$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});

});
