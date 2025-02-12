<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID 중복 체크 결과</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<style>
body {
	
	font-family: 'Noto Sans KR', sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.result-container {
	background-color: white;
	padding: 30px;
	border-radius: 15px;
	box-shadow: 0 0 15px rgba(255, 182, 193, 0.2);
	text-align: center;
	min-width: 300px;
}

.result-message {
	font-size: 1.2em;
	margin: 20px 0;
	padding: 15px;
	border-radius: 10px;
}

.success-message {
	
	color: #ff8fa3;
	border: 2px solid #ffd1dc;
}

.error-message {
	
	color: #ff6b81;
	border: 2px solid #ffd1dc;
}

.close-button {
	background-color: #ff8fa3;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
	font-size: 1em;
}

.close-button:hover {
	background-color: #ff7088;
}
</style>
</head>
<body>
	<div class="result-container">
		<c:choose>
			<c:when test="${result == true }">
				<div class="result-message error-message">이미 사용중인 아이디 입니다.</div>
				<button id="close" class="close-button">창 닫기</button>
				<script>
					$("#close").on("click", function() {
						opener.document.getElementById("userid").value = "";
						window.close();
					});
				</script>
			</c:when>
			<c:otherwise>
				<div class="result-message success-message">
					사용 가능한 아이디 입니다.
					사용하시겠습니까?
				</div>
				<button id="use" class="close-button">사용</button>
				<button id="cancel" class="close-button">취소</button>
				<script>
					$("#use").on("click", function() {
						window.close();
					});
					$("#cancel").on("click", function() {
						opener.document.getElementById("id").value = ""; 
						window.close();
					});
				</script>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>