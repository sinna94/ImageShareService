<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="image.ImageDAO" %>
<%@ page import="image.ImageDTO" %>
<%@ page import="util.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>title</title>

<!--��Ʈ��Ʈ��-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="ISS_picture.css?ver=1">
<script src="ISS_album.js?ver=1"></script>

<%
	String account = (String) session.getAttribute("id");
	String id = (String) request.getParameter("id");
	String snick = (String) session.getAttribute("nick");
	
	if (account == null || account.equals("") || snick ==null) {
		response.sendRedirect("index.jsp");
		return;
	}
	else if (id == null || Integer.parseInt(id) < 0){
		response.sendRedirect("account.jsp");
		return;
	}
	DBConnection db = new DBConnection();
	String query = "select Album.name from Album where Album.id = " + id + ";";
	ResultSet rs = null;
	String name="";
	try{
		 rs = db.getQueryResult(query);
		 if(rs.next()){
		 	name = rs.getString("name");
		 }
	} catch(SQLException e){
		e.printStackTrace();
	}
%>

</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<h3><%=name %></h3>

	<div id="carousel-generic" class="carousel slide" data-ride="carousel">
		
		<div class="carousel-inner" role="listbox">
			<%
			
			
			query = "select user_id, path from Album_image, Image where album_id="+ id +" and image_id = id order by Album_image.order;";
			boolean first = true;
			try{
				rs = db.getQueryResult(query);
				while(rs.next()){
					if(first){
						out.println("<div class='item active'>");
						first = false;
					}
					else{
						out.println("<div class='item'>");
					}
					out.println("<img class='center-block' src='upload/" + rs.getString("user_id") + "/" + rs.getString("path") + "'>");
					out.println("</div>");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			%>
		</div>
		
		<a class="left carousel-control" href="#carousel-generic" role="button" data-slide="prev">
			<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		    <span class="sr-only">Previous</span>
		</a>
		<a class="right carousel-control" href="#carousel-generic" role="button" data-slide="next">
		    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		    <span class="sr-only">Next</span>
 		</a>
		
	</div>
	
</body>
</html>