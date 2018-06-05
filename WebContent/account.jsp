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

<title><%=nick %></title>

</head>
<body>
	<jsp:include page="navbar.jsp"/>

	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 ">
				<p class="pull-right visible-xs"></p>
				<!-- profile -->
				<div class="jumbotron profile">
					<div class="row">
						<!-- profile image -->
						<div class="col-xs-5">
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
						<div class="col-xs-7">
							<div class="row">
								<div class="col-xs-12">
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
										if (!session.getAttribute("nick").equals(nick)){
									%>
								<!--follow button -->
								<div class="col-xs-3">
									<button type="button" class="btn btn-defalut" onclick="clickFollow();">
										<jsp:include page="getFollowServlet">
											<jsp:param name="id" value="<%=nick %>"/>
										</jsp:include>	
											Follow
									</button>
								</div>
								<%
									}
									else{
								%>
								<!-- upload button -->
								<div class="btn-group" role="group">
									<button type="button" class="btn btn-defalut" data-toggle="modal" data-target="#upload">
										<span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>
										Upload
									</button>
								<!-- edit button -->
									<button type="button" class="btn btn-defalut" data-toggle="modal" data-target="#edit">
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										Edit
									</button>
								<!-- album button -->
									<button type="button" class="btn btn-defalut" data-toggle="modal" data-target="#album">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
										album
									</button>
								<!-- logout button -->
									<form action="./UserLogoutServlet" method="GET">
										<button type="submit" class="btn btn-defalut">
											<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
											Logout
										</button>
									</form>
								</div>
								<!-- upload modal -->
								<div class="modal fade" id="upload" tabindex="-1" role="dialog"	aria-labelledby="myModalLabel" aria-hidden="true">
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
								<!-- edit modal -->
								<div class="modal fade" id="edit" tabindex="-1" role="dialog"	aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">정보 수정</h4>
											</div>
											<form action="ImageUploadServlet" method="post">
												<div class="modal-body">
													<jsp:include page="GetUserInfoServlet"/>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default">회원 탈퇴</button>
													<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
													<button class="btn btn-primary" type="submit"> 등록 </button>
												</div>
											</form>
										</div>
									</div>
								</div>
								<!-- album modal -->
								<div class="modal fade" id="album" tabindex="-1" role="dialog"	aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">앨범 추가</h4>
											</div>
											<form action="AlbumUploadServlet" method="post" enctype="multipart/form-data">
												<input type="text" id="album-name" name="name" class="form-control" placeholder="앨범 이름">
												<input class="form-control" type="file" id="files" accept="image/*" name="filename" multiple required/>
														
												<div class="modal-footer">
													<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
													<button class="btn btn-primary"> 등록 </button>
												</div>
											</form>
										</div>
									</div>
								</div>
								<%} %>
							</div>
						</div>
					</div>
				</div>
				
				<!-- tab pannel -->
				<div role="tabpanel">
				
				<!-- Nav tabs -->
					<ul class="nav nav-tabs nav-justified" id="tab" role="tablist">
						<li role="presentation" class="active">
							<a href="#image-tab" aria-controls="image-tab" role="tab" data-toggle="tab">사진</a>
						</li>
						<li role="presentation">
							<a href="#album-tab" aria-controls="album-tab" role="tab" data-toggle="tab">앨범</a>
						</li>
					</ul>
					
				<!-- Tab panes -->
					<div class="tab-content">
				<!-- image tab  -->
						<div role="tabpanel" class="image-wrapper row tab-pane active" id="image-tab">
						
						<%
							DBConnection db = new DBConnection();
							
							ResultSet rs;
							String query = "Select id, path from Image Where user_id = '" +  nick + "' order by id desc;";
							
							try {
								rs = db.getQueryResult(query);
								while(rs.next()) {
									out.print("<div class='col-xs-4 col-md-3'><a href='picture.jsp?id="+ rs.getInt("id") + "' class='thumbnail'> <img src='upload/" + nick + "/" +rs.getString("path") + "' alt='사진'></a></div>");
								}
							}catch(Exception e) {
								e.printStackTrace();
							}
					
						%>
						</div>
				<!-- album tab -->
						<div role="tabpanel" class="album-wrapper row tab-pane" id="album-tab">
						<%
							query="select * from Album where user_id ='" + snick + "';";
							int i=0;
							try{
								rs = db.getQueryResult(query);
								if(rs.next()){
									out.print("<div class='row'>");
									do{
										String name = rs.getString("name");
										if(name == null){
											name = snick + "의 앨범 " + String.valueOf(++i);
										}
										out.print("<div class='col-xs-4'><a href='albumview.jsp?id=" + rs.getString("id") + "'>" + name +"</a></div>");
										
									}while(rs.next());
									out.print("</div>");	
								} else{
									out.print("<h3>앨범이 없습니다.</h3>");
								}
							}catch(SQLException e){
								e.printStackTrace();
							}
						%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%} %>
</body>
</html>