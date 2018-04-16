function changeLoginSignin() {
    if ($(".login").css("display") != "none") {
    	$(".login").hide();
        $(".signin").show();
        document.title = "회원가입";
    } else {
    	$(".login").show();
        $(".signin").hide();
        document.title = "로그인";
    }
};

var ckId = false;
var ckIdC = false;
var ckPw = false;
var ckNick = false;

function checkEmail(){	
	var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	var email = $('#email').val();
	
	if(email.match(regex)){
	$.ajax({
		type : 'POST',
		url : './UserEmailCheckServlet',			
		data:{
			email : email
		},
		success : function(result){
			if(result == 1){
				$("#email").css("background-color", "#69fd1f");
				ckId = true;
				ckIdC = true;
			}
			else{
				$("#email").css("background-color", "#ec5252");
				ckId = false;
			}
		}
	})
	}
	else{
		$("#email").css("background-color", "#ffffff");
		ckIdC = false;
	}
}

function checkPassword(){
	var password = $('#password').val();
	var passwordc = $('#passwordc').val();
	
	if(password != "" && passwordc != ""){	
	if(password == passwordc){
		$("#passwordc").css("background-color", "#69fd1f");
		ckPw = true;
	}
	else{
		$("#passwordc").css("background-color", "#ec5252");
		ckPw = false;
	}
	}
}

function checkNickname(){	
	var nickname = $('#nickname').val();

	if(nickname != ""){
	$.ajax({
		type : 'POST',
		url : './UserNickCheckServlet',			
		data:{
			nickname : nickname
		},
		success : function(result){
			if(result == 1){
				$("#nickname").css("background-color", "#69fd1f");
				ckNick = true;
			}
			else{
				$("#nickname").css("background-color", "#ec5252");
				ckNick = false;
			}
		}
	})
	}
}

function checkInfo(){
	if(!ckIdC){
		alert("이메일을 다시 확인해주세요.");
		return false;
	}
	if(!ckId){
		alert("중복되는 이메일이 있습니다.");
		return false;
	}
	if(!ckPw){
		alert("비밀번호를 다시 확인해주세요.");
		return false;
	}
	if(!ckNick){
		alert("중복되는 닉네임이 있습니다.");
		return false;
	}
	if(ckId && ckIdC && ckPw && ckNick){
		return true;
	}
}