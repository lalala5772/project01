<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
/* 기본 스타일 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Noto Sans KR', sans-serif;
	background-color: #f8f9ff;
	line-height: 1.6;
	color: #5a5a5a;
	min-height: 100vh;
	display: flex;
	flex-flow: column;
	justify-content: center;
	align-items: center;
}

/* 컨테이너 스타일 */
.container {
	width: 100%;
	max-width: 500px;
	margin: 0 auto;
	padding: 40px 20px;
	background-color: #ffffff;
	border-radius: 20px;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.03);
	text-align: center;
}

/* 제목 스타일 */
.container h1 {
	color: #50398b;
	font-size: 28px;
	font-weight: 600;
	margin-bottom: 30px;
}

/* 폼 요소 스타일 */
.form-group {
	margin-bottom: 25px;
	text-align: left;
}

.form-group input {
	width: 100%;
	padding: 15px;
	border: 2px solid #969ece;
	border-radius: 12px;
	font-size: 15px;
	transition: all 0.3s ease;
}

.form-group input:focus {
	border-color: #969ece;
	outline: none;
	box-shadow: 0 0 0 4px rgba(255, 209, 220, 0.1);
}

/* 체크박스 스타일 */
.remember-me {
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 20px 0;
	color: #888;
}

.remember-me input[type="checkbox"] {
	margin-right: 8px;
	accent-color: #5c5ae2;
}

/* 버튼 그룹 스타일 */
.btn-group {
	display: flex;
	gap: 12px;
	margin-top: 30px;
}

.btn {
	flex: 1;
	padding: 15px;
	border: none;
	border-radius: 12px;
	cursor: pointer;
	font-size: 15px;
	font-weight: 500;
	transition: all 0.3s ease;
}

.btn-login {
	background-color: #5c5ae2;
	color: white;
	border: none;
}

.btn-login:hover {
	
	transform: translateY(-2px);
}

.btn-register {
	background-color: #5c5ae2;
	color: #ffffff;
	border: 2px solid #5c5ae2;
	display: flex;
	align-items: center;
	justify-content: center;
}

.btn-register:hover {
	
	transform: translateY(-2px);
}

/* 로그인된 상태 메시지 스타일 */
.container>div:first-child {
	font-size: 18px;
	color: #5a5a5a;
	margin-bottom: 20px;
}

/* 반응형 스타일 */
@media ( max-width : 576px) {
	.container {
		padding: 30px 20px;
		margin: 15px;
	}
	.btn-group {
		flex-direction: column;
	}
	.btn {
		width: 100%;
	}
}

/* 추가 애니메이션 */
@
keyframes fadeIn {from { opacity:0;
	transform: translateY(10px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
.container {
	animation: fadeIn 0.8s ease-out;
}

/* placeholder 스타일 */
input::placeholder {
	color: #bbb;
}
</style>
</head>
<body>


	<c:choose>
		<c:when test="${loginID != null}">
			<div class="container">${loginID}님환영합니다.</div>
			<div class="btn-group">
			
				<a href="/list.board?cpage=1">
				<button class="btn btn-login" id="toBoard">회원게시판</button>
				</a>
				
				<a href="/myPage.members">
					<button class="btn btn-login" id="myPage">마이페이지</button>
				</a> 
				
				<a href="/signOut.members">
					<button class="btn btn-login" id="signOut">로그아웃</button>
				</a> 
				
				<a href="/membersOut.members">
					<button class="btn btn-login" id="membersOut">회원탈퇴</button>
				</a>
				
				
			</div>

			

		</c:when>
		<c:otherwise>
			<div class="container">
				<h1>Login Box!</h1>
				<form action="/login.members" method="post">
					<div class="form-group">
						<input type="text" id="inputId" name="id" placeholder="아이디를 입력하세요" required>
					</div>
					<div class="form-group">
						<input type="password" name="pw" placeholder="비밀번호를 입력하세요"
							required>
					</div>
					<div class="remember-me">
						<input type="checkbox" id="remember" name="remember"> <label
							for="remember">아이디를 기억합니다</label>
					</div>
					<div class="btn-group">
						<button type="submit" class="btn btn-login">로그인</button>
						<a href="/signUp.members" class="btn btn-register"
							style="text-decoration: none; text-align: center;">회원가입</a>
					</div>
				</form>
			</div>
		</c:otherwise>
	</c:choose>
	<script>
		$("#remember").on("change",function(){
			$(".btn-login").on("click",function(){
				let rememberId = $("#inputId").val();
				localStorage.setItem("ID",rememberId);
			})
		})
		$("#inputId").val(localStorage.getItem("ID"));
	
	</script>

</body>
</html>