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
table{
	width: 380px;
	margin: 0 auto;
}
span{
	font-size: 13px;
}
span#idOk, span#emailOk{
	color: blue;
}
span#idNo, span#emailNo{
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
<form name="registerForm" action="member_register.mem" method="post" enctype="multipart/form-data" onsubmit="return checkPW()">
	<table>
		<caption> <h1>Register</h1> </caption>
		<tr>
		<td colspan="2">
			<label for="inputImg">
				<div class="img_box">
					<img id="previewImg" alt="NONE" src="profile_img/profile.png"><br />
				</div>
			</label>
		</td>
		</tr>
		<tr>
			<td><label for="email">E-MAIL</label></td>
			<td><input type="email" id="email" name="email" required="required" maxlength="30" placeholder="ex@ex.com"></td>
		</tr>
		<tr>
			<td colspan="2"><span id="emailOk">EMAIL => 7~30글자의 영문, 숫자, _ 조합만 가능합니다.</span><span id="emailNo"></span></td>
		</tr>
		<tr>
			<td><label for="id">ID</label></td>
			<td><input type="text" id="id" name="id" maxlength="15" required="required" placeholder="Guest_1"></td>
		</tr>
		<tr>
			<td colspan="2"><span id="idOk">ID => 4~15글자의 영문, 숫자, _ 조합만 가능합니다.</span><span id="idNo"></span></td>
		</tr>
		<tr>
			<td><label for="pw">PASSWORD</label></td>
			<td><input type="password" id="pw" name="pw" required="required" maxlength="20"></td>
		</tr>
		<tr>
			<td><label for="pw1">PASSWORD CHECK</label></td>
			<td><input type="password" id="pw1" name="pw1" required="required" maxlength="20"></td>
		</tr>
		<tr>
		<td colspan="2">
		<input type="submit" value="SUBMIT">
		<%-- <input type="reset" value="RESET">--%>
		<a href="BoardList.do"> <input type="button" value="HOME" ></a>
		</td>
		</tr>
	</table>
	<input type="file" name="inputImg" id="inputImg" hidden>
	<input type="text" name="emailCheck" id="emailCheck" required="required" min="7" hidden>
	<input type="text" name="idCheck" id="idCheck" required="required" min="7" hidden>
</form>

<script type="text/javascript">
	function checkPW() {
		let pw = document.forms["registerForm"]["pw"].value;
		let pw1 = document.forms["registerForm"]["pw1"].value;
		let pwArr = pw.split('');
		let charFlag = true;
		let spCharFlag = true;
		let numFlag = true;
		for (var i = 0; i < pwArr.length; i++) {
			
			if((pwArr[i]>="A" && pwArr[i]<="Z") || (pwArr[i]>="a" && pwArr[i]<="z")){
				charFlag=false;
				break;
			}
		}
		for (var i = 0; i < pwArr.length; i++) {
			console.log(pwArr[i]);
			if((pwArr[i]>="\!" && pwArr[i]<='\/') || (pwArr[i]>="\:" && pwArr[i]<="\@")
					|| (pwArr[i]>="\[" && pwArr[i]<="\`") ||(pwArr[i]>="\{" && pwArr[i]<="\~")){
				spCharFlag=false;
				break;
			}
		}
		for (var i = 0; i < pwArr.length; i++) {
			console.log(pwArr[i]);
			let num = parseInt(pwArr[i]);
			if(pwArr[i]>="0" && pwArr[i] <="1"){
				numFlag=false;
				break;
			}
		}
		console.log(charFlag+","+spCharFlag+","+numFlag);
		console.log(pw.length);
		if(pw===pw1){
			if ((pw.length<6 || pw.length>30) || spCharFlag || numFlag || charFlag) {
				alert("비밀번호는 7~20글자 영문+숫자+특수문자 조합으로 입력해주세요");
				return false;
			}else{
				//alert("성공");
				return true;
			}
		}
		else{
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
	}
	
	function readImg(input) {
	    if (input.files && input.files[0]) {
	        const reader = new FileReader();
	        
	        reader.onload = (e) => {
	            const previewImg = document.getElementById('previewImg');
	            previewImg.src = e.target.result;
	        }
	        reader.readAsDataURL(input.files[0]);
	    }else{
	    	previewImg.src ="img/previewImg.png"
	    }
	}
	// 이벤트 리스너
	document.getElementById('inputImg').addEventListener('change', (e) => {
	    readImg(e.target);
	})
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
<script>
		$('#email').focusout(function(){
			let email = $('#email').val();

			$.ajax({
				url : "member_emailcheck.mem",
				type:  "post",
				data: {email: email},
				dataType: 'json',
				success: function(result){
					if(result==0){
						$("#emailNo").html('사용할 수 없는 이메일 입니다.');
						$("#emailOk").html(' ');
						document.getElementById('emailCheck').value="";
					} else if(result==2){
						$("#emailOk").html(' ');
						$("#emailNo").html('이메일을 입력해주세요.');
						document.getElementById('emailCheck').value="";
					} else{
						$("#emailOk").html('사용할 수 있는 이메일 입니다.');
						$("#emailNo").html(' ');
						document.getElementById('emailCheck').value="사용가능한 이메일";
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