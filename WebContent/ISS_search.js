function search(){
	var data = $("#searchbar").val();
	
	if(date =""){}
	else{
		$("#searchbar").autocomplete({
			source: function(request, response){
				$.ajax({
					type : 'POST',
					url : './searchServlet',
					dataType: "json",
					data : {
						keyword : data
					},
					success : function(result){
						response(result);
					}
				});
			},
			minLength : 2,
		});
	}
}
	
