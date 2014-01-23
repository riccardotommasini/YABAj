$(document).ready(function() {
	var container = $("#post-form .input-group");
	var waitBlock = $("#wait-geolocation");
	if($("#register").length == 0){
		waitBlock.hide();	
		getLocation();
	}
	
	function getLocation() {
		waitBlock.show();
		if (navigator.geolocation) {
			return navigator.geolocation.getCurrentPosition(setPosition,errorCallback,{timeout:10000});
		} else{
			element.html("<div class='alert alert-danger alert-dismissable'>" +
					"<button type='button' class='close' data-dismiss='alert' " +
					"aria-hidden='true'>&times;</button>" +
					"<strong>Warning!</strong> Geolocation is not supported " +
					"by your browser ... " +
					"<a href='http://www.google.it/intl/it/chrome/browser/' " +
					"class='alert-link'>get Chrome!</a></div>");
		}
	}

	function setPosition(position) {
		waitBlock.remove();
		if($("#errorHandler").length > 0){
			$("#errorHandler").remove();
		}
		container.append("<button id='new-post' class='btn btn-primary'>New Post</button>");
		$("#new-post").click(function() {
			window.location.href = "/posts/add?latitude=" + position.coords.latitude + 
									"&longitude=" + position.coords.longitude;;
		});
	}
	
	function errorCallback(){
		if($("#errorHandler").length == 0){
			container.append("<div id='errorHandler' class='alert alert-warning '>" +
				"<p><strong>Oh Snap!</strong> Takes a long time ... </p>" +
				"did you allowed position tracking?</div>");
		}
		getLocation();
	}
	
});

