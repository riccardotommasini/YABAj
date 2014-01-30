$(document).ready(function() {
	var addable = $("#addable");
	var id = 1;
	$("#remove").attr("disabled", true);
	$("#product0").autocomplete({
		source: function( request, response ) {
			console.log(request);
			$.ajax({
				url: "/search/json?type=shopProducts",
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
	
	function generate() {
		return "<div class='shop-product form-group'>" + 
		"<div class='col-xs-12 col-md-12'>" +
		"<input id='product" + id + "' type='text' name='products' class='form-control' placeholder='Product'/>" +
		"</div>";
	}
	
	
	$("#add").click(function() {
		addable.append(generate());
		$("#remove").removeAttr("disabled");
		$("#product" + id).autocomplete({
			source: function( request, response ) {
				console.log(request);
				$.ajax({
					url: "/search/json?type=shopProducts?query="request.term,
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
		id++;
	});
	
	$("#remove").click(function() {
		var listInput = $(".shop-product");
		if(listInput.length == 2){
			$("#remove").attr("disabled", true);
		}
		var last = listInput[listInput.length - 1 ];
		last.remove();
		id--;
	});
	
	
});
