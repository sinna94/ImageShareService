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

<link rel="stylesheet" href="css/ISS.css">
<script src="js/ISS.js"></script>

<!--��Ʈ��Ʈ��-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Brand</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
					<li><a href="#">Link</a></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container">
		<div>
			<div class="row">
				<div class="col-xs-12 col-md-7">
				<% 
					String id = (String) request.getParameter("id");
					
					ImageDTO image = new ImageDAO().getImage(id); 
					
					out.print("<img src='upload/" + image.getUser_id() + "/" + image.getPath() + "' alt='사진'>");
				%>
				</div>	
				<div class="col-xs-12 col-md-5">
					<div class="content">
						<div class="nickname">
							<%
							String nick = image.getUser_id();
							out.print("<h3><a href='account.jsp?id=" + nick + "'>" + nick + "</a></h3>");
							%>
						</div>
						<div class="board">
							<%
							out.print(image.getContent());
							%>
						</div>
					</div>
					<div class="comment">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>