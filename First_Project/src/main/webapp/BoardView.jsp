<%@page import="com.project.www.dto.BoardDto"%>
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

div.view_box {
	border: 1px solid #8fb59d;
	border-radius: 10px;
	width: 500px;
	height: fit-content;
	margin: 0 auto;
	margin-bottom: 5px;
}

div.img_box {
	width: 500px;
	height: fit-content;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-bottom: 10px;
}

img#viewImage {
	width: 95%;
	object-fit: contain;
	object-position: center;
	border-radius: 10px;	
}
img.smallProfileImg {
	width: 30px;
	height: 30px;
	border-radius: 5px;
	border: 1px solid #8fb59d;
	box-sizing: border-box;
	margin: 5px;
}
div.contents {
	margin: 0 auto;
	margin-top: 5px;
	padding: 5px;
	width: 95%;
	height: fit-content;
	resize: none;
	border: 1px solid #8fb59d;
	border-radius: 10px;
	white-space: pre-wrap;
	overflow: auto;
	
}

div.buttons {
	width: 500px;
	margin: 0 auto;
	position: relative;
}
div.id_box{
	width: 50%;
	height: 100%;
	display: flex;
	align-items: center;
}
div.profile_box{
	width: 100%;
	height: 60px;
	display: flex;
	align-items: center;
}
div.id_box a{
	display: block;
	width: fit-content;
	height: fit-content;
	display: flex;
}
div.id_text{
	font-size: 20px;
	box-sizing: border-box;
	height: 20px;
	line-height: 20px;
}
div.followOrUnfollowBtn_box{
	width: 45%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: end;
}
div.hit_box{
	width: 100%;
	height: 20px;
	padding: 0 10px;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}
img.profileImg{
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: 1px solid #356246;
	box-sizing: border-box;
	margin: 5px;
}
div.hit_box img{
	width: 20px;
	height: 20px;
	margin-right: 2px;
}
div.writeReply_box{
	width: 480px;
	margin: 0 auto;
	display: flex;
	justify-content: flex-start;
	align-items: center;
}
input.reply {
	width: 430px;
	height:30px;
	border: none;
	border-radius: 5px;
	background: #dae0da;
	margin: 3px;
	margin-left: 0;
}

div.see_reply{
	width: 480px;
	margin: 0 auto;
	margin-bottom: 10px;
}
div.reply_row{
	width: 100%
	height: 100%;
	display: flex;
	align-items: center;
}
div.reply_row a{
	display: block;
	width: fit-content;
	height: fit-content;
	display: flex;
}
a{
	text-decoration: none;
	color: black;
}
input[type='button']{
		background: #4f8262;
		color: white;
		border-radius: 5px;
		border: none;
		height: 25px;
		padding: 5px;
		cursor: pointer;
}
input#delete {
	display: inline-block;
	position: absolute;
	right: 0;
}
div.hit_box > img:first-of-type {
	cursor: pointer;
}
</style>
</head>
<body>
<%@ include file ='HeaderLayout.jsp' %>
	<div class="view_box" id="test">
		<div class="profile_box">
				<div class="id_box">
				<a href="member_userProfile.mem?userId=${view.id}"> <img
					class="profileImg" alt="profileImg" src="${view.profileImgSrc}"></a>
					<a href="member_userProfile.mem?userId=${view.id}"><div class="id_text">${view.id}</div></a>
				</div>
				<div class="followOrUnfollowBtn_box">
				<c:if test="${view.id!=id}">
						<input class="${view.id}" type="button" value="${view.isFollow ?'UNFOLLOW' : 'FOLLOW' }" onclick="followAndUnFollow('${view.id}')">
				</c:if>
			</div>
			</div>
		<div class="img_box">
			<img id="viewImage" alt="NONE" src="${view.fileSrc}"><br />
		</div>
		<div class="hit_box">
				<img class="${view.no}" alt="none"  src="${view.isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}" onclick="plusOrMinusGoodHit('${view.no}')">
				<span class="goodHit_count${view.no}">${view.goodHit}</span>
				<img class="reply" alt="none" src="img/reply.png"><span class="replyCnt${view.no}">${view.replyCnt}</span>
				<input class="input${view.no}" value="${view.isGoodHit ? 'yes' : 'no'}"type="hidden" >
				<input class="goodCnt${view.no}" value="${view.goodHit}" type="hidden" >
			</div>
		<div class="contents" name="contents" id="contents"
			readonly="readonly">${view.contents}</div>
			<div class="writeReply_box"><input type="text" class="reply" id="reply${view.no}" placeholder=" to comment..">
				<input type="button" onclick="inputReply(${view.no})" value="WRITE"></div>
				<div class="see_reply" id="seeReply${view.no}">
				<c:if test="${view.replyCnt>0}">
				<a onclick="seeReply(${view.no})">See all comments</a>
				</c:if>
				<input class="see_default${view.no}" type="hidden" value="default"></div>
	</div>
	<c:choose>
		<c:when test="${view.id.equals(id)}">
			<div class="buttons">
				<a href="BoardModify.do?no=${view.no}"><input type="button" value="Modify"></a>
				<input id="delete" type="button" value="Delete" onclick="popup(${view.no})">
			</div>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
<script src="js/jquery-3.6.0.min.js"></script>
<script>
	function followAndUnFollow(id) {
		let className = "."+id;
		let value = $(className).val();
		console.log(value);
		console.log(id);
		if(value=="UNFOLLOW"){
			$.ajax({
				url: "member_unfollow.mem",
				type:  "post",
				cache: false,
				data: {user_id: id},
				dataType: 'json',
				success: function(result){
					//alert("언팔로우 성공")
					$(className).attr('value','FOLLOW');
				},
				error: function(){
					alert("서버요청실패")
				}
			})
		}else{
			$.ajax({
				url: "member_follow.mem",
				type:  "post",
				cache: false,
				data: {user_id: id},
				dataType: 'json',
				success: function(result){
					//alert("팔로우 성공")
					$(className).attr('value','UNFOLLOW');
				},
				error: function(){
					alert("서버요청실패")
				}
			})
		}
	}
</script>
<script>
function plusOrMinusGoodHit(no) {
	let className = ".input"+no;
	let btnClassName = "."+no
	//let goodHitClassName = ".goodHit_count"+no;
	//let goodHitCnt = $(goodHitClassName).innerHtml;
	//let cntClassName = ".goodCnt"+no;
	//let goodCnt = $(cntClassName).val();
	let value = $(className).val();
	console.log(value);
	console.log(className);
	if(value=="yes"){
		$.ajax({
			url: "BoardMinusGoodHit.do",
			type:  "post",
			cache: false,
			data: {no: no},
			dataType: 'json',
			success: function(result){
				//alert("좋아요 취소")
				$(className).attr('value','no');
				$(btnClassName).attr('src','img/goodHit.png');
				//goodHitCnt--;
				//console.log(goodHitCnt);
				//$(goodHitClassName).html(goodHitCnt);
				//$(cntClassName).attr('value',goodHitCnt);
				getGoodHitCnt(no);
				
			},
			error: function(){
				alert("서버요청실패")
			}
		})
	}else{
		$.ajax({
			url: "BoardPlusGoodHit.do",
			type:  "post",
			cache: false,
			data: {no: no},
			dataType: 'json',
			success: function(result){
				//alert("좋아요 추가")
				$(className).attr('value','yes');
				$(btnClassName).attr('src','img/goodHitAfter.png');
				//goodHitCnt++;
				//console.log(goodHitCnt);
				//$(goodHitClassName).html(goodHitCnt);
				//$(cntClassName).attr('value',goodHitCnt);
				getGoodHitCnt(no);
			},
			error: function(){
				alert("서버요청실패")
			}
		})
	}
}

function getGoodHitCnt(no) {
	let goodHitCntClassName = ".goodHit_count"+no;
	$.ajax({
		url: "BoardGetGoodHitCnt.do",
		type:  "post",
		cache: false,
		data: {no: no},
		dataType: 'json',
		success: function(goodHitCnt){
			console.log(goodHitCnt);
			$(goodHitCntClassName).html(goodHitCnt);
		},
		error: function(){
			alert("서버요청실패")
		}
	})
}
</script>
<script>
function inputReply(no) {
	let replyCntClassName = ".replyCnt"+no;
	let seeReplyIdName = "#seeReply"+no;
	let idName = "#reply"+no;
	let className = ".see_default"+no;
	let reply = $(idName).val();
	let value = $(className).val();
	let moreHtml="<a onclick=\"seeReply("+no+")\">See all comments</a>";
	
	console.log(reply);
	console.log(value);
		$.ajax({
			url: "BoardWriteReply.do",
			type:  "post",
			cache: false,
			data: {reply: reply, no: no},
			dataType: 'json',
			success: function(result){
				//alert("댓글 입력 성공")
				$(idName).val('');
				if ($(replyCntClassName).text()==0) {
					$(seeReplyIdName).append(moreHtml);
				}
				getReplyCnt(no);
				if (value!="default") {
					seeReply(no);
				}
			},
			error: function(){
				alert("서버요청실패")
			}
		})
	}
	
	function deleteReply(no,num) {
	let replyCntClassName = ".replyCnt"+no;
	let seeReplyIdName = "#seeReply"+no;
	let resetHmtl="<input class=\"see_default"+no+"\" type=\"hidden\" value=\"default\">";
			$.ajax({
				url: "BoardDeleteReply.do",
				type:  "post",
				cache: false,
				data: {num: num},
				dataType: 'json',
				success: function(result){
					//alert("댓글 삭제 성공")
					if ($(replyCntClassName).text()==1) {
						$(seeReplyIdName).html(resetHmtl);
					}else{
						seeReply(no);
					}
					getReplyCnt(no);
				},
				error: function(){
					alert("서버요청실패")
				}
			})
		}
	
	function getReplyCnt(no) {
		let replyCntClassName = ".replyCnt"+no;
		$.ajax({
			url: "BoardGetReplyCnt.do",
			type:  "post",
			cache: false,
			data: {no: no},
			dataType: 'json',
			success: function(replyCnt){
				$(replyCntClassName).html(replyCnt);
			},
			error: function(){
				alert("서버요청실패")
			}
		})
	}

	
	function seeReply(no) {
		let idName ="#seeReply"+no;
			$.ajax({
				url: "BoardReplyView.do",
				type:  "post",
				data: {no: no},
				dataType: 'json',
				success: function(list){
					console.log(list);
					
					let moreListHtml = "";
					for (var i in list) {
						
						moreListHtml +="<div class='reply_box'>";
						moreListHtml += "<div class='reply_row'>";
						moreListHtml += "<a href='member_userProfile.mem?userId="+list[i].id+"'>";
						moreListHtml += "<img class='smallProfileImg' alt='profileImg' src='"+list[i].profileImgSrc+"'></a>";
						moreListHtml += "<a href='member_userProfile.mem?userId="+list[i].id+"'><div class='id_text'>"+list[i].id+"</div> </a>"+list[i].reply;
						if (list[i].id=='${id}') {
							moreListHtml +="<input type='button' value='X' onclick=\"deleteReply('"+list[i].no+"','"+list[i].num+"')\">";
						}
							moreListHtml +="</div>"
								
					}
					if (list.length >= 1) {
					$(idName).html(moreListHtml);
						//alert("댓글 보기 성공");
					}else {
					$(idName).html("");
						//alert("댓글이 없습니다.");
					}
				},
				error: function(){
					alert("서버요청실패")
				}
			})
	}
	
	function popup(no){ // 여기까지 했음.
		console.log("popup 로그"+no)
		url = 'BoardDelete.jsp?no='+no;
		cw=300;
		ch=170;
		//sw=window.screen.width;
		//sh=window.screen.height;
		//px=(sw-cw)/2;
		//py=(sh-ch)/2;
		scX = window.screenLeft;
		scY = window.screenTop;
		
		mtWidth = window.outerWidth;
		mtHeight = window.outerHeight;
		popX = scX +(mtWidth - cw)/2 -50;
		popY = scY +(mtHeight - ch)/2 - 50;
		popWindow = window.open( url, 'DeleteBoard', 'left='+popX+',top='+popY+',width='+cw+',height='+ch+'');
	}
</script>
</body>
</html>