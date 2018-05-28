<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="image.ImageDAO" %>
<%@ page import="image.ImageDTO" %>

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
<script src="ISS_picture.js?ver=1"></script>

<%
	String account = (String) session.getAttribute("id");
	String id = (String) request.getParameter("id");
	
	if (account == null || account.equals("")) {
		response.sendRedirect("index.jsp");
	}
	else if (id == null || Integer.parseInt(id) < 0){
		response.sendRedirect("account.jsp");
		return;
	}
	
	
%>

</head>
<body>
	<jsp:include page="navbar.jsp"/>

	<div class="container">
			<div class="row">
				<div class="col-xs-12 image-content">
					<% 
						ImageDTO image = new ImageDAO().getImage(id); 
							
						out.print("<img src='upload/" + image.getUser_id() + "/" + image.getPath() + "' class='img-responsive' alt='사진'>");
					%>
				</div>
			</div>
			<div class="row">
			<div class="content">
				<div class="row">
					<div class="like-wrapper col-xs-1">
						<jsp:include page="likeServlet">
							<jsp:param name="id" value="<%=id %>"/>
						</jsp:include>
					</div>
					<div class="col-xs-2 col-xs-offset-7 dropdown">
						<button class="btn btn-default dropdown-toggle" type="button" id="imageMenu" data-toggle="dropdown" aria-expanded="true">
							Menu
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
							<li role="presentation"><a role="menuitem" tabindex="-1" href="SetProfileImageServlet?id=<%=image.getPath() %>">프로필 사진으로 지정</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="ImageDeleteServlet?id=<%=id %>">사진 삭제</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="#">앨범으로 추가</a></li>
						</ul>
					</div>
				</div>
				<div class="nickname col-xs-12">
				<%
					String nick = image.getUser_id();
					out.print("<h3><a href='account.jsp?id=" + nick + "'>" + nick + "</a></h3>");
				%>
				</div>
				<div class="board col-xs-12">
					<%
						out.print(image.getContent());
					%>
					<br>
					<jsp:include page="getTagServlet">
						<jsp:param name="id" value="<%=id %>"/>
					</jsp:include>
				</div>
				<div class="comment-wrapper col-xs-12">
					<jsp:include page="commentServlet">
						<jsp:param name="id" value="<%=id %>"/>
					</jsp:include>
				</div>
				<div class="inputComment">
					<form action="commentInputServlet" method="post" onsubmit="return commentCheck();">
						<input name="id" type="hidden" id="image-id" value="<%=id%>">
						<div class="row">
							<div class="col-xs-10">
								<input type="text" name="comment" class="form-control" id="comment" required>								
							</div>
							<div class="col-xs-2">
								<button type="submit" class="btn-primary">등록</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>