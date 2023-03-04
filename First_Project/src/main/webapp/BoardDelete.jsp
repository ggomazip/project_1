<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="HTML Study">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="author" content="Bruce">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style type="text/css">
input[type='button'] {
	background: #4f8262;
	color: white;
	border-radius: 5px;
	border: none;
	height: 25px;
	padding: 5px;
	cursor: pointer;
	width: 50px;
	margin: 0 auto;
}

div.box {
	border: 1px solid #8fb59d;
	border-radius: 10px;
	width: 85%;
	height: 120px;
	margin: 20px; auto;
	text-align: center;
}

div.btn_box {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 48px;
}

</style>
</head>
<body>
	<div class="box">
		<p class="text">정말 삭제하시겠습니까?</p>
		<div class="btn_box">
			<input type="button" value="삭제" onclick="deleteBoard('<%=request.getParameter("no")%>')">
			
			<input type="button" value="취소" onclick="window.close()">
		</div>
	</div>
	<script>
	function deleteBoard(no){
		let url = "BoardDelete.do?no="+no;
		console.log(url);
		opener.location.href =url;
		window.close(); //창닫기
	}
	</script>
</body>
</html>