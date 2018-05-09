$(document).ready(function() {
	$('#file').change(function(){
		addPreview($(this));
	});

});


function addPreview(input){
	if(input[0].files){
		for(var fileindex=0;fileindex < input[0].files.length; fileindex++){
			var file = input[0].files[fileindex];
			var reader = new FileReader();
			
			reader.onload = function(img){
				$("#thepicture").attr("src", img.target.result);
			//	$("#thepicture").append("<img src=\"" + img.target.result + "\"\/>");
			};
			reader.readAsDataURL(file);
		}
	}
	else {
		alert("incalid file input");
	}
}

jQuery(function() {
	  var picture = $('#thepicture')

	  var camelize = function() {
	    var regex = /[\W_]+(.)/g
	    var replacer = function (match, submatch) { return submatch.toUpperCase() }
	    return function (str) { return str.replace(regex, replacer) }
	  }()

	  picture.on('load', function() {
	    picture.guillotine({ eventOnChange: 'guillotinechange' })
	    picture.guillotine('fit')
	    for (var i=0; i<5; i++) { picture.guillotine('zoomIn') }

	    // Show controls and data
	    $('#controls').removeClass('hidden')

	    // Bind actions
	    $('#controls a').click(function(e) {
	      e.preventDefault()
	      action = camelize(this.id)
	      picture.guillotine(action)
	    })

	    // Update data on change
	    picture.on('guillotinechange', function(e,action) { })
	  })
})

