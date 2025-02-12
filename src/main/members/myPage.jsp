<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
/* 공통 스타일 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Noto Sans KR', sans-serif;
	
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

.container {
	width: 100%;
	max-width: 900px;
	background-color: #fff;
	border-radius: 16px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
	padding: 30px;
}

.mypage-header {
	text-align: center;
	margin-bottom: 20px;
}

.mypage-header h1 {
	font-size: 28px;
	color: blue;
	font-weight: 600;
}

.mypage-header p {
	font-size: 18px;
	color: #666;
}

/* 프로필 카드 스타일 */
.profile-card {
	padding: 20px;
}

/* 각 정보 필드 스타일 */
.info-group {
	margin-bottom: 15px;
}

.info-label {
	font-size: 16px;
	color: #555;
	margin-bottom: 8px;
	font-weight: 500;
}

.info-value {
	width: 100%;
	padding: 10px;
	font-size: 16px;
	border: 1px solid #ddd;
	border-radius: 8px;
	color: #333;
}

.readonly {
	background-color: #f5f5f5;
	cursor: not-allowed;
}

.address-group {
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.address-group .info-value {
	width: calc(100% - 60px);
	display: inline-block;
}

.address-group .btn-search {
	width: 50px;
	padding: 10px;
	background-color: blue;
	color: #fff;
	border: none;
	border-radius: 8px;
	cursor: pointer;
	transition: all 0.3s ease;
}

.address-group .btn-search:disabled {
	background-color: #ddd;
	cursor: not-allowed;
}

.address-group .btn-search:hover {
	background-color: #f68c9e;
}

/* 버튼 그룹 스타일 */
.btn-group {
	display: flex;
	gap: 12px;
	justify-content: center;
	margin-top: 30px;
}

.btn-group.active {
	display: flex;
}

.btn {
	padding: 12px 20px;
	border-radius: 8px;
	font-size: 16px;
	font-weight: 500;
	cursor: pointer;
	transition: all 0.3s ease;
	
	background-color: #fff;
	color: blue;
	border: 2px solid #1c33b9;
}

.btn:hover {
	background-color: #acb8f8;
}



@media (max-width: 768px) {
	.container {
		width: 90%;
		padding: 20px;
	}

	.address-group {
		flex-direction: column;
	}

	.address-group .info-value {
		width: 100%;
	}

	.btn-group {
		flex-direction: column;
	}
}
</style>
</head>
<body>
	<div class="container">
		<div class="mypage-header">
			<h1>마이페이지</h1>
			<p>${dto.id}님의 회원 정보</p>
		</div>

		<div class="profile-card">
			<form id="modifyForm" action="/modify.members" method="post">
				<!-- 기존 폼 필드들 -->
				<div class="info-group">
					<div class="info-label">아이디</div>
					<input type="text" class="info-value readonly" name="id"
						value="${dto.id}" readonly>
				</div>

				<div class="info-group">
					<div class="info-label">이름</div>
					<input type="text" class="info-value readonly" name="name"
						value="${dto.name}" readonly>
				</div>

				<div class="info-group">
					<div class="info-label">이메일</div>
					<input type="email" class="info-value" name="email"
						value="${dto.email}" >
				</div>

				<div class="info-group">
					<div class="info-label">전화번호</div>
					<input type="tel" class="info-value" name="phone"
						value="${dto.phone}" >
				</div>

				<div class="info-group">
					<div class="info-label">주소</div>
					<div class="address-group">
						<div style="display: flex; gap: 10px;">
							<input type="text" class="info-value" name="postcode"
								value="${dto.postcode}" >
							<button type="button" class="btn-search" id="searchAddr" >주소
								검색</button>
						</div>
						<input type="text" class="info-value" name="address1"
							value="${dto.addr1}" > 
						<input type="text"
							class="info-value" name="address2" value="${dto.addr2}"
							>
					</div>
				</div>

				<div class="info-group">
					<div class="info-label">가입일</div>
					<input type="text" class="info-value readonly"
						readonly>
				</div>

				<!-- 초기 버튼 그룹 -->
				<div class="btn-group active" id="initialButtons">
					<button type="button" class="btn btn-edit" id="editBtn">수정하기</button>
					<button type="button" class="btn btn-cancel"
						onclick="history.back()">뒤로가기</button>
				</div>

				<!-- 수정 모드 버튼 그룹 -->
				<div class="btn-group" id="editButtons">
					<button type="button" class="btn btn-complete" id="completeBtn">수정완료</button>
					<button type="button" class="btn btn-cancel" id="cancelBtn">취소</button>
				</div>
			</form>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			const form = $("#modifyForm");
			const inputs = $(".info-value:not(.readonly)");
			const searchBtn = $("#searchAddr");

			// 수정하기 버튼 클릭
			$("#editBtn").click(function() {
				inputs.prop("readonly", false);
				searchBtn.prop("disabled", false);
				$("#initialButtons").removeClass("active");
				$("#editButtons").addClass("active");
				inputs.first().focus();
			});

			// 취소 버튼 클릭
			$("#cancelBtn").click(function() {
				// form 초기화 (원래 값으로 복구)
				form[0].reset();
				inputs.prop("readonly", true);
				searchBtn.prop("disabled", true);
				$("#editButtons").removeClass("active");
				$("#initialButtons").addClass("active");
			});

			// 수정완료 버튼 클릭
			$("#completeBtn").click(function() {
				if (confirm("수정하신 내용을 저장하시겠습니까?")) {
					// 여기서 removeAttribute 처리를 위해 readonly 속성 제거
					inputs.each(function() {
						$(this).removeAttr("readonly");
					});
					form.submit();
				}
			});

			// 주소 검색
			$("#searchAddr").click(function() {
				new daum.Postcode({
					oncomplete : function(data) {
						$("input[name=postcode]").val(data.zonecode);
						$("input[name=address1]").val(data.address);
						$("input[name=address2]").focus();
					}
				}).open();
			});
		});
	</script>
</body>
</html>
