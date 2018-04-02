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
<title>회원 가입</title>
</head>
<body>
<%
	member.setEmail(request.getParameter("email"));
	member.setPassword(request.getParameter("password"));
	member.setNickname(request.getParameter("nickname"));
	
	DBConnection db = new DBConnection();
	String query ="INSERT INTO Users values ('" 
	+ member.getEmail() 
	+ "', '" + member.getPassword() 
	+"', '" + member.getNickname() + "');";
	
	int result = 0;
	
	try{
		result = db.noExcuteQuery(query);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	if(result != 0){
		%>회원가입 성공<%  
	}
	else{
		%>회원가입 실패<%
	}
	%>
</body>
</html>