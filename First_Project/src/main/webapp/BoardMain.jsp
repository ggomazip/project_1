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

<style type="text/css">
div.main_box{
	margin-bottom: 10px;
	height: fit-content;
}

div.view_box {
	border: 1px solid #8fb59d;
	border-radius: 10px;
	width: 500px;
	height: fit-content;
	margin: 15px auto;
}

div.img_box {
	width: 500px;
	height: fit-content;
	display: flex;
	justify-content: center;
	align-items: center;
	margin-bottom: 10px;
}

img.viewImage {
	width: 95%;
	height: 95%;
	object-fit: contain;
	object-position: center;
	border-radius: 10px;
}

div.contents_box {
	margin: 0 auto;
	margin-top: 5px;
	width: 480px;
	height: fit-content;
	resize: none;
	border: 1px solid #8fb59d;
	border-radius: 10px;
	white-space: pre-wrap;
	overflow: hidden;
	display: flex;
	align-items: center;
}
div.contents{
	padding: 5px;
	width: 85%;
	height: fit-content;
	display: inline-block;
	
}
div.more_btn{
	padding: 5px;
	width: fit-content;
	height: 25px;
	display: none;
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
div.list_button_box {
	width: 500px;
	margin: 0 auto;
}
input.list_button{
	width: 100%;
}

div.profile_box{
	width: 100%;
	height: 60px;
	display: flex;
	align-items: center;
}
div.id_box{
	width: 50%;
	height: 100%;
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
div.hit_box {
	width: 100%;
	height: 20px;
	padding: 0 10px;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}

img.profileImg {
	width: 50px;
	height: 50px;
	border-radius: 10px;
	border: 1px solid #8fb59d;
	box-sizing: border-box;
	margin: 5px;
}
img.smallProfileImg {
	width: 30px;
	height: 30px;
	border-radius: 5px;
	border: 1px solid #8fb59d;
	box-sizing: border-box;
	margin: 5px;
}

div.hit_box img {
	width: 20px;
	height: 20px;
}

button.follow {
	position: absolute;
	right: 10px;
	top: 20px;
}

a {
	text-decoration: none;
	color: black;
	cursor: pointer;
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
div.hit_box > img:first-of-type {
	cursor: pointer;
}
</style>

</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
	<div class="main_box">
	<c:forEach var="view" items="${list}" varStatus="status">
		<div class="view_box">
			<div class="profile_box">
				<div class="id_box">
				<a href="member_userProfile.mem?userId=${view.id}"> 
					<img class="profileImg" alt="profileImg" src="${view.profileImgSrc}"></a>
					<a href="member_userProfile.mem?userId=${view.id}"><div class="id_text">${view.id}</div></a>
				</div>
				<div class="followOrUnfollowBtn_box">
				<c:if test="${view.id!=id}">
						<input class="${view.id}" type="button" value="${view.isFollow ? 'UNFOLLOW' : 'FOLLOW' }" onclick="followAndUnFollow('${view.id}')">
				</c:if>
			</div>
			</div>
			<div class="img_box">
				<img class="viewImage" alt="NONE" src="${view.fileSrc}"><br />
			</div>
			<div class="hit_box">
				<img class="${view.no}" alt="none"  src="${view.isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}" onclick="plusOrMinusGoodHit('${view.no}')">
				<span class="goodHit_count${view.no}">${view.goodHit}</span>
				<img class="reply" alt="none" src="img/reply.png"><span class="replyCnt${view.no}">${view.replyCnt}</span>
				<input class="input${view.no}" value="${view.isGoodHit ? 'yes' : 'no'}"type="hidden" >
				<input class="goodCnt${view.no}" value="${view.goodHit}" type="hidden" >
			</div>
				<div class="contents_box" id="contents_box${status.index}">
				<div class="contents" id="contents${status.index}">${view.contents}</div>
				<div class="more_btn" id="more_btn${status.index}">..<a onclick="moreContents('${status.index}')">더보기</a></div>
				</div>
				<div class="writeReply_box"><input type="text" class="reply" id="reply${view.no}" placeholder=" to comment..">
				<input type="button" onclick="inputReply(${view.no})" value="WRITE"></div>
				<div class="see_reply" id="seeReply${view.no}">
				<c:if test="${view.replyCnt>0}">
					<a onclick="seeReply(${view.no})">See all comments</a>
				</c:if>
				<input class="see_default${view.no}" type="hidden" value="default"></div>
				
			</div>
	</c:forEach>
	</div>
	  <%-- <div class="list_button_box">
		<input type="button" class="list_button" value="MORE" onclick="moreList(5)">
	</div>--%>
<script src="js/jquery-3.6.0.min.js"></script>
<script>
	let offSet= 0;
	let idx = 0;
	let add =5;
	function moreList() {
		if(add==5){
		offSet = offSet + add;
		idx = offSet;
		let start = idx;
		console.log("오프셋"+offSet);
			$.ajax({
				url: "BoardListMore.do",
				type:  "post",
				data: {offSet: offSet, add: add, from: "main_feed"},
				dataType: 'json',
				success: function(list){
					console.log(list);
					
					let moreListHtml = "";
					for (var i in list) {
						
						moreListHtml +="<div class='view_box'>";
						moreListHtml +="<div class='profile_box'>";
						moreListHtml += "<div class='id_box'>";
						moreListHtml += "<a href='member_userProfile.mem?userId="+list[i].id+"'>";
						moreListHtml += "<img class='profileImg' alt='profileImg' src='"+list[i].profileImgSrc+"'></a>";
						moreListHtml += "<a href='member_userProfile.mem?userId="+list[i].id+"'><div class='id_text'>"+list[i].id+"</div></a></div>";
						moreListHtml += "<div class='followOrUnfollowBtn_box'>";
						if (list[i].id!="${id}"){
						moreListHtml += "<input class=\""+list[i].id+"\" type=\"button\" value=";
							if (list[i].isFollow==true) {
								moreListHtml +="\"UNFOLLOW\" onclick=\"return followAndUnFollow('"+list[i].id+"')\">";
							}else{
								moreListHtml +="\"FOLLOW\" onclick=\"return followAndUnFollow('"+list[i].id+"')\">";
							}
						}
						moreListHtml += "</div></div>";
						moreListHtml += "<div class='img_box'><img class='viewImage' alt='NONE' src='"+list[i].fileSrc+"'><br/></div>";
						moreListHtml += "<div class='hit_box'>";
						if (list[i].isGoodHit==true) {
							moreListHtml += "<img class=\""+list[i].no+"\" alt=\"none\"  src=\"img/goodHitAfter.png\" onclick=\"plusOrMinusGoodHit('"+list[i].no+"')\">";
							moreListHtml += "<span class=\"goodHit_count"+list[i].no+"\">"+list[i].goodHit+"</span>";
							moreListHtml += "<img class='reply' alt='none' src='img/reply.png'><span class=\"replyCnt"+list[i].no+"\">"+list[i].replyCnt+"</span>";
							moreListHtml += "<input class=\"input"+list[i].no+"\" value=\"yes\" type=\"hidden\" >"
						}else{
							moreListHtml += "<img class=\""+list[i].no+"\" alt=\"none\"  src=\"img/goodHit.png\" onclick=\"plusOrMinusGoodHit('"+list[i].no+"')\">";
							moreListHtml += "<span class=\"goodHit_count"+list[i].no+"\">"+list[i].goodHit+"</span>";
							moreListHtml += "<img class='reply' alt='none' src='img/reply.png'><span class=\"replyCnt"+list[i].no+"\">"+list[i].replyCnt+"</span>";
							moreListHtml += "<input class=\"input"+list[i].no+"\" value=\"no\" type=\"hidden\" >"
						}
						moreListHtml += "<input class=\"goodCnt"+list[i].no+"\" value=\""+list[i].goodHit+"\" type=\"hidden\" ></div>"
						moreListHtml += "<div class=\"contents_box\" id=\"contents_box"+idx+"\">";
						moreListHtml +=	"<div class=\"contents\" id=\"contents"+idx+"\">"+list[i].contents+"</div>";
						moreListHtml +=	"<div class=\"more_btn\" id=\"more_btn"+idx+"\">..<a onclick=\"moreContents('"+idx+"')\">더보기</a></div>";
						moreListHtml +="</div>";
						moreListHtml +="<div class=\"writeReply_box\"><input type=\"text\" class=\"reply\" id=\"reply"+list[i].no+"\" placeholder=\" to comment..\">";
						moreListHtml +="<input type=\"button\" onclick=\"inputReply("+list[i].no+")\" value=\"WRITE\"></div>";
						moreListHtml +="<div class=\"see_reply\" id=\"seeReply"+list[i].no+"\">";
						if (list[i].replyCnt>0) {
						moreListHtml +="<a onclick=\"seeReply("+list[i].no+")\">See all comments</a>";
						}
						moreListHtml +="<input class=\"see_default"+list[i].no+"\" type=\"hidden\" value=\"default\"></div>";
						moreListHtml +="</div>";
						idx++;
					}
					if (list.length >= 1) {
						$('.main_box').append(moreListHtml);
						for (var i = start; i <idx ; i++) {
							var contentsBox = "#contents_box"+i;
							var contents = "#contents"+i;
							var more_btn = "#more_btn"+i;	
							var box = document.querySelector(contentsBox);
							var con = document.querySelector(contents);
							var more = document.querySelector(more_btn);
							var innerHeight = $(contents).outerHeight();
							var outerHeight = $(contentsBox).outerHeight();
							//box.style.height=innerHeight+"px";	
							
							if ( outerHeight > 50 ){
						 	 	more.style.display = 'inline';
						 	 	//box.style.height='35px';
						 	 	$(contentsBox).css('white-space','nowrap');
						 	 	con.style.height='25px';
						 	 	con.style.overflow='hidden';
						 	 	$(contents).css('text-overflow','ellipsis');
							}
						}
						//alert("더보기 성공");
					}else {
						add=0;
						console.log("더보기 비활성화");
						//alert("더 불러올 페이지가 없습니다.");
					}
				},
				error: function(){
					alert("서버요청실패")
				}
			})
		}
	}
	$(window).scroll(function() {
		   if($(window).scrollTop() + $(window).height() >= $(document).height()) {// ==이 맞는데 자꾸 동작 안될때가 있어서 >= 사용.
		       //alert("바닥");
		       moreList(5);
		   }
		})

</script>
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
</script>
<script>
for (var i = 0; i < 5; i++) {
	var contentsBox = "#contents_box"+i;
	var contents = "#contents"+i;
	var more_btn = "#more_btn"+i;	
	var box = document.querySelector(contentsBox);
	var con = document.querySelector(contents);
	var more = document.querySelector(more_btn);
	var innerHeight = $(contents).outerHeight();
	var outerHeight = $(contentsBox).outerHeight();
	//box.style.height=innerHeight+"px";	
	
	if ( outerHeight > 50 ){
 	 	more.style.display = 'inline';
 	 	//box.style.height='35px';
 	 	$(contentsBox).css('white-space','nowrap');
 	 	con.style.height='25px';
 	 	con.style.overflow='hidden';
 	 	$(contents).css('text-overflow','ellipsis');
	}
}
</script>
<script type="text/javascript">
	function moreContents(i) {
		var contentsBox = "#contents_box"+i;
		var contents = "#contents"+i;
		var more_btn = "#more_btn"+i;	
		var box = document.querySelector(contentsBox);
		var con = document.querySelector(contents);
		var more = document.querySelector(more_btn);
		more.style.display='none';
		box.style.height='fit-content';
		con.style.height='fit-content';
		con.style.width='100%';
		$(contentsBox).css('white-space','pre-wrap');
	}
</script>
</body>
</html>