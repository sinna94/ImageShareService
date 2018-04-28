<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>

<!--부트스트랩-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>


<link rel="stylesheet" href="ISS.css">
<script src="ISS.js"></script>

</head>
<body>
<%
	String id ="";
	id = (String) session.getAttribute("id");
	
	if(id == null || id.equals("")){
 %>
	<div class="container">
		<div class="login">
			<form class="form-login" method="post" action="UserLoginServlet">
				<h3 class="form-login-heading">로그인해주세요</h3>
				<input type="email" name="email" class="form-control" placeholder="이메일" required autofocus>
				<input type="password" name="password" class="form-control" placeholder="비밀번호" required>
				<button class="btn btn-primary" type="submit">로그인</button>
			</form>
			<div class="login-join">
				<p>계정이 없으신가요?
					<a href="javascript:void(0);" onclick="changeLoginSignin();">가입하기</a>
				</p>
			</div>
		</div>
		<div class="signin">
		<form class="form-signin" method="post" action="UserSignupServlet" onsubmit="return checkInfo();">
			<input type="email" id="email" name="email" oninput="checkEmail();" class="form-control" placeholder="이메일 주소" required autofocus>
			<input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required>
			<input type="password" id="passwordc" name="passwordCheck" oninput="checkPassword();" class="form-control" placeholder="비밀번호 확인" required>
			<input type="text" id="nickname" name="nickname" class="form-control" oninput="checkNickname();" placeholder="닉네임" required>
			<button class="btn btn-primary" type="submit">가입</button>
		</form>
		<div class="signin-login">
				<p>계정이 있으신가요?
					<a href="javascript:void(0);" onclick="changeLoginSignin();">로그인</a>
				</p>
			</div>
		</div>
	</div>
<%
		} else{
			response.sendRedirect("account.jsp");
		}
	
%>
</body>
</html>