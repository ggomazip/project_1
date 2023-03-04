<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style type="text/css">

table{
	width: 350px;
	margin: 0 auto;
	border: 1px solid #8fb59d;
	border-radius: 10px;
}
td{
	padding: 2px 5px;
}
tr  td:first-of-type{
	width: fit-content;
}
div.img_box{
	width:350px;
	height: 350px;
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 0 auto;	
}
img#previewImg{
	border-radius: 10px;
	width: 95%;
	height: 95%;
	object-fit: cover; 
	object-position: center;
	
	cursor: url("img/inputImg.cur"), auto;
}
span{
	font-size: 13px;
}
span#idOk{
	color: blue;
}
span#idNo{
	color: red;
}
input[type='text'], input[type='password'], input[type='email'] {
	width: 100%;
	height:25px;
	border: none;
	border-radius: 5px;
	background: #dae0da;
}
</style>
</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
<form name="registerForm" action="member_modifyUpdate.mem" method="post" enctype="multipart/form-data">
	<table>
		<caption> <h1>Modify</h1> </caption>
		<tr>
		<td colspan="2">
			<label for="inputImg">
				<div class="img_box">
					<img id="previewImg" alt="NONE" src="${dto.profileImgSrc}"><br />
				</div>
			</label>
		</td>
		</tr>
		<tr>
			<td><label for="email">E-MAIL</label></td>
			<td><input type="email" id="email" name="email" readonly="readonly" maxlength="30" value="${dto.email}"></td>
		</tr>
		<tr>
			<td><label for="id">ID</label></td>
			<td><input type="text" id="id" name="id" maxlength="15" required="required" value="${dto.id}"></td>
		</tr>
		<tr>
			<td colspan="2"><span id="idOk">ID => 4~15글자의 영문, 숫자, _ 조합만 가능합니다.</span><span id="idNo"></span></td>
		</tr>
		<tr>
			<td><label for="pw">PASSWORD</label></td>
			<td><input type="password" id="pw" name="pw" required="required" maxlength="20"></td>
		</tr>
		<tr>
		<td colspan="2">
		<input type="submit" value="SUBMIT">
		<a href="member_userProfile.mem"> <input type="button" value="BACK" ></a>
		<input type="button" value="DELETE IMG" id="imgReset" onclick="delImg()">
		<a href="pwChange.jsp"> <input type="button" value="PW CHANGE" ></a>
		</td>
		</tr>
	</table>
	<input type="file" name="inputImg" id="inputImg" hidden>
	<input type="text" id="deleteImg" name="deleteImg" value="notChanged" hidden>
	<input type="text" name="idCheck" id="idCheck" required="required" min="2" hidden value="기존아이디유지">
</form>

<script type="text/javascript">
	function readImg(input) {
	    if (input.files && input.files[0]) {
	        const reader = new FileReader();
	        
	        reader.onload = (e) => {
	            const previewImg = document.getElementById('previewImg');
	            previewImg.src = e.target.result;
	        }
	        reader.readAsDataURL(input.files[0]);
	        document.getElementById('deleteImg').value="addedImg";
	    }else{
	    	previewImg.src ="img/previewImg.png"
	    	document.getElementById('previewImg').src="profile_img/profile.png";
		    document.getElementById('deleteImg').value="deleteImg";
	    }
	}
	// 이벤트 리스너
	document.getElementById('inputImg').addEventListener('change', (e) => {
	    readImg(e.target);
	})
		function delImg() {
	    document.getElementById('previewImg').src="profile_img/profile.png";
	    document.getElementById('deleteImg').value="deleteImg";
	}	
	
	
</script>
<script src="js/jquery-3.6.0.min.js"></script>
<script>
		$('#id').focusout(function(){
			let id = $('#id').val();

			$.ajax({
				url : "member_idcheck.mem",
				type:  "post",
				data: {id: id},
				dataType: 'json',
				success: function(result){
					if(result==0){
						$("#idNo").html('사용할 수 없는 아이디 입니다.');
						$("#idOk").html(' ');
						document.getElementById('idCheck').value="";
					} else if(result==2){
						$("#idOk").html(' ');
						$("#idNo").html('아이디를 입력해주세요.');
						document.getElementById('idCheck').value="";
					} else{
						$("#idOk").html('사용할 수 있는 아이디 입니다.');
						$("#idNo").html(' ');
						$("#idCheck").text('사용가능한 아이디');
						document.getElementById('idCheck').value="사용가능한 아이디";
					}
				},
				error:function(){
					alert("서버요청실패");
				}
			})
		})
</script>
</body>
</html>