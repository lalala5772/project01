<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
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

    table {
        border-collapse: collapse;
        width: 80%;
        max-width: 900px;
        
        margin: 20px auto;
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    th, td {
        padding: 15px;
        border: 1px solid #ddd;
        text-align: center;
    }

    th {
        background-color: #4CAF50;
        color: white;
        font-size: 1.2em;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    a {
        text-decoration: none;
        color: #4CAF50;
    }

    a:hover {
        text-decoration: underline;
    }

    button {
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 10px 20px;
        cursor: pointer;
        font-size: 1em;
    }

    button:hover {
        background-color: #45a049;
    }
    
    .title{
	width:40%;    
    }
    
    .paging{
    	
   		border:1px solid black;
    	cursor:pointer;
    	
    	display:inline-block;
    	width:20px;
    	height:20px;
    }
</style>
</head>
<body>

<table>
    <tr>
        <th colspan="5">자유게시판</th>
    </tr>
    <tr>
        <td>번호</td>
        <td class="title">제목</td>
        <td>작성자</td>
        <td>날짜</td>
        <td>조회</td>
    </tr>
    <c:forEach var="i" items="${list}">
    <tr>
        <td>${i.seq}</td>
        <td class="title"><a href="detail.board?seq=${i.seq}" name="seq">${i.title}</a></td>
        <td>${i.writer}</td>
        <td>${i.dateWrite}</td>
        <td>${i.viewCount}</td>
    </tr>
    
    
    </c:forEach>
    <tr>
    	<td colspan="5" align="center">
    	<c:if test="${needPrev }">
    		<span class="paging" page="${startNavi - 1 }">< </span> 
    	</c:if>
    	
    	<c:forEach var="i" begin="${startNavi }" end="${endNavi }">
    		<span class="paging" page="${i }">${i }</span>
    	</c:forEach>
    	
    	<c:if test="${needNext }">
    		<span class="paging" page="${endNavi + 1 }"> ></span>
    	</c:if>
    	
    	
    	</td>
    </tr>
    <tr>
        <td colspan="5">
            <a href="/toWrite.board">
                <button>작성하기</button>
            </a>
            <a href="/index.jsp">
				<button>돌아가기</button>            
            </a>
        </td>
    </tr>
</table>
	<script>
		$(".paging").on("click",function(){
			
			let pageNum = $(this).attr("page");
			sessionStorage.setItem("last_cpage",pageNum);
			location.href="/list.board?cpage="+pageNum;
		})
		
	
	</script>


</body>