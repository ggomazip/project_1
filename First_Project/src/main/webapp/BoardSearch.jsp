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
<style type="text/css">

table.search_target {
	width: 300px;
	margin: 0 auto;
	font-size: 30px;
	margin-bottom: 20px;
}

td#id_tab {
	width: 150px;
	color: white;
	background: #4f8262;
	border: 1px solid lightgray;
	border-radius: 10px;
	text-align: center;
}

td#hashTag_tab {
	width: 150px;
	color: #4f8262;
	border: 1px solid lightgray;
	border-radius: 10px;
	text-align: center;
}

table#id_list, table#hashTag_list {
	width: 300px;
	margin: 0 auto;
	border: 1px solid #356246;
	border-radius: 5px;
	font-size: 25px;	
}
table#hashTag_list{
 	display: none; 
}

td.profileImg {
	width: 55px;
	height: 55px;
	display: flex;
	justify-content: center;
	align-items: center;
}

td.profileImg a {
	display: block;
	width: 50px;
	height: 50px;
}

td.profileImg img {
	width: 50px;
	height: 50px;
	box-sizing: border-box;
	border: 1px solid #356246;
	border-radius: 5px;
}

td.profileId {
	width: 195px;
}

td.hashTag_text{
	width: 280px;
	font-size: 20px;
}
td.hashTag_count{
	text-align: right;
	width: 20px;
}
div.search_tag, div#list_label{
	width: fit-content;
	margin: 0 auto;
}
td.empty_result{
	font-size: 20px;
}
</style>
</head>
<body>
	<%@ include file='HeaderLayout.jsp'%>
			<div class="search_tag">
				<h1>Search : ${search}</h1>
			</div>
	<table class="search_target">
		<tr>
			<td id="id_tab" onclick="id_tab()">아이디</td>
			<td id="hashTag_tab" onclick="hashTag_tab()">해시태그</td>
		</tr>
	</table>
<div id="list_label"><h1>ID LIST</h1></div>
	<table id="id_list">
		<c:if test="${tap!='#'}">
			<c:forEach var="dto" items="${id_list}">
				<tr>
					<td class="profileImg"><a
						href="member_userProfile.mem?userId=${dto.id}"><img
							alt="profileImg" src="${dto.profileImgSrc}"></a></td>
					<td class="profileId"><a
						href="member_userProfile.mem?userId=${dto.id}">${dto.id}</a></td>
				</tr>
			</c:forEach>
					<c:if test="${id_listSize<1}">
					<tr><td class="empty_result">${search} 검색 결과가 없음</td></tr>
					</c:if>
		</c:if>
		<c:if test="${tap=='#'}"><tr><td class="empty_result">${search} 검색 결과가 없음</td></tr></c:if>
		</table>
	<table id="hashTag_list">
		<c:if test="${tap!='@'}">
			<c:forEach var="dto" items="${hashTag_list}">
				<tr>
					<td class="hashTag_text"><a
						href="BoardSearch.do?hashTag=${dto.hashTag}">#${dto.hashTag}</a></td>
					<td class="hashTag_count">${dto.count}</td>
				</tr>
			</c:forEach>
				<c:if test="${hashTag_listSize<1}">
					<tr><td class="empty_result">${search} 검색 결과가 없음</td></tr>
				</c:if>
		</c:if>
				<c:if test="${tap=='@'}"><tr><td class="empty_result">${search} 검색 결과가 없음</td></tr></c:if>
		</table>
	
	<script type="text/javascript">
		if (${tap!='#'}) {
			id_tab();
		}else{
			hashTag_tab();
		}
	
		function id_tab() {
			document.getElementById("id_tab").style.background = "#4f8262";
			document.getElementById("id_tab").style.color = "white";
			document.getElementById("hashTag_tab").style.background = "white";
			document.getElementById("hashTag_tab").style.color = "#4f8262";
			document.getElementById("id_list").style.display="block";
			document.getElementById("hashTag_list").style.display="none";
			document.getElementById("list_label").innerHTML="<h1>ID LIST</h1>";
		}
		function hashTag_tab() {
			document.getElementById("hashTag_tab").style.background = "#4f8262";
			document.getElementById("hashTag_tab").style.color = "white";
			document.getElementById("id_tab").style.background = "white";
			document.getElementById("id_tab").style.color = "#4f8262";
			document.getElementById("hashTag_list").style.display="block";
			document.getElementById("id_list").style.display="none";
			document.getElementById("list_label").innerHTML="<h1>HASHTAG LIST</h1>";
		}
	</script>

</body>
</html>