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
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="ISS_private_page.css">
<script src="ISS_private_page.js"></script>

<%
	String account = (String) session.getAttribute("id");

	if (account == null || account.equals("")) {
		response.sendRedirect("index.jsp");
	}
	
	String nick = (String) request.getParameter("id");
	
	if (nick == null){
		nick = (String) session.getAttribute("nick");
	}

	MemberBean member = new MemberBean();
	member = new MemberDAO().getMember(nick, 1);
	
	if(member.getNickname() == null){
		
	}
	else{
%>

<title><%=nick %></title>

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
				<a class="navbar-brand" href="account.jsp">Home</a>
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
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 ">
				<p class="pull-right visible-xs"></p>
				<!-- profile -->
				<div class="jumbotron profile">
					<div class="row">
						<!-- profile image -->
						<div class="col-xs-5 col-md-5">
							<%
									if (member.getImage() == null) {
								%>
								<img src="profile.png" class="img-circle img-responsive profile-image" alt="프로필 이미지">
								
								<%
									} else {
	
										String path = member.getImage();
										String src = "upload/" + member.getNickname() + "/" + path;
										out.print("<img src='" + src
												+ "' class='img-responsive img-circle profile-image' alt='프로필 이미지'>");
									}
							%>
						</div>
						<!-- profile intro -->
						<div class="col-xs-6 col-md-6">
							<div id="profile">
								<%
									String nickname = member.getNickname();
									out.print("<h2 id='profile-nick'>" + nickname + "</h2>");
									String intro = member.getIntro();
									if (intro == null) {
										out.print("<p id='profile-intro'>" + nickname + " 입니다." + "</p>");
									} else {
										out.print("<p id='profile-intro'>" + member.getIntro() + "</p>");
									}
								%>
							</div>
						</div>
						
						<%
							if (session.getAttribute("nick").equals(nick)){
						%>
						<div class="col-xs-2 col-xs-offset-2 col-md-2 col-md-offset-4">
							<button type="button" class="btn btn-defalut" data-toggle="modal" data-target="#upload">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
								Upload
							</button>
						</div>
						<!-- upload modal -->
						<div class="modal fade" id="upload" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">이미지 업로드</h4>
									</div>
									<form action="ImageUploadServlet" method="post"	enctype="multipart/form-data">
										<div class="modal-body">
											<div class="row">
												<div class="col-xs-12">
													<input class="form-control" type="file" id="file" accept="image/*" name="filename" required/>
												</div>
												<div class="col-xs-12">
													<textarea id="content" class="form-control" rows="10" name="content" placeholder="내용 입력"></textarea>											
												</div>
												<div class="col-xs-12">
													<input type="text" id='tag' class="form-control" name="tag" placeholder="ex)#tag#abc">
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
											<button class="btn btn-primary" type="submit"> 등록 </button>
										</div>
									</form>
								</div>
							</div>
						</div>
						<%} %>
					</div>
				</div>
				<!-- image  -->
				<div class="image-wrapper row">
					
					<%
						DBConnection db = new DBConnection();
						
						ResultSet rs;
						String query = "Select id, path from Image Where user_id = '" +  nick + "' order by id desc;";
						
						try {
							rs = db.getQueryResult(query);
							while(rs.next()) {
								out.print("<div class='col-xs-4'><a href='picture.jsp?id="+ rs.getInt("id") + "' class='thumbnail'> <img src='upload/" + nick + "/" +rs.getString("path") + "' alt='사진'></a></div>");
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
				
					%>
					
				</div>
			</div>
		</div>
	</div>
	<%} %>
</body>
</html>