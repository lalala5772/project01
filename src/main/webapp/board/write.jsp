<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.7.1.js"
  integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
  crossorigin="anonymous"></script>
<style>
  table{
    border: 1px solid black;
    margin: auto;
    width: 700px;
  }

  tr{
    border: 1px solid black;
  }
  td{
    border: 1px solid black;
  }
  .title{
    width: 100%;
  }
  .title input{
    width: 60%;
    
  }

  #text{
    width: 100%;
    height: 300px;

  }
  #text textarea{
    width: 99%;
    height: 95%;
  }


</style>
<body>
  <form action="/add.board" method="post" enctype="multipart/form-data">
<table>
  <tr>
    <th>
      글 작성하기
    </th>
  </tr>
  <tr>
    <td class="title">
      <input type="text" name="title" id="title" placeholder="제목을 입력하세요">
    </td>
  </tr>
  
  <tr>
  <td>
  	<input type="file" name="file1">
  	<input type="file" name="file2">
  </td>
  </tr>
  
  <tr>
    <td id="text">
      <textarea name="contents" id="contents"  placeholder="내용을 입력하세요"></textarea>
    </td>
  </tr>
  <tr>
    <td align="right">
	
		<button id=complete>작성완료</button>
	
      
      <a href="/list.board">
        <button type="button">목록으로</button>
      </a>
      
    </td>
  </tr>

  
</table>

</form>

<script>
	$("#complete").on("click",function(){
		
		if($("#title").val() == ""){
			alert("제목을 작성해주세요");
			return false;
		}
		else if($("#contents").val() == ""){
			alert("내용을 입력해주세요")
			return false;
		}
		
		
	})
</script>

</body>
</html>