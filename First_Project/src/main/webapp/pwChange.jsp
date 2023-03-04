<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table{
	width: 400px;
	margin: 0 auto;
	border: 1px solid #8fb59d;
	border-radius: 10px;
}
td{
	padding: 2px 5px;
}
tr td:first-of-type{
	width: fit-content;
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
	<form name="registerForm" action="member_pwChange.mem" method="post" onsubmit="return checkPW()">
	<table>
		<caption> <h1>PW CHANGE</h1> </caption>
		<tr>
			<td><label for="pastPw">PASSWORD</label></td>
			<td><input type="password" id="pastPw" name="pastPw" required="required" maxlength="20"></td>
		</tr>
		<tr>
			<td><label for="pw">NEW PASSWORD</label></td>
			<td><input type="password" id="pw" name="pw" required="required" maxlength="20"></td>
		</tr>
		<tr>
			<td><label for="pw1">NEW PASSWORD CHECK</label></td>
			<td><input type="password" id="pw1" name="pw1" required="required" maxlength="20"></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="submit" value="SUBMIT">
			<a href="member_modify.mem"> <input type="button" value="BACK" ></a>
			</td>
		</tr>
	</table>
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

</script>
</body>
</html>