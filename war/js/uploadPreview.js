$(document).ready(function() {
	 
	$("#cameraInput").change(function(event){
       handleFile(event);
    });
	
	function handleFile(e) {
		var canvas = document.getElementById('canvas');
	    var ctx = canvas.getContext("2d");
	    var reader = new FileReader;
	    reader.onload = function (event) {
	        $("#thumbnail").load(function(){
	            console.log( this.width, this.height );
	            canvas.width = this.width*.25;
	            canvas.height = this.height*.25;
	            ctx.drawImage(this, 0, 0, canvas.width, canvas.height)
	        }); 
	        $("#thumbnail").attr("src",reader.result);
	    }
	    reader.readAsDataURL(e.target.files[0]);
	}
});

