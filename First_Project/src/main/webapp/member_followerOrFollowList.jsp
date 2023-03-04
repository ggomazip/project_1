<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	
	table{
		width: fit-content;
		margin: 0 auto;
		border: 1px solid #356246;
		border-radius: 5px;
	}
	td.profileImg{
		width: 35px;
		height: 35px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	td.profileImg a{
		display: block;
		width: 30px;
		height: 30px;
	}
	td img{
		width: 30px;
		height: 30px;
		box-sizing: border-box;
		border: 1px solid #356246;
		border-radius: 5px;
	}
	td.profileId{
		width: 100px;
	}
	td.followOrUnfollowBtn{
		width: fit-content;
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
	
</style>
</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
	<table>
		<c:choose>
			<c:when test="${follow!=null}"><caption><h1>FOLLOW</h1></caption></c:when>
			<c:otherwise><caption><caption><h1>FOLLOWER</h1></caption></c:otherwise>
		</c:choose>
		<c:forEach var="dto" items="${list}">
			<tr>
				<td class="profileImg"><a href="member_userProfile.mem?userId=${dto.id}"><img alt="profileImg" src="${dto.profileImgSrc}"></a></td>
				<td class="profileId"><a href="member_userProfile.mem?userId=${dto.id}">${dto.id}</a></td>
				<c:choose>
					<c:when test="${dto.isFollow}">
						<td class="followOrUnfollowBtn"><input class="${dto.id}" type="button" value="UNFOLLOW" onclick="unfollow('${dto.id}')"></td>
					</c:when>
					<c:when test="${dto.isFollow==false}">
						<td class="followOrUnfollowBtn"><input class="${dto.id}" type="button" value="FOLLOW" onclick="follow('${dto.id}')"></td>
					</c:when>
					<c:otherwise><td class="followOrUnfollowBtn"></td></c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
<script src="js/jquery-3.6.0.min.js"></script>
<script>
	function unfollow(id) {
		let className = "."+id;
		let funcName = "follow('"+id+"')";
			$.ajax({
				url: "member_unfollow.mem",
				type:  "post",
				data: {user_id : id},
				dataType: 'json',
				success: function(result){
					//alert("언팔로우 성공")
					$(className).attr('value','FOLLOW');
					$(className).attr('onclick',funcName);
					//location.reload();
				},
				error: function(){
					alert("서버요청실패")
				}
			})
	}
</script>
<script>
	function follow(id) {
		let className = "."+id;
		let funcName = "unfollow('"+id+"')";
			$.ajax({
				url: "member_follow.mem",
				type:  "post",
				data: {user_id : id},
				dataType: 'json',
				success: function(result){
					//alert("팔로우 성공")
					$(className).attr('value','UNFOLLOW');
					$(className).attr('onclick',funcName);
					//location.reload();
				},
				error: function(){
					alert("서버요청실패")
				}
			})
	}
</script>
</body>
</html>