function changeLoginSignin() {
    if ($(".login").css("display") != "none") {
    	$(".login").hide();
        $(".signin").show();
    } else {
    	$(".login").show();
        $(".signin").hide();
    }
};

function checkEmail(){	
	var email = $('#email').val();
	$.ajax({
		type : 'POST',
		url : './UserEmailCheckServlet',			
		data:{
			email : email
		},
		success : function(result){
			if(result == 1){
				$("#email").css("background-color", "#aaaaaa");
			}
			else{
				$("#email").css("background-color", "#25FF25");
			}
		}
	})
}