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