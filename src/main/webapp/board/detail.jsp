<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>

<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f9;
    display: flex;
    justify-content: center;
    align-items: center;
    height: auto;
    margin: 0;
}

.container {
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 100%;
    height: auto;
    max-width: 700px;
    margin: 20px;
    background-color: #fff;
    padding: 10px;
}

.title, .footer {
    width: 97%;
    padding: 10px;
    background-color: #4cb04f;
    color: white;
    text-align: center;
}

.footer {
    display: flex;
    justify-content: right;
    align-items: center;
}

.contents {
    width: 95%;
    height: auto;
    min-height:200px;
    padding: 15px;
    background-color: #fff;
    border-top: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
}

.time {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    color: #666;
    font-size: 0.9em;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

button {
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1em;
}

button:hover {
    background-color: #45a049;
}

.replyBox {
    width: 100%;
    height: auto;
    margin-top: 10px;
    margin-bottom: 10px;
    margin-left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0;
}

#replyText {
    margin-left: 0;
    margin-right: 10px;
    margin-left: 0;
    min-width: 85%;
    height: 40px;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

#replyBtn {
    width: 100px;
    height: 70%;
}

.replyCard {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 10px;
    margin-bottom: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: #f9f9f9;
}

.replyAuthor {
    font-weight: bold;
    color: #333;
    margin-bottom: 5px;
}

.replyContent {
    color: #555;
    margin-bottom: 5px;
}

.replyDate {
    font-size: 0.8em;
    color: #999;
    text-align:right;
}

.replyUpdateOK{
 	margin:10px;
}

.replyFooter button{
	width:100px;
	height:40px;
	align-items: center;
}

.file{
	text-align:right;
}
</style>
</head>
<body>
<form action="/update.board" method="post" id="frm"> 
<input type="hidden" name="seq" value="${dto.seq}" /> 
<input type="hidden" name="title" id="hiddenTitle" /> 
<input type="hidden" name="contents" id="hiddenContents" /> 
</form>

<div class="container">
    <div class="title editable">${dto.title}</div>
    <div class="contents editable">${dto.contents}</div>
    
    <br>
    
    <c:forEach var="i" items="${list }">
    <div class="file" multiple>첨부된 파일 :  
    <a href="/download.files?filename=${i.sysName }&oriname=${i.oriName}">${i.oriName }</a>
     </div>
    <br>
    </c:forEach>
    <div class="time">작성자: ${dto.writer} &nbsp;|&nbsp; 작성일: ${dto.writeDate} &nbsp;|&nbsp; 조회수: ${dto.viewCount}</div>

    <p>로그인 ID: ${id}</p>
    <p>작성자: ${dto.writer}</p>

    <c:choose>
        <c:when test="${id eq dto.writer}">
            <div class="footer">
                <button id="update" type="button">수정하기</button>
                <button id="delete">삭제하기</button>
                <button id="toList">목록으로</button>
            </div>
            <script>
            	
                $("#toList").on("click",function(){
                	let last_cpage = sessionStorage.getItem("last_cpage");
                    location.href="/list.board?cpage="+last_cpage;
                });
            
            
                // 삭제 버튼 이벤트
                $("#delete").on("click", function() {
                    let result = confirm("정말 삭제하시겠습니까?");
                    if (result) {
                        location.href = "/delete.board?seq=${dto.seq}";
                    }
                });

                // 수정 버튼 이벤트
                $("#update").on("click", function() {
                    // 편집 가능 상태로 변경
                    $(".editable").attr("contenteditable", "true");
                    $(".editable").focus();

                    // 기존 버튼 숨기기
                    $("#update, #delete, #toList").css("display", "none");

                    // 수정 완료 버튼 생성
                    let updateOK = $("<button>");
                    updateOK.html("수정완료");
                    updateOK.attr("type", "button");
                    updateOK.on("click", function() {
                        // 수정된 내용을 hidden 필드에 설정
                        $("#hiddenTitle").val($(".title").text());
                        $("#hiddenContents").val($(".contents").text());
                        // 폼 제출
                        $("#frm").submit();
                    });

                    // 취소 버튼 생성
                    let updateCancel = $("<button>");
                    updateCancel.html("취소");
                    updateCancel.attr("type", "button");
                    updateCancel.on("click", function() {
                        location.reload(); // 페이지 새로고침
                    });

                    // 버튼 추가
                    $(".footer").append(updateOK);
                    $(".footer").append(updateCancel);
                });
            </script>
        </c:when>
        <c:otherwise>
            <div class="footer">
                <button id="toList">목록으로</button>
                <script>
                    $("#toList").on("click",function(){
                    	let last_cpage = sessionStorage.getItem("last_cpage");
                        location.href="/list.board?cpage="+last_cpage;
                    });
                </script>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="replyBox">
    	<input type="text" name="replyText" placeholder="댓글을 입력하세요" id="replyText">
        
        <button id="replyBtn"> 등록 </button>
    </div>
    
    <script>
        $("#replyBtn").on("click", function() {
            let replyText = $("#replyText").val();
            let seq = ${dto.seq}; // seq 값을 가져옴
            if (replyText.trim() !== "") {
                $.ajax({
                    url: "/register.reply",
                    type: "POST",
                    data: {
                        seq: seq,
                        replyText: replyText
                    },
                    success: function(data) {
                        // 댓글 목록 갱신
                        loadReplies(seq);
                        $("#replyText").val(""); // 텍스트박스 초기화
                    },
                    error: function(error) {
                        console.error("댓글 등록 실패:", error);
                    }
                });
            } else {
                alert("댓글을 입력하세요.");
            }
            location.reload();
        });
    </script>

    <hr>
    
<form action="/update.reply" method="post" id="frm2"> 
        <input type="hidden" name="replyId" id="hiddenReplyId" /> 
        <input type="hidden" name="contents" id="hiddenReplyContents" /> 
        <input type="hidden" name="seq" value="${dto.seq}" /> 
    </form>
  <form action="/delete.reply" method="post" id="frm3"> 
        <input type="hidden" name="replySeq" id="hiddenDeleteReplySeq" />
  </form>

    <div id="replyList">
        <c:forEach var="i" items="${replyList}">
            <div class="replyCard" data-reply-id="${i.id }">
                <div class="replyAuthor">작성자: ${i.writer} and ${i.id}</div>
                <div class="replyContent">내용: <span class="editableReply">${i.contents}</span></div>
                <div class="replyDate">작성일자: ${i.writeDate}</div>
			<hr>
                <c:if test="${id eq i.writer}">
                    <div class="replyFooter" align="right">
                        <button class="updateReply" type="button">수정하기</button>
                        <button class="deleteReply" type="button">삭제하기</button>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <script>
    $(document).ready(function() {
        // 댓글 수정 버튼 이벤트
        $(".updateReply").on("click", function() {
            let replyCard = $(this).closest(".replyCard");
            let replyId = replyCard.data("reply-id");
            let contentSpan = replyCard.find(".editableReply");

            // 이미 편집 모드인지 확인
            if (!contentSpan.attr("contenteditable")) {
                // 편집 가능 상태로 변경
                contentSpan.attr("contenteditable", "true");
                contentSpan.focus();

                // 기존 버튼 숨기기
                $(this).hide();
                replyCard.find(".deleteReply").hide();

                // 수정 완료 및 취소 버튼 생성
                let updateOK = $("<button class='replyUpdateOK'>수정완료</button>");
                let updateCancel = $("<button class='replyUpdateCancel'>취소</button>");

                // 수정 완료 버튼 이벤트
                updateOK.on("click", function() {
                    let updatedContent = contentSpan.text();

                    // hidden 필드에 값 설정
                    $("#hiddenReplyId").val(replyId);
                    $("#hiddenReplyContents").val(updatedContent);

                    // 폼 제출
                    $("#frm2").submit();
                });

                // 취소 버튼 이벤트
                updateCancel.on("click", function() {
                    location.reload(); // 페이지 새로고침
                });

                // 버튼 추가
                replyCard.find(".replyFooter").append(updateOK).append(updateCancel);
            }
        });

        // 댓글 삭제 버튼 이벤트
        $(".deleteReply").on("click", function() {
            let replyId = parseInt($(this).closest(".replyCard").data("reply-id"));
            console.log(replyId);
            let seq = ${dto.seq};
            console.log(seq);
            let result = confirm("정말 삭제하시겠습니까?");
            if (result) {
                $.ajax({
                    url: "/delete.reply",
                    type: "POST",
                    data: {
                        seq: seq,
                        replyId: replyId
                    },
                    success: function(data) {
                    	alert("삭제완료");
                        location.reload(); // 페이지 새로고침
                    },
                    error: function(error) {
                        console.error("댓글 삭제 실패:", error);
                    }
                });
            }
        });
    });
          
            
            
            
       
    </script>

</div>
</body>
</html>
