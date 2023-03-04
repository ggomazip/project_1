<%@page import="com.project.www.dto.BoardDto"%>
<%@page import="com.project.www.dao.BoardDao"%>
<%@page import="java.util.ArrayList"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="Board.css">
<style type="text/css">
div.main_box {
	width: 1110px;
	height: fit-content;
	margin: 0 auto;
	display: flex;
	justify-content: space-between;
}

div.column_box {
	width: 200px;
	display: flex;
	flex-flow: column;
	flex-wrap: nowrap;
	display: flex;
}

div.search_tag {
	width: 100%;
	text-align: center;
}

div.row_box {
	margin: 0;
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}


div.board_box {
	display: inline-block;
	width: 200px;
	height: fit-content;
	margin: 10px auto;
	border: 1px solid #8fb59d;
	border-radius: 20px;
	background: white;
}
div.board_box, div.board_box div, div.board_box img{
	transition: all 0.5s;
}
div.board_box:hover{
	width: 300px;
	z-index: 100;
	transition: all 1s;
}
div.board_box:hover div.img_box{
	width:300px;
	z-index:100;
	transition: all 1s;
}
div.board_box:hover div.board_img{
	width:300px;
	z-index: 100;
	transition: all 1s;
}

div.board_box:hover  div.id_box {
	height: 50px;
	font-size: 30px;
	transition: all 1s;
}
div.board_box:hover  img.goodHit, div.board_box:hover  img.reply, div.board_box:hover  img.profileImg {
	width: 30px;
	height: 30px;
	transition: all 1s;
}

div.img_box {
	width: 200px;
	height: fit-content;
	border-bottom: 1px solid #8fb59d;
	box-sizing: border-box;
	margin: 0;
}

img.board_img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	object-position: center;
	border-radius: 20px 20px 0 0;
	margin: 0px;
}

div.id_box {
	width: 100%;
	height: 30px;
	display: flex;
	justify-content: flex-start;
	align-items: center;
}

img.goodHit, img.reply, img.profileImg {
	width: 20px;
	height: 20px;
	border-radius: 10px;
	margin: 0 5px;
}

div.list_button_box {
	width: 1090px;
	margin: 0 auto;
}

input.list_button {
	width: 100%;
}

table {
	width: fit-content;
	font-size: 30px;
	margin-bottom: 20px;
}

td#id_tab {
	padding: 0 10px;
	width: fit-content;
	color: white;
	background: #4f8262;
	border-bottom: 2px solid black;
}

td#hashTag_tab {
	padding: 0 10px;
	width: fit-content;
	color: #4f8262;
	border-bottom: 1px solid lightgray;
}

table.id_list {
	width: fit-content;
	margin: 0 auto;
	border: 1px solid #356246;
	border-radius: 5px;
}

td.profileImg {
	width: 35px;
	height: 35px;
	display: flex;
	justify-content: center;
	align-items: center;
}

td.profileImg a {
	display: block;
	width: 30px;
	height: 30px;
}

td img {
	width: 30px;
	height: 30px;
	box-sizing: border-box;
	border: 1px solid #356246;
	border-radius: 5px;
}

td.profileId {
	width: 100px;
}
</style>
</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
	<c:if test="${hashTag!=null}">
		<div class="search_tag">
			<h1>#${hashTag}</h1>
		</div>
	</c:if>
	<div class="main_box">
		<c:forEach var="i" items="${idx}">
			<div class="column_box" id="column_box${i}">
				<c:if test="${list[i].no!=null}">
				<div class="board_box">
					<a href="BoardView.do?no=${list[i].no}">
						<div class="img_box">
							<img alt="none" src="${list[i].fileSrc}" class="board_img">
						</div>
					</a>
					<div class="id_box">
						<img class="profileImg" alt="profileImg"
							src="${list[i].profileImgSrc}"> <span class="id_text">${list[i].id}</span>
						<img class="goodHit" alt="none"
							src="${list[i].isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}">
						<span>${list[i].goodHit}</span> <img class="reply" alt="none"
							src="img/reply.png"><span>${list[i].replyCnt}</span>
					</div>
				</div>
				</c:if>
				<c:if test="${list[i+5].no!=null}">
				<div class="board_box">
					<a href="BoardView.do?no=${list[i+5].no}">
						<div class="img_box">
							<img alt="none" src="${list[i+5].fileSrc}" class="board_img">
						</div>
					</a>
					<div class="id_box">
						<img class="profileImg" alt="profileImg"
							src="${list[i+5].profileImgSrc}"> <span class="id_text">${list[i+5].id}</span>
						<img class="goodHit" alt="none"
							src="${list[i+5].isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}">
						<span>${list[i+5].goodHit}</span> <img class="reply" alt="none"
							src="img/reply.png"><span>${list[i+5].replyCnt}</span>
					</div>
				</div>
				</c:if>
				<c:if test="${list[i+10].no!=null}">
				<div class="board_box">
					<a href="BoardView.do?no=${list[i+10].no}">
						<div class="img_box">
							<img alt="none" src="${list[i+10].fileSrc}" class="board_img">
						</div>
					</a>
					<div class="id_box">
						<img class="profileImg" alt="profileImg"
							src="${list[i+10].profileImgSrc}"> <span class="id_text">${list[i+10].id}</span>
						<img class="goodHit" alt="none"
							src="${list[i+10].isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}">
						<span>${list[i+10].goodHit}</span> <img class="reply" alt="none"
							src="img/reply.png"><span>${list[i+10].replyCnt}</span>
					</div>
				</div>
			</c:if>
			</div>
		</c:forEach>
		<c:if test="${listSize%5!=0}">
	</div>
	</c:if>
	</div>
	<div class="list_button_box">
		<c:if test="${hashTag==null}">
			<input type="button" class="list_button" value="MORE"
				onclick="moreList(15,)">
		</c:if>
		<c:if test="${hashTag!=null}">
			<c:if test="${listSize==5}">
				<input type="button" class="list_button" value="MORE"
					onclick="moreList(5,'${hashTag}')">
			</c:if>
		</c:if>
	</div>
	<script src="js/jquery-3.6.0.min.js"></script>
	<script>
		let offSet = 0;
		function moreList(add, hashTag) {
			offSet = offSet + add;

			$.ajax({
						url : "BoardListMore.do",
						type : "post",
						data : {
							offSet : offSet,
							add : add,
							from : "search_list",
							hashTag : hashTag
						},
						dataType : 'json',
						success : function(list) {
							console.log(list);
							if (list.length < 1) {
								alert("더 불러올 페이지가 없습니다.");
							} else {
							for ( var i in list) {
								let idx = i%5;
								let idName ="#column_box"+idx;
								let moreListHtml = "";
								
								moreListHtml += "<div class='board_box'>";
								moreListHtml += "<a href='BoardView.do?no="
										+ list[i].no
										+ "'><div class='img_box'><img alt='none' src='"
											+list[i].fileSrc+"' class='board_img'></div></a>";
								moreListHtml += "<div class='id_box'>";
								moreListHtml += "<img class='profileImg' alt='profileImg' src='"+list[i].profileImgSrc+"'>";
								moreListHtml += "<span class='id_text'>"
										+ list[i].id + "</span>";
								if(list[i].isGoodHit==true){
								moreListHtml += "<img class='goodHit' alt='none' src='img/goodHitAfter.png'><span>"
								}else{
								moreListHtml += "<img class='goodHit' alt='none' src='img/goodHit.png'><span>"
								}
								moreListHtml += "<span class=\"goodHit_count\">"+list[i].goodHit+"</span>";
										
								moreListHtml += "<img class='reply' alt='none' src='img/reply.png'><span>"+list[i].replyCnt+"</span></div></div>";
								$(idName).append(moreListHtml);
							}
							}						
							},
						error : function() {
							alert("서버요청실패")
						}
					})
		}
	</script>
</body>
</html>