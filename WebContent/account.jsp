<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="jsp.member.MemberBean" %>
<%@ page import="jsp.member.MemberDAO" %>
<%@ page import="java.io.*" %>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>

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

<link rel="stylesheet" href="ISS_private_page.css">
<script src="ISS_private_page.js"></script>

<%
	String account = (String) session.getAttribute("id");
	MemberBean member = new MemberBean();
	member = new MemberDAO().getMember(account);
%>

</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
	    <div class="container-fluid">
	        <!-- Brand and toggle get grouped for better mobile display -->
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
	          <span class="sr-only">Toggle navigation</span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	        </button>
	            <a class="navbar-brand" href="#">Brand</a>
	        </div>
	
	        <!-- Collect the nav links, forms, and other content for toggling -->
	        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
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
			<div class="col-xs-12 col-sm-12">
				<p class="pull-right visible-xs">
				</p>
				<!-- profile -->
				<div class="jumbotron profile">
					<div class="row">
						<div class="col-xs-5 col-lg-5">
						<% if(member.getImage() == null){ %>
							<!-- <img src="profile.png" class="img-responsive img-circle" id="profile-img" alt="프로필 이미지">
							--><%} else{
								
									InputStream in = member.getImage().getBinaryStream();
									BufferedImage bimg = ImageIO.read(in);
									in.close();
									
									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									ImageIO.write(bimg, "jpg", baos);
									baos.flush();
									byte[] imageInByteArray = baos.toByteArray();
									baos.close();
									String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
									out.print("<img src='data:x-image/jpg;base64,"+ b64+"' class='img-responsive img-circle' alt='프로필 이미지'>");
								
							}
						%>
						</div>
						<div class="col-xs-3 col-lg-6">
							<div id="profile">
								<% 
								String nickname = member.getNickname();
								out.print("<h2 id='profile-nick'>" + nickname + "</h2>");
								String intro = member.getIntro();
								if(intro == null){
									out.print("<p id='profile-intro'>" + nickname + " 입니다." + "</p>");
								} else{
									out.print("<p id='profile-intro'>" + member.getIntro() + "</p>");
								}
								%>
							</div>
						</div>
						<div class="col-xs-1 col-lg-1">
							<button type="button" class="btn btn-defalut">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
							Upload
							</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4 col-lg-4">
						<p>사진</p>
						 <a href="#" class="thumbnail">
      						<img src="..." alt="...">
 						 </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>