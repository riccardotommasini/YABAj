$(document).ready(function() {
	$('#tags').tagit({
		autocomplete: {
			delay: 0, 
			minLength: 2,
			source: function( request, response ) {
				$.ajax({
					url: "/search/json?type=tag&query="+request.term,
					dataType: "json",
					success: function (data) {
						response( $.map( data, function(item) {
				            return {
					            value: item.name
				            }
				        }));
					}
				});
			}
		},
		singleField: true,
		singleFieldNode: $('#tag-list')
	});
});
