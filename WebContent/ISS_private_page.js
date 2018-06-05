function clickFollow(){
	var id = document.getElementById("profile-nick").textContent;
	$.ajax({
		type : 'POST',
		url : './followClickServlet',
		data :{
			id : id
		},
		success : function(result){
			alert(result);
			if(result == 1){
				$("#follow").css("color", "#d45a40");
			}
			else if(result == 0){
				$("#follow").css("color", "#212020");
			}
		}
	})
}

$('#tab a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	})

$('#tab a[href="#image-tab"]').tab('show')
$('#tab a[href="#album-tab"]').tab('show')
