function clickLike(){
	
	var id = $('#image-id').val();
	
	$.ajax({
		type : 'POST',
		url : './likeClickServlet',
		data :{
			id : id
		},
		success : function(result){
			if(result == 1){
				$("#like").css("color", "#d45a40");
			}
			else if(result == 0){
				$("#like").css("color", "#212020");
			}
		}
	})
}