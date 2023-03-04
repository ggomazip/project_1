<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="Board.css">
<style type="text/css">
div.write_box {
	border: 1px solid #8fb59d;
	border-radius: 10px;
	width: 500px;
	height: fit-content;
	margin: 0 auto;
	margin-bottom: 5px;
}
div.img_box{
	width:500px;
	height: fit-content;
	display: flex;
	justify-content: center;
	align-items: center;	
}
img#previewImg{
	width: 95%;
	object-fit: contain; 
	object-position: center;
	border-radius: 10px;
	cursor: url("img/inputImg.cur"), auto;
}
textarea.contents{
	display: block;
	margin: 10px auto;
	padding: 5px;
	width: 95%;
	height: 300px;
	resize: none;
	border: 1px solid #8fb59d;
	border-radius: 10px;
}
div.buttons {
	width: 500px;
	margin: 0 auto;
	position: relative;
}
div.id_box{
	width: 100%;
	height: 60px;
	align-content: center;
}
img.profileImg{
	width: 50px;
	height: 50px;
	border-radius: 10px;
}

</style>
</head>
<body>
<%@ include file ='HeaderLayout.jsp' %>
			<form name="boardWrite" action="BoardWrite.do" method="post" enctype="multipart/form-data" onsubmit="return fileCheck()">
				<div class="write_box" >
				<div class="id_box">
					<img class="profileImg" alt="profileImg" src="${dto.profileImgSrc}">
					<span class="id_text">${dto.id}</span>
				</div>
				<label for="inputImg">
				<div class="img_box">
				<img id="previewImg" alt="NONE" src="img/previewImg.png"><br />
				</div>
				</label>
					<textarea class="contents"  name="contents" id="contents" placeholder="사진 첨부 & 내용 작성 필수" ></textarea>
				</div>
				<div class="buttons">
					<input type="submit" value="Upload">					
				</div>
				<input type="file" name="inputImg" id="inputImg" hidden="hidden" value="none"><br />
				<input type="hidden" class="fileValue" value="no">
				
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
	        $('.fileValue').attr('value','yes');
	    }else{
	    	previewImg.src ="img/previewImg.png"
	        $('.fileValue').attr('value','no');
	    }
	}
	// 이벤트 리스너
	document.getElementById('inputImg').addEventListener('change', (e) => {
	    readImg(e.target);
	})
	
	function fileCheck() {
		let fileValue = $('.fileValue').val();
		let contentsValue = $('.contents').val();
		console.log(fileValue);
		console.log(contentsValue);
		if (fileValue=="no" && contentsValue=="" ) {
			alert("사진 첨부 & 내용 작성 필수");
			return false;
		}else if (fileValue=="no" && contentsValue!="") {
			alert("사진 첨부 필수");
			return false;
		}else if (fileValue!="no" && contentsValue=="") {
			alert("내용 작성 필수");
			return false;
		}
		else{
			return true;
		}
	}
	</script>
</body>
</html>