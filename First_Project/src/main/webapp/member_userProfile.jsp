<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style type="text/css">
*{
	margin: 0;
	padding: 0;
	text-decoration: none;
	color: black;
}
div.main_box {
	width: 1090px;
	margin: 0 auto;
}

div.profile_box {
	width: 100%;
	height: 200px;
	display: flex;
}

div.profileImg_box {
	width: 300px;
	height: 100%;
	display: flex;
	justify-content: center;
}

img.head_profileImg {
	width: 200px;
	height: 200px;
	border-radius: 10px;
}

div.row_box {
	width: fit-content;
	display: flex;
	align-items: center;
	justify-content: space-around;
}

div.board_box {
	width: 250px;
	height: 280px;
	margin: 10px 10px;
	border: 1px solid #8fb59d;
	border-radius: 20px;
}

div.img_box {
	width: 250px;
	height: 250px;
}

img.board_img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	object-position: center;
	border-radius: 20px 20px 0 0;
}
div.id_box{
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

div.cummunicationInfo_box ul {
	padding: 0;
	margin: 0;
	list-style: none;
}

div.cummunicationInfo_box li {
	margin: 10px 0;
	width: 150px;
	text-align: left;
	float: left;
}


div.id_text {
	font-size: 25px;
	height: 30px;
	line-height: 30px;
	margin-right: 20px;
}

div.id_box a {
	margin-right: 20px;
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
div.list_button_box {
	width: 1070px;
	margin: 0 auto;
}

input.list_button {
	width: 100%;
}
</style>
</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
		<div class="main_box">
			<div class="profile_box">
				<div class="profileImg_box">
					<img class="head_profileImg" alt="profileImg"
						src="${view.profileImgSrc}">
				</div>
				<div class="profileInfo_box">
					<div class="id_box">
						<div class="id_text">${view.id}</div>
						<c:if test="${view.id==id}">
							<a href="member_modify.mem"><input type="button"
								value="MODIFY"></a>
							<a href="member_logout.mem"><input type="button"
								value="LOGOUT"></a>
						</c:if>
					</div>
					<div class="cummunicationInfo_box">
						<ul>
							<li>POSTING '${posting}'</li>
							<li>FOLLOWER '<a class="followerCnt" href="member_followerList.mem?listOffer=follower&userId=${view.id}">${follower}</a>'</li>
							<li>FOLLOW '<a class="followCnt" href="member_followerList.mem?listOffer=follow&userId=${view.id}">${follow}</a>'</li>
						</ul>
					</div>
					<div>
						<c:if test="${view.id!=id}">
							<form class="follow_unfollow">
								<input class="${view.id}" type="button" value="${isFollow==true? 'UNFOLLOW' : 'FOLLOW'}" onclick="return followAndUnFollow('${view.id}')">
							</form>
						</c:if>
					</div>
				</div>
			</div>
			<c:forEach var="dto" items="${list}" varStatus="status">
				<c:if test="${(status.index)%4==0}">
					<div class="row_box">
				</c:if>
				<div class="board_box">
					<a href="BoardView.do?no=${dto.no}">
						<div class="img_box">
							<img alt="none" src="${dto.fileSrc}" class="board_img">
						</div>
					</a>
					 <div class="id_box">
						<img class="profileImg" alt="profileImg"
							src="${dto.profileImgSrc}"> <span class="id_text">${dto.id}</span>
						<img class="goodHit" alt="none" src="${dto.isGoodHit ? 'img/goodHitAfter.png' : 'img/goodHit.png'}"><span>${dto.goodHit}</span>
						<img class="reply" alt="none" src="img/reply.png"><span>${dto.replyCnt}</span>
					</div>
					
					<c:if test="${status.count%4==0}"></div></c:if>
					</div>
			</c:forEach>
			<c:if test="${listSize%4!=0}">
				</div>
			</c:if>
	</div>
		<c:if test="${listSize==12}">
			<div class="list_button_box">
			<input type="button" class="list_button" value="MORE"
				onclick="moreList(12,'${view.id}')">
		</c:if>
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
				getFollowerCnt(id);
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
				getFollowerCnt(id);
			},
			error: function(){
				alert("서버요청실패")
			}
		})
	}
}

function getFollowerCnt(id) {
	$.ajax({
		url: "MemberGetFollowerCnt.mem",
		type:  "post",
		cache: false,
		data: {userId: id},
		dataType: 'json',
		success: function(followerCnt){
			//console.log(followerCnt);
			$('.followerCnt').html(followerCnt);
		},
		error: function(){
			alert("서버요청실패")
		}
	})
}

let offSet = 0;
let result = 0;
function moreList(add,userId) {
	offSet = offSet + add;

	$.ajax({
				url : "BoardListMore.do",
				type : "post",
				data : {
					offSet : offSet,
					add : add,
					userId : userId,
					from : "userProfile"
				},
				dataType : 'json',
				success : function(list) {
					console.log(list);

					let moreListHtml = "";
					for ( var i in list) {
						if (i % 4 == 0) {
							moreListHtml += "<div class='row_box'>";
						}
						result++;
						moreListHtml += "<div class='board_box'>";
						moreListHtml += "<a href='BoardView.do?no="+ list[i].no+"'><div class='img_box'>"+
								"<img alt='none' src='"+list[i].fileSrc+"' class='board_img'></div></a>";
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
						if (result%4 == 0) {
							moreListHtml += "</div>";
						}
					}
					moreListHtml+= "</div>"
					if (result%4 != 0) {
						moreListHtml += "</div>";
					}
					if (list.length >= 1) {
						console.log(moreListHtml)
						$('.main_box').append(moreListHtml);
						//alert("더보기 성공");
					} else {
						alert("더 불러올 페이지가 없습니다.");
					}
				},
				error : function() {
					alert("서버요청실패")
				}
			})
}

$(window).scroll(function() {
	   if($(window).scrollTop() + $(window).height() == $(document).height()) {// ==이 맞는데 자꾸 동작 안될때가 있어서 >= 사용.
	       //alert("바닥");
	   		let userId = ${view.id};
	       moreList(12,userId);
	   }
	})
</script>
</body>
</html>