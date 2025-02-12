<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SignUp</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
  <style>
    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
    }
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
    }
    .container {
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        width: 400px;
        padding: 20px;
        margin: 20px;
    }
    .header {
        text-align: center;
        margin-bottom: 400px;
    }
    .header h2 {
        color: #333;
        font-size: 24px;
    }
    fieldset {
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-bottom: 20px;
        padding: 20px;
    }
    legend h4 {
        color: #666;
        font-size: 18px;
    }
    .inputbox {
        width: calc(100% - 22px);
        height: 40px;
        margin-bottom: 15px;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    .inputbox::placeholder {
        color: #aaa;
    }
    .checkId, .find-postcode {
        width: 100%;
        height: 40px;
        margin-bottom: 15px;
        padding: 10px;
        background: #fda085;
        border: none;
        border-radius: 5px;
        color: #fff;
        cursor: pointer;
    }
    .checkId:hover, .find-postcode:hover {
        background: #f6d365;
    }
    .box, .box2, .box3 {
        display: inline-block;
        margin: 10px 5px;
    }
    .box button, .box2 button, .box3 button {
        width: 100px;
        height: 40px;
        padding: 10px;
        background: #fda085;
        border: none;
        border-radius: 5px;
        color: #fff;
        cursor: pointer;
    }
    .box button:hover, .box2 button:hover, .box3 button:hover {
        background: #f6d365;
    }
</style>
</head>
<body>
<div class="container">
    <form action="/add.members" method="post" id="frm">
        <div class="header">
            <h2>회원가입</h2>
        </div>
        <div class="contents">
            <fieldset>
                <legend>
                    <h4>아이디 / 패스워드</h4>
                </legend>
                <input type="text" name="id" placeholder="아이디를 입력하세요" id="id" class="inputbox">
                <div id="idcheckBox"></div>
                <button type="button" class="checkId" id="checkId">ID중복체크</button>
                <input type="password" name="pw" placeholder="패스워드를 입력하세요" id="pw" class="inputbox">
                <input type="password" name="pw2" placeholder="패스워드를 다시 입력하세요" id="repw" class="inputbox">
                <div class="textbox" id="box"></div>
                <p id="pw-text"></p>
                <p id="pw-text2"></p>
            </fieldset>
            <fieldset>
                <legend>
                    <h4>이름 / 전화번호 / 이메일</h4>
                </legend>
                <input type="text" name="name" placeholder="이름을 입력하세요" id="name" class="inputbox">
                <input type="text" name="phone" placeholder="전화번호를 입력하세요" id="phone" class="inputbox">
                <input type="text" name="email" placeholder="이메일을 입력하세요" id="email" class="inputbox">
            </fieldset>
            <fieldset>
                <legend>
                    <h4>주소</h4>
                </legend>
                <input type="text" name="postcode" id="sample4_postcode" placeholder="우편번호" class="inputbox" readonly>
                <button type="button" onclick="sample4_execDaumPostcode()" class="find-postcode">우편번호 찾기</button><br>
                <input type="text" name="addr1" id="sample4_roadAddress" placeholder="도로명주소" class="inputbox">
                <input type="text" name="addr2" id="sample4_jibunAddress" placeholder="지번주소" class="inputbox">
                <input type="text" name="addr3" id="sample4_detailAddress" placeholder="상세주소" class="inputbox">
            </fieldset>
        </div>
        <div class="box">
            <button type="submit" id="join">가입</button>
        </div>
        <div class="box2">
            <button type="reset" id="reset">초기화</button>
        </div>
        <a href="/index.jsp">
            <div class="box3">
                <button type="button">돌아가기</button>
            </div>
        </a>
    </form>
</div>
 <script>

  
  
  	$("#checkId").on("click",function(){
  		if(id.value != ""){
	        let rex = /[a-z0-9_]{8,20}/g;
	        if(rex.test(id.value)==false){
	        		
	        	$("#idcheckBox").html("소문자,숫자,_ 조합으로 8~20자 이하로 작성해주세요");
	          
	          id.focus();
	          return false;
	        }
  		}else if(id.value == ""){
  			$("#idcheckBox").html("아이디를 입력해주세요");
  			id.focus();
  			return false;
  			
  		}
  		$.ajax({
			url:"/idCheck.members?id="+$("#id").val()
			
			
		}).done(function(resp){
			
			//JSON.parse -> 문자열 모습에 어울리는 javascript 타입으로 변환
			resp = JSON.parse(resp);
			
			
			 
			
			if(resp == true){
				$("#idcheckBox").html("중복된 아이디가 있습니다.");
				$("#idcheckBox").css("color","red");
			}
			else{
				$("#idcheckBox").html("아이디 사용 가능합니다.");
				$("#idcheckBox").css("color","blue");
			}
			
		})
      
      
    })

    let frm = document.getElementById("frm");
    frm.onsubmit = function(){

      if(id.value == ""){
        Swal.fire({
          icon: "error",
          title: "아이디는 필수입력입니다.",
          text: "소문자,숫자,_ 조합으로 8~20자 사이로 작성해주세요.",
            });
        id.focus();
        return false;
      }

      if(id.value != ""){
        let rex = /[a-z0-9_]{8,20}/g;
        if(rex.test(id.value)==false){
        		
        
          Swal.fire({
          icon: "error",
          title: "아이디 조건이 맞지 않습니다.",
          text: "소문자,숫자,_ 조합으로 8~20자 이하로 작성해주세요",
            });
          id.focus();
          return false;
        }
        

      }
      
      if(pw.value != ""){
        let rex2 = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/g;
        if(rex2.test(pw.value)==false){
          Swal.fire({
          icon: "error",
          title: "패스워드 조건이 맞지 않습니다.",
          text: "대문자,소문자,숫자가 각각 한글자 이상 포함된 8자이상으로 작성해주세요",
            });
          pw.focus();
          return false;
        }

        if(pw.value == repw.value){
              box.innerHTML="패스워드가 일치합니다.";
          }
        else{
              box.innerHTML="패스워드가 맞지 않습니다.";
              repw.focus();
              return false;
          }
      }



      if(pw.value == ""){
        Swal.fire({
          icon: "error",
          title: "패스워드는 필수입력입니다.",
          text: "대문자,소문자,숫자가 각각 한글자 이상 포함된 8자이상으로 작성해주세요",
            });
        pw.focus();
        return false;
      }

      if(name.value != ""){
        let rex3 = /[가-힣]{2,5}/g;
        if(rex3.test(name.value)==false){
          Swal.fire({
          icon: "error",
          title: "이름의 조건이 맞지 않습니다.",
          text: "한글로 2~5글자 사이로 작성해주세요",
            });
          name.focus();
          return false;
        }
      }

      if(name.value == ""){
        Swal.fire({
          icon: "error",
          title: "이름은 필수입력입니다.",
          text: "한글로 2~5글자 사이로 작성해주세요",
            });
        name.focus();
        return false;
      }

      if(phone.value != ""){
        let rex4 = /^010[ -]?.{4}[ -]?.{4}$/g;
        if(rex4.test(phone.value)==false){
          Swal.fire({
          icon: "error",
          title: "올바른 전화번호를 입력해주세요.",
          text: "예)01012345678, 010-1234-5678, 010 1234 5678",
            });
          phone.focus();
          return false;
        }

      }

      if(email.value != ""){
        let rex5 = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/g;
        if(rex5.test(email.value)==false){
          Swal.fire({
          icon: "error",
          title: "올바른 이메일주소를 작성해주세요.",
          text: "예) Honggildong@naver.com",
            });
          email.focus();
          return false;
        }

      }

      
    

    }
    
    




    let pw = document.getElementById("pw");
    let repw = document.getElementById("repw");
    let box = document.getElementById("box");
    let join = document.getElementById("join");
    let id = document.getElementById("id");
    let name = document.getElementById("name");
    let phone = document.getElementById("phone");
    let email = document.getElementById("email");
    let sample4_postcode = document.getElementById("sample4_postcode");
    let reset = document.getElementById("reset");
    
    





   

     function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                

                
            }
        }).open();
    }

  </script>
  
  
</body>
</html>