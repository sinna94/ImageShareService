<%@page import="java.sql.ResultSet"%>
<%@page import="jsp.util.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<jsp:useBean id="member" class="jsp.member.MemberBean"/>
<jsp:setProperty name="member" property="*"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
</head>
<body>

<%
	member.setEmail(request.getParameter("email"));
	member.setPassword(request.getParameter("password"));

	DBConnection db = new DBConnection();
	String query = "select count(*) from Users where email = '" 
	+ member.getEmail() + "' and password = '" 
	+ member.getPassword() + "';";
	
	ResultSet rs = null;
	
	try{
		rs = db.getQueryResult(query);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	if(rs.next()){
		switch (rs.getInt("count(*)")){
		case 0:
			%>
			<script>alert("이메일 또는 비밀번호를 확인해주세요.");</script>
			<script>history.back(-1);</script>
			<%
		break;
		case 1:
			response.sendRedirect("account.jsp");
			break;
			default:
				break;
		}
	}

%>
</body>
</html>