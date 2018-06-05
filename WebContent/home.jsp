<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="member.MemberBean"%>
<%@ page import="member.MemberDAO"%>
<%@ page import="util.DBConnection"%>

<%@ page import="java.io.*"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!--��Ʈ��Ʈ��-->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery-1.11.0.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="ISS_private_page.css">
<script src="ISS_private_page.js"></script>
<link rel="stylesheet" href="ISS.css">
<script src="ISS.js"></script>


<%

	String account = (String) session.getAttribute("id");

	if (account == null || account.equals("")) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	String nick = (String) request.getParameter("id");
	String snick = (String) session.getAttribute("nick");
	
	if (snick == null || snick.equals("")) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	if (nick == null){
		nick = snick;
	}

	MemberBean member = new MemberBean();
	member = new MemberDAO().getMember(nick, 1);
	
	if(member.getNickname() == null){
		
	}
	else{
%>

<title>Feed</title>

</head>
<body>
	<jsp:include page="navbar.jsp"/>

	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 ">
			<%
				DBConnection db = new DBConnection();
							
				ResultSet rs;
				String query = "select id, Image.user_id, path from Image, follow where follower_id = '" + snick + "' " + 
						"and Image.user_id = follow.user_id;";
							
				try {
					rs = db.getQueryResult(query);
					while(rs.next()) {
						out.print("<div class='col-xs-4 col-md-3'><a href='picture.jsp?id="+ rs.getInt("id") + "' class='thumbnail'> <img src='upload/" + rs.getString("user_id") + "/" +rs.getString("path") + "' alt='사진'></a></div>");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
					
			%>
			</div>
		</div>
	</div>
	<%} %>
</body>
</html>