<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
::-webkit-scrollbar{
	width: 15px;
	height: 5px;
}
::-webkit-scrollbar-track{
	background: #4f8262;
	border-radius: 10px;
	box-shadow: inset 0px 0px 5px white;
}
::-webkit-scrollbar-thumb{
	border: 2px solid transparent;
	background: ivory;
	border-radius: 10px;
	background-clip: padding-box;
}
html::-webkit-scrollbar{
	width: 20px;
	height: 5px;
}
*{
	margin: 0;
	padding: 0;
	text-decoration: none;
	color: black;
}
a{
	text-decoration: none;
	cursor: pointer;
}
.header_box {
	position: fixed;
	background-color: white;
	width: 100%;
	margin: 0;
	top: 0;
	border-bottom: 1px solid #c2d1c8;
	height: 80px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	z-index: 1000;
}

div.logo {
	width: 30%;
	min-width : 150px;
	display: flex;
	justify-content: flex-end;
}

.logo img {
	height: 100px;
	margin-right: 50px;
}
div.search_box{
	width: 40%;
	min-width: 350px;
	display: flex;
	justify-content: center;
}
input.search {
	width: 350px;
	height:30px;
	border: none;
	border-radius: 5px;
	background: #dae0da;
}
div.menu_box{
	width: 30%;
	min-width: 300px;
	display: flex;
	justify-content: center;
	align-items: center;
}
div.menu_box a {
	display: inline-block;
	width: 40px;
	margin-right: 10px; 
}
div.menu_box img {
	width: 40px;
}
img.profile_img{
	border: 2px solid black;
	border-radius: 10px;
	width: 30px !important; 
}

div.top_line{
	margin-top: 90px;
	width: 100px;
}
input[type='button'], input[type='submit'], input[type='reset']{
		background: #4f8262;
		color: white;
		border-radius: 5px;
		border: none;
		height: 25px;
		padding: 5px;
		cursor: pointer;
}
a.hashTag{
	color: #4f8262;
}

</style>
</head>
<body>
	<div class="header_box">
		<div class="logo">
			<img alt="logo" src="img/logo.png">
		</div>
		<div class="search_box">
			<form action="BoardSearchList.do" method="post">
				<input type="text" name="search" class="search">
				<input type="submit" hidden>
			</form>
		</div>
		<div class="menu_box">
				<a href="BoardMain.do"><img alt="home" src="img/home.png"></a>
				<a href=""><img alt="message" src="img/message.png"></a>
				<a href="BoardWriteView.do"><img alt="write" src="img/write.png"></a>
				<a href=""><img alt="like" src="img/like.png"></a>
				<a href="BoardList.do"><img alt="search" src="img/search.png"></a>
				<c:if test="${profileImgSrc==null}">
				<a href="member_userProfile.mem?userId=${id}"><img class="profile_img" alt="profile" src="profile_img/profile.png"></a>
				</c:if>
				<c:if test="${profileImgSrc!=null}">
				<a href="member_userProfile.mem?userId=${id}"><img class="profile_img" alt="profile" src="${profileImgSrc}"></a>
				</c:if>
		</div>
	</div>
	<div class="top_line"></div>
	<script src="js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		function openSearch() {
			document.getElementById("search_main_box").style.display = "block";
		}
		function closeSearch() {
			document.getElementById("search_main_box").style.display = "none";
		}
	</script>
</body>
</html>