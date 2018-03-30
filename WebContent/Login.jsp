<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>

<link rel="stylesheet" href="css/ISS.css">
<script src="js/ISS.js"></script>

<!--부트스트랩-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>
<body>
	<div class="container">
		<div class="login">
			<form class="form-login" method="post" action="Login_c.jsp">
				<h3 class="form-login-heading">로그인해주세요</h3>
				<input type="email" name="email" class="form-control" placeholder="이메일" required autofocus>
				<input type="password" name="password" class="form-control" placeholder="비밀번호" required>
				<button class="btn btn-primary" type="submit">로그인</button>
			</form>
			<div class="join">
				<p class="form-join">계정이 없으신가요?
					<a herf="/">가입하기</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>