<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table {
	width: 250px;
	margin: 0 auto;
}
</style>
</head>
<body>
<%@ include file ='HeaderLayout.jsp' %>
	<form action="member_login.mem" method="post">
		<table class="login">
			<caption>
				<h2>Login</h2>
			</caption>
			<tr>
				<td><label for="id"> ID : </label></td>
				<td><input type="text" id="id" name="id" required="required"
					value="${cookie.remId.value!=null ? (cookie.remId.value) : ''}">
				</td>
			</tr>
			<tr>
				<td><label for="pw"> PW : </label></td>
				<td><input type="password" id="pw" name="pw"
					required="required"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="checkbox" name="remId" id="remId"
					value="checked"
					${cookie.remId.value==null ? "" : "checked='checked'"}> <label
					for="remId">REMEMBER ID</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="login" id="login"
					value="LOGIN"> <a href="member_register.jsp"><input
						type="button" name="register" id="register" value="REDGISTER"></a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>