<%@page import="com.project.www.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="Board.css">
<style type="text/css">
div.write_box {
	border: 1px solid #8fb59d;
	border-radius: 10px;
	width: 500px;
	height: fit-content;
	margin: 0 auto;
	margin-bottom: 5px;
}
div.img_box{
	width:500px;
	height: fit-content;
	display: flex;
	justify-content: center;
	align-items: center;	
}
div.id_text{
	font-size: 20px;
	box-sizing: border-box;
	height: 100%;
	line-height: 60px;
}
img#previewImage{
	width: 95%;
	object-fit: contain; 
	object-position: center;
	border-radius: 10px;
}
textarea.contents{
	display: block;
	margin: 10px auto;
	padding: 5px;
	width: 95%;
	height: 300px;
	resize: none;
	border: 1px solid #8fb59d;
	border-radius: 10px;
}
div.buttons {
	width: 500px;
	margin: 0 auto;
	position: relative;
}
div.id_box{
	width: 100%;
	height: 60px;
	display: flex;
}
img.profileImg{
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: 1px solid #356246;
	box-sizing: border-box;
	margin: 5px;
}

</style>
</head>
<body>
<%@ include file ='HeaderLayout.jsp' %>
	<form action="BoardModifyUpdate.do?no=${view.no}" method="post" >
				
		<div class="write_box" id="test">
			<div class="id_box">
				<img class="profileImg" alt="profileImg" src="${view.profileImgSrc}">
				<div class="id_text">${view.id}</div>
			</div>
			<div class="img_box">
				<img id="previewImage" alt="NONE" src="${view.fileSrc}"><br />
			</div>
			<textarea class="contents"  name="contents" id="contents" required="required">${view.contentsText}</textarea>
		</div>
		<div class="buttons">
			<input type="submit" value="Update">
		</div>
	</form>

</body>
</html>