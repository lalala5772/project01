<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 탈퇴 확인</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Noto Sans KR', sans-serif;
	background-color: #f8f9ff;
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

.confirm-container {
	width: 90%;
	max-width: 500px;
	background-color: white;
	padding: 40px;
	border-radius: 20px;
	box-shadow: 0 10px 30px rgba(255, 182, 193, 0.1);
	text-align: center;
	animation: fadeIn 0.5s ease;
}

.warning-icon {
	width: 80px;
	height: 80px;
	background-color: #ffe9ed;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0 auto 20px;
}

.warning-icon span {
	color: #ff9eb5;
	font-size: 40px;
}

h1 {
	color: #ff9eb5;
	font-size: 24px;
	margin-bottom: 20px;
}

.message {
	color: #666;
	line-height: 1.6;
	margin-bottom: 30px;
}

.highlight {
	color: #ff7d98;
	font-weight: 600;
}

.button-group {
	display: flex;
	gap: 15px;
	justify-content: center;
}

.btn {
	flex: 1;
	max-width: 200px;
	padding: 15px;
	border-radius: 12px;
	font-size: 15px;
	font-weight: 500;
	cursor: pointer;
	transition: all 0.3s ease;
}

.btn-confirm {
	background-color: #ffd1dc;
	color: white;
	border: none;
}

.btn-confirm:hover {
	background-color: #ffbfcf;
	transform: translateY(-2px);
}

.btn-cancel {
	background-color: white;
	color: #ffd1dc;
	border: 2px solid #ffd1dc;
}

.btn-cancel:hover {
	background-color: #fff5f7;
	transform: translateY(-2px);
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(20px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
@media ( max-width : 576px) {
	.confirm-container {
		padding: 30px 20px;
	}
	.button-group {
		flex-direction: column;
		align-items: center;
	}
	.btn {
		width: 100%;
	}
}
</style>
</head>
<body>
	<div class="confirm-container">
		<div class="warning-icon">
			<span>!</span>
		</div>
		<h1>회원 탈퇴 확인</h1>
		<div class="message">
			<p>
				<span class="highlight">${loginID}</span>님, 정말 탈퇴하시겠습니까?
			</p>
			<p>탈퇴하시면 모든 회원정보가 삭제되며, 복구가 불가능합니다.</p>
		</div>
		<div class="button-group">
			<button type="button" class="btn btn-cancel" id="cancel">취소하기</button>

			<button type="button" class="btn btn-confirm" id="memberout">탈퇴하기</button>
		</div>
	</div>

	<script>
		// 취소하기
		$("#cancel").on("click", function(){
			location.href="/index.jsp";
		});
		//탈퇴하기
		$("#memberout").on("click", function(){
			location.href="/memberout.members";
		});
	</script>
</body>
</html>